package com.cc.android.zcommon.base;

import java.lang.ref.WeakReference;

/**
 * Presenter基类, Fragment需使用继承自此类的子类, 泛型需传入继承自{@link BaseView}的MVPView.
 * <br/>
 * 子类可直接调用通过attachView传递过来的view来操作Activity, 无需再声明绑定.
 * <br/>
 * 如子类需要自行管理生命周期, 请在Activity/Fragment的onCreate中调用{@link #attachView}方法,
 * 并一定要在onDestroy中调用{
 */
public abstract class BasePresenter<T extends BaseView> {

  protected static String TAG = "TAG";
  protected WeakReference<T> viewRef;

  public BasePresenter() {
    TAG = this.getClass().getSimpleName();
  }

  protected void onViewAttached() {
  }

  public T getView() {
    return viewRef == null ? null : viewRef.get();
  }

  /**
   * Checks if a view is attached to this presenter. You should always call this method before
   * calling {@link #getView()} to get the view instance.
   */
  public boolean isViewAttached() {
    return viewRef != null && viewRef.get() != null;
  }

  /**
   * 将Presenter与MVPView绑定起来.
   *
   * @param view MVPView
   */
  public void attachView(T view) {
    viewRef = new WeakReference<>(view);
    onViewAttached();
  }

  /**
   * 将Presenter与MVPView解除.
   */
  public void detachView() {
    if (viewRef != null) {
      viewRef.clear();
      viewRef = null;
    }
  }
}
