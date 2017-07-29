package com.common.utils;

import android.util.Log;



public class LogUtils {

    private static boolean LOGGING_ENABLED = false;

    private static boolean isLoggable() {
        return BuildConfig.DEBUG;
    }

    private static String createTag(Class tag) {
        String tempTag = tag.getSimpleName();
        return tempTag.length() > 23 ? tempTag.substring(0, 22) : tempTag;
    }

//    /**
//     * Don't use this when obfuscating class names!
//     */
//    public static String makeLogTag(Class cls) {
//        return makeLogTag(cls.getSimpleName());
//    }

    public static void error(Class tag, String message) {
        if (isLoggable()) {
            Log.e(createTag(tag), message);
        }
    }

    public static void warning(Class tag, String message) {
        if (isLoggable()) {
            Log.w(createTag(tag), message);
        }
    }

    public static void information(Class tag, String message) {
        if (isLoggable()) {
            Log.i(createTag(tag), message);
        }
    }

    public static void debug(Class tag, String message) {
        if (isLoggable()) {
            Log.d(createTag(tag), message);
        }
    }

    public static void verbose(Class tag, String message) {
        if (isLoggable()) {
            Log.v(createTag(tag), message);
        }
    }

    public static void wtf(Class tag, String message) {
        if (isLoggable()) {
            Log.wtf(createTag(tag), message);
        }
    }

    public static void LOGE(final String tag, String message) {
        if (LOGGING_ENABLED){
            Log.e(tag, message);
        }
    }
    public static void LOGD(final String tag, String message) {
        if (LOGGING_ENABLED) {
            Log.d(tag, message);
        }
    }

    public static void LOGD(final String tag, String message, Throwable cause) {
        if (LOGGING_ENABLED) {
            Log.d(tag, message, cause);
        }
    }
}