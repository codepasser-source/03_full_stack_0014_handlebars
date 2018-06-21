package com.mattdamon.core.env;

import java.io.Serializable;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mattdamon.core.exception.GeneralException;

/**
 * 
 * MultiModeSession
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date Apr 16, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 *
 */
public interface MultiModeSession {

	boolean isLocal() throws GeneralException;

	String getSessionID() throws GeneralException;

	void generatedSessionID(HttpServletRequest request,
			HttpServletResponse response) throws GeneralException;

	Map<String, Serializable> getSession() throws GeneralException;

	Serializable getSession(String key) throws GeneralException;

	void setSession(String key, Serializable data) throws GeneralException;

	void removeSession(String name) throws GeneralException;

	void clearSession() throws GeneralException;

	Cookie[] getCookie() throws GeneralException;

	String getCookie(String key) throws GeneralException;

	void setCookie(HttpServletResponse response, String key, String value)
			throws GeneralException;

	void setCookie(HttpServletResponse response, String key, String value,
			int day) throws GeneralException;

	void removeCookie(HttpServletResponse response, String key)
			throws GeneralException;

}
