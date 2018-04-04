package com.cc.android.zcommon.utils.android;

import android.util.Log;

import com.cc.android.zcommon.BuildConfig;


/**
 * Created by LiuLiWei on 2017/4/5 0005.
 *
 * Log Utils
 */
public class L {
    private static final boolean debug = BuildConfig.DEBUG;

    private L() {
        throw new UnsupportedOperationException("Class L cannot be instantiated.");
    }

    public static void d(String tag, String msg) {
        if(null == msg || msg.equals("")) {
            return;
        }
        if(null == tag || tag.equals("")) {
            tag = "TAG";
        }
        if(debug) {
            Log.d(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if(null == msg || msg.equals("")) {
            return;
        }
        if(null == tag || tag.equals("")) {
            tag = "TAG";
        }
        if(debug) {
            Log.e(tag, msg);
        }
    }
}
