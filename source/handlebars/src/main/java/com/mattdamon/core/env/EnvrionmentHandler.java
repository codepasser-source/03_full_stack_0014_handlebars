package com.mattdamon.core.env;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.mattdamon.core.common.GlobalVariables;
import com.mattdamon.core.exception.GeneralException;
import com.mattdamon.core.log.CoreLogger;
import com.mattdamon.core.log.CoreLoggerFactory;
import com.mattdamon.core.properties.CoreProperties;
import com.mattdamon.core.properties.PropertiesHandler;
import com.mattdamon.core.utils.EscapeEncode;
import com.mattdamon.core.utils.SpringUtiler;
import com.whalin.MemCached.MemCachedClient;

/**
 * 
 * 
 * EnvrionmentHandler
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date May 13, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 *
 */
public class EnvrionmentHandler implements MultiModeSession, GlobalVariables {

	private static final CoreLogger logger = CoreLoggerFactory
			.getLogger(EnvrionmentHandler.class);

	private static final String SESSION_MODE_LOCAL = "local";

	// 获取Session
	public Serializable getSession(String name) throws GeneralException {
		return getSession().get(name);
	}

	/**
	 * 获取Session
	 * 
	 * @return
	 * @throws GeneralException
	 */
	public Map<String, Serializable> getSession() throws GeneralException {

		logger.consoleLog("> SESSIONID:[" + getSessionID() + "]");

		// 生产模式：保存在memcached中
		Map<String, Serializable> map = null;
		if (!isLocal()) {
			map = getMapFromMemcached();// not null
			return map;
		} else {
			// 开发模式：保存在本地session中
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes()).getRequest();

			map = new HashMap<String, Serializable>();
			HttpSession session = request.getSession(true);
			@SuppressWarnings("rawtypes")
			Enumeration en = session.getAttributeNames();
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				map.put(key, (Serializable) session.getAttribute(key));
			}
			return map;
		}
	}

	/**
	 * 获取session 数据 Form memcached
	 * 
	 * @return
	 * @throws GeneralException
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Serializable> getMapFromMemcached()
			throws GeneralException {

		Map<String, Serializable> map = null;
		String sessionId = getSessionID();
		MemCachedClient client = getMemcachedClient();
		Object data = client.get(sessionId);
		if (data == null) {
			map = new HashMap<String, Serializable>();
			client.set(sessionId, map);
		} else {
			map = (Map<String, Serializable>) data;
		}
		return map;
	}

	/**
	 * 获取Memcached
	 * 
	 * @return
	 * @throws GeneralException
	 */
	public MemCachedClient getMemcachedClient() throws GeneralException {
		return (MemCachedClient) SpringUtiler.getBean("memcachedClient");
	}

	/**
	 * 设置Session
	 * 
	 * @param name
	 * @param obj
	 * @throws GeneralException
	 */
	public void setSession(String name, Serializable obj)
			throws GeneralException {
		// 生产模式：保存在memcached中
		if (!isLocal()) {
			Map<String, Serializable> map = getMapFromMemcached();
			map.put(name, obj);
			MemCachedClient client = getMemcachedClient();
			client.set(getSessionID(), map);
		} else {
			// 开发模式：保存在本地session中
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes()).getRequest();
			HttpSession session = request.getSession(true);
			session.setAttribute(name, obj);
		}
	}

	/**
	 * 清理session
	 * 
	 * @param name
	 * @throws GeneralException
	 */
	public void removeSession(String name) throws GeneralException {

		if (!isLocal()) {
			Map<String, Serializable> map = getMapFromMemcached();
			map.remove(name);
			MemCachedClient client = getMemcachedClient();
			client.set(getSessionID(), map);
		} else {
			// 开发模式：保存在本地session中
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes()).getRequest();
			HttpSession session = request.getSession(true);
			session.removeAttribute(name);
		}
	}

	/**
	 * 清理session
	 * 
	 * @throws GeneralException
	 */
	public void clearSession() throws GeneralException {
		// 生产模式：保存在memcached中
		if (!isLocal()) {
			Map<String, Serializable> map = getMapFromMemcached();
			if (map != null) {
				map.clear();
			}
			MemCachedClient client = getMemcachedClient();
			client.set(getSessionID(), map);
		} else {
			// 开发模式：保存在本地session中
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes()).getRequest();
			HttpSession session = request.getSession(true);
			@SuppressWarnings("rawtypes")
			Enumeration en = session.getAttributeNames();
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				session.removeAttribute(key);
			}
		}
	}

	/**
	 * 获取 Session Model
	 * 
	 * @return
	 * @throws GeneralException
	 */
	public boolean isLocal() throws GeneralException {

		CoreProperties prop = PropertiesHandler
				.getProperties(CoreProperties.class);

		String sessionMode = prop.getSessionMode();

		boolean isLocal = SESSION_MODE_LOCAL.equalsIgnoreCase(sessionMode);

		if (isLocal) {
			logger.consoleLog(">session:本地模式");
		} else {
			logger.consoleLog(">session:远程 memcached模式");
		}
		return isLocal;
	}

	/**
	 * 设置 SessionID
	 * 
	 * @throws GeneralException
	 */
	public String getSessionID() throws GeneralException {
		String CLIENT_COOKIE_NECID = ((CoreProperties) PropertiesHandler
				.getProperties(CoreProperties.class)).getSessionKey();

		Cookie[] cookies = getCookie();
		if (cookies != null && cookies.length > 0) {
			for (int i = 0; i < cookies.length; i++) {
				if (CLIENT_COOKIE_NECID.equalsIgnoreCase(cookies[i].getName())) {
					return cookies[i].getValue();
				}
			}
		}
		// 解决 clust第一次请求时 setSession session为null
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession(true);
		if (session != null) {
			return (String) session.getAttribute(CLIENT_COOKIE_NECID);
		}
		// 不能发生
		return null;
	}

	/**
	 * 创建 SessionID
	 * 
	 * @param response
	 * @throws GeneralException
	 */
	public void generatedSessionID(HttpServletRequest request,
			HttpServletResponse response) throws GeneralException {
		String CLIENT_COOKIE_NECID = ((CoreProperties) PropertiesHandler
				.getProperties(CoreProperties.class)).getSessionKey();
		String sessionId = UUID.randomUUID().toString().replace("-", "");
		setCookie(response, CLIENT_COOKIE_NECID, sessionId);
		// if (!isLocal()) { //本地环境第一次请求session id 为null,性能考虑可以打开
		// 第一次创建会话，将session id保存住
		HttpSession session = request.getSession(true);
		if (session != null) {
			session.setAttribute(CLIENT_COOKIE_NECID, sessionId);
		}
		// }
	}

	/**
	 * 获取Cookies
	 * 
	 * @param key
	 * @return
	 * @throws GeneralException
	 */
	public String getCookie(String key) throws GeneralException {
		Cookie[] cookies = getCookie();
		if (cookies != null && cookies.length > 0) {
			for (int i = 0; i < cookies.length; i++) {
				if (key.equalsIgnoreCase(cookies[i].getName())) {
					return cookies[i].getValue();
				}
			}
		}
		return null;
	}

	/**
	 * 获取Cookies
	 * 
	 * @return
	 * @throws GeneralException
	 */
	public Cookie[] getCookie() throws GeneralException {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		return request.getCookies();
	}

	/**
	 * 保存cookie,默认有效时间为1天
	 * 
	 * @param response
	 * @param key
	 * @param value
	 * @throws GeneralException
	 */
	public void setCookie(HttpServletResponse response, String key, String value)
			throws GeneralException {
		setCookie(response, key, value, 0);
	}

	/**
	 * 保存Cookie
	 * 
	 * @param response
	 * @param key
	 * @param value
	 * @param day
	 * @throws GeneralException
	 */
	public void setCookie(HttpServletResponse response, String key,
			String value, int day) throws GeneralException {

		CoreProperties prop = PropertiesHandler
				.getProperties(CoreProperties.class);

		value = EscapeEncode.escape(value);
		Cookie cookie = new Cookie(key, value);
		cookie.setDomain(prop.getDomain());
		cookie.setPath("/");
		// HTTPS传递
		cookie.setSecure(false);
		// HTTP传递
		cookie.setHttpOnly(true);
		if (day > 0) {
			cookie.setMaxAge(60 * 60 * 24 * day);
		}
		response.addCookie(cookie);
	}

	/**
	 * 删除Cookie
	 * 
	 * @param response
	 * @param key
	 * @throws GeneralException
	 */
	public void removeCookie(HttpServletResponse response, String key)
			throws GeneralException {
		Cookie[] cookies = getCookie();
		if (cookies != null && cookies.length > 0) {
			for (int i = 0; i < cookies.length; i++) {
				if (key.equalsIgnoreCase(cookies[i].getName())) {
					Cookie cookie = new Cookie(key, null);
					cookie.setMaxAge(0);
					response.addCookie(cookie);
					break;
				}
			}
		}
	}
}
