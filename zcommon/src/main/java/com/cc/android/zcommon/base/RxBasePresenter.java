package com.cc.android.zcommon.base;

import android.support.annotation.CallSuper;

import com.cc.android.zcommon.support.RxHelper;

import io.reactivex.disposables.CompositeDisposable;


/**
 * 参考{@link BasePresenter}, Activity需使用继承自此类的子类
 */
public abstract class RxBasePresenter<T extends RxBaseView> extends BasePresenter<T> {
  protected CompositeDisposable mSubscriptions = new CompositeDisposable();
  /**
   * 注册到Activity/Fragment生命周期
   */
  @CallSuper
  public void registerLifeCycle() {
    mSubscriptions = RxHelper.getNewCompositeSubIfUnsubscribed(mSubscriptions);
  }

  /**
   * 解绑到Activity/Fragment生命周期
   */
  @CallSuper
  public void unRegisterLifeCycle() {
    RxHelper.unsubscribeIfNotNull(mSubscriptions);
  }
}
