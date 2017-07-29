package com.xmpp.library.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SharedPreferenceUtils {
    private static final String TAG = SharedPreferenceUtils.class.getSimpleName();
    private static SharedPreferenceUtils sInstance;
    protected Context mContext;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private SharedPreferenceUtils(Context context) {
        mContext = context;
        int stringId = context.getApplicationInfo().labelRes;
        pref = context.getSharedPreferences(context.getString(stringId) + "_SharedPreferences", 0);
        editor = pref.edit();
    }

    public static synchronized SharedPreferenceUtils getInstance(Context context) {

        if (sInstance == null) {
            sInstance = new SharedPreferenceUtils(context.getApplicationContext());
        }
        return sInstance;
    }

    public void setValue(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public void setValue(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public void setValue(String key, double value) {
        setValue(key, Double.toString(value));
    }

    public void setValue(String key, long value) {
        editor.putLong(key, value);
        editor.commit();
    }

    public void setValue(String key, float value) {
        editor.putFloat(key, value);
        editor.commit();
    }

    public void setValue(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void setValue(String key, ArrayList<String> valuesList) {
        Gson gson = new Gson();
        String value = gson.toJson(valuesList);
        editor.putString(key, value);
        editor.commit();
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

    public float getFloatValue(String key, float defaultValue) {
        return pref.getFloat(key, defaultValue);
    }

    public boolean getBoolanValue(String keyFlag, boolean defaultValue) {
        return pref.getBoolean(keyFlag, defaultValue);
    }

    public ArrayList<String> getArrayListValue(String key, String defaultValue) {
        Gson gson = new Gson();

        String value = pref.getString(key, defaultValue);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();

        ArrayList<String> listValues = new ArrayList<>();

        if (value != null) {
            listValues = gson.fromJson(value, type);
        }
        return listValues;
    }

    public void removeKey(String key) {

        if (editor != null) {
            editor.remove(key);
            editor.commit();
        }
    }

    public void clear() {
        editor.clear().commit();
    }

    public boolean isContain(String key) {
        return pref.contains(key);
    }

}


