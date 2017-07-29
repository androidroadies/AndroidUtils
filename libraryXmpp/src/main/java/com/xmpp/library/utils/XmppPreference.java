package com.xmpp.library.utils;

import android.content.Context;


/**
 * Created by Pranay on 9/5/17.
 */
public class XmppPreference {

    private static XmppPreference instance;
    private Context context;
    private SharedPreferenceUtils preferenceUtils;

    public XmppPreference(Context context) {
        this.context = context;
        preferenceUtils = SharedPreferenceUtils.getInstance(context);
    }

    public static XmppPreference getInstance(Context context) {
        if (instance == null) {
            instance = new XmppPreference(context);
        }
        return instance;
    }

    public String getUserId() {
        return preferenceUtils.getStringValue(XmppConstants.USER.USER_ID, "");
    }

    public void setUserId(String userId) {
        preferenceUtils.setValue(XmppConstants.USER.USER_ID, userId);
    }

    public String getPassword() {
        return preferenceUtils.getStringValue(XmppConstants.USER.PASSWORD, "");
    }

    public void setPassword(String password) {
        preferenceUtils.setValue(XmppConstants.USER.PASSWORD, password);
    }

    public void clear() {
        setUserId("");
        setPassword("");
    }

}
