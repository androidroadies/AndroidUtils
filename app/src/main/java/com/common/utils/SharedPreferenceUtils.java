package com.common.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtils {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public SharedPreferenceUtils(Context context) {
        pref = context.getSharedPreferences("DealsPreferences", 0);
        editor = pref.edit();
    }

    public static synchronized SharedPreferenceUtils getInstance(Context context) {
        return new SharedPreferenceUtils(context);
    }

    public void setValue(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    public void setValue(String key, int value) {
        editor.putInt(key, value);
        editor.apply();
    }

    public void setValue(String key, double value) {
        setValue(key, Double.toString(value));
    }

    public void setValue(String key, long value) {
        editor.putLong(key, value);
        editor.apply();
    }

    public void setValue(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.apply();
    }

    public String getStringValue(String key, String defaultValue) {

        return pref.getString(key, defaultValue);
    }

    public int getIntValue(String key, int defaultValue) {
        return pref.getInt(key, defaultValue);
    }

    public long getLongValue(String key, long defaultValue) {
        return pref.getLong(key, defaultValue);
    }

    public boolean getBoolanValue(String keyFlag, boolean defaultValue) {
        return pref.getBoolean(keyFlag, defaultValue);
    }

    public void removeKey(String key) {

        if (editor != null) {
            editor.remove(key);
            editor.apply();
        }
    }

    public void clear() {
        editor.clear().apply();
    }
}
