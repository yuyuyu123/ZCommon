package com.cc.android.zcommon.support;

import android.app.Activity;
import android.view.WindowManager;

public class WindowHelper {

    public static  void setBackgroundAlpha(Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        activity.getWindow().setAttributes(lp);
    }
}
