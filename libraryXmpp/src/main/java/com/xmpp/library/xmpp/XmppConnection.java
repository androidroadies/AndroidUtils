package com.xmpp.library.xmpp;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.xmpp.library.database.DBChat;
import com.xmpp.library.model.ModelChat;
import com.xmpp.library.utils.XmppConstants;
import com.xmpp.library.utils.XmppPreference;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterListener;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.chatstates.ChatState;
import org.jivesoftware.smackx.chatstates.ChatStateListener;
import org.jivesoftware.smackx.chatstates.ChatStateManager;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.jivesoftware.smackx.muc.DiscussionHistory;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.MultiUserChatManager;
import org.jivesoftware.smackx.receipts.DeliveryReceiptManager;
import org.jivesoftware.smackx.receipts.DeliveryReceiptRequest;
import org.jivesoftware.smackx.receipts.ReceiptReceivedListener;
import org.jivesoftware.smackx.search.ReportedData;
import org.jivesoftware.smackx.search.UserSearchManager;
import org.jivesoftware.smackx.xdata.Form;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.xmpp.library.utils.XmppConstants.CHAT_HISTORY;


/**
 * Created by ln-003 on 5/4/17.
 */

public class XmppConnection implements ConnectionListener, RosterListener, ChatManagerListener, ReceiptReceivedListener, MessageListener {

    public static final String TAG = "XmppConnection";
    private static XmppConnection mInstance = null;
    // Multi User Chat
    public MultiUserChat mMultiUserChat;
    private Context mContext;
    private AbstractXMPPConnection mAbstractXMPPConnection;
    private ChatManager mChatManager;
    private Chat mChat;
    private DBChat mDBChat;
    private MultiUserChatManager mMultiUserChatManager;

    /* public static XmppConnection getInstance(Context mContext){
         if(mInstance == null)
         {
             mInstance = new XmppConnection(mContext);
         }
         return mInstance;
     }*/
    private ChatStateListener chatMessageListener = new ChatStateListener() {

        @Override
        public void stateChanged(Chat chat, ChatState state) {
            Log.d(TAG, "stateChanged(): state = [" + state.name() + "]");
            //State Change composing,active,paused,gone,etc
            Intent intent = new Intent(XmppConstants.ACTION.TYPING);
            intent.putExtra(XmppConstants.EXTRA.DATA, state.name());
            intent.putExtra(XmppConstants.CHAT.CHAT_DATA, chat.getParticipant().split("@")[0]);
            LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);

           /* if (true) return;
            else return;*/
        }


        @Override
        public void processMessage(Chat chat, Message message) {
            Log.d(TAG, "processMessage(): message = [" + message + "]");
            //Incoming Message

            String from = message.getFrom().split("/")[1]; // So, we split here by forward slash "/" and take the {userId}@{host}
            Log.e(TAG, "userFrom#" + from);

            if (message.getType().name().equals("chat")) {

                if (message.getBody() != null) {

                    try {

                        JSONObject object = new JSONObject(message.getBody());
                        Log.e("RECEIVED MESSAGE", "object: " + object);

                        //String userTo = object.getString(XmppConstants.CHAT.COL_TO);
                        String userTo = message.getTo().contains("@") ? message.getTo().split("@")[0] : message.getTo();
                        //String userFrom = object.getString(XmppConstants.CHAT.COL_FROM);
                        String userFrom = message.getFrom().contains("@") ? message.getFrom().split("@")[0] : message.getFrom();

                        ModelChat modelChat = new ModelChat();
                        modelChat.setToUser(userTo);
                        modelChat.setFromUser(userFrom);
                        modelChat.setTime(System.currentTimeMillis());
                        modelChat.setType(object.getString(XmppConstants.CHAT.COL_TYPE));
                        modelChat.setValue(object.getString(XmppConstants.CHAT.COL_VALUE));
                        //modelChat.setConversationType(object.getString(XmppConstants.CHAT.COL_CONVERSATION_TYPE));
                        modelChat.setConversationType(XmppConstants.CHAT.CONVERSATION_SINGLE);
                        modelChat.setConversationId(XmppConstants.getConversionId(modelChat.getToUser(), modelChat.getFromUser()));
                        modelChat.setReceiptId("0");
                        modelChat.setStatus(XmppConstants.CHAT.STATUS_DELIVER);
                        modelChat.setThumb(object.getString(XmppConstants.CHAT.COL_THUMB));
                        modelChat.setDuration(object.getString(XmppConstants.CHAT.COL_DURATION));
                        modelChat.setIsFromUser("0");
                        //modelChat.setLatlong(object.getString(XmppConstants.CHAT.COL_LATLNG));
                        //Insert current user message into database here
                        long id = mDBChat.insert(modelChat);
                        Log.e("insert ModelChat", "Rowid: " + id);

                        Intent intent = new Intent(XmppConstants.ACTION.RECEIVE_MESSAGE);
                        intent.putExtra(XmppConstants.EXTRA.DATA, modelChat);
                        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }

        }
    };

    public XmppConnection(Context mContext) {
        this.mContext = mContext;
        mDBChat = new DBChat(mContext);

    }

    public void connect() throws IOException, XMPPException, SmackException, InterruptedException {
        Log.d(TAG, "connect() initialize");

        XMPPTCPConnectionConfiguration xmpptcpConnectionConfiguration = XMPPTCPConnectionConfiguration.builder()
                .setHost(XmppConstants.CONFIGURATION.XMPP_HOST)
                .setServiceName(XmppConstants.CONFIGURATION.XMPP_SERVER_NAME)
                .setPort(XmppConstants.CONFIGURATION.XMPP_PORT)
                .setSecurityMode(XmppConstants.CONFIGURATION.SECURITY_MODE)
                .setDebuggerEnabled(XmppConstants.CONFIGURATION.XMPP_DEBUGGER_ENABLED)
                .build();
        mAbstractXMPPConnection = new XMPPTCPConnection(xmpptcpConnectionConfiguration);
        mAbstractXMPPConnection.addConnectionListener(this);
        mAbstractXMPPConnection.connect();

    }

    public boolean isConnected() {
        return mAbstractXMPPConnection != null && mAbstractXMPPConnection.isConnected();
    }

    public void authenticate(String username, String password) throws IOException, XMPPException, SmackException, InterruptedException {
        Log.e(TAG, "authenticate() called with: username = [" + username + "], password = [" + password + "]");
        if (mAbstractXMPPConnection == null) {
            connect();
        }
        if (!mAbstractXMPPConnection.isAuthenticated()) {
            mAbstractXMPPConnection.login(username, password);
        }

    }

    public boolean isAuthenticated() {
        return mAbstractXMPPConnection != null && mAbstractXMPPConnection.isAuthenticated();
    }

    public void disconnect() throws XMPPException, IOException, SmackException, InterruptedException {
        Log.d(TAG, "disconnect() called");
        if (mAbstractXMPPConnection != null) {
            mAbstractXMPPConnection.disconnect();
        }
    }

    public void deleteAccount() throws SmackException, XMPPException, InterruptedException, IOException {
        if (mAbstractXMPPConnection != null)
            connect();
        AccountManager accountManager = AccountManager.getInstance(mAbstractXMPPConnection);
        accountManager.deleteAccount();
    }

    public void getRosters() throws SmackException.NotLoggedInException, InterruptedException, SmackException.NotConnectedException {
        Roster roster = Roster.getInstanceFor(mAbstractXMPPConnection);
        roster.addRosterListener(this);
        ArrayList<RosterEntryItem> rosterEntryItems = new ArrayList<>();
        for (RosterEntry rosterEntry : roster.getEntries()) {
            Presence presence = roster.getPresence(rosterEntry.getUser());

            rosterEntryItems.add(new RosterEntryItem(rosterEntry, presence));
        }

        Intent intent = new Intent(XmppConstants.ACTION.GET_ROSTERS);
        intent.putExtra(XmppConstants.EXTRA.DATA, rosterEntryItems);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

    public void searchUser(String query) throws SmackException.NotConnectedException, XMPPException.XMPPErrorException, SmackException.NoResponseException {
        UserSearchManager userSearchManager = new UserSearchManager(mAbstractXMPPConnection);
        Form searchForm = userSearchManager.getSearchForm("search." + mAbstractXMPPConnection.getServiceName());
        Form answerForm = searchForm.createAnswerForm();
        answerForm.setAnswer("Name", true);
        answerForm.setAnswer("Username", true);
        answerForm.setAnswer("search", query);

        ReportedData resultData;
        resultData = userSearchManager.getSearchResults(answerForm, "search." + mAbstractXMPPConnection.getServiceName());

        List<ReportedData.Row> searchUser = resultData.getRows();

    }

    public void initiateChat(String jid) {
        Log.d(TAG, "initiateChat() called with: jid = [" + jid + "]");
        if (mChatManager != null) {
            mChat = mChatManager.createChat(jid);
        }

    }

    public void initiateGroupChat(String room, String groupOrUserId) {
        try {
            if (mMultiUserChatManager == null) {
                mMultiUserChatManager = MultiUserChatManager.getInstanceFor(mAbstractXMPPConnection);
            }

            mMultiUserChat = mMultiUserChatManager.getMultiUserChat(getConferenceRoom(room));
            mMultiUserChat.addMessageListener(this);

            DiscussionHistory history = new DiscussionHistory();
            history.setMaxStanzas(CHAT_HISTORY);  //Integer.MAX_VALUE
            mMultiUserChat.join(groupOrUserId, "", history, SmackConfiguration.getDefaultPacketReplyTimeout());

            // mMultiUserChat.join(UserId);
            Log.d(TAG, "initiateGroupChat() called with: room = [" + room + "] " + mMultiUserChat.isJoined());

        } catch (SmackException.NoResponseException e) {
            e.printStackTrace();
        } catch (XMPPException.XMPPErrorException e) {
            e.printStackTrace();
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }

    }

    private String getConferenceRoom(String room) {
        return room + "@" + XmppConstants.CONFIGURATION.XMPP_CONFERENCE_NAME;
    }

    public void sendMessage(ModelChat modelChat) throws SmackException.NotConnectedException {

        try {
            JSONObject jsonObjectChat = new JSONObject();
            jsonObjectChat.put(XmppConstants.CHAT.COL_VALUE, modelChat.getValue());
            //jsonObjectChat.put(XmppConstants.CHAT.COL_TIMESTAMP, modelChat.getTime());
            jsonObjectChat.put(XmppConstants.CHAT.COL_TO, modelChat.getToUser());
            jsonObjectChat.put(XmppConstants.CHAT.STORE_ID, modelChat.getStoreID());
            jsonObjectChat.put(XmppConstants.CHAT.COL_TYPE, modelChat.getType());
            jsonObjectChat.put(XmppConstants.CHAT.COL_THUMB, modelChat.getThumb() == null ? "" : modelChat.getThumb());
            jsonObjectChat.put(XmppConstants.CHAT.COL_DURATION, modelChat.getDuration() == null ? "" : modelChat.getDuration());
            //jsonObjectChat.put(XmppConstants.CHAT.COL_CONVERSATION_TYPE, modelChat.getConversationType());
            //jsonObjectChat.put(XmppConstants.CHAT.COL_LATLNG, modelChat.getLatlong() == null ? "" : modelChat.getLatlong());

            Message msg;

            if (modelChat.getConversationType().equals(XmppConstants.CHAT.CONVERSATION_SINGLE)) {
                msg = new Message();
                msg.setBody(jsonObjectChat.toString());
                mChat.sendMessage(msg);
            } else {
                msg = new Message(modelChat.getFromUser(), Message.Type.groupchat);
                msg.setBody(jsonObjectChat.toString());
                msg.setFrom(modelChat.getToUser());
                mMultiUserChat.sendMessage(msg);
            }

            DeliveryReceiptManager.receiptMessageFor(msg);
            String deliveryReceiptId = DeliveryReceiptRequest.addTo(msg);
            Log.e("delivery object", jsonObjectChat.toString());
            Log.e("delivery ReceiptId", deliveryReceiptId);

            modelChat.setReceiptId(deliveryReceiptId);
            modelChat.setStatus(XmppConstants.CHAT.STATUS_SENT);
            modelChat.setIsFromUser("1");
            long id;
            //Insert current user message into database here
            if (modelChat.getConversationType().equals(XmppConstants.CHAT.CONVERSATION_SINGLE)) {
                id = mDBChat.insert(modelChat);
                //Log.e("insert ModelChat", "Rowid: " + id);

            } else {
                id = mDBChat.insertGroupData(modelChat);
            }
            Log.e("insert ModelChat", "Rowid: " + id);

            Intent intent = new Intent(XmppConstants.ACTION.UPDATE);
            LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendChatStatus(ChatState chatState) {
        Log.d(TAG, "sendChatStatus() called with: chatState = [" + chatState + "]");
        try {
            if (mChat != null) {
                ChatStateManager.getInstance(mAbstractXMPPConnection).setCurrentState(chatState, mChat);
            }
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }

    }


    /*
   ConnectionListener method
    */

    @Override
    public void connected(XMPPConnection connection) {
        Log.d(TAG, "Connected Successfully");
        Intent intent = new Intent(XmppConstants.ACTION.CONNECT);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);

        if (!XmppPreference.getInstance(mContext).getUserId().equals("")) {
            String username = XmppPreference.getInstance(mContext).getUserId();
            String password = XmppPreference.getInstance(mContext).getPassword();
            if (!mAbstractXMPPConnection.isAuthenticated()) {
                try {
                    /*SASLAuthentication.unBlacklistSASLMechanism("PLAIN");
                    SASLAuthentication.blacklistSASLMechanism("DIGEST-MD5");*/
                    mAbstractXMPPConnection.login(username, password);
                } catch (XMPPException e) {
                    e.printStackTrace();
                } catch (SmackException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Intent intentAuth = new Intent(XmppConstants.ACTION.AUTHENTICATE);
                LocalBroadcastManager.getInstance(mContext).sendBroadcast(intentAuth);
            }
        }
    }

    @Override
    public void authenticated(XMPPConnection connection, boolean resumed) {
        Log.d(TAG, "Authenticated Successfully");
        Intent intent = new Intent(XmppConstants.ACTION.AUTHENTICATE);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);

        mChatManager = ChatManager.getInstanceFor(mAbstractXMPPConnection);
        mChatManager.addChatListener(this);
        ChatStateManager.getInstance(mAbstractXMPPConnection);

        //Group Chat
        mMultiUserChatManager = MultiUserChatManager.getInstanceFor(mAbstractXMPPConnection);

        DeliveryReceiptManager dm = DeliveryReceiptManager.getInstanceFor(mAbstractXMPPConnection);
        dm.setAutoReceiptMode(DeliveryReceiptManager.AutoReceiptMode.always);
        dm.autoAddDeliveryReceiptRequests();
        dm.addReceiptReceivedListener(this);
    }

    @Override
    public void connectionClosed() {
        Log.d(TAG, "connectionClosed() called");
        Intent intent = new Intent(XmppConstants.ACTION.DISCONNECT);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

    @Override
    public void connectionClosedOnError(Exception e) {
        Log.d(TAG, "connectionClosedOnError() called with: e = [" + e + "]");
        /*if(mAbstractXMPPConnection != null){
            mAbstractXMPPConnection.disconnect();
        }*/

        Intent intent = new Intent(XmppConstants.ACTION.DISCONNECT);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

    @Override
    public void reconnectionSuccessful() {
        Log.d(TAG, "reconnectionSuccessful() called");

    }

    @Override
    public void reconnectingIn(int seconds) {
        Log.d(TAG, "reconnectingIn(): seconds = [" + seconds + "]");

    }

    @Override
    public void reconnectionFailed(Exception e) {
        Log.d(TAG, "reconnectionFailed(): e = [" + e + "]");
        Intent intent = new Intent(XmppConstants.ACTION.DISCONNECT);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

    /*
     Roster: addRosterListener
     */
    @Override
    public void entriesAdded(Collection<String> addresses) {
        Log.d(TAG, "entriesAdded(): addresses = [" + addresses + "]");
    }

    @Override
    public void entriesUpdated(Collection<String> addresses) {
        Log.d(TAG, "entriesUpdated(): addresses = [" + addresses + "]");
    }

    @Override
    public void entriesDeleted(Collection<String> addresses) {
        Log.d(TAG, "entriesDeleted(): addresses = [" + addresses + "]");
    }

    @Override
    public void presenceChanged(Presence presence) {
        Log.d(TAG, "presenceChanged(): presence = [" + presence + "]");
        try {
            getRosters();
        } catch (SmackException.NotLoggedInException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
    }

    /*
     CHAT Create Initialize
     */

    @Override
    public void chatCreated(Chat chat, boolean createdLocally) {
        Log.d(TAG, "chatCreated(): chat = [" + chat + "], createdLocally = [" + createdLocally + "]");
        Intent intent = new Intent(XmppConstants.ACTION.INITIATE_CHAT);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);

       /* if (!createdLocally)
            chat.addMessageListener(this);*/

        chat.addMessageListener(chatMessageListener);

    }

    /*
        Received received listener
     */

    @Override
    public void onReceiptReceived(String fromJid, String toJid, String receiptId, Stanza receipt) {
        Log.d(TAG, "onReceiptReceived(): fromJid = [" + fromJid + "], toJid = [" + toJid + "], receiptId = [" + receiptId + "]");
        mDBChat.updateReceiptStatus(receiptId, XmppConstants.CHAT.STATUS_DELIVER);

        Intent intent = new Intent(XmppConstants.ACTION.UPDATE);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);


    }

    // GroupChat message listener
    @Override
    public void processMessage(Message message) {
        Log.d(TAG, "processMessage(): message = [" + message + "]");
        //Incoming Message

        if (message.getType().name().equals("groupchat")) {

            if (message.getBody() != null) {

                try {

                    Log.e("FORM-->", message.getFrom());
                    String from = message.getFrom().split("/")[1]; // So, we split here by forward slash "/" and take the {userId}@{host}
                    Log.e(TAG, "userFrom#" + from);

                    String conversationId = message.getFrom().split("/")[0];
                    JSONObject object = new JSONObject(message.getBody());
                    Log.e(TAG, "object: " + object);

                    //String userTo = object.getString(XmppConstants.CHAT.COL_TO);
                    String userTo = message.getTo().contains("@") ? message.getTo().split("@")[0] : message.getTo();
                    Log.e(TAG, "userTo: " + userTo);
                    if (!from.equals(XmppPreference.getInstance(mContext).getUserId())) {

                        //String userFrom = object.getString(XmppConstants.CHAT.COL_FROM);
                        //String userFrom = message.getFrom().contains("@") ? message.getFrom().split("@")[0] : message.getFrom();
                        Log.e(TAG, "userFrom: " + from);

                        ModelChat modelChat = new ModelChat();
                        modelChat.setToUser(userTo);
                        modelChat.setFromUser(from);
                        //modelChat.setTime(object.getLong(XmppConstants.CHAT.COL_TIMESTAMP));
                        modelChat.setTime(System.currentTimeMillis());
                        modelChat.setType(object.getString(XmppConstants.CHAT.COL_TYPE));
                        modelChat.setValue(object.getString(XmppConstants.CHAT.COL_VALUE));
                        modelChat.setStoreID(object.getString(XmppConstants.CHAT.STORE_ID));
                        modelChat.setConversationType(XmppConstants.CHAT.CONVERSATION_GROUP);
                        modelChat.setConversationId(conversationId);
                        modelChat.setReceiptId("0");
                        modelChat.setStatus(XmppConstants.CHAT.STATUS_DELIVER);
                        modelChat.setThumb(object.getString(XmppConstants.CHAT.COL_THUMB));
                        modelChat.setDuration(object.getString(XmppConstants.CHAT.COL_DURATION));
                        //modelChat.setIsFromUser(userTo.equals(XmppPreference.getInstance(mContext).getUserId())?"1":"0");
                        modelChat.setIsFromUser("0");
                        //modelChat.setLatlong(object.getString(XmppConstants.CHAT.COL_LATLNG));
                        //Insert current user message into database here
                        long id = mDBChat.insertGroupData(modelChat);
                        Log.e("insert ModelChat", "Rowid: " + id);

                        Intent intent = new Intent(XmppConstants.ACTION.RECEIVE_MESSAGE);
                        intent.putExtra(XmppConstants.EXTRA.DATA, modelChat);
                        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }
    }
}
