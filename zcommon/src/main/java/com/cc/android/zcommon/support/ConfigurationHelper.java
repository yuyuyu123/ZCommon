package com.cc.android.zcommon.support;

import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;

import com.ccclubs.common.api.RetrofitFactory;

import java.util.Map;

import okhttp3.OkHttpClient;

/**
 * 项目配置
 */
public class ConfigurationHelper {
  /**
   * 是否竖屏显示
   */
  private static boolean isScreenPortrait = false;
  /**
   * 是否打印网络参数
   */
  private static boolean isShowNetworkParams = false;

  /**
   * 是否打印网络参数
   */
  private static int modalLoadingColor = -1;

  private static String modalLoadingText = "请稍候...";

  /**
   * 开启竖屏显示
   */
  public static void enableScreenPortrait() {
    isScreenPortrait = true;
  }

  /**
   * 获取横屏显示
   */
  public static boolean getScreenPortrait() {
    return isScreenPortrait;
  }

  /**
   * 获取模态dialog颜色值
   */
  public static int getModalLoadingColor() {
    return modalLoadingColor;
  }

  /**
   * 设置模态dialog颜色值
   */
  public static void setModalLoadingColor(@ColorRes int modalLoadingColor) {
    ConfigurationHelper.modalLoadingColor = modalLoadingColor;
  }

  /**
   * 获取模态dialog显示字符串
   */
  public static String getModalLoadingText() {
    return modalLoadingText;
  }

  /**
   * 设置模态dialog显示字符串
   */
  public static void setModalLoadingText(String modalLoadingText) {
    ConfigurationHelper.modalLoadingText = modalLoadingText;
  }

  /**
   * 开启打印网络请求参数
   */
  public static void enableLoggingNetworkParams() {
    isShowNetworkParams = true;
  }

  /**
   * 关闭打印网络请求参数
   */
  public static void disableLoggingNetworkParams() {
    isShowNetworkParams = false;
  }

  /**
   * 是否打印网络请求参数
   */
  public static boolean isShowNetworkParams() {
    return isShowNetworkParams;
  }

  /**
   * 设置baseUrl
   */
  public static void setBaseUrl(@NonNull String baseUrl) {
    RetrofitFactory.setBaseUrl(baseUrl);
  }

  public static void setBaseUrlMap(@NonNull Map<String, String> map) {
    RetrofitFactory.setBaseUrlMap(map);
  }

  /**
   * 设置OkHttpClient
   */
  public static void setOkhttpClient(@NonNull OkHttpClient client) {
    RetrofitFactory.setOkhttpClient(client);
  }

  public static void setOkhttpClientMap(@NonNull Map<String, OkHttpClient> map) {
    RetrofitFactory.setOkhttpClientMap(map);
  }
}
