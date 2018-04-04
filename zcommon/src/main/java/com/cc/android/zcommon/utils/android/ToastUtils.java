package com.cc.android.zcommon.utils.android;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.widget.Toast;

public class ToastUtils {
  private static Toast mToast;
  private static Handler mHandler = new Handler();

  private static Runnable runnable = new Runnable() {
    @Override
    public void run() {
      if (mToast != null) {
        mToast.cancel();
      }
    }
  };

  /**
   * @param context 上下文对象
   * @param text 提示信息
   * @param duration 显示时间
   */
  public synchronized static void showToast(Context context, @NonNull String text, int duration) {
    mHandler.removeCallbacks(runnable);
    if (mToast == null) {
      mToast = Toast.makeText(context, text, duration);
    } else {
      mToast.setText(text);
    }
    mHandler.postDelayed(runnable, 1000);
    mToast.show();
  }

  public static void showToastL(Context context, @NonNull String text) {
    showToast(context, text, Toast.LENGTH_LONG);
  }

  public static void showToastS(Context context, @NonNull String text) {
    showToast(context, text, Toast.LENGTH_SHORT);
  }

  public static void showToastL(Context context, @StringRes int resId) {
    showToast(context, resId, Toast.LENGTH_LONG);
  }

  public static void showToastS(Context context, @StringRes int resId) {
    showToast(context, resId, Toast.LENGTH_SHORT);
  }

  public static void showToast(Context context, @StringRes int resId, int duration) {
    String text = context.getResources().getString(resId);
    showToast(context, text, duration);
  }
}