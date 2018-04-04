package com.cc.android.zcommon.utils.android;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cc.android.zcommon.R;

/**
 * View click utils.
 *
 * @Author:LiuLiWei
 */
public class ViewClickUtils {

  /**
   * Judge whether a clickable view is clicked quickly.
   * @param v
   * @return
   */
  public static boolean isFastClick(View v) {
    return isSpecificTimeClick(v, 500);
  }

  /**
   * Judge whether a clickable view is clicked within specific time..
   */
  public static boolean isSpecificTimeClick(View v, long interval) {
    try {
      long time = System.currentTimeMillis();
      long lastClickTime = 0;
      final Object tag = v.getTag(R.id.tag_click);
      if(null != tag && tag instanceof Long) {
        lastClickTime = (long) tag;
      }
      long timeD = time - lastClickTime;
      if (timeD > 0 && timeD < interval) {
        return true;
      }
      v.setTag(R.id.tag_click, time);
      return false;
    }catch (Exception e) {
      return false;
    }
  }

  /**
   *
   * @param view
   * @param imgRes
   * @param emptyText
   */
  public static void setEmptyMessage(View view, int imgRes, String emptyText) {
    if (imgRes != -1) ((ImageView) view.findViewById(R.id.empty_img)).setImageResource(imgRes);
    if (!TextUtils.isEmpty(emptyText)) {
      ((TextView) view.findViewById(R.id.empty_text)).setText(emptyText);
    }
  }
}
