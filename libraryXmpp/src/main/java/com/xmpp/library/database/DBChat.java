package com.xmpp.library.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.xmpp.library.model.ModelChat;
import com.xmpp.library.utils.XmppConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ln- on 11/8/16.
 */
public class DBChat implements DBOperation {

    private static final String TAG = "DBChat";

    private SQLiteDatabase database;

    public DBChat(Context context) {
        DatabaseHelper databasehelper = DatabaseHelper.getInstance(context);
        database = databasehelper.getWritableDatabase();
    }

    @Override
    public long insert(Object object) {
        ModelChat modelChat = (ModelChat) object;
        Log.d(TAG, "insert() called with: object = [" + modelChat + "]");

        try {
            ContentValues values = new ContentValues();
            values.put(XmppConstants.CHAT.COL_CONVERSATION_ID, modelChat.getConversationId());
            values.put(XmppConstants.CHAT.COL_CONVERSATION_TYPE, modelChat.getConversationType());
            values.put(XmppConstants.CHAT.COL_RECEIPT_ID, modelChat.getReceiptId());
            values.put(XmppConstants.CHAT.COL_TO, modelChat.getToUser());
            values.put(XmppConstants.CHAT.COL_FROM, modelChat.getFromUser());
            values.put(XmppConstants.CHAT.COL_TYPE, modelChat.getType());
            values.put(XmppConstants.CHAT.COL_VALUE, modelChat.getValue());
            values.put(XmppConstants.CHAT.STORE_ID, modelChat.getStoreID());
            values.put(XmppConstants.CHAT.COL_STATUS, modelChat.getStatus());
            values.put(XmppConstants.CHAT.COL_TIMESTAMP, modelChat.getTime());
            values.put(XmppConstants.CHAT.COL_THUMB, modelChat.getThumb());
            values.put(XmppConstants.CHAT.COL_DURATION, modelChat.getDuration());
            values.put(XmppConstants.CHAT.COL_USER_NAME, modelChat.getUserName());
            values.put(XmppConstants.CHAT.COL_IS_FROM, modelChat.getIsFromUser());
            values.put(XmppConstants.CHAT.COL_LATLNG, modelChat.getLatlong() == null ? "" : modelChat.getLatlong());
            Log.e("insert ModelChat", "values: " + values);
            return database.insert(XmppConstants.CHAT.TABLE_CHAT, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int update(Object object) {
        return 0;
    }

    @Override
    public int delete(Object object) {
        return 0;
    }

    @Override
    public List getAll() {
        return null;
    }

    public ArrayList<ModelChat> getByConversationId(String currentUserId, String oppositeUserId, String CONVERSATION_TYPE) {
        ArrayList<ModelChat> modelChatArrayList = new ArrayList<>();

        String query = "Select * from " + XmppConstants.CHAT.TABLE_CHAT +
                " where " + XmppConstants.CHAT.COL_CONVERSATION_ID + "='" + XmppConstants.getConversionId(currentUserId, oppositeUserId) +
                "' OR " + XmppConstants.CHAT.COL_CONVERSATION_ID + "='" + XmppConstants.getConversionId(oppositeUserId, currentUserId) + "'" +
                " AND " + XmppConstants.CHAT.COL_CONVERSATION_TYPE + "='" + CONVERSATION_TYPE + "'" +
                "";

        Log.e("Query", query);

        Cursor cursor = database.rawQuery(query, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    ModelChat modelChat = new ModelChat();
                    modelChat.setId(cursor.getInt(cursor.getColumnIndex(XmppConstants.CHAT.COL_ID)));
                    modelChat.setConversationId(cursor.getString(cursor.getColumnIndex(XmppConstants.CHAT.COL_CONVERSATION_ID)));
                    modelChat.setConversationType(cursor.getString(cursor.getColumnIndex(XmppConstants.CHAT.COL_CONVERSATION_TYPE)));
                    modelChat.setReceiptId(cursor.getString(cursor.getColumnIndex(XmppConstants.CHAT.COL_RECEIPT_ID)));
                    modelChat.setToUser(cursor.getString(cursor.getColumnIndex(XmppConstants.CHAT.COL_TO)));
                    modelChat.setFromUser(cursor.getString(cursor.getColumnIndex(XmppConstants.CHAT.COL_FROM)));
                    modelChat.setValue(cursor.getString(cursor.getColumnIndex(XmppConstants.CHAT.COL_VALUE)));
                    modelChat.setStoreID(cursor.getString(cursor.getColumnIndex(XmppConstants.CHAT.STORE_ID)));
                    modelChat.setType(cursor.getString(cursor.getColumnIndex(XmppConstants.CHAT.COL_TYPE)));
                    modelChat.setStatus(cursor.getString(cursor.getColumnIndex(XmppConstants.CHAT.COL_STATUS)));
                    modelChat.setThumb(cursor.getString(cursor.getColumnIndex(XmppConstants.CHAT.COL_THUMB)));
                    modelChat.setDuration(cursor.getString(cursor.getColumnIndex(XmppConstants.CHAT.COL_DURATION)));
                    modelChat.setUserName(cursor.getString(cursor.getColumnIndex(XmppConstants.CHAT.COL_USER_NAME)));
                    modelChat.setLatlong(cursor.getString(cursor.getColumnIndex(XmppConstants.CHAT.COL_LATLNG)));
                    modelChat.setTime(Long.parseLong(cursor.getString(cursor.getColumnIndex(XmppConstants.CHAT.COL_TIMESTAMP))));
                    //modelChat.setSender(modelChat.getToUser().equals(currentUserId));
                    modelChat.setSender(cursor.getString(cursor.getColumnIndex(XmppConstants.CHAT.COL_IS_FROM)).equals("1"));
                    modelChatArrayList.add(modelChat);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return modelChatArrayList;
    }

    public ArrayList<ModelChat> getGroupHistory(String currentUserId, String groupID, String CONVERSATION_TYPE) {
        ArrayList<ModelChat> modelChatArrayList = new ArrayList<>();

        String query = "Select * from " + XmppConstants.CHAT.TABLE_GROUP_CHAT +
                " where " + XmppConstants.CHAT.COL_CONVERSATION_ID + "='" + XmppConstants.getGroupID(groupID) +
                "'";
        /*String query = "Select * from " + XmppConstants.CHAT.TABLE_GROUP_CHAT +
                " where " + XmppConstants.CHAT.COL_CONVERSATION_ID + "='" + XmppConstants.getConversionId(currentUserId, groupID) +
                "' OR " + XmppConstants.CHAT.COL_CONVERSATION_ID + "='" + XmppConstants.getConversionId(groupID, currentUserId) + "'" +
                " AND " + XmppConstants.CHAT.COL_CONVERSATION_TYPE + "='" + CONVERSATION_TYPE + "'" +
                "";*/


        Log.e("Query", query);

        Cursor cursor = database.rawQuery(query, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    ModelChat modelChat = new ModelChat();
                    modelChat.setId(cursor.getInt(cursor.getColumnIndex(XmppConstants.CHAT.COL_ID)));
                    modelChat.setConversationId(cursor.getString(cursor.getColumnIndex(XmppConstants.CHAT.COL_CONVERSATION_ID)));
                    modelChat.setConversationType(cursor.getString(cursor.getColumnIndex(XmppConstants.CHAT.COL_CONVERSATION_TYPE)));
                    modelChat.setReceiptId(cursor.getString(cursor.getColumnIndex(XmppConstants.CHAT.COL_RECEIPT_ID)));
                    modelChat.setToUser(cursor.getString(cursor.getColumnIndex(XmppConstants.CHAT.COL_TO)));
                    modelChat.setFromUser(cursor.getString(cursor.getColumnIndex(XmppConstants.CHAT.COL_FROM)));
                    modelChat.setValue(cursor.getString(cursor.getColumnIndex(XmppConstants.CHAT.COL_VALUE)));
                    modelChat.setStoreID(cursor.getString(cursor.getColumnIndex(XmppConstants.CHAT.STORE_ID)));
                    modelChat.setType(cursor.getString(cursor.getColumnIndex(XmppConstants.CHAT.COL_TYPE)));
                    modelChat.setStatus(cursor.getString(cursor.getColumnIndex(XmppConstants.CHAT.COL_STATUS)));
                    modelChat.setThumb(cursor.getString(cursor.getColumnIndex(XmppConstants.CHAT.COL_THUMB)));
                    modelChat.setDuration(cursor.getString(cursor.getColumnIndex(XmppConstants.CHAT.COL_DURATION)));
                    modelChat.setUserName(cursor.getString(cursor.getColumnIndex(XmppConstants.CHAT.COL_USER_NAME)));
                    modelChat.setLatlong(cursor.getString(cursor.getColumnIndex(XmppConstants.CHAT.COL_LATLNG)));
                    modelChat.setTime(Long.parseLong(cursor.getString(cursor.getColumnIndex(XmppConstants.CHAT.COL_TIMESTAMP))));
                    modelChat.setSender(cursor.getString(cursor.getColumnIndex(XmppConstants.CHAT.COL_IS_FROM)).equals("1"));
                    //modelChat.setSender(modelChat.getToUser().equals(currentUserId));
                    modelChatArrayList.add(modelChat);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return modelChatArrayList;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public ContentValues getContentValues(Object object) {
        return null;
    }

    public void updateReceiptStatus(String receiptId, String status) {

        ContentValues values = new ContentValues();
        values.put(XmppConstants.CHAT.COL_STATUS, status);

        // updating row
        database.update(XmppConstants.CHAT.TABLE_CHAT, values, XmppConstants.CHAT.COL_RECEIPT_ID + " = ?",
                new String[]{receiptId});

    }

    public long insertGroupData(Object object) {
        ModelChat modelChat = (ModelChat) object;
        Log.d(TAG, "insert() called with: object = [" + modelChat + "]");

        try {
            ContentValues values = new ContentValues();
            values.put(XmppConstants.CHAT.COL_CONVERSATION_ID, modelChat.getConversationId());
            values.put(XmppConstants.CHAT.COL_CONVERSATION_TYPE, modelChat.getConversationType());
            values.put(XmppConstants.CHAT.COL_RECEIPT_ID, modelChat.getReceiptId());
            values.put(XmppConstants.CHAT.COL_TO, modelChat.getToUser());
            values.put(XmppConstants.CHAT.COL_FROM, modelChat.getFromUser());
            values.put(XmppConstants.CHAT.COL_TYPE, modelChat.getType());
            values.put(XmppConstants.CHAT.COL_VALUE, modelChat.getValue());
            values.put(XmppConstants.CHAT.STORE_ID, modelChat.getStoreID());
            values.put(XmppConstants.CHAT.COL_STATUS, modelChat.getStatus());
            values.put(XmppConstants.CHAT.COL_TIMESTAMP, modelChat.getTime());
            values.put(XmppConstants.CHAT.COL_THUMB, modelChat.getThumb());
            values.put(XmppConstants.CHAT.COL_DURATION, modelChat.getDuration());
            values.put(XmppConstants.CHAT.COL_USER_NAME, modelChat.getUserName());
            values.put(XmppConstants.CHAT.COL_IS_FROM, modelChat.getIsFromUser());
            values.put(XmppConstants.CHAT.COL_LATLNG, modelChat.getLatlong() == null ? "" : modelChat.getLatlong());
            Log.e("insert ModelChat", "values: " + values);
            return database.insert(XmppConstants.CHAT.TABLE_GROUP_CHAT, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void updateUserName(ModelChat modelChat) {
        ContentValues values = new ContentValues();
        values.put(XmppConstants.CHAT.COL_USER_NAME, modelChat.getUserName());

        // updating row for user's username
        database.update(XmppConstants.CHAT.TABLE_CHAT, values, XmppConstants.CHAT.COL_FROM + " = ?",
                new String[]{modelChat.getFromUser()});
    }
}
