package com.cc.android.zcommon.utils.android;

import android.util.Log;

/**
 *
 */
public class LogUtils {
  /**
   * This flag to indicate the log is enabled or disabled.
   */
  private static boolean isLogEnable = true;

  /**
   * Disable the log output.
   */
  public static void disableLog() {
    isLogEnable = false;
  }

  /**
   * Enable the log output.
   */
  public static void enableLog() {
    isLogEnable = true;
  }

  /**
   * Debug
   */
  public static void d(String tag, String msg) {
    if (isLogEnable) {
      StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
      Log.d(tag, rebuildMsg(stackTraceElement, msg));
    }
  }

  /**
   * Information
   */
  public static void i(String tag, String msg) {
    if (isLogEnable) {
      StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
      Log.i(tag, rebuildMsg(stackTraceElement, msg));
    }
  }

  /**
   * Verbose
   */
  public static void v(String tag, String msg) {
    if (isLogEnable) {
      StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
      Log.v(tag, rebuildMsg(stackTraceElement, msg));
    }
  }

  /**
   * Warning
   */
  public static void w(String tag, String msg) {
    if (isLogEnable) {
      StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
      Log.w(tag, rebuildMsg(stackTraceElement, msg));
    }
  }

  /**
   * Error
   */
  public static void e(String tag, String msg) {
    if (isLogEnable) {
      StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
      Log.e(tag, rebuildMsg(stackTraceElement, msg));
    }
  }

  /**
   * Rebuild Log Msg
   */
  private static String rebuildMsg(StackTraceElement stackTraceElement, String msg) {
    StringBuffer sb = new StringBuffer();
    sb.append(stackTraceElement.getFileName());
    sb.append(" (");
    sb.append(stackTraceElement.getLineNumber());
    sb.append(") ");
    sb.append(stackTraceElement.getMethodName());
    sb.append(": ");
    sb.append(msg);
    return sb.toString();
  }
}
