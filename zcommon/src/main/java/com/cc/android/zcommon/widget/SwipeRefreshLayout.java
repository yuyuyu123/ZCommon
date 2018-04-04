package com.cc.android.zcommon.widget;

import android.content.Context;
import android.util.AttributeSet;

public class SwipeRefreshLayout extends android.support.v4.widget.SwipeRefreshLayout{
  public SwipeRefreshLayout(Context context) {
    super(context);
  }

  public SwipeRefreshLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  public boolean canChildScrollUp() {
    if (isRefreshing()){
      return true;
    }
    return false;
  }
}
