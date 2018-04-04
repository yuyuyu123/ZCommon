package com.cc.android.zcommon.netstate;

/**
 * Net change observer.
 *
 * @Author:LiuLiWei
 */
public  interface NetChangeObserver {

   void onNetConnected(int state);

   void onNetDisConnected();
}
