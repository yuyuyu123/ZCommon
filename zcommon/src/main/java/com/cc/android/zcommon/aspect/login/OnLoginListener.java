package com.cc.android.zcommon.aspect.login;

import android.content.Context;


/**
 * On login listener.
 *
 * @Author:LiuLiWei
 */
public interface OnLoginListener {

    void login(Context context, int val);

    boolean isLogin(Context context);
}
