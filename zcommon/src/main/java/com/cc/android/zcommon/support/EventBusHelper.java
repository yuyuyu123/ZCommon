package com.cc.android.zcommon.support;

import org.greenrobot.eventbus.EventBus;

/**
 * EventBus工具类
 * Description:
 */
public class EventBusHelper {
  private static EventBus eventBus = EventBus.getDefault();

  /**
   * 注册EventBus
   */
  public static void register(Object subscriber) {
    eventBus.register(subscriber);
  }

  /**
   *
   * @param subscriber
   */
  public static void unregister(Object subscriber) {
    eventBus.unregister(subscriber);
  }

  public static void post(Object target) {
    eventBus.post(target);
  }
}
