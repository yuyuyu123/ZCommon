package com.cc.android.zcommon.widget.loadmore;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.cc.android.zcommon.R;

/**
 * 可以自动加载更多的RecyclerView
 * <br/>
 * 感谢：https://github.com/Aspsine/IRecyclerView
 * Created by aspsine on 16/3/3.
 */
public class RxRecyclerView extends RecyclerView {
  private static final String TAG = RxRecyclerView.class.getSimpleName();

  private static final boolean DEBUG = false;

  private boolean mLoadMoreEnabled;

  private OnLoadMoreListener mOnLoadMoreListener;

  private OnLoadMoreScrollListener mOnLoadMoreScrollListener;

  private FrameLayout mLoadMoreFooterContainer;

  private LinearLayout mFooterViewContainer;

  private View mLoadMoreFooterView;

  private @Nullable
  View emptyView;

  public RxRecyclerView(Context context) {
    this(context, null);
  }

  public RxRecyclerView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public RxRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    final TypedArray a =
        context.obtainStyledAttributes(attrs, R.styleable.RxRecyclerView, defStyle, 0);
    @LayoutRes int loadMoreFooterLayoutRes = -1;
    boolean loadMoreEnabled;

    try {

      loadMoreEnabled = a.getBoolean(R.styleable.RxRecyclerView_loadMoreEnabled, false);
      loadMoreFooterLayoutRes =
          a.getResourceId(R.styleable.RxRecyclerView_loadMoreFooterLayout, -1);
    } finally {
      a.recycle();
    }

    setLoadMoreEnabled(loadMoreEnabled);

    if (loadMoreFooterLayoutRes != -1) {
      setLoadMoreFooterView(loadMoreFooterLayoutRes);
    }
  }

  @Override
  protected void onMeasure(int widthSpec, int heightSpec) {
    super.onMeasure(widthSpec, heightSpec);
  }

  /**
   * 判断 getAdapter().getItemCount() > 0 显示内容，否则显示emptyView
   */
  private void checkIfEmpty() {
    if (emptyView != null) {
      if (getAdapter() != null) {
        emptyView.setVisibility(getIAdapter().getItemCount() > 0 ? View.GONE : View.VISIBLE);
      }
    }
  }

  final AdapterDataObserver observer = new AdapterDataObserver() {
    @Override
    public void onChanged() {
      super.onChanged();
      checkIfEmpty();
    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
      super.onItemRangeInserted(positionStart, itemCount);
      checkIfEmpty();
    }

    @Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {
      super.onItemRangeRemoved(positionStart, itemCount);
      checkIfEmpty();
    }

    @Override
    public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
      super.onItemRangeMoved(fromPosition, toPosition, itemCount);
      checkIfEmpty();
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount) {
      super.onItemRangeChanged(positionStart, itemCount);
      checkIfEmpty();
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
      super.onItemRangeChanged(positionStart, itemCount, payload);
      checkIfEmpty();
    }
  };

  @Override
  public void setAdapter(Adapter adapter) {
    final Adapter oldAdapter = getAdapter();
    if (oldAdapter != null) {
      oldAdapter.unregisterAdapterDataObserver(observer);
    }

    if (adapter != null) {
      adapter.registerAdapterDataObserver(observer);
    }

    super.setAdapter(adapter);
    checkIfEmpty();
  }

  @Override
  public void swapAdapter(Adapter adapter, boolean removeAndRecycleExistingViews) {
    final Adapter oldAdapter = getAdapter();
    if (oldAdapter != null) {
      oldAdapter.unregisterAdapterDataObserver(observer);
    }

    if (adapter != null) {
      adapter.registerAdapterDataObserver(observer);
    }
    super.swapAdapter(adapter, removeAndRecycleExistingViews);
    checkIfEmpty();
  }

  /**
   * 设置EmptyView
   */
  public void setEmptyView(@Nullable View emptyView) {
    if (this.emptyView != null) {
      this.emptyView.setVisibility(GONE);
    }

    this.emptyView = emptyView;
    checkIfEmpty();
  }

  /**
   * 获取绑定到RecyclerView的EmptyView
   *
   * @return emptyView
   */
  public View getEmptyView() {
    return this.emptyView;
  }

  public void setLoadMoreEnabled(boolean enabled) {
    this.mLoadMoreEnabled = enabled;
    if (mLoadMoreEnabled) {
      if (mOnLoadMoreScrollListener == null) {
        mOnLoadMoreScrollListener = new OnLoadMoreScrollListener() {
          @Override
          public void onLoadMore(RecyclerView recyclerView) {

            if (mOnLoadMoreListener != null) {
              mOnLoadMoreListener.onLoadMore(mLoadMoreFooterView);
            }
          }
        };
      } else {
        removeOnScrollListener(mOnLoadMoreScrollListener);
      }
      addOnScrollListener(mOnLoadMoreScrollListener);
    } else {
      if (mOnLoadMoreScrollListener != null) {
        removeOnScrollListener(mOnLoadMoreScrollListener);
      }
    }
  }

  public void setOnLoadMoreListener(OnLoadMoreListener listener) {
    this.mOnLoadMoreListener = listener;
  }

  public void setLoadMoreFooterView(View loadMoreFooterView) {
    if (mLoadMoreFooterView != null) {
      removeLoadMoreFooterView();
    }
    if (mLoadMoreFooterView != loadMoreFooterView) {
      this.mLoadMoreFooterView = loadMoreFooterView;
      ensureLoadMoreFooterContainer();
      mLoadMoreFooterContainer.addView(loadMoreFooterView);
    }
  }

  public void setLoadMoreFooterView(@LayoutRes int loadMoreFooterLayoutRes) {
    ensureLoadMoreFooterContainer();
    final View loadMoreFooter = LayoutInflater.from(getContext())
        .inflate(loadMoreFooterLayoutRes, mLoadMoreFooterContainer, false);
    if (loadMoreFooter != null) {
      setLoadMoreFooterView(loadMoreFooter);
    }
  }

  public View getLoadMoreFooterView() {
    return mLoadMoreFooterView;
  }

  public LinearLayout getFooterContainer() {
    ensureFooterViewContainer();
    return mFooterViewContainer;
  }

  public void addFooterView(View footerView) {
    ensureFooterViewContainer();
    mFooterViewContainer.addView(footerView);
    Adapter adapter = getAdapter();
    if (adapter != null) {
      adapter.notifyItemChanged(adapter.getItemCount() - 2);
    }
  }

  public Adapter getIAdapter() {
    final WrapperAdapter wrapperAdapter = (WrapperAdapter) getAdapter();
    if (wrapperAdapter == null) return null;
    return wrapperAdapter.getAdapter();
  }

  public void setIAdapter(Adapter adapter) {
    ensureFooterViewContainer();
    ensureLoadMoreFooterContainer();
    setAdapter(new WrapperAdapter(adapter, mFooterViewContainer, mLoadMoreFooterContainer));
  }

  private void ensureLoadMoreFooterContainer() {
    if (mLoadMoreFooterContainer == null) {
      mLoadMoreFooterContainer = new FrameLayout(getContext());
      mLoadMoreFooterContainer.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
          ViewGroup.LayoutParams.WRAP_CONTENT));
    }
  }

  private void ensureFooterViewContainer() {
    if (mFooterViewContainer == null) {
      mFooterViewContainer = new LinearLayout(getContext());
      mFooterViewContainer.setOrientation(LinearLayout.VERTICAL);
      mFooterViewContainer.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
          ViewGroup.LayoutParams.WRAP_CONTENT));
    }
  }

  private void removeLoadMoreFooterView() {
    if (mLoadMoreFooterContainer != null) {
      mLoadMoreFooterContainer.removeView(mLoadMoreFooterView);
    }
  }

  private int mActivePointerId = -1;
  private int mLastTouchX = 0;
  private int mLastTouchY = 0;

  @Override
  public boolean onInterceptTouchEvent(MotionEvent e) {
    final int action = MotionEventCompat.getActionMasked(e);
    final int actionIndex = MotionEventCompat.getActionIndex(e);
    switch (action) {
      case MotionEvent.ACTION_DOWN: {
        mActivePointerId = MotionEventCompat.getPointerId(e, 0);
        mLastTouchX = (int) (MotionEventCompat.getX(e, actionIndex) + 0.5f);
        mLastTouchY = (int) (MotionEventCompat.getY(e, actionIndex) + 0.5f);
      }
      break;

      case MotionEvent.ACTION_POINTER_DOWN: {
        mActivePointerId = MotionEventCompat.getPointerId(e, actionIndex);
        mLastTouchX = (int) (MotionEventCompat.getX(e, actionIndex) + 0.5f);
        mLastTouchY = (int) (MotionEventCompat.getY(e, actionIndex) + 0.5f);
      }
      break;

      case MotionEventCompat.ACTION_POINTER_UP: {
        onPointerUp(e);
      }
      break;
    }

    return super.onInterceptTouchEvent(e);
  }

  private int getMotionEventX(MotionEvent e, int pointerIndex) {
    return (int) (MotionEventCompat.getX(e, pointerIndex) + 0.5f);
  }

  private int getMotionEventY(MotionEvent e, int pointerIndex) {
    return (int) (MotionEventCompat.getY(e, pointerIndex) + 0.5f);
  }

  private void onPointerUp(MotionEvent e) {
    final int actionIndex = MotionEventCompat.getActionIndex(e);
    if (MotionEventCompat.getPointerId(e, actionIndex) == mActivePointerId) {
      // Pick a new pointer to pick up the slack.
      final int newIndex = actionIndex == 0 ? 1 : 0;
      mActivePointerId = MotionEventCompat.getPointerId(e, newIndex);
      mLastTouchX = getMotionEventX(e, newIndex);
      mLastTouchY = getMotionEventY(e, newIndex);
    }
  }
}
