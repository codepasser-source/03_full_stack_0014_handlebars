package com.mattdamon.core.utils;

import java.util.Iterator;
import java.util.Map;

/**
 * 
 * StringUtiler
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date Apr 17, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 *
 */
public class StringUtiler {

	/**
	 * 左起标识
	 */
	public static int LEFT = 1;

	/**
	 * 右标识
	 */
	public static int RIGHT = 2;

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 *            字符串
	 * @return boolean
	 */
	public static boolean isNullOrEmpty(String str) {
		return str == null || "".equals(str);

	}

	/**
	 * 格式化字符串，如果str为null,返回（""）空
	 * 
	 * @param str
	 *            字符串
	 * @return string
	 */
	public static String nullToEmpty(Object str) {
		if (str == null) {
			return "";
		}
		return str.toString();
	}

	/**
	 * 判断数组内是否存在指定字符串(完全相等)
	 * 
	 * @param str
	 *            字符串
	 * @param strArray
	 *            数组
	 * @return boolean
	 */
	public static boolean isInArray(String str, String[] strArray) {
		boolean result = false;

		for (int i = 0, length = strArray.length; i < length; i++) {
			if (strArray[i].equals(str)) {
				result = true;
				break;
			}
		}

		return result;
	}

	/**
	 * 获取指定长度的随机数字符串
	 * 
	 * @param length
	 *            长度
	 * @return 返回随机字符串
	 */
	public static String getDesignedLengthRandom(int length) {
		return String.valueOf(Math.random()).substring(2, length + 2);
	}

	/**
	 * 判断Map对象元素有无空值，有就返回true
	 * 
	 * @param map
	 *            map对象
	 * @return boolean
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isMapParamNull(Map<String, Object> map) {
		boolean isMapParamNull = false;
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry) it.next();

			if (e.getValue() == null || e.getValue().equals("")) {
				isMapParamNull = true;
			}
		}

		return isMapParamNull;
	}

	/**
	 * 填充字符
	 * 
	 * @param str
	 *            字符串
	 * @param totalLenth
	 *            长度
	 * @param leftOrRight
	 *            左右
	 * @param padingChar
	 *            填充字符
	 * @return
	 */
	public static String Pading(String str, int totalLenth, int leftOrRight,
			char padingChar) {

		String rtn = null;

		if (str.length() >= totalLenth) {
			return str;
		}

		int count = totalLenth - str.length();
		char[] padingChars = new char[count];
		for (int i = 0; i < count; i++) {
			padingChars[i] = padingChar;
		}

		if (leftOrRight == LEFT) {
			rtn = String.valueOf(padingChars) + str;
		} else if (leftOrRight == RIGHT) {
			rtn = str + padingChars.toString();
		} else {
			return str;
		}

		return rtn;
	}
}
