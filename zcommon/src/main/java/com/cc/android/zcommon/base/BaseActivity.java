package com.cc.android.zcommon.base;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.cc.android.zcommon.R;
import com.cc.android.zcommon.event.NoEvent;
import com.cc.android.zcommon.netstate.NetStateReceiver;
import com.cc.android.zcommon.support.ActivityManagerHelper;
import com.cc.android.zcommon.support.ConfigurationHelper;
import com.cc.android.zcommon.support.EventBusHelper;
import com.cc.android.zcommon.utils.android.StatusBarUtil;
import com.cc.android.zcommon.utils.android.ToastUtils;

import org.greenrobot.eventbus.Subscribe;

/**
 * Activity基类, 继承自此类的Activity需要实现{@link #getLayoutId},{@link #init}
 * 以及{@link #createPresenter()}, 不需要覆写onCreate方法.
 * <br/>
 * 实现此类需遵循MVP设计, 第一个泛型V需传入一个继承自{@link BaseView}的MVPView,
 * 第二个泛型需传入继承自{@link RxBasePresenter}的MVPPresenter.
 * <br/>
 * Presenter的生命周期已交由此类管理, 子类无需管理. 如果子类要使用多个Presenter, 则需要自行管理生命周期.
 * 此类已经实现了BaseView中的抽象方法, 子类无需再实现, 如需自定可覆写对应的方法.
 * <br/>
 */
public abstract class BaseActivity<V extends BaseView, T extends BasePresenter<V>>
        extends AppCompatActivity implements BaseActivityInterface, BaseView {

    protected static Handler sHandler = new Handler();
    private static long lastClickBackButtonTime; //记录上次点击返回按钮的时间，用来配合实现双击返回按钮退出应用程序的功能
    private int doubleClickSpacingInterval = 2 * 1000; //双击退出程序的间隔时间
    //private long createTime; //创建时间
    private boolean enableDoubleClickExitApplication; //是否开启双击退出程序功能
    // Log 的tag
    protected static String TAG = "TAG";
    // 网络链接状态
//    protected NetChangeObserver mNetChangeObserver = null;
    // 一些屏幕参数
    protected int mScreenWidth = 0;
    protected int mScreenHeight = 0;
    protected float mScreenDensity = 0.0f;

    protected T presenter;


    @CallSuper
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManagerHelper.getManager().addActivity(this);
        //如果需要去掉标题栏
        if (isRemoveTitleBar()) requestWindowFeature(Window.FEATURE_NO_TITLE);
        //如果需要全屏就去掉通知栏
        if (isRemoveStatusBar()) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        if(getLayoutView() == null) {
            setContentView(getLayoutId());
        } else {
            setContentView(getLayoutView());
        }
//        StatusBarUtil.showStatusBarWithLightMode(this, R.color.material_light_white);
        dynamicAddView();

        presenter = createPresenter();
        if (presenter != null) presenter.attachView((V) this);

        TAG = this.getClass().getSimpleName();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mScreenDensity = displayMetrics.density;
        mScreenHeight = displayMetrics.heightPixels;
        mScreenWidth = displayMetrics.widthPixels;
        EventBusHelper.register(this);
        init(savedInstanceState);
    }

    /**
     * 动态添加视图
     */
    public void dynamicAddView() {
    }

    public Toolbar initToolbar(@StringRes int title) {
        return initToolbar(getString(title));
    }

    public Toolbar initToolbar(@NonNull CharSequence title) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (null != toolbar) {
            setTitle(title);
            setSupportActionBar(toolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        return toolbar;
    }


    /**
     * 指定Activity需加载的布局ID, {@link BaseActivity}
     * 会通过{@link #setContentView}方法来加载布局
     *
     * @return 需加载的布局ID
     */
    protected abstract
    @LayoutRes
    int getLayoutId();

    protected View getLayoutView() {
        return null;
    };

    /**
     * 初始化方法, 类似OnCreate, 仅在此方法中做初始化操作, findView与事件绑定请使用ButterKnife
     */
    protected abstract void init(Bundle savedInstanceState);

    /**
     * 创建Presenter, 然后通过调用{@link #getPresenter()}来使用生成的Presenter
     *
     * @return Presenter
     */
    protected abstract T createPresenter();

    /**
     * 获取通过{@link #createPresenter()}生成的presenter对象
     *
     * @return Presenter
     */
    public T getPresenter() {
        return presenter;
    }

    @Override
    public void onBackPressed() {
        if (enableDoubleClickExitApplication) {
            long currentMillisTime = System.currentTimeMillis();
            //两次点击的间隔时间尚未超过规定的间隔时间将执行退出程序
            if (lastClickBackButtonTime != 0
                    && (currentMillisTime - lastClickBackButtonTime) < doubleClickSpacingInterval) {
                ActivityManagerHelper.getManager().finishAllActivity();
            } else {
                onPromptExitApplication();
                lastClickBackButtonTime = currentMillisTime;
            }
        } else {
            finish();
        }
    }

    @Override
    public void finish() {
        super.finish();
        ActivityManagerHelper.getManager().deleteActivity(this);

    }

    @Override
    protected void onDestroy() {
        sHandler.removeCallbacksAndMessages(null);
        if (presenter != null) presenter.detachView();
        presenter = null;
        super.onDestroy();
        EventBusHelper.unregister(this);
    }


    /**
     * 判断是否需要去除标题栏，默认不去除
     *
     * @return 是否需要去除标题栏
     */
    @Override
    public boolean isRemoveTitleBar() {
        return false;
    }

    /**
     * 判断是否需要全屏，默认不全屏
     *
     * @return 是否需要全屏
     */
    @Override
    public boolean isRemoveStatusBar() {
        return false;
    }

    @Override
    public boolean isSwipeBackEnabled() {
        return true;
    }

    @Override
    public boolean isNetworkAvailable() {
        return NetStateReceiver.isNetworkAvailable();
    }

    @Override
    public SharedPreferences getDefaultPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(getBaseContext());
    }

    @Override
    public boolean isFirstUsing() {
        return getDefaultPreferences().getBoolean(PRFERENCES_FIRST_USING, true);
    }

    @Override
    public void setFirstUsing(boolean firstUsing) {
        SharedPreferences.Editor editor = getDefaultPreferences().edit();
        editor.putBoolean(PRFERENCES_FIRST_USING, firstUsing);
        editor.commit();
    }


    protected void onPromptExitApplication() {
        toastS("再按一次退出程序！");
    }

    public void setEnableDoubleClickExitApplication(boolean enableDoubleClickExitApplication) {
        this.enableDoubleClickExitApplication = enableDoubleClickExitApplication;
    }

    @Override
    public BaseActivity getViewContext() {
        return this;
    }

    /************************************************ Toast *************************************************/
    @Override
    public void toastL(@StringRes int resId) {
        ToastUtils.showToastL(this, resId);
    }

    @Override
    public void toastS(@StringRes int resId) {
        ToastUtils.showToastS(this, resId);
    }

    @Override
    public void toastL(@NonNull String content) {
        ToastUtils.showToastL(this, content);
    }

    @Override
    public void toastS(@NonNull String content) {
        ToastUtils.showToastS(this, content);
    }

    @Override
    public String getStringResource(int id) {
        return getResources().getString(id);
    }

    @Subscribe
    public void onEventMainThread(NoEvent msg) {
    }
}
