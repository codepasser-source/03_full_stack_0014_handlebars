package com.mattdamon.core.base;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * BaseCookie
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date May 19, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 *
 */
@Getter
@Setter
@ToString
public class BaseCookie {

	private Map<Object, Object> cookies;

	public void setCookies(Cookie[] cookies) {
		if (this.cookies == null) {
			this.cookies = new HashMap<Object, Object>();
		} else {
			this.cookies.clear();
		}
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				String key = cookies[i].getName();
				String value = cookies[i].getValue();
				this.cookies.put(key, value);
			}
		}
	}

	public Map<Object, Object> getCookies() {
		if (this.cookies == null) {
			this.cookies = new HashMap<Object, Object>();
		}
		return this.cookies;
	}

	public void addCookie(Object key, Object value) {
		if (this.cookies == null) {
			this.cookies = new HashMap<Object, Object>();
		}
		if (key != null && value != null) {
			this.cookies.put(key, value);
		}
	}

}
