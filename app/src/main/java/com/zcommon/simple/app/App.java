package com.zcommon.simple.app;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.cc.android.zcommon.aspect.login.LoginManager;
import com.cc.android.zcommon.aspect.login.OnLoginListener;

/**
 * App.
 *
 * @Author:LiuLiWei
 */
public class App extends Application {

    private static final String TAG = "App";

    @Override
    public void onCreate() {
        super.onCreate();
        LoginManager.get().init(this, new OnLoginListener() {
            @Override
            public void login(Context context, int val) {
                Log.e(TAG, "login-------->" + val);
                //这里写登录逻辑，比如跳转到登录界面
                if(val == 5) {//根据变量的值可以做不同的操作

                }
            }

            @Override
            public boolean isLogin(Context context) {
                Log.e(TAG, "isLogin-------->");
                //这里判断是否已经登录
                return false;
            }
        });
    }
}
