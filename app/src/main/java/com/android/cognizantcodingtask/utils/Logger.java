package com.android.cognizantcodingtask.utils;

import android.util.Log;

import com.android.cognizantcodingtask.BuildConfig;


public class Logger {

    private static void writeLogD(String tag, String value) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, value);
        }
    }

    private static void writeLogE(String tag, Exception value) {
        if (BuildConfig.DEBUG) {
            value.printStackTrace();
        }
    }

    public static void d(String value) {
        writeLogD(CZConstants.TAG, value);
    }

    public static void d(String tag, String value) {
        writeLogD(CZConstants.TAG + tag, "" + value);
    }


    public static void e(Exception value) {
        writeLogE(CZConstants.TAG, value);
    }

    public static void e(String tag, Exception value) {
        writeLogE(CZConstants.TAG + tag, value);
    }

}
