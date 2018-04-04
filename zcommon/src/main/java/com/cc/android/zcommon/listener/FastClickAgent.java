package com.cc.android.zcommon.listener;

import android.view.View;

import com.cc.android.zcommon.utils.android.ViewClickUtils;

/**
 * Fast click agent.
 *
 * @Author:LiuLiWei
 */
public class FastClickAgent implements View.OnClickListener {

    private long _interval;

    private OnFastClickListener _fastClickListener;

    public FastClickAgent(OnFastClickListener fastClickListener) {
        this(500, fastClickListener);
    }

    public FastClickAgent(long interval, OnFastClickListener fastClickListener) {
        this._interval = interval;
        this._fastClickListener = fastClickListener;
    }

    public interface OnFastClickListener {
        void onFastClick(View view);
    }

    @Override
    public void  onClick(View view) {
        final boolean isFastClick = this._interval == 0 ? ViewClickUtils.isFastClick(view) : ViewClickUtils.isSpecificTimeClick(view, _interval);
        if(isFastClick) {
            return;
        }
        if(this._fastClickListener != null) {
            this._fastClickListener.onFastClick(view);
        }
    }

    /**
     * Get interval.
     * @return
     */
    public long getClickInterval() {
        return _interval;
    }
}
