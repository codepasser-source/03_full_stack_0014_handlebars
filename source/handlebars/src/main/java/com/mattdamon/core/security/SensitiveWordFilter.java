package com.mattdamon.core.security;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 
 * SensitiveWordFilter
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date Apr 17, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 *
 */
public class SensitiveWordFilter {

	/** 直接禁止的 */
	@SuppressWarnings("rawtypes")
	private static HashMap keysMap = new HashMap();

	// 1:最小长度匹配 2：最大长度匹配
	private static final int MATCHTYPE = 1;

	private static final String SUCCEDANEUM = "X";

	public static synchronized String doFilter(String sensitive, String txt) {
		addKeywords(sensitive);
		boolean boo = hasSensitiveWord(sensitive, txt);
		if (boo) {
			@SuppressWarnings("rawtypes")
			Set set = getTxtKeyWords(txt);
			@SuppressWarnings("rawtypes")
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				String keyword = (String) iterator.next();
				txt = txt.replaceAll(keyword, SUCCEDANEUM);
			}
		}

		return txt;
	}

	public static synchronized boolean hasSensitiveWord(String sensitive,
			String txt) {
		addKeywords(sensitive);
		for (int i = 0; i < txt.length(); i++) {
			int len = checkKeyWords(txt, i, 1);
			if (len > 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 检查一个字符串从begin位置起开始是否有keyword符合， 如果有符合的keyword值，返回值为匹配keyword的长度，否则返回零
	 * flag 1:最小长度匹配 2：最大长度匹配
	 */
	@SuppressWarnings("rawtypes")
	private static synchronized int checkKeyWords(String txt, int begin,
			int flag) {
		HashMap nowhash = null;
		nowhash = keysMap;
		int maxMatchRes = 0;
		int res = 0;
		int l = txt.length();
		char word = 0;
		for (int i = begin; i < l; i++) {
			word = txt.charAt(i);
			Object wordMap = nowhash.get(word);
			if (wordMap != null) {
				res++;
				nowhash = (HashMap) wordMap;
				if (nowhash.get("isEnd").equals("1")) {
					if (flag == 1) {
						wordMap = null;
						nowhash = null;
						txt = null;
						return res;
					} else {
						maxMatchRes = res;
					}
				}
			} else {
				txt = null;
				nowhash = null;
				return maxMatchRes;
			}
		}
		txt = null;
		nowhash = null;
		return maxMatchRes;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static synchronized void addKeywords(String sensitive) {

		if (keysMap.size() > 0) {
			return;
		}

		String keywords[] = sensitive.split(";");

		if (keywords != null && keywords.length > 0) {
			for (int i = 0; i < keywords.length; i++) {
				String key = keywords[i].trim();
				HashMap nowhash = null;
				nowhash = keysMap;
				for (int j = 0; j < key.length(); j++) {
					char word = key.charAt(j);
					Object wordMap = nowhash.get(word);
					if (wordMap != null) {
						nowhash = (HashMap) wordMap;
					} else {
						HashMap<String, String> newWordHash = new HashMap<String, String>();
						newWordHash.put("isEnd", "0");
						nowhash.put(word, newWordHash);
						nowhash = newWordHash;
					}
					if (j == key.length() - 1) {
						nowhash.put("isEnd", "1");
					}
				}
			}
		}

	}

	/**
	 * 返回txt中关键字的列表
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static synchronized Set<String> getTxtKeyWords(String txt) {
		Set set = new HashSet();
		int l = txt.length();
		for (int i = 0; i < l;) {
			int len = checkKeyWords(txt, i, MATCHTYPE);
			if (len > 0) {
				set.add(txt.substring(i, i + len));
				i += len;
			} else {
				i++;
			}
		}
		txt = null;
		return set;
	}

}