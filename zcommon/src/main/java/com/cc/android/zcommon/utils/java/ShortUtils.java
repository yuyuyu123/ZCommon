package com.cc.android.zcommon.utils.java;

/**
 * Short相关的工具函数
 */
public class ShortUtils {
	/**
	 * 将字节数组data转换成一个短整型数据
	 * @param data 源数组
	 * @return 一个短整型数据
	 */
	public static short valueOf(byte[] data){
		short res = 0;
		if(data.length >= 2){
			res = (short)((data[0] << 8) + (data[1] & 0xFF));
		}else if(data.length == 1){
			res = data[0];
		}
		return res;
	}
}
