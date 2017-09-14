package com.xmpp.library.utils;

import org.jivesoftware.smack.ConnectionConfiguration;

import static com.xmpp.library.utils.XmppConstants.CONFIGURATION.XMPP_CONFERENCE_NAME;

/**
 * Created by ln-141 on 7/5/16.
 */
public class XmppConstants {

    public static final int CHAT_HISTORY = 0; //Integer.MAX_VALUE

    public static String getConversionId(String toUserId, String fromUserId) {
        return toUserId + "@" + fromUserId;
    }

    public static String getJabberId(String userId) {
        return userId + "@" + CONFIGURATION.XMPP_SERVER_NAME;
    }

    public static String getGroupID(String uid) {
        return uid + "@" + XMPP_CONFERENCE_NAME;
    }

    public interface CONFIGURATION {
        String XMPP_HOST = "";
        String XMPP_SERVER_NAME = "";
        String XMPP_CONFERENCE_NAME = "";
        int XMPP_PORT = 5222;
        boolean XMPP_DEBUGGER_ENABLED = true;
        ConnectionConfiguration.SecurityMode SECURITY_MODE = ConnectionConfiguration.SecurityMode.disabled;
        String XMPP_USER_PASSWORD = "123456";
    }

    public interface ACTION {
        String CONNECT = "Connect";
        String AUTHENTICATE = "Authenticate";
        String AUTHENTICATE_FAILED = "AuthenticateFailed";
        String DISCONNECT = "Disconnect";
        String GET_ROSTERS = "GetRosters";
        String SEARCH_USER = "SearchUser";
        String SEND_MESSAGE = "SendMessage";
        String INITIATE_CHAT = "InitiateChat";
        String RECEIVE_MESSAGE = "ReceiveMessage";
        String TYPING = "Typing";
        String UPDATE = "DateUpdate";

    }

    public interface EXTRA {
        String USERNAME = "Username";
        String PASSWORD = "Password";
        String DATA = "Data";
        String USER_ID = "user_id";
    }

    public interface USER {
        String USER_ID = "UserId";
        String PASSWORD = "Password";
    }

    public interface CHAT {
        String CHAT_DATA = "ChatData";
        String TABLE_CHAT = "chat";
        String TABLE_GROUP_CHAT = "group_chat";
        String COL_ID = "_id";
        String COL_CONVERSATION_ID = "conversationId";
        String COL_CONVERSATION_TYPE = "conversationType";
        String COL_TO = "toUser";
        String COL_FROM = "fromUser";
        String COL_TYPE = "type";
        String COL_VALUE = "value";
        String COL_LOCAL_PATH = "localPath";
        String COL_RECEIPT_ID = "receiptId";
        String COL_TIMESTAMP = "time";
        String COL_STATUS = "status";
        String COL_THUMB = "thumb";
        String COL_DURATION = "duration";
        String COL_USER_NAME = "username";
        String COL_LATLNG = "latlng";
        String COL_IS_FROM = "is_from_user";
        String STORE_ID = "storeID";

        String CONVERSATION_SINGLE = "Single";
        String CONVERSATION_GROUP = "Group";
        String STATUS_PENDING = "Pending"; //"Sent";
        String STATUS_SENT = "Sent"; //"SentToServer";
        String STATUS_DELIVER = "Delivered"; //"ReceiveToUser";

    }

    public interface CHAT_TYPE {
        String CHAT_TEXT = "message";
        String CHAT_IMAGE = "image";
        String CHAT_AUDIO = "audio";
        String CHAT_VIDEO = "video";
        String CHAT_LOCATION = "location";
        String CHAT_STICKER = "sticker";
        String CHAT_TEXT_IMAGE = "textImage";
        String CHAT_TEXT_VIDEO = "textVideo";

    }
}
