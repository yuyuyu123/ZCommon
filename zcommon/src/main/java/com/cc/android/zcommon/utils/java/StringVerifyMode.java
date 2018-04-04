package com.cc.android.zcommon.utils.java;

/**
 * 字符串验证模式
 */
public enum StringVerifyMode {
	/**
	 * 包含标记
	 */
	CONTAIN_KEYWORDS,
	/**
	 * 不包含标记
	 */
	NOT_CONTAIN_KEYWORDS,
	/**
	 * 等于标记
	 */
	EQUAL_KEYWORDS,
	/**
	 * 不等于标记
	 */
	NOT_EQUAL_KEYWORDS,
	/**
	 * 以标记结尾
	 */
	ENDS_WITH_KEYWORDS,
	/**
	 * 不以标记结尾
	 */
	NOT_ENDS_WITH_KEYWORDS,
	/**
	 * 以标记开头
	 */
	STARTS_WIT_KEYWORDS,
	/**
	 * 不以标记开头
	 */
	NOT_STARTS_WIT_KEYWORDS;
}