package com.cc.android.zcommon.aspect.login;

import android.content.Context;


/**
 * Login manager.
 *
 * @Author:LiuLiWei
 */
public class LoginManager {

    private Context mContext;

    private OnLoginListener mOnLoginListener;

    private LoginManager() {}

    private static class LoginWrapper {
        private static LoginManager instance = new LoginManager();
    }

    /**
     * Get the singleton.
     *
     * @return LoginManager
     */
    public static LoginManager get() {
        return LoginWrapper.instance;
    }

    public void init(Context context, OnLoginListener listener) {
        mContext = context.getApplicationContext();
        mOnLoginListener = listener;
    }

    public OnLoginListener getListener() {
        return mOnLoginListener;
    }

    public Context getContext() {
        return mContext;
    }
}
