package com.cc.android.zcommon.base;

import android.os.Bundle;
import android.view.View;

/**
 * Fragment基类, 继承自此类的Fragment需要实现{@link #getLayoutId}, {@link #init}
 * 以及{@link #createPresenter()}, 不需要覆写onCreate方法.
 * <br/>
 * 鉴于FragmentManager的attach与detach会销毁Fragment的视图, 此基类会在onCreate中生成一个
 * parentView, 缓存起来, 并在onCreateView中直接返回该View, 来达到保存Fragment视图状态的目的,
 * 同时避免不停的销毁与创建.
 * 实现此类需遵循MVP设计, 第一个泛型V需传入一个继承自{@link RxBaseView}的MVPView
 * <br/>
 * 第二个泛型P需传入继承自{@link RxBasePresenter}的MVPPresenter.
 * <br/>
 * Presenter的生命周期已交由此类管理, 子类无需管理. 如果子类要使用多个Presenter, 则需要自行管理生命周期.
 * 此类已经实现了BaseView中的抽象方法, 子类无需再实现, 如需自定可覆写对应的方法.
 */
public abstract class RxBaseFragment<V extends RxBaseView, P extends RxBasePresenter<V>>
    extends BaseFragment<V, P> implements RxBaseView {

  @Override
  protected void init(Bundle savedInstanceState, View view) {
    super.init(savedInstanceState, view);
    if (getPresenter() != null) getPresenter().registerLifeCycle();
  }

  @Override
  public void showModalLoading() {
    showModalLoading("");
  }

  @Override
  public void showModalLoading(String txt) {
    showModalLoading(txt, true);
  }

  @Override
  public void showModalLoading(String txt, boolean cancelable) {
    getRxContext().showModalLoading(txt, cancelable);
  }

  @Override
  protected int getLayoutId() {
    return 0;
  }

  @Override
  protected boolean isBindEventBusHere() {
    return false;
  }

  @Override
  protected P createPresenter() {
    return null;
  }

  @Override
  public void closeModalLoading() {
    getRxContext().closeModalLoading();
  }

  @Override
  public void onDestroy() {
    // 解绑
    if (getPresenter() != null) getPresenter().unRegisterLifeCycle();
    super.onDestroy();
  }

  public RxBaseActivity getRxContext() {
    return getActivity() instanceof RxBaseActivity ? ((RxBaseActivity) getActivity()) : null;
  }
}
