package com.xmpp.library.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.xmpp.library.utils.XmppConstants;

/**
 * Created by LN on 15/6/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String TAG = DatabaseHelper.class.getSimpleName();
    // Database Version
    private static final int DATABASE_VERSION = 1;
    private static DatabaseHelper sInstance;
    private static String DB_NAME = "Xmpp.db";
    private static String DB_PATH;
    private static String strPath = "";
    private Context context;

    @SuppressLint("SdCardPath")
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        this.context = context;
        DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        strPath = DB_PATH + DB_NAME;
        //Log.e(TAG, "DB Path : " + strPath);
        createDatabase();
    }

    public static synchronized DatabaseHelper getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void createDatabase() {

        if (!hasTable(XmppConstants.CHAT.TABLE_CHAT)) {
            createChatTable();
        }
        if (!hasTable(XmppConstants.CHAT.TABLE_GROUP_CHAT)) {
            createGroupChatTable();
        }
    }

    // count no of table in database
    private boolean hasTable(String name) {
        String sql = "SELECT COUNT(*) FROM sqlite_master WHERE TYPE='table' AND NAME = '" + name + "'";
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        cursor.moveToFirst();
        getReadableDatabase().close();
        int count = cursor.getInt(0);
        cursor.close();

        return count > 0;
    }


    /**
     * create table chat
     */
    private void createChatTable() {
        String sql = "CREATE TABLE IF NOT EXISTS " + XmppConstants.CHAT.TABLE_CHAT + "("
                + XmppConstants.CHAT.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + XmppConstants.CHAT.COL_RECEIPT_ID + " TEXT, "
                + XmppConstants.CHAT.COL_CONVERSATION_ID + " TEXT, "
                + XmppConstants.CHAT.COL_CONVERSATION_TYPE + " TEXT, "
                + XmppConstants.CHAT.COL_TO + " TEXT, "
                + XmppConstants.CHAT.COL_FROM + " TEXT, "
                + XmppConstants.CHAT.COL_TYPE + " TEXT, "
                + XmppConstants.CHAT.COL_VALUE + " TEXT, "
                + XmppConstants.CHAT.STORE_ID + " TEXT, "
                + XmppConstants.CHAT.COL_LOCAL_PATH + " TEXT, "
                + XmppConstants.CHAT.COL_STATUS + " TEXT, "
                + XmppConstants.CHAT.COL_THUMB + " TEXT, "
                + XmppConstants.CHAT.COL_DURATION + " TEXT, "
                + XmppConstants.CHAT.COL_USER_NAME + " TEXT, "
                + XmppConstants.CHAT.COL_LATLNG + " TEXT, "
                + XmppConstants.CHAT.COL_IS_FROM+ " TEXT, "
                + XmppConstants.CHAT.COL_TIMESTAMP + " TEXT)";

        getWritableDatabase().execSQL(sql);
    }

    /**
     * create table chat
     */
    private void createGroupChatTable() {
        String sql = "CREATE TABLE IF NOT EXISTS " + XmppConstants.CHAT.TABLE_GROUP_CHAT + "("
                + XmppConstants.CHAT.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + XmppConstants.CHAT.COL_RECEIPT_ID + " TEXT, "
                + XmppConstants.CHAT.COL_CONVERSATION_ID + " TEXT, "
                + XmppConstants.CHAT.COL_CONVERSATION_TYPE + " TEXT, "
                + XmppConstants.CHAT.COL_TO + " TEXT, "
                + XmppConstants.CHAT.COL_FROM + " TEXT, "
                + XmppConstants.CHAT.COL_TYPE + " TEXT, "
                + XmppConstants.CHAT.COL_VALUE + " TEXT, "
                + XmppConstants.CHAT.STORE_ID + " TEXT, "
                + XmppConstants.CHAT.COL_LOCAL_PATH + " TEXT, "
                + XmppConstants.CHAT.COL_STATUS + " TEXT, "
                + XmppConstants.CHAT.COL_THUMB + " TEXT, "
                + XmppConstants.CHAT.COL_DURATION + " TEXT, "
                + XmppConstants.CHAT.COL_USER_NAME + " TEXT, "
                + XmppConstants.CHAT.COL_IS_FROM + " TEXT, "
                + XmppConstants.CHAT.COL_LATLNG + " TEXT, "
                + XmppConstants.CHAT.COL_TIMESTAMP + " TEXT)";

        getWritableDatabase().execSQL(sql);
    }
}
