package com.cc.android.zcommon.base;

import android.content.SharedPreferences;

/**
 * Activity基类接口
 */
public interface BaseActivityInterface {
  /**
   * 最小用时
   */
  int MIN_USE_TIME = 1000;
  /**
   * Prefences选项 - 第一次使用
   */
  String PRFERENCES_FIRST_USING = "FIRST_USING";

		/* ********************************************** 常用 ************************************************ */

  /**
   * 终止应用程序
   */
//  public void finishApplication();

  /**
   * 当前网络是否可用
   */
  public boolean isNetworkAvailable();

  /**
   * 获取默认的Preferecnes对象
   *
   * @return 默认的Preferecnes对象
   */
  public SharedPreferences getDefaultPreferences();

//  /**
//   * 获取当前Activity的ID
//   */
//  public long getActivityId();

  /**
   * 判断是否是第一次使用
   *
   * @return 是否是第一次使用
   */
  public boolean isFirstUsing();

  /**
   * 设置是否是第一次使用
   *
   * @param firstUsing 是否是第一次使用
   */
  public void setFirstUsing(boolean firstUsing);

  	/* ********************************************** 初始化 ************************************************ */

  /**
   * 判断是否需要去除标题栏，默认不去除
   *
   * @return 是否需要去除标题栏
   */
  public boolean isRemoveTitleBar();

  public boolean isSwipeBackEnabled();
  /**
   * 判断是否需要去掉状态栏
   *
   * @return 是否需要去掉状态栏
   */
  public boolean isRemoveStatusBar();

  /* ********************************************** Toast ************************************************ */

  /**
   * 吐出一个显示时间较长的提示
   *
   * @param resId 显示内容资源ID
   */
  public void toastL(int resId);

  /**
   * 吐出一个显示时间较短的提示
   *
   * @param resId 显示内容资源ID
   */
  public void toastS(int resId);

  /**
   * 吐出一个显示时间较长的提示
   *
   * @param content 显示内容
   */
  public void toastL(String content);

  /**
   * 吐出一个显示时间较短的提示
   *
   * @param content 显示内容
   */
  public void toastS(String content);

  /**
   * Get string resource.
   * @param id resouce id.
   * @return text content.
   */
  public String getStringResource(int id);
}