package com.cc.android.zcommon.utils.java;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * <h2>字符串工具类，提供一些字符串相关的便捷方法</h2>
 */
public class StringUtils {

  /**
   * 判断给定的字符串是否为null或者是空的
   *
   * @param string 给定的字符串
   */
  public static boolean isEmpty(String string) {
    return string == null || "".equals(string.trim());
  }

  /**
   * 判断给定的字符串是否不为null且不为空
   *
   * @param string 给定的字符串
   */
  public static boolean isNotEmpty(String string) {
    return !StringUtils.isEmpty(string);
  }

  /**
   * 判断给定的字符串数组中的所有字符串是否都为null或者是空的
   *
   * @param strings 给定的字符串
   */
  public static boolean isEmpty(String... strings) {
    boolean result = true;
    for (String string : strings) {
      if (StringUtils.isNotEmpty(string)) {
        result = false;
        break;
      }
    }
    return result;
  }

  /**
   * 判断给定的字符串数组中是否全部都不为null且不为空
   *
   * @param strings 给定的字符串数组
   * @return 是否全部都不为null且不为空
   */
  public static boolean isNotEmpty(String... strings) {
    boolean result = true;
    for (String string : strings) {
      if (StringUtils.isEmpty(string)) {
        result = false;
        break;
      }
    }
    return result;
  }

  /**
   * 如果字符串是null或者空就返回""
   */
  public static String filterEmpty(String string) {
    return StringUtils.isNotEmpty(string) ? string : "";
  }

  /**
   * 在给定的字符串中，用新的字符替换所有旧的字符
   *
   * @param string 给定的字符串
   * @param oldchar 旧的字符
   * @param newchar 新的字符
   * @return 替换后的字符串
   */
  public static String replace(String string, char oldchar, char newchar) {
    char chars[] = string.toCharArray();
    for (int w = 0; w < chars.length; w++) {
      if (chars[w] == oldchar) {
        chars[w] = newchar;
        break;
      }
    }
    return new String(chars);
  }

  /**
   * 把给定的字符串用给定的字符分割
   *
   * @param string 给定的字符串
   * @param ch 给定的字符
   * @return 分割后的字符串数组
   */
  public static String[] split(String string, char ch) {
    ArrayList<String> stringList = new ArrayList<String>();
    char chars[] = string.toCharArray();
    int nextStart = 0;
    for (int w = 0; w < chars.length; w++) {
      if (ch == chars[w]) {
        stringList.add(new String(chars, nextStart, w - nextStart));
        nextStart = w + 1;
        if (nextStart == chars.length) {  //当最后一位是分割符的话，就再添加一个空的字符串到分割数组中去
          stringList.add("");
        }
      }
    }
    if (nextStart < chars.length) {  //如果最后一位不是分隔符的话，就将最后一个分割符到最后一个字符中间的左右字符串作为一个字符串添加到分割数组中去
      stringList.add(new String(chars, nextStart, chars.length - 1 - nextStart + 1));
    }
    return stringList.toArray(new String[stringList.size()]);
  }

  /**
   * 计算给定的字符串的长度，计算规则是：一个汉字的长度为2，一个字符的长度为1
   *
   * @param string 给定的字符串
   * @return 长度
   */
  public static int countLength(String string) {
    int length = 0;
    char[] chars = string.toCharArray();
    for (int w = 0; w < string.length(); w++) {
      char ch = chars[w];
      if (ch >= '\u0391' && ch <= '\uFFE5') {
        length++;
        length++;
      } else {
        length++;
      }
    }
    return length;
  }

  /**
   * 比较给定的两个字符串的大小，包括中文
   *
   * @param string1 前者
   * @param string2 后者
   * @return 返回0：两者相等；返回大于0：前者大于后者；返回小于0：前者小于后者
   */
  public static int compare(String string1, String string2) {
    int result = 0;
    char[] chars1 = string1.toCharArray();
    char[] chars2 = string2.toCharArray();
    int char1Index = 0;
    int char2Index = 0;
    while (char1Index < chars1.length && char2Index < chars2.length) {
      char[] charss1 = StringUtils.getChars(chars1, char1Index);
      char1Index += charss1.length;
      char[] charss2 = StringUtils.getChars(chars2, char2Index);
      char2Index += charss2.length;

      //如果都只获取到了一个字符
      if (charss1.length == 1 && charss2.length == 1) {
        char char1 = charss1[0];
        char char2 = charss2[0];
        if (char1 != char2) {
          boolean char1IsChinese = CharUtils.isChinese(char1);
          boolean char2IsChinese = CharUtils.isChinese(char2);
          //如果都是中文
          if (char1IsChinese && char2IsChinese) {
            //TODO 如果都是中文
            //result = CharUtils.getPinYinByCharOnlyFirst(char1).compareTo(CharUtils.getPinYinByCharOnlyFirst(char2));
            result = -1;
          } else {
            result = char1 - char2;
          }
          break;
        }
      } else {
        boolean chars1AllDigital = StringUtils.isAllDigital(charss1);
        boolean chars2AllDigital = StringUtils.isAllDigital(charss2);
        //如果两个字符都是数字
        if (chars1AllDigital && chars2AllDigital) {
          result = Integer.valueOf(new String(charss1)) - Integer.valueOf(new String(charss2));
          if (result != 0) {
            break;
          }
        } else {
          //如果第一个是数字
          if (chars1AllDigital) {
            result = -5;
            //如果第二个是数字
          } else if (chars2AllDigital) {
            result = 5;
          }
          break;
        }
      }
    }
    if (result == 0) {
      result = chars1.length - chars2.length;
    }
    return result;
  }

  private static char[] getChars(char[] chars, int startIndex) {
    int endIndex = startIndex + 1;
    //如果第一个是数字
    if (Character.isDigit(chars[startIndex])) {
      //如果下一个是数字
      while (endIndex < chars.length && Character.isDigit(chars[endIndex])) {
        endIndex++;
      }
    }
    char[] resultChars = new char[endIndex - startIndex];
    System.arraycopy(chars, startIndex, resultChars, 0, resultChars.length);
    return resultChars;
  }

  /**
   * 是否全是数字
   */
  public static boolean isAllDigital(char[] chars) {
    boolean result = true;
    for (int w = 0; w < chars.length; w++) {
      if (!Character.isDigit(chars[w])) {
        result = false;
        break;
      }
    }
    return result;
  }

  /**
   * 根据给定的字节数组以及给定的字符集创建一个字符串
   *
   * @param bytes 给定的字节数组
   * @param charset 给定的字符集
   * @return 字符串
   */
  public static String valueOf(byte[] bytes, Charset charset) {
    try {
      return new String(
          IOUtils.read(new InputStreamReader(new ByteArrayInputStream(bytes), charset)));
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 根据给定的字节数组以及给定的字符集名字创建一个字符串
   *
   * @param bytes 字节数组
   * @param charsetName 字符集名字
   * @return 字符串
   */
  public static String valueOf(byte[] bytes, String charsetName) {
    return StringUtils.valueOf(bytes, Charset.forName(charsetName));
  }

  /**
   * 根据给定的字节数组以及给定的字符集创建一个字符串
   *
   * @param bytes 字节数组
   * @param offSet 偏移量，从此处开始获取数据
   * @param length 长度，获取数据的个数
   * @param charset 字符集
   * @return 字符串
   */
  public static String valueOf(byte[] bytes, int offSet, int length, Charset charset) {
    try {
      return new String(IOUtils.read(
          new InputStreamReader(new ByteArrayInputStream(bytes, offSet, length), charset)));
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 删除给定字符串中所有的旧的字符
   *
   * @param string 源字符串
   * @param ch 要删除的字符
   * @return 删除后的字符串
   */
  public static String removeChar(String string, char ch) {
    StringBuffer sb = new StringBuffer();
    for (char cha : string.toCharArray()) {
      if (cha != '-') {
        sb.append(cha);
      }
    }
    return sb.toString();
  }

  /**
   * 删除给定字符串中给定位置处的字符
   *
   * @param string 给定字符串
   * @param index 给定位置
   */
  public static String removeChar(String string, int index) {
    String result = null;
    char[] chars = string.toCharArray();
    if (index == 0) {
      result = new String(chars, 1, chars.length - 1);
    } else if (index == chars.length - 1) {
      result = new String(chars, 0, chars.length - 1);
    } else {
      result = new String(chars, 0, index) + new String(chars, index + 1, chars.length - index);
      ;
    }
    return result;
  }

  /**
   * 删除给定字符串中给定位置处的字符
   *
   * @param string 给定字符串
   * @param index 给定位置
   * @param ch 如果同给定位置处的字符相同，则将给定位置处的字符删除
   */
  public static String removeChar(String string, int index, char ch) {
    String result = null;
    char[] chars = string.toCharArray();
    if (chars.length > 0 && chars[index] == ch) {
      if (index == 0) {
        result = new String(chars, 1, chars.length - 1);
      } else if (index == chars.length - 1) {
        result = new String(chars, 0, chars.length - 1);
      } else {
        result = new String(chars, 0, index) + new String(chars, index + 1, chars.length - index);
        ;
      }
    } else {
      result = string;
    }
    return result;
  }

  /**
   * 根据给定的关键字以及给定的检验方式检验给定的字符串是否符合要求
   *
   * @param string 给定的字符串
   * @param listKeywords 给定的关键字
   * @param checkUpWay 给定的检验方式
   * @return 是否符合要求
   */
  public static boolean checkUp(String string, List<String> listKeywords,
                                StringVerifyMode checkUpWay) {
    boolean result = false;
    if (checkUpWay == StringVerifyMode.CONTAIN_KEYWORDS) {
      for (String flag : listKeywords) {
        if (string.indexOf(flag) != -1) {
          result = true;
          break;
        }
      }
    } else if (checkUpWay == StringVerifyMode.NOT_CONTAIN_KEYWORDS) {
      result = !StringUtils.checkUp(string, listKeywords, StringVerifyMode.CONTAIN_KEYWORDS);
    } else if (checkUpWay == StringVerifyMode.EQUAL_KEYWORDS) {
      for (String flag : listKeywords) {
        if (string.equals(flag)) {
          result = true;
          break;
        }
      }
    } else if (checkUpWay == StringVerifyMode.NOT_EQUAL_KEYWORDS) {
      result = !StringUtils.checkUp(string, listKeywords, StringVerifyMode.EQUAL_KEYWORDS);
    } else if (checkUpWay == StringVerifyMode.ENDS_WITH_KEYWORDS) {
      for (String flag : listKeywords) {
        if (string.endsWith(flag)) {
          result = true;
          break;
        }
      }
    } else if (checkUpWay == StringVerifyMode.NOT_ENDS_WITH_KEYWORDS) {
      result = !StringUtils.checkUp(string, listKeywords, StringVerifyMode.ENDS_WITH_KEYWORDS);
    } else if (checkUpWay == StringVerifyMode.STARTS_WIT_KEYWORDS) {
      for (String flag : listKeywords) {
        if (string.startsWith(flag)) {
          result = true;
          break;
        }
      }
    } else if (checkUpWay == StringVerifyMode.NOT_STARTS_WIT_KEYWORDS) {
      result = !StringUtils.checkUp(string, listKeywords, StringVerifyMode.STARTS_WIT_KEYWORDS);
    }
    return result;
  }

  /**
   * 对给定的字符串进行空白过滤
   *
   * @param string 给定的字符串
   * @return 如果给定的字符串是一个空白字符串，那么返回null；否则返回本身。
   */
  public static String filterBlank(String string) {
    if ("".equals(string)) {
      return null;
    } else {
      return string;
    }
  }

  /**
   * 将给定字符串中给定的区域的字符转换成小写
   *
   * @param str 给定字符串中
   * @param beginIndex 开始索引（包括）
   * @param endIndex 结束索引（不包括）
   * @return 新的字符串
   */
  public static String toLowerCase(String str, int beginIndex, int endIndex) {
    return str.replaceFirst(str.substring(beginIndex, endIndex),
        str.substring(beginIndex, endIndex).toLowerCase(Locale.getDefault()));
  }

  /**
   * 将给定字符串中给定的区域的字符转换成大写
   *
   * @param str 给定字符串中
   * @param beginIndex 开始索引（包括）
   * @param endIndex 结束索引（不包括）
   * @return 新的字符串
   */
  public static String toUpperCase(String str, int beginIndex, int endIndex) {
    return str.replaceFirst(str.substring(beginIndex, endIndex),
        str.substring(beginIndex, endIndex).toUpperCase(Locale.getDefault()));
  }

  /**
   * 将给定字符串的首字母转为小写
   *
   * @param str 给定字符串
   * @return 新的字符串
   */
  public static String firstLetterToLowerCase(String str) {
    return StringUtils.toLowerCase(str, 0, 1);
  }

  /**
   * 将给定字符串的首字母转为大写
   *
   * @param str 给定字符串
   * @return 新的字符串
   */
  public static String firstLetterToUpperCase(String str) {
    return StringUtils.toUpperCase(str, 0, 1);
  }

  /**
   * 将给定的字符串MD5加密
   *
   * @param string 给定的字符串
   * @return MD5加密后生成的字符串
   */
  public static String MD5(String string) {
    String result = null;
    try {
      char[] charArray = string.toCharArray();
      byte[] byteArray = new byte[charArray.length];
      for (int i = 0; i < charArray.length; i++) {
        byteArray[i] = (byte) charArray[i];
      }

      StringBuffer hexValue = new StringBuffer();
      byte[] md5Bytes = MessageDigest.getInstance("MD5").digest(byteArray);
      for (int i = 0; i < md5Bytes.length; i++) {
        int val = md5Bytes[i] & 0xff;
        if (val < 16) {
          hexValue.append("0");
        }
        hexValue.append(Integer.toHexString(val));
      }

      result = hexValue.toString();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  /**
   * 判断给定的字符串是否以一个特定的字符串开头，忽略大小写
   *
   * @param sourceString 给定的字符串
   * @param newString 一个特定的字符串
   */
  public static boolean startsWithIgnoreCase(String sourceString, String newString) {
    int newLength = newString.length();
    int sourceLength = sourceString.length();
    if (newLength == sourceLength) {
      return newString.equalsIgnoreCase(sourceString);
    } else if (newLength < sourceLength) {
      char[] newChars = new char[newLength];
      sourceString.getChars(0, newLength, newChars, 0);
      return newString.equalsIgnoreCase(String.valueOf(newChars));
    } else {
      return false;
    }
  }

  /**
   * 判断给定的字符串是否以一个特定的字符串结尾，忽略大小写
   *
   * @param sourceString 给定的字符串
   * @param newString 一个特定的字符串
   */
  public static boolean endsWithIgnoreCase(String sourceString, String newString) {
    int newLength = newString.length();
    int sourceLength = sourceString.length();
    if (newLength == sourceLength) {
      return newString.equalsIgnoreCase(sourceString);
    } else if (newLength < sourceLength) {
      char[] newChars = new char[newLength];
      sourceString.getChars(sourceLength - newLength, sourceLength, newChars, 0);
      return newString.equalsIgnoreCase(String.valueOf(newChars));
    } else {
      return false;
    }
  }

  /**
   * 检查字符串长度，如果字符串的长度超过maxLength，就截取前maxLength个字符串并在末尾拼上appendString
   */
  public static String checkLength(String string, int maxLength, String appendString) {
    if (string.length() > maxLength) {
      string = string.substring(0, maxLength);
      if (appendString != null) {
        string += appendString;
      }
    }
    return string;
  }

  /**
   * 检查字符串长度，如果字符串的长度超过maxLength，就截取前maxLength个字符串并在末尾拼上…
   */
  public static String checkLength(String string, int maxLength) {
    return StringUtils.checkLength(string, maxLength, "…");
  }

  /**
   * 获得UUID字符串
   * @return
   */
  public static String getUUID() {
    return UUID.randomUUID().toString().replaceAll("-","");
  }
}