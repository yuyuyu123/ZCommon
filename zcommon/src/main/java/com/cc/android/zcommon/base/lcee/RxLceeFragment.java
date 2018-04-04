package com.cc.android.zcommon.base.lcee;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.view.View;
import android.widget.TextView;

import com.cc.android.zcommon.R;
import com.cc.android.zcommon.base.RxBaseActivity;
import com.cc.android.zcommon.base.RxBaseFragment;
import com.cc.android.zcommon.base.RxBasePresenter;
import com.cc.android.zcommon.support.LceeAnimatorHelper;
import com.cc.android.zcommon.utils.android.ViewClickUtils;


/**
 * Fragment基类, 继承自此类的Fragment需要实现{@link #getLayoutId}, {@link #init}
 * 以及{@link #createPresenter()}, 不需要覆写onCreate方法.
 * <br/>
 * 鉴于FragmentManager的attach与detach会销毁Fragment的视图, 此基类会在onCreate中生成一个
 * parentView, 缓存起来, 并在onCreateView中直接返回该View, 来达到保存Fragment视图状态的目的,
 * 同时避免不停的销毁与创建.
 * 实现此类需遵循MVP设计, 第一个泛型CV需传入一个继承自{@link View}的ContentView,
 * <br/>
 * 第二个泛型M需传入一个实体Bean或者默认Object,
 * <br/>
 * 第三个泛型V需传入一个继承自{@link RxLceeView}的MVPView,
 * <br/>
 * 第四个泛型P需传入继承自{@link RxBasePresenter}的MVPPresenter.
 * <br/>
 * Presenter的生命周期已交由此类管理, 子类无需管理. 如果子类要使用多个Presenter, 则需要自行管理生命周期.
 * 此类已经实现了BaseView中的抽象方法, 子类无需再实现, 如需自定可覆写对应的方法.
 */
public abstract class RxLceeFragment<CV extends View, M, V extends RxLceeView<M>, P extends RxBasePresenter<V>>
    extends RxBaseFragment<V, P> implements RxLceeView<M> {

  protected View loadingView;
  protected CV contentView;
  protected TextView errorView;
  protected View emptyView;


  @CallSuper
  @Override
  protected void init(Bundle savedInstanceState, View view) {
    super.init(savedInstanceState, view);
    loadingView = view.findViewById(R.id.loadingView);
    contentView =  view.findViewById(R.id.contentView);
    errorView =  view.findViewById(R.id.errorView);
    emptyView = view.findViewById(R.id.emptyView);

    if (loadingView == null) {
      throw new NullPointerException(
          "Loading view is null! Have you specified a loading view in your layout xml file?"
              + " You have to give your loading View the id R.id.loadingView");
    }

    if (contentView == null) {
      throw new NullPointerException(
          "Content view is null! Have you specified a content view in your layout xml file?"
              + " You have to give your content View the id R.id.contentView");
    }

    if (errorView == null) {
      throw new NullPointerException(
          "Error view is null! Have you specified a content view in your layout xml file?"
              + " You have to give your error View the id R.id.contentView");
    }

    if (emptyView == null) {
      throw new NullPointerException(
          "Empty view is null! Have you specified a content view in your layout xml file?"
              + " You have to give your empty View the id R.id.emptyView");
    }

    errorView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onErrorViewClicked();
      }
    });
    ViewClickUtils.setEmptyMessage(emptyView, getEmptyImage(), getEmptyMessage());

    emptyView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onEmptyViewClicked();
      }
    });
  }

  @Override
  public void onDestroy() {
    // 解绑
    getPresenter().unRegisterLifeCycle();
    super.onDestroy();
  }

  /**
   * Called if the error view has been clicked. To disable clicking on the errorView use
   * <code>errorView.setClickable(false)</code>
   */
  protected abstract void onErrorViewClicked();

  public abstract void onEmptyViewClicked();

  @Override
  public void showLoading(boolean pullToRefresh) {
    if (!pullToRefresh) {
      animateLoadingViewIn();
    }
  }

  /**
   * Override this method if you want to provide your own animation for showing the loading view
   */
  protected void animateLoadingViewIn() {
    LceeAnimatorHelper.showLoading(loadingView, contentView, errorView, emptyView);
  }

  @Override
  public void showContent() {
    animateContentViewIn();
  }

  /**
   * Called to animate from loading view to content view
   */
  protected void animateContentViewIn() {
    LceeAnimatorHelper.showContent(loadingView, contentView, errorView, emptyView);
  }

  @Override
  public void showError(Throwable e, boolean pullToRefresh) {
    if (!pullToRefresh) {
      animateErrorViewIn();
    }
  }

  /**
   * Animates the error view in (instead of displaying content view / loading view)
   */
  protected void animateErrorViewIn() {
    LceeAnimatorHelper.showErrorView(loadingView, contentView, errorView, emptyView);
  }

  public RxBaseActivity getRxContext() {
    return getActivity() instanceof RxBaseActivity ? ((RxBaseActivity) getActivity()) : null;
  }
}
