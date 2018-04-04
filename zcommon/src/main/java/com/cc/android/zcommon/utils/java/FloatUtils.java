package com.cc.android.zcommon.utils.java;

import java.math.BigDecimal;

/**
 * Float相关的工具函数
 */
public class FloatUtils {
  /**
   * 默认保留2位小数
   */
  public static float formatFloat(double d) {
    return formatFloat(d, 2);
  }

  /**
   * 保留两位小数，
   */
  public static float formatFloat(double d, int num) {
    BigDecimal bd = new BigDecimal(d);
    bd = bd.setScale(num, BigDecimal.ROUND_HALF_UP);
    return bd.floatValue();
  }

  public static float formatFloat(String i) {
    try {
      BigDecimal b = new BigDecimal(i);
      BigDecimal bb = b.setScale(2, BigDecimal.ROUND_HALF_UP);
      return bb.floatValue();
    } catch (Exception e) {
      return 0.00f;
    }
  }
}
