package com.cc.android.zcommon.utils.java;

import java.util.Random;

/**
 * <h2>整形数据工具类，提供一些有关整形数据的便捷方法</h2>
 * <br/>
 * (01)、获取指定数量的随机数，可以指定是否允许重复：static int[]
 * getRandoms(int size, int maxValue, boolean repeat) <br/>
 * (02)、计算chuShu与beiChuShu的百分比，默认保留两位小数：static String
 * countPercent(int chuShu, int beiChuShu) <br/>
 * (03)、计算chuShu与beiChuShu的百分比，并将结果用df格式化：static String
 * countPercent(int chuShu, int beiChuShu, DecimalFormat df)
 */
public class IntegerUtils {
  /**
   * (01)、获取指定数量的随机数，可以指定是否允许重复
   *
   * @param size 指定数量,取值范围大于0
   * @param maxValue 最大值，如果小于0，无限制
   * @param repeat 是否允许重复，true：允许
   * @return 长度为size的随机数数组
   */
  public static int[] getRandoms(int size, int maxValue, boolean repeat) {
    CheckingUtils.valiIntMinValue(size, 1, "size");

    Random random = new Random();
    int[] randoms = new int[size];
    if (repeat) { //如果允许重复
      if (maxValue < 0) { //如果无限制
        for (int w = 0; w < randoms.length; w++) {
          randoms[w] = random.nextInt();
        }
      } else { //如果有限制
        for (int w = 0; w < randoms.length; w++) {
          randoms[w] = random.nextInt(maxValue);
        }
      }
    } else { //如果不允许重复
      if (maxValue < 0) { //如果无限制
        int length = 0;
        while (length <= size) { //条件为长度小于等于size
          boolean skip = false;
          int ran = random.nextInt();
          for (int e = 0; e < length; e++) { //遍历寻找是否有重复的
            if (randoms[e] == ran) { //有的话
              skip = true; //跳
              break; //结束本层循环
            }
          }
          if (skip) { //如果跳
            continue;
          }
          randoms[length++] = ran; //如果不跳，放进去，长度加1
        }
      } else { //如果有限制
        int length = 0;
        while (length <= size) { //条件为长度小于等于size
          boolean skip = false;
          int ran = random.nextInt(maxValue);
          for (int e = 0; e < length; e++) { //遍历寻找是否有重复的
            if (randoms[e] == ran) { //有的话
              skip = true; //跳
              break; //结束本层循环
            }
          }
          if (skip) { //如果跳
            continue;
          }
          randoms[length++] = ran; //如果不跳，放进去，长度加1
        }
      }
    }
    return randoms;
  }

  /**
   * 将字节数组data转换成一个整型数据
   *
   * @param data 源数组，如果长度为1则直接取第一个字节并强制转换为int；如果长度为2则先转为short再转为强制转为int；
   * 如果长度为4则采用位移的方式转为int，其它的情况直接抛出非法参数异常
   * @return 一个整型数据
   */
  public static int valueOf(byte[] data) {
    int res = 0;
    if (data.length >= 4) {
      res = (data[0] << 24) + ((data[1] & 0xFF) << 16) + ((data[2] & 0xFF) << 8) + (data[3] & 0xFF);
    } else if (data.length == 2) {
      res = ShortUtils.valueOf(data);
    } else if (data.length == 1) {
      res = data[0];
    } else {
      throw new IllegalArgumentException();
    }
    return res;
  }

  public static int[] valueOf(String content) {
    char[] chars = content.toCharArray();
    int[] ints = new int[(chars.length - 1) / 2];
    int f = 0;
    for (int w = 1; w < chars.length - 1; w += 2) {
      ints[f++] = Integer.valueOf(new String(chars, w, 1));
    }
    return ints;
  }

  /**
   * 将给定的数字转换为给定长度的字符串，位数不够用0补
   *
   * @param digit 给定的数字
   * @param stringLength 给定长度。如果给定的长度小于等于给定的数字转换为字符串后的长度的话不做任何处理
   * @return 例给定数字是2，给定长度是5，结果是00002
   */
  public static String fillZero(int digit, int stringLength) {
    return String.format("%0" + stringLength + "d", digit);
  }

  /**
   * 将给定的数字转换为给定长度的字符串，位数不够用0补
   *
   * @param digit 给定的数字
   * @param stringLength 给定长度。如果给定的长度小于等于给定的数字转换为字符串后的长度的话不做任何处理
   * @return 例给定数字是2，给定长度是5，结果是00002
   */
  public static String fillZero(long digit, int stringLength) {
    return String.format("%0" + stringLength + "d", digit);
  }

  /**
   * 转成int类型
   *
   * @author qsxiaogang
   * @createtime 2014-11-25 下午2:02:48
   */
  public static int getInteger(Object object, int defaultValue) {
    if (object == null || object.equals("")) {
      return defaultValue;
    }
    try {
      return Integer.parseInt(object.toString());
    } catch (ClassCastException e) {
      System.out.print(e.getMessage());
      return defaultValue;
    }
  }
}