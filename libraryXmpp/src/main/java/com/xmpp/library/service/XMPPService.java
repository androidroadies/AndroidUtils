package com.xmpp.library.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.xmpp.library.model.ModelChat;
import com.xmpp.library.utils.XmppConstants;
import com.xmpp.library.xmpp.XmppConnection;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.chatstates.ChatState;

import java.io.IOException;

/**
 * MAKE XMPP CONNECTION ALWAYS AND RECEIVE ALL THE LISTENER FROM XMPP USING THIS SERVICE
 *
 */

public class XMPPService extends Service {

    public static final String TAG = "XMPPService";


    private XmppConnection mXmppConnection;
    private Context mContext;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind() called");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate() called");
        mContext = this;
        mXmppConnection = new XmppConnection(mContext);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand() called");

        String action;

        if (intent == null) {
            xmppConnect();
        }
        else{
            action = intent.getAction();
            if (action.equals(XmppConstants.ACTION.CONNECT)) {
                xmppConnect();
            } else if (action.equals(XmppConstants.ACTION.AUTHENTICATE)) {
                String username = intent.getStringExtra(XmppConstants.EXTRA.USERNAME);
                String password = intent.getStringExtra(XmppConstants.EXTRA.PASSWORD);
                authenticate(username, password);
            } else if (action.equals(XmppConstants.ACTION.GET_ROSTERS)) {
                getRosters();
            } else if(action.equals(XmppConstants.ACTION.SEARCH_USER)){
                String query = intent.getStringExtra(XmppConstants.EXTRA.DATA);
                searchUser(query);
            } else if(action.equals(XmppConstants.ACTION.INITIATE_CHAT)){
                String groupOrUserId = intent.getStringExtra(XmppConstants.EXTRA.DATA);

                if(intent.getStringExtra(XmppConstants.CHAT.COL_CONVERSATION_TYPE).equals(XmppConstants.CHAT.CONVERSATION_SINGLE)) {
                    initializeChat(groupOrUserId);
                }
                else{
                    String userId=intent.getStringExtra(XmppConstants.EXTRA.USER_ID);
                   initiateGroupChat(groupOrUserId,userId);
                }

            }  else if(action.equals(XmppConstants.ACTION.SEND_MESSAGE)){
                ModelChat modelChat = (ModelChat) intent.getSerializableExtra(XmppConstants.EXTRA.DATA);
                sendMessage(modelChat);
            } else if(action.equals(XmppConstants.ACTION.TYPING)){
                ChatState chatState = ChatState.valueOf(intent.getStringExtra(XmppConstants.EXTRA.DATA));
                mXmppConnection.sendChatStatus(chatState);
            }
        }

        return START_STICKY;
    }

    private void sendMessage(ModelChat modelChat) {
        try {
            mXmppConnection.sendMessage(modelChat);
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
    }

    private void initializeChat(String jabberId) {
        mXmppConnection.initiateChat(jabberId);
    }
    private void initiateGroupChat(String jabberId,String groupOrUserId)
    {
        Log.e("Room User:",groupOrUserId);
        mXmppConnection.initiateGroupChat(jabberId,groupOrUserId);
    }

    public static Intent getConnectIntent(Context context) {
        Intent intent = new Intent(context, XMPPService.class);
        intent.setAction(XmppConstants.ACTION.CONNECT);
        return intent;
    }

    private void xmppConnect() {
        Log.d(TAG, "connect() called "+mXmppConnection.isConnected());
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    if (mXmppConnection.isConnected()) {
                        Intent intent = new Intent(XmppConstants.ACTION.CONNECT);
                       // LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
                    } else {
                        mXmppConnection.connect();
                    }

                } catch (IOException | XMPPException | SmackException | InterruptedException e) {
                    e.printStackTrace();
                    Intent intent = new Intent(XmppConstants.ACTION.AUTHENTICATE);
                    LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(intent);
                }
                return null;
            }
        }.execute();
    }

    public static Intent getAuthenticationIntent(Context context, String username, String password) {
        Intent intent = new Intent(context, XMPPService.class);
        intent.setAction(XmppConstants.ACTION.AUTHENTICATE);
        intent.putExtra(XmppConstants.EXTRA.USERNAME, username);
        intent.putExtra(XmppConstants.EXTRA.PASSWORD, password);
        return intent;
    }

    private void authenticate(String username, String password) {
        new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... params) {
                try {
                    mXmppConnection.authenticate(params[0], params[1]);
                } catch (IOException | XMPPException | SmackException | InterruptedException e) {
                    Intent intent = new Intent(XmppConstants.ACTION.AUTHENTICATE_FAILED);
                    LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(intent);
                    e.printStackTrace();
                    try {
                        //mXmppConnection.deleteAccount();
                        mXmppConnection.disconnect();
                    } catch (XMPPException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (SmackException e1) {
                        e1.printStackTrace();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }

                }
                return null;
            }
        }.execute(username, password);
    }

    public static Intent getRosterIntent(Context context) {
        Intent intent = new Intent(context, XMPPService.class);
        intent.setAction(XmppConstants.ACTION.GET_ROSTERS);
        return intent;
    }

    public void getRosters() {

        try {
            mXmppConnection.getRosters();

        } catch (SmackException.NotLoggedInException
                | InterruptedException
                | SmackException.NotConnectedException e) {
            e.printStackTrace();
        }

    }

    public static Intent getSearchUserIntent(Context context, String query) {
        Intent intent = new Intent(context, XMPPService.class);
        intent.setAction(XmppConstants.ACTION.SEARCH_USER);
        intent.putExtra(XmppConstants.EXTRA.DATA, query);
        return intent;
    }

    public void searchUser(String query) {
        try {
            mXmppConnection.searchUser(query);
                   /* Intent intent = new Intent(XmppConstants.ACTION.SEARCH_USER);
                    intent.putExtra(XmppConstants.EXTRA.DATA, (Parcelable) mXmppConnection.searchUser(params[0]));
                    LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(intent);
*/
        } catch (SmackException.NotConnectedException
                | XMPPException.XMPPErrorException
                | SmackException.NoResponseException e) {
            e.printStackTrace();
        }
    }


    public static Intent getMessageIntent(Context context, ModelChat modelChat) {
        Intent intent = new Intent(context, XMPPService.class);
        intent.setAction(XmppConstants.ACTION.SEND_MESSAGE);
        intent.putExtra(XmppConstants.EXTRA.DATA, modelChat);
        return intent;
    }

    public static Intent getIntializeChatIntent(Context context, String jid,String conversationType,String userId) {
        Intent intent = new Intent(context, XMPPService.class);
        intent.setAction(XmppConstants.ACTION.INITIATE_CHAT);
        intent.putExtra(XmppConstants.EXTRA.DATA, jid);
        intent.putExtra(XmppConstants.EXTRA.USER_ID,userId);
        intent.putExtra(XmppConstants.CHAT.COL_CONVERSATION_TYPE, conversationType);
        return intent;
    }

    public static Intent getTypingIntent(Context context, ChatState chatState) {
        Intent intent = new Intent(context, XMPPService.class);
        intent.setAction(XmppConstants.ACTION.TYPING);
        intent.putExtra(XmppConstants.EXTRA.DATA, chatState.name());
        return intent;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");

        if(mXmppConnection != null){

            try {
                // mXmpp.deleteAccount();
                mXmppConnection.disconnect();
            } catch (XMPPException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (SmackException e1) {
                e1.printStackTrace();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }
}
