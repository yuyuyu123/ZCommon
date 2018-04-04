package com.cc.android.zcommon.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.cc.android.zcommon.R;
import com.cc.android.zcommon.support.EventBusHelper;


/**
 * Fragment基类, 继承自此类的Fragment需要实现{@link #getLayoutId}, {@link #init}
 * 以及{@link #createPresenter()}, 不需要覆写onCreate方法.
 * <br/>
 * Fragment基类中{@link #isBindEventBusHere()} 注册EventBus，使用时，需要自行注册、注销
 * <br/>
 * 鉴于FragmentManager的attach与detach会销毁Fragment的视图, 此基类会在onCreate中生成一个
 * parentView, 缓存起来, 并在onCreateView中直接返回该View, 来达到保存Fragment视图状态的目的,
 * 同时避免不停的销毁与创建.
 * <br/>
 * 实现此类需遵循MVP设计, 第一个泛型V需传入一个继承自{@link BaseView}的MVPView,
 * 第二个泛型需传入继承自{@link BasePresenter}的MVPPresenter.
 * <br/>
 * Presenter的生命周期已交由此类管理, 子类无需管理. 如果子类要使用多个Presenter, 则需要自行管理生命周期.
 * 此类已经实现了BaseView中的抽象方法, 子类无需再实现, 如需自定可覆写对应的方法.
 * <br/>
 * 对于多个Presenter，需要重写 onCreate，ondDestroy 方法
 *
 * ==================================================
 * 注1:
 * 如果是与ViewPager一起使用，调用的是setUserVisibleHint。
 *
 * 注2:
 * 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
 * 针对初始就show的Fragment 为了触发onHiddenChanged事件 达到lazy效果 需要先hide再show
 * eg:
 * transaction.hide(aFragment);
 * transaction.show(aFragment);
 */
public abstract class BaseFragment<V extends BaseView, T extends BasePresenter<V>> extends Fragment
    implements BaseView {
  // Log 的tag
  protected static String TAG = "TAG";

  protected View parentView;
  protected T presenter;

  // 一些屏幕参数
  protected int mScreenWidth = 0;
  protected int mScreenHeight = 0;
  protected float mScreenDensity = 0.0f;

  @CallSuper
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    presenter = createPresenter();
    if (presenter != null) presenter.attachView((V) this);
    TAG = this.getClass().getSimpleName();
    if (isBindEventBusHere()) {
      EventBusHelper.register(this);
    }
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    initData();

  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    parentView = inflater.inflate(getLayoutId(), null, false);
    init(savedInstanceState, parentView);
    return parentView;
  }

  public Toolbar initToolbar(View view, @StringRes int title) {
    return initToolbar(view, getString(title));
  }

  public Toolbar initToolbar(View view, @NonNull CharSequence title) {
    Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
    if (null != toolbar) {
      getActivity().setTitle(title);
      ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
      ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
      ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    return toolbar;
  }

  /**
   * 如果是与ViewPager一起使用，调用的是setUserVisibleHint
   *
   * @param isVisibleToUser 是否显示出来了
   */
  @Override
  public void setUserVisibleHint(boolean isVisibleToUser) {
    super.setUserVisibleHint(isVisibleToUser);
    if (getUserVisibleHint()) {
      onVisible();
    } else {
      onInvisible();
    }
  }

  /**
   * 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
   * 若是初始就show的Fragment 为了触发该事件 需要先hide再show
   *
   * @param hidden hidden True if the fragment is now hidden, false if it is not
   * visible.
   */
  @Override
  public void onHiddenChanged(boolean hidden) {
    super.onHiddenChanged(hidden);
    if (!hidden) {
      onVisible();
    } else {
      onInvisible();
    }
  }

  protected void onVisible() {
  }

  protected void onInvisible() {
  }

  @Override
  public void onDestroy() {
    if (presenter != null) presenter.detachView();
    presenter = null;
    if (isBindEventBusHere()) {
      EventBusHelper.unregister(this);
    }
    super.onDestroy();
  }

  public T getPresenter() {
    return presenter;
  }

  public View getParentView() {
    return parentView;
  }

  /**
   * 指定Fragment需加载的布局ID
   *
   * @return 需加载的布局ID
   */
  protected abstract @LayoutRes
  int getLayoutId();

  protected abstract boolean isBindEventBusHere();

  protected void init(Bundle savedInstanceState, View view) {}

  protected void initData() {
    DisplayMetrics displayMetrics = new DisplayMetrics();
    getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    mScreenDensity = displayMetrics.density;
    mScreenHeight = displayMetrics.heightPixels;
    mScreenWidth = displayMetrics.widthPixels;
  }

  public void titleRightClicked() {}

  public void titleLeftClicked() {getActivity().finish();}
  /**
   * 创建Presenter, 然后通过调用{@link #getPresenter()}来使用生成的Presenter
   *
   * @return Presenter
   */
  protected abstract T createPresenter();

  @Override
  public BaseActivity getViewContext() {
    return getActivity() instanceof BaseActivity ? ((BaseActivity) getActivity()) : null;
  }

  public String getStringResource(int id) {
    return getActivity().getResources().getString(id);
  }

  public void hideSoftKey(View view) {
    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
  }
}
