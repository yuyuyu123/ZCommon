package com.cc.android.zcommon.utils.java;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * <h2>日期时间工具类，提供一些有关日期时间的便捷方法</h2>
 */
public class DateTimeUtils {
  /*
   * **************************************获取DateFormat************************
	 * *********
	 */

  /**
   * 根据给定的样式以及给定的环境，获取一个日期格式化器
   *
   * @param dateStyle 给定的样式，取值范围为DateFormat中的静态字段DEFAULT或SHORT或LONG或MIDIUM或FULL
   * @param locale 给定的环境
   * @return 日期格式化器
   */
  public static DateFormat getDateFormat(int dateStyle, Locale locale) {
    return DateFormat.getDateInstance(dateStyle, locale);
  }

  /**
   * 根据给定的样式以及默认的环境，获取一个日期格式化器
   *
   * @param dateStyle 给定的样式，取值范围为DateFormat中的静态字段DEFAULT或SHORT或LONG或MIDIUM或FULL
   * @return 日期格式化器
   */
  public static DateFormat getDateFormat(int dateStyle) {
    return DateFormat.getDateInstance(dateStyle, Locale.getDefault());
  }

  /**
   * 根据默认的样式以及给定的环境，获取一个日期格式化器
   *
   * @param locale 给定的环境
   * @return 日期格式化器
   */
  public static DateFormat getDateFormat(Locale locale) {
    return DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
  }

  /**
   * 根据默认的样式以及默认的环境，获取一个日期格式化器
   *
   * @return 日期格式化器
   */
  public static DateFormat getDateFormat() {
    return DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.getDefault());
  }

  /**
   * 获取一个默认格式（yyyy-MM-dd）的日期格式化器
   *
   * @return 日期格式化器
   */
  public static DateFormat getDateFormatByDefult() {
    return getDateTimeFormatByCustom("yyyy-MM-dd");
  }

	/*
   * **************************************获取TimeFormat************************
	 * *********
	 */

  /**
   * 根据给定的样式以及给定的环境，获取一个时间格式化器
   *
   * @param timeStyle 给定的样式，取值范围为DateFormat中的静态字段DEFAULT或SHORT或LONG或MIDIUM或FULL
   * @param locale 给定的环境
   * @return 时间格式化器
   */
  public static DateFormat getTimeFormat(int timeStyle, Locale locale) {
    return DateFormat.getTimeInstance(timeStyle, locale);
  }

  /**
   * 根据给定的样式以及默认的环境，获取一个时间格式化器
   *
   * @param timeStyle 给定的样式，取值范围为DateFormat中的静态字段DEFAULT或SHORT或LONG或MIDIUM或FULL
   * @return 时间格式化器
   */
  public static DateFormat getTimeFormat(int timeStyle) {
    return DateFormat.getTimeInstance(timeStyle, Locale.getDefault());
  }

  /**
   * 根据默认的样式以及给定的环境，获取一个时间格式化器
   *
   * @param locale 给定的环境
   * @return 时间格式化器
   */
  public static DateFormat getTimeFormat(Locale locale) {
    return DateFormat.getTimeInstance(DateFormat.DEFAULT, locale);
  }

  /**
   * 根据默认的样式以及默认的环境，获取一个时间格式化器
   *
   * @return 时间格式化器
   */
  public static DateFormat getTimeFormat() {
    return DateFormat.getTimeInstance(DateFormat.DEFAULT, Locale.getDefault());
  }

  /**
   * 获取一个默认格式（hh:mm:ss）的时间格式化器
   *
   * @return 时间格式化器
   */
  public static DateFormat getTimeFormatByDefult() {
    return getDateTimeFormatByCustom("HH:mm:ss");
  }

  /**
   * 获取一个默认的24小时制格式（HH:mm:ss）的时间格式化器
   *
   * @return 时间格式化器
   */
  public static DateFormat getTimeFormatByDefult24Hour() {
    return getDateTimeFormatByCustom("HH:mm:ss");
  }

	/*
   * **************************************获取DateTimeFormat********************
	 * *************
	 */

  /**
   * 根据给定的日期样式、时间样式以及环境，获取一个日期时间格式化器
   *
   * @param dateStyle 给定的日期样式，取值范围为DateFormat中的静态字段DEFAULT或SHORT或LONG或MIDIUM或FULL
   * @param timeStyle 给定的时间样式
   * @param locale 给定的环境
   * @return 日期时间格式化器
   */
  public static DateFormat getDateTimeFormat(int dateStyle, int timeStyle, Locale locale) {
    return DateFormat.getDateTimeInstance(dateStyle, timeStyle, locale);
  }

  /**
   * 根据给定的日期样式、时间样式以及默认的环境，获取一个日期时间格式化器
   *
   * @param dateStyle 给定的日期样式，取值范围为DateFormat中的静态字段DEFAULT或SHORT或LONG或MIDIUM或FULL
   * @param timeStyle 给定的时间样式
   * @return 日期时间格式化器
   */
  public static DateFormat getDateTimeFormat(int dateStyle, int timeStyle) {
    return DateFormat.getDateTimeInstance(dateStyle, timeStyle, Locale.getDefault());
  }

  /**
   * 根据默认的日期样式、时间样式以及给定的环境，获取一个日期时间格式化器
   *
   * @param locale 给定的环境
   * @return 日期时间格式化器
   */
  public static DateFormat getDateTimeFormat(Locale locale) {
    return DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, locale);
  }

  /**
   * 根据默认的日期样式、时间样式以及默认的环境，获取一个日期时间格式化器
   *
   * @return 日期时间格式化器
   */
  public static DateFormat getDateTimeFormat() {
    return DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT,
        Locale.getDefault());
  }

  /**
   * 获取一个自定义格式的日期时间格式化器
   *
   * @param customFormat 给定的自定义格式，例如："yyyy-MM-dd hh:mm:ss"
   * @return 日期时间格式化器
   */
  public static DateFormat getDateTimeFormatByCustom(String customFormat) {
    return new SimpleDateFormat(customFormat, Locale.getDefault());
  }

  /**
   * 获取一个默认格式的（yyyy-MM-dd hh:mm:ss）日期时间格式化器
   *
   * @return 日期时间格式化器
   */
  public static DateFormat getDateTimeFormatByDefult() {
    return getDateTimeFormatByCustom("yyyy-MM-dd HH:mm:ss");
  }

  /**
   * 获取一个默认24小时制格式的（yyyy-MM-dd HH:mm:ss）日期时间格式化器
   *
   * @return 日期时间格式化器
   */
  public static DateFormat getDateTimeFormatByDefult24Hour() {
    return getDateTimeFormatByCustom("yyyy-MM-dd HH:mm:ss");
  }

	/*
	 * **************************************获取其它具体的Format***********************
	 * **********
	 */

  /**
   * 获取年份格式化器
   *
   * @return 年份格式化器
   */
  public static DateFormat getYearFormat() {
    return getDateTimeFormatByCustom("yyyy");
  }

  /**
   * 获取月份格式化器
   *
   * @return 月份格式化器
   */
  public static DateFormat getMonthFormat() {
    return getDateTimeFormatByCustom("MM");
  }

  /**
   * 获取日份格式化器
   *
   * @return 日份格式化器
   */
  public static DateFormat getDayFormat() {
    return getDateTimeFormatByCustom("dd");
  }

  /**
   * 获取星期格式化器
   *
   * @return 星期格式化器
   */
  public static DateFormat getWeekFormat() {
    return getDateTimeFormatByCustom("E");
  }

  /**
   * 获取小时格式化器
   *
   * @return 小时格式化器
   */
  public static DateFormat getHourFormat() {
    return getDateTimeFormatByCustom("hh");
  }

  /**
   * 获取24小时制的小时格式化器
   *
   * @return 24小时格式化器
   */
  public static DateFormat getHourFormatBy24() {
    return getDateTimeFormatByCustom("HH");
  }

  /**
   * 获取分钟格式化器
   *
   * @return 分钟格式化器
   */
  public static DateFormat getMinuteFormat() {
    return getDateTimeFormatByCustom("mm");
  }

  /**
   * 获取秒格式化器
   *
   * @return 秒格式化器
   */
  public static DateFormat getSecondFormat() {
    return getDateTimeFormatByCustom("ss");
  }

	/*
	 * **************************************获取日期时间******************************
	 * ***
	 */

  /**
   * 根据给定的格式化器，获取当前的日期时间
   *
   * @param fromat 给定的格式化器
   * @return 当前的日期时间
   */
  public static String getCurrentDateTimeByFormat(DateFormat fromat) {
    return fromat.format(new Date());
  }

  /**
   * 根据给定的自定义的格式，获取当前的日期时间
   *
   * @param customFormat 给定的自定义的格式，例如："yyyy-MM-dd hh:mm:ss"
   * @return 当前的日期时间
   */
  public static String getCurrentDateTimeByFormat(String customFormat) {
    return getCurrentDateTimeByFormat(getDateTimeFormatByCustom(customFormat));
  }

  /**
   * 根据默认的格式（yyyy-MM-dd hh:mm:ss）获取当前的日期时间
   *
   * @return 当前的日期时间
   */
  public static String getCurrentDateTimeByDefultFormat() {
    return getCurrentDateTimeByFormat(getDateTimeFormatByDefult());
  }

  /**
   * 根据默认的24小时制格式（yyyy-MM-dd HH:mm:ss）获取当前的日期时间
   *
   * @return 当前的日期时间
   */
  public static String getCurrentDateTimeByDefult24HourFormat() {
    return getCurrentDateTimeByFormat(getDateTimeFormatByDefult24Hour());
  }

  /**
   * 根据给定的日期样式、时间样式以及环境，获取当前的日期时间
   *
   * @param dateStyle 给定的日期样式，取值范围为DateFormat中的静态字段DEFAULT或SHORT或LONG或MIDIUM或FULL
   * @param timeStyle 给定的时间样式，取值范围为DateFormat中的静态字段DEFAULT或SHORT或LONG或MIDIUM或FULL
   * @param locale 给定的环境
   * @return 当前的日期时间的字符串表示形式
   */
  public static String getCurrentDateTime(int dateStyle, int timeStyle, Locale locale) {
    return getCurrentDateTimeByFormat(getDateTimeFormat(dateStyle, timeStyle, locale));
  }

  /**
   * 根据给定的日期样式、时间样式以及默认的环境，获取当前的日期时间
   *
   * @param dateStyle 给定的日期样式，取值范围为DateFormat中的静态字段DEFAULT或SHORT或LONG或MIDIUM或FULL
   * @param timeStyle 给定的时间样式，取值范围为DateFormat中的静态字段DEFAULT或SHORT或LONG或MIDIUM或FULL
   * @return 当前的日期时间
   */
  public static String getCurrentDateTime(int dateStyle, int timeStyle) {
    return getCurrentDateTimeByFormat(getDateTimeFormat(dateStyle, timeStyle));
  }

  /**
   * 根据默认的日期样式、时间样式以及给定的环境，获取当前的日期时间
   *
   * @param locale 给定的环境
   * @return 当前的日期时间的字符串表示形式
   */
  public static String getCurrentDateTime(Locale locale) {
    return getCurrentDateTimeByFormat(getDateTimeFormat(locale));
  }

  /**
   * 根据默认的日期样式、默认的时间样式以及当前默认的环境，获取当前的日期时间
   *
   * @return 当前的日期时间
   */
  public static String getCurrentDateTime() {
    return getCurrentDateTimeByFormat(getDateTimeFormat());
  }

	/*
	 * **************************************获取日期********************************
	 * *
	 */

  /**
   * 根据给定的样式以及给定的环境，获取当前的日期
   *
   * @param dateStyle 给定的样式，取值范围为DateFormat中的静态字段DEFAULT或SHORT或LONG或MIDIUM或FULL
   * @param locale 给定的环境
   * @return 当前的日期
   */
  public static String getCurrentDate(int dateStyle, Locale locale) {
    return getCurrentDateTimeByFormat(getDateFormat(dateStyle, locale));
  }

  /**
   * 根据给定的样式以及默认的环境，获取当前的日期
   *
   * @param dateStyle 给定的样式，取值范围为DateFormat中的静态字段DEFAULT或SHORT或LONG或MIDIUM或FULL
   * @return 当前的日期
   */
  public static String getCurrentDate(int dateStyle) {
    return getCurrentDateTimeByFormat(getDateFormat(dateStyle));
  }

  /**
   * 根据默认的样式以及给定的环境，获取当前的日期
   *
   * @param locale 给定的环境
   * @return 当前的日期
   */
  public static String getCurrentDate(Locale locale) {
    return getCurrentDateTimeByFormat(getDateFormat(locale));
  }

  /**
   * 根据默认的样式以及默认的环境，获取当前的日期
   *
   * @return 当前的日期
   */
  public static String getCurrentDate() {
    return getCurrentDateTimeByFormat(getDateFormat());
  }

  /**
   * 根据默认的自定义格式的获取当前的日期，格式为"yyyy-MM-dd"
   *
   * @return 当前的日期
   */
  public static String getCurrentDateByDefultFormat() {
    return getCurrentDateTimeByFormat(getDateFormatByDefult());
  }

	/*
	 * **************************************获取时间********************************
	 * *
	 */

  /**
   * 根据给定的样式以及给定的环境，获取当前的时间
   *
   * @param timeStyle 给定的样式，取值范围为DateFormat中的静态字段DEFAULT或SHORT或LONG或MIDIUM或FULL
   * @param locale 给定的环境
   * @return 当前的时间
   */
  public static String getCurrentTime(int timeStyle, Locale locale) {
    return getCurrentDateTimeByFormat(getTimeFormat(timeStyle, locale));
  }

  /**
   * 根据给定的样式以及默认的环境，获取当前的时间
   *
   * @param timeStyle 给定的样式，取值范围为DateFormat中的静态字段DEFAULT或SHORT或LONG或MIDIUM或FULL
   * @return 当前的时间
   */
  public static String getCurrentTime(int timeStyle) {
    return getCurrentDateTimeByFormat(getTimeFormat(timeStyle));
  }

  /**
   * 根据默认的样式以及给定的环境，获取当前的时间
   *
   * @param locale 给定的环境
   * @return 当前的时间
   */
  public static String getCurrentTime(Locale locale) {
    return getCurrentDateTimeByFormat(getTimeFormat(locale));
  }

  /**
   * 根据默认的样式以及默认的环境，获取当前的时间
   *
   * @return 当前的时间
   */
  public static String getCurrentTime() {
    return getCurrentDateTimeByFormat(getTimeFormat());
  }

  /**
   * 根据默认格式的（hh:mm:ss）当前时间
   *
   * @return 当前的时间
   */
  public static String getCurrentTimeByDefultFormat() {
    return getCurrentDateTimeByFormat(getTimeFormatByDefult());
  }

  /**
   * 根据默认24小时制格式的（HH:mm:ss）当前时间
   *
   * @return 当前的时间
   */
  public static String getCurrentTimeByDefult24HourFormat() {
    return getCurrentDateTimeByFormat(getTimeFormatByDefult24Hour());
  }

	/*
	 * **************************************获取年*********************************
	 */

  /**
   * 获取给定的日期中的年份
   *
   * @param date 给定的日期
   * @return 年份
   */
  public static int getYear(Date date) {
    return Integer.valueOf(getYearFormat().format(date));
  }

  /**
   * 获取给定时间的毫秒值中的年份
   *
   * @param timeMillis 给定时间的毫秒值
   * @return 年份
   */
  public static int getYear(long timeMillis) {
    return getYear(new Date(timeMillis));
  }

  /**
   * 获取当前的年份
   *
   * @return 当前的年份
   */
  public static int getCurrentYear() {
    return getYear(System.currentTimeMillis());
  }

	/*
	 * **************************************获取月*********************************
	 */

  /**
   * 获取给定的日期中的月份
   *
   * @param date 给定的日期
   * @return 月份
   */
  public static int getMonth(Date date) {
    return Integer.valueOf(getMonthFormat().format(date));
  }

  /**
   * 获取给定时间的毫秒值中的月份
   *
   * @param timeMillis 给定时间的毫秒值
   * @return 月份
   */
  public static int getMonth(long timeMillis) {
    return getMonth(new Date(timeMillis));
  }

  /**
   * 获取当前的月份
   *
   * @return 当前的月份
   */
  public static int getCurrentMonth() {
    return getMonth(System.currentTimeMillis());
  }

	/*
	 * **************************************获取日*********************************
	 */

  /**
   * 获取给定的日期中的日份
   *
   * @param date 给定的日期
   * @return 日份
   */
  public static int getDay(Date date) {
    return Integer.valueOf(getDayFormat().format(date));
  }

  /**
   * 获取给定时间的毫秒值中的日份
   *
   * @param timeMillis 给定时间的毫秒值
   * @return 日份
   */
  public static int getDay(long timeMillis) {
    return getDay(new Date(timeMillis));
  }

  /**
   * 获取当前的日份
   *
   * @return 当前的日份
   */
  public static int getCurrentDay() {
    return getDay(System.currentTimeMillis());
  }

	/*
	 * **************************************获取小时********************************
	 * *
	 */

  /**
   * 获取给定的日期中的小时
   *
   * @param date 给定的日期
   * @return 小时
   */
  public static int getHour(Date date) {
    return Integer.valueOf(getHourFormat().format(date));
  }

  /**
   * 获取给定的时间的毫秒值中的小时
   *
   * @param timeMIllis 给定的时间的毫秒值
   * @return 小时
   */
  public static int getHour(long timeMIllis) {
    return getHour(new Date(timeMIllis));
  }

  /**
   * 获取当前的小时
   *
   * @return 当前的小时
   */
  public static int getCurrentHour() {
    return getHour(System.currentTimeMillis());
  }

  /**
   * 获取给定的日期中的24小时制的小时
   *
   * @param date 给定的日期
   * @return 小时
   */
  public static int getHourBy24(Date date) {
    return Integer.valueOf(getHourFormatBy24().format(date));
  }

  /**
   * 获取给定的时间的毫秒值中的小时
   *
   * @param timeMillis 给定的时间的毫秒值
   * @return 小时
   */
  public static int getHourBy24(long timeMillis) {
    return getHourBy24(new Date(timeMillis));
  }

  /**
   * 获取当前的小时
   *
   * @return 当前的小时
   */
  public static int getCurrentHourBy24() {
    return getHourBy24(System.currentTimeMillis());
  }

	/*
	 * **************************************获取分*********************************
	 */

  /**
   * 获取给定的日期中的分钟
   *
   * @param date 给定的日期
   * @return 分钟
   */
  public static int getMinute(Date date) {
    return Integer.valueOf(getMinuteFormat().format(date));
  }

  /**
   * 获取给定的时间的毫秒值中的分钟
   *
   * @param timeMillis 给定的时间的毫秒值
   * @return 分钟
   */
  public static int getMinute(long timeMillis) {
    return getMinute(new Date(timeMillis));
  }

  /**
   * 获取当前的分钟
   *
   * @return 当前的分钟
   */
  public static int getCurrentMinute() {
    return getMinute(System.currentTimeMillis());
  }

	/*
	 * **************************************获取秒*********************************
	 */

  /**
   * 获取给定的日期中的秒
   *
   * @param date 给定的日期
   * @return 秒
   */
  public static int getSecond(Date date) {
    return Integer.valueOf(getSecondFormat().format(date));
  }

  /**
   * 获取给定的时间的毫秒值中的秒
   *
   * @param timeMillis 给定的时间的毫秒值
   * @return 秒
   */
  public static int getSecond(long timeMillis) {
    return getSecond(new Date(timeMillis));
  }

  /**
   * 获取当前的秒
   *
   * @return 当前的秒
   */
  public static int getCurrentSecond() {
    return getSecond(System.currentTimeMillis());
  }

	/* **************************************其它********************************* */

  /**
   * 将字符串型的日期当中的'-'替换成相应的'年'、'月'、'日'
   *
   * @param strDate 字符串型的日期，例如：2010-01-01或2010-01
   * @return 2010年01月01日或2010年01月
   */
  public static String converDate(String strDate) {
    StringBuffer sb = new StringBuffer();
    char[] chars = strDate.toCharArray();
    for (int w = 0; w < strDate.length(); w++) {
      sb.append(chars[w]);
      if (w + 1 == 4) {
        sb.append('年');
        w++;
      } else if (w + 1 == 7) {
        sb.append('月');
        w++;
      } else if (w + 1 == 10) {
        sb.append('日');
        w++;
      }
    }
    return sb.toString();
  }

  /**
   * 将字符串型的时间当中的':'替换成相应的'时'、'分'、'秒'
   *
   * @param time 字符串型的时间
   */
  public static String converTime(String time) {
    StringBuffer sb = new StringBuffer();
    char[] chars = time.toCharArray();
    for (int w = 0; w < time.length(); w++) {
      sb.append(chars[w]);
      if (w + 1 == 2) {
        sb.append('时');
        w++;
      } else if (w + 1 == 5) {
        sb.append('分');
        w++;
      } else if (w + 1 == 8) {
        sb.append('秒');
        w++;
      }
    }
    return sb.toString();
  }

  /**
   * 获取从当前的年份开始向前的年份
   *
   * @param number 获取的个数
   * @return 从当前的年份开始向前的年份
   */
  public static String[] getBygoneYear(int number) {
    String[] years = new String[number];
    int currentYear = Integer.valueOf(getCurrentYear());
    for (int w = 0; w < number; w++) {
      years[w] = String.valueOf(currentYear - w);
    }
    return years;
  }

  /**
   * 将给定的毫秒时间转换为完整的天时分秒
   *
   * @param milliSecondTime 给定的毫秒时间
   * @param dayStr 天，当dayStr为null的时候将不会再处理到天，直接以小时计算，例如：2天16小时5分33秒（处理）；64小时5分33秒
   * （不处理），dayStr和hourStr至少有一个不能为null\，否则将返回null
   * @param hourStr 小时，当hourStr为null的时候将不会再处理到小时、分钟、秒、毫秒，例如：2天5小时6分钟59秒350毫秒（处理）；2
   * 天（不处理），dayStr和hourStr至少有一个不能为null，否则将返回null
   * @param minuteStr 分钟，当minuteStr为null的时候将不会再处理到分钟、秒、毫秒，例如：2天5小时6分钟59秒350毫秒（处理）；2
   * 天5小时（不处理）
   * @param secondStr 秒，当secondStr为null的时候将不会再处理到秒、毫秒，例如：2天5小时6分钟59秒350毫秒（处理）；2
   * 天5小时6分钟（不处理）
   * @param milliSecondStr 毫秒，当milliSecondStr为null的时候将不会再处理到毫秒，例如：2天5小时6分钟59秒350毫秒（处理）；2
   * 天5小时6分钟59秒（不处理）
   * @param isFillZero 当小时、分钟、秒不足两位时以及毫秒不足三位时是否用0补充。例如：02小时05分钟06秒025毫秒（补充）；2
   * 小时5分钟6秒25毫秒（不补充）
   * @param isFull 是否是完整的，例如：2天0小时4分0秒111毫秒（true）；2天4分111毫秒（false）
   * @return 完整的时分秒，例如：2天5小时6分钟59秒350毫秒
   */
  public static String milliSecondToDayHourMinuteSecond(long milliSecondTime, String dayStr,
                                                        String hourStr, String minuteStr, String secondStr, String milliSecondStr, boolean isFillZero,
                                                        boolean isFull) {
    if (milliSecondTime < 1) {
      return null;
    }

    int milliSecondSurplus = (int) (milliSecondTime % 1000);
    long second = milliSecondTime / 1000;
    int secondSurplus = (int) (second % 60);
    long minute = second / 60;
    int minuteSurplus = (int) (minute % 60);
    long hour = minute / 60;
    int hourSurplus = (int) (hour % 24);
    long day = hour / 24;

    // 拼接天
    if (dayStr != null) {
      StringBuffer stringBuffer = new StringBuffer();

      if (isFull || day > 0) {
        stringBuffer.append(day);
        stringBuffer.append(dayStr);
      }

      // 拼接小时
      if (hourStr != null) {
        if (isFull || hourSurplus > 0) {
          stringBuffer.append(isFillZero ? IntegerUtils.fillZero(hourSurplus, 2) : hourSurplus);
          stringBuffer.append(hourStr);
        }

        // 拼接分钟
        if (minuteStr != null) {
          if (isFull || minuteSurplus > 0) {
            stringBuffer.append(
                isFillZero ? IntegerUtils.fillZero(minuteSurplus, 2) : minuteSurplus);
            stringBuffer.append(minuteStr);
          }

          // 拼接秒
          if (secondStr != null) {
            if (isFull || secondSurplus > 0) {
              stringBuffer.append(
                  isFillZero ? IntegerUtils.fillZero(secondSurplus, 2) : secondSurplus);
              stringBuffer.append(secondStr);
            }

            // 拼接毫秒
            if (milliSecondStr != null) {
              if (isFull || milliSecondSurplus > 0) {
                stringBuffer.append(
                    isFillZero ? IntegerUtils.fillZero(milliSecondSurplus, 3) : milliSecondSurplus);
                stringBuffer.append(milliSecondStr);
              }
            }
          }
        }
      }

      return stringBuffer.toString();
    } else {
      // 拼接小时
      if (hourStr != null) {
        StringBuffer stringBuffer = new StringBuffer();

        if (isFull || hourSurplus > 0) {
          stringBuffer.append(isFillZero ? IntegerUtils.fillZero(hour, 2) : hour);
          stringBuffer.append(hourStr);
        }

        // 拼接分钟
        if (minuteStr != null) {
          if (isFull || minuteSurplus > 0) {
            stringBuffer.append(
                isFillZero ? IntegerUtils.fillZero(minuteSurplus, 2) : minuteSurplus);
            stringBuffer.append(minuteStr);
          }

          // 拼接秒
          if (secondStr != null) {
            if (isFull || secondSurplus > 0) {
              stringBuffer.append(
                  isFillZero ? IntegerUtils.fillZero(secondSurplus, 2) : secondSurplus);
              stringBuffer.append(secondStr);
            }

            // 拼接毫秒
            if (milliSecondStr != null) {
              if (isFull || milliSecondSurplus > 0) {
                stringBuffer.append(
                    isFillZero ? IntegerUtils.fillZero(milliSecondSurplus, 3) : milliSecondSurplus);
                stringBuffer.append(milliSecondStr);
              }
            }
          }
        }

        return stringBuffer.toString();
      } else {
        return null;
      }
    }
  }

  /**
   * 将给定的毫秒时间转换为以':'分割的时分秒
   *
   * @param milliSecond 毫秒给定的毫秒时间
   * @return 以':'分割的时分秒
   */
  public static String milliSecondToHourMinuteSecond(long milliSecond) {
    return milliSecondToDayHourMinuteSecond(milliSecond, null, ":", ":", null, null, true, true);
  }

  /**
   * 将给定的小时按24小时制往后推移
   *
   * @param hour 给定的小时
   * @param addNumber 往后推移小时数
   * @return 例如：23点 向后推移5个小时结果是4点
   */
  public static int hourAddBy24Hour(int hour, int addNumber) {
    return hour + addNumber % 24;
  }

  /**
   * 将给定的小时按12小时制往后推移
   *
   * @param hour 给定的小时
   * @param addNumber 往后推移小时数
   * @return 例如：11点 向后推移5个小时结果是4点
   */
  public static int hourAddBy12Hour(int hour, int addNumber) {
    return hour + addNumber % 12;
  }

  /**
   * 获取当前的年、月（从1开始）、日、时（12小时制）、分
   *
   * @return 按顺序排放的int数组
   */
  public static int[] getCurrentTimes() {
    Date date = new Date(System.currentTimeMillis());
    return new int[] {
        Integer.valueOf(DateTimeUtils.getYearFormat().format(date)),
        Integer.valueOf(DateTimeUtils.getMonthFormat().format(date)),
        Integer.valueOf(DateTimeUtils.getDayFormat().format(date)),
        Integer.valueOf(DateTimeUtils.getHourFormat().format(date)),
        Integer.valueOf(DateTimeUtils.getMinuteFormat().format(date))
    };
  }

  /**
   * 获取当前的年、月（从1开始）、日、时（24小时制）、分
   *
   * @return 按顺序排放的int数组
   */
  public static int[] getCurrentTimesBy24Hour() {
    Date date = new Date(System.currentTimeMillis());
    return new int[] {
        Integer.valueOf(DateTimeUtils.getYearFormat().format(date)),
        Integer.valueOf(DateTimeUtils.getMonthFormat().format(date)),
        Integer.valueOf(DateTimeUtils.getDayFormat().format(date)),
        Integer.valueOf(DateTimeUtils.getHourFormatBy24().format(date)),
        Integer.valueOf(DateTimeUtils.getMinuteFormat().format(date))
    };
  }

  @SuppressLint("UseSparseArrays") private static final Map<Integer, String> WEEK_CHINESE_NAME_MAP =
      new HashMap<Integer, String>(7);

  /**
   * 通过 dayOfWeek，获取星期的中文名，如：一、二、三。。日
   *
   * @return 如：一、二、三。。日
   */
  public static String getWeekChineseName(int dayOfWeek) {
    if (WEEK_CHINESE_NAME_MAP.size() != 7) {
      WEEK_CHINESE_NAME_MAP.put(1, "日");
      WEEK_CHINESE_NAME_MAP.put(2, "一");
      WEEK_CHINESE_NAME_MAP.put(3, "二");
      WEEK_CHINESE_NAME_MAP.put(4, "三");
      WEEK_CHINESE_NAME_MAP.put(5, "四");
      WEEK_CHINESE_NAME_MAP.put(6, "五");
      WEEK_CHINESE_NAME_MAP.put(7, "六");
    }
    return WEEK_CHINESE_NAME_MAP.get(dayOfWeek % 8);
  }

  public static enum TIME_UNIT {
    MILLIS, SECS, MINS, HOURS, DAYS
  }

  /**
   * 1 秒 = 1000 毫秒
   */
  private static final long MILLIS_IN_SEC = 1000;
  /**
   * 1 分钟 = 60 秒
   */
  private static final long MILLIS_IN_MIN = MILLIS_IN_SEC * 60;
  /**
   * 1 小时 = 60 分钟
   */
  private static final long MILLIS_IN_HOUR = MILLIS_IN_MIN * 60;
  /**
   * 1 天 = 24 小时
   */
  private static final long MILLIS_IN_DAY = MILLIS_IN_HOUR * 24;
  private static String INVALID_DATE_ERR = "日期为空，或者不是有效的日期";
  private static String INVALID_CAL_ERR = "日历为空";

  /**
   * 获取 cal1 与 cal2 的时间差，返回毫秒
   *
   * @param timeUnit - 时间单位，毫秒，秒，分钟，小时，天
   * @return 如果 cal2 比 cal1 大则返回正数
   */
  public static long getDiff(Calendar cal1, Calendar cal2, TIME_UNIT timeUnit) {
    if (cal1 == null || cal2 == null || timeUnit == null) {
      throw new NullPointerException(INVALID_DATE_ERR);
    }

    long timeDiffInMillis = cal2.getTimeInMillis() - cal1.getTimeInMillis();

    switch (timeUnit) {
      case MILLIS:
        return timeDiffInMillis;
      case SECS:
        return timeDiffInMillis / MILLIS_IN_SEC;
      case MINS:
        return timeDiffInMillis / MILLIS_IN_MIN;
      case HOURS:
        return timeDiffInMillis / MILLIS_IN_HOUR;
      case DAYS:
        return timeDiffInMillis / MILLIS_IN_DAY;
      default:
        return timeDiffInMillis;
    }
  }

  /**
   * 获取 date1 与 date2 的时间差，返回毫秒
   *
   * @param date1 -
   * @param date2 -
   * @param timeUnit - 时间单位，毫秒，秒，分钟，小时，天
   * @return 如果 date2 比 date1 大则返回正数
   */
  public static long getDiff(Date date1, Date date2, TIME_UNIT timeUnit) {
    if (date1 == null || date2 == null || timeUnit == null) {
      throw new NullPointerException();
    }

    Calendar cal1 = Calendar.getInstance();
    cal1.setTime(date1);

    Calendar cal2 = Calendar.getInstance();
    cal2.setTime(date2);

    return getDiff(cal1, cal2, timeUnit);
  }

  /**
   * 返回 cal 指定时间单位 timeUnit 加上 amount 后的 Calendar
   *
   * @param cal -
   * @param amount - 数量 ，可为负数
   * @param timeUnit - 时间单位，毫秒，秒，分钟，小时，天
   * @return 返回改变后的 Calendar
   */
  public static Calendar addTime(Calendar cal, long amount, TIME_UNIT timeUnit) {
    if (cal == null) {
      throw new NullPointerException(INVALID_CAL_ERR);
    }
    Calendar newCalendar = Calendar.getInstance();
    newCalendar.setTime(cal.getTime());
    switch (timeUnit) {
      case MILLIS:
        newCalendar.setTimeInMillis(newCalendar.getTimeInMillis() + amount);
        return newCalendar;
      case SECS:
        long secondsToAdd = newCalendar.getTimeInMillis() + amount * MILLIS_IN_SEC;
        newCalendar.setTimeInMillis(secondsToAdd);
        return newCalendar;
      case MINS:
        long minsToAdd = newCalendar.getTimeInMillis() + amount * MILLIS_IN_MIN;
        newCalendar.setTimeInMillis(minsToAdd);
        return newCalendar;
      case HOURS:
        long hoursToAdd = newCalendar.getTimeInMillis() + amount * MILLIS_IN_HOUR;
        newCalendar.setTimeInMillis(hoursToAdd);
        return newCalendar;
      case DAYS:
        long daysToAdd = newCalendar.getTimeInMillis() + amount * MILLIS_IN_DAY;
        newCalendar.setTimeInMillis(daysToAdd);
        return newCalendar;
      default:
        return null;
    }
  }

  /**
   * 返回 date 指定时间单位 timeUnit 加上 amount 后的 Date
   *
   * @param amount - 数量 ，可为负数
   * @param timeUnit - 时间单位，毫秒，秒，分钟，小时，天
   * @return 返回改变后的 Date
   */
  public static Date addTime(Date date, long amount, TIME_UNIT timeUnit) {
    if (date == null) {
      throw new NullPointerException(INVALID_DATE_ERR);
    }
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    addTime(cal, amount, timeUnit);
    return cal.getTime();
  }

  /**
   * 按照 format 格式化一个 date
   *
   * @param date - 等待被格式化的日期类型
   * @param format - 格式化参数，如："yyyy-MM-dd HH:mm"
   * @return 被格式化的字符串
   */
  public static String formatDate(Date date, String format) {
    String dateStr = "";
    if (date == null) return "";
    try {
      dateStr = getDateTimeFormatByCustom(format).format(date);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return dateStr;
  }

  /**
   * 按照 format 格式化一个 date
   *
   * @param date - 等待被格式化的日期字符串
   * @param format - 格式化参数，如："yyyy-MM-dd HH:mm"
   * @return 被格式化后的日期类型
   */
  public static Date formatDate(String date, String format) {
    if (date == null || date.equals("")) return null;
    if (format == null || format.equals("")) return null;
    try {
      return getDateTimeFormatByCustom(format).parse(date);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 根据年，月，日，时，分，秒获得一个日期类型
   *
   * @param y - 年份，如：2013
   * @param m - 月份，从 1 开始
   * @param d - 日
   * @param hour - 小时
   * @param min - 分钟
   * @param second -秒
   * @return 获得一个指定年，月，日，时，分，秒的日期对象
   */
  public static Date getDate(int y, int m, int d, int hour, int min, int second) {
    return getCalendar(y, m, d, hour, min, second).getTime();
  }

  /**
   * 根据年，月，日，时，分，秒获得一个日期类型
   *
   * @param y - 年份，如：2013
   * @param m - 月份，从 1 开始
   * @param d - 日
   * @param hour - 小时
   * @param min - 分钟
   * @param second -秒
   * @return 获得一个指定年，月，日，时，分，秒的 Calendar 对象
   */
  public static Calendar getCalendar(int y, int m, int d, int hour, int min, int second) {
    Calendar cal = Calendar.getInstance();
    cal.set(y, m - 1, d, hour, min, second);
    return cal;
  }

  /**
   * 根据 date 日期，获得与当前时间的间隔描述
   *
   * @param date - 日期
   * @return 返回字符串，如：3分钟之前
   */
  public static String getSimpleTimeDesc(Date date) {
    if (date == null) return null;

    long second = (System.currentTimeMillis() - date.getTime()) / MILLIS_IN_SEC;

    if (second < 60) return "几秒之前";
    if (second < 60 * 60) return "" + second / 60 + "分钟之前";

    if (second < 60 * 60 * 24) return "" + second / 60 / 60 + "小时之前";

    if (second < 60 * 60 * 24 * 30) return "" + second / 60 / 60 / 24 + "天之前";

    if (second < 60 * 60 * 24 * 365) return "" + second / 60 / 60 / 24 / 30 + "个月之前";

    return "" + second / 60 / 60 / 24 / 365 + "年之前";
  }

  public static String getTimeDesc(Calendar start, Calendar finish) {
    return getTimeDesc(start.getTime(), finish.getTime());
  }

  public static String getTimeDesc(Date start, Date finish) {
    if (start == null) return null;
    if (start.after(finish)) {
      Date temp = start;
      start = finish;
      finish = temp;
    }

    long min = getDiff(start, finish, TIME_UNIT.MINS);
    if (min < 60) return "" + min + "分钟";
    if (min < 60 * 24) {
      if (min % 60 == 0) {
        return "" + min / 60 + "小时";
      } else {
        return "" + min / 60 + "小时" + min % 60 + "分钟";
      }
    }
    // 整天
    if (min % (60 * 24) == 0) {
      return "" + min / (60 * 24) + "天";
    }
    // 有小时，没分钟
    if (min / 60 > 0 && min % 60 == 0) {
      return "" + min / (60 * 24) + "天" + min % (60 * 24) / 60 + "小时";
    }
    // 有小时，有分钟
    if (min / 60 > 0 && min % 60 != 0) {
      return "" + min / (60 * 24) + "天" + min % (60 * 24) / 60 + "小时" + min % 60 + "分钟";
    }
    // 没小时，有分钟
    return "" + min / (60 * 24) + "天" + min % 60 + "分钟";
  }

  /**
   * 按照 "yyyy-MM-dd hh:mm" 格式化一个日期字符串
   */
  public static Date getDateTime(String dateString) {
    return getDateTime(dateString, "yyyy-MM-dd HH:mm");
  }

  /**
   * 自定义格式化一个日期字符串
   */
  public static Date getDateTime(String dateString, String format) {
    Date date = null;
    if (dateString == null || "".equals(dateString)) {
      return date;
    } else {
      try {
        date = new SimpleDateFormat(format).parse(dateString);
      } catch (Exception e) {
        try {
          date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (Exception ex) {
          e.printStackTrace();
        }
      }
    }
    return date;
  }

  /**
   * 根据当前时间返回问候语，如：当前上午9点，返回 “上午好”
   *
   * @return 凌晨好，上午好等
   */
  public static String getGreetings() {
    int hour = DateTimeUtils.getCurrentHourBy24();
    if (hour < 7) return "凌晨好";
    if (hour < 12) return "上午好";
    if (hour < 14) return "中午好";
    if (hour < 17) return "下午好";
    if (hour < 19) return "傍晚好";
    if (hour < 22) return "晚上好";
    return "夜深了";
  }

  public static Calendar getFormatCalendar(Calendar cal, int tempMin) {
    int minute = getMinute(cal.getTime());
    int addMinute = (int) Math.ceil(minute / (tempMin * 1.0F)) * tempMin;

    cal.set(Calendar.MINUTE, addMinute);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    return cal;
  }

  public static Calendar getFormatCalendar(int tempMin) {
    Calendar cal = Calendar.getInstance();
    return getFormatCalendar(cal, tempMin);
  }

  /**
   * 获取提前120分钟后的时间
   *
   * @param durMin 提前分钟数
   * @param tempMin 分钟间隔数
   */
  public static Calendar getFormatCalendar(int durMin, int tempMin) {
    //cal.add(Calendar.MINUTE, durMin);
    Calendar cal = addTime(Calendar.getInstance(), durMin, TIME_UNIT.MINS);
    return getFormatCalendar(cal, tempMin);
  }

  /**
   * 格式化时间
   *
   * @param string
   * @return
   */
  public static String getTimeInfo(String string) {
   return getTimeInfo(string, "--");
  }

  public static String getTimeInfo(String string, String holder) {
    if (android.text.TextUtils.isEmpty(string)) {
      return holder;
    }
    try {
      Date parse = DateTimeUtils.getDateTimeFormatByCustom("yyyy-MM-dd HH:mm:ss")
              .parse(string);

      String s = DateTimeUtils.getDateTimeFormatByCustom("MM-dd HH:mm")
              .format(parse);
      return s;
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return holder;
  }

  /**
   *
   * @param date1
   * @param date2
   * @return
   */
  public static boolean isSameDay(Date date1, Date date2) {
    Calendar calDateA = Calendar.getInstance();
    calDateA.setTime(date1);

    Calendar calDateB = Calendar.getInstance();
    calDateB.setTime(date2);

    return calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR)
            && calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH)
            && calDateA.get(Calendar.DAY_OF_MONTH) == calDateB
            .get(Calendar.DAY_OF_MONTH);
  }
}