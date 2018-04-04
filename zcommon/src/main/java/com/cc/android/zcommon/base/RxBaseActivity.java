package com.cc.android.zcommon.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.text.TextUtils;

import com.afollestad.materialdialogs.MaterialDialog;
import com.cc.android.zcommon.support.ConfigurationHelper;

/**
 * Activity基类, 继承自此类的Activity需要实现{@link #getLayoutId},{@link #init}
 * 以及{@link #createPresenter()}, 不需要覆写onCreate方法.
 * <br/>
 * 实现此类需遵循MVP设计, 第一个泛型V需传入一个继承自{@link RxBaseView}的MVPView
 * <br/>
 * 第二个泛型P需传入继承自{@link RxBasePresenter}的MVPPresenter.
 * <br/>
 * Presenter的生命周期已交由此类管理, 子类无需管理. 如果子类要使用多个Presenter, 则需要自行管理生命周期.
 * 此类已经实现了BaseView中的抽象方法, 子类无需再实现, 如需自定可覆写对应的方法.
 * <br/>
 */
public abstract class RxBaseActivity<V extends RxBaseView, P extends RxBasePresenter<V>>
        extends BaseActivity<V, P> implements RxBaseView {

    private MaterialDialog mLoadingDialog;

    private LoadingRunnable mLoadingRunnable;

    private class  LoadingRunnable implements Runnable {
        private String _txt;
        private boolean _cancelable;
        public LoadingRunnable(String txt, boolean cancelable) {
            this._txt = txt;
            this._cancelable = cancelable;
        }
        @Override
        public void run() {
            mLoadingDialog =
                    new MaterialDialog.Builder(getRxContext()).content(TextUtils.isEmpty(_txt) ? ConfigurationHelper.getModalLoadingText() : _txt)
                            .progress(true, 0)
                            .cancelable(_cancelable)
                            .progressIndeterminateStyle(false)
                            .show();
        }
    }

    @CallSuper
    @Override
    protected void init(Bundle savedInstanceState) {
        // 绑定到生命周期
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
    public void showModalLoading(final String txt, final boolean cancelable) {
        dismissLoadingDialog();
        sHandler.postDelayed(mLoadingRunnable = new LoadingRunnable(txt, cancelable), 350);
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected P createPresenter() {
        return null;
    }


    @Override
    public void closeModalLoading() {
        dismissLoadingDialog();
    }

    private void dismissLoadingDialog() {
        if(mLoadingRunnable != null) {
            sHandler.removeCallbacks(mLoadingRunnable);
        }
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public RxBaseActivity getRxContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        // 解绑
        if (getPresenter() != null) getPresenter().unRegisterLifeCycle();
        super.onDestroy();
    }


}
