package com.cc.android.zcommon.swipe;

import android.os.Build;

public class RelateSlider implements OnSwipeBackListener {
    public SwipeBackPage curPage;
    private static final int DEFAULT_OFFSET = 40;
    private int offset = 500;

    public RelateSlider(SwipeBackPage curActivity) {
        this.curPage = curActivity;
        //curPage.addListener(this);
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setEnable(boolean enable){
        if (enable)curPage.addListener(this);
        else curPage.removeListener(this);
    }

    @Override
    public void onScroll(float percent, int px) {
        if (Build.VERSION.SDK_INT>11){
            SwipeBackPage page = SwipeBackManager.getPrePage(curPage);
            if (page!=null){
                page.getSwipeBackLayout().setX(Math.min(-offset * Math.max(1 - percent,0)+DEFAULT_OFFSET,0));
                if (percent == 0){
                    page.getSwipeBackLayout().setX(0);
                }
            }
        }
    }

    @Override
    public void onEdgeTouch() {

    }

    @Override
    public void onScrollToClose() {
        SwipeBackPage page = SwipeBackManager.getPrePage(curPage);
        if (Build.VERSION.SDK_INT>11) {
            if (page != null) page.getSwipeBackLayout().setX(0);
        }
    }
}
