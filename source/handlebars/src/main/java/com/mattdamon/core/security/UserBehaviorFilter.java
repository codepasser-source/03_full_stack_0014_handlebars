package com.mattdamon.core.security;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mattdamon.core.log.CoreLogger;
import com.mattdamon.core.log.CoreLoggerFactory;

/**
 * 
 * UserBehaviorFilter.java
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date May 5, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 *
 */
public class UserBehaviorFilter extends HttpServlet implements Filter {
	private static final long serialVersionUID = -1765753785871003826L;

	private static final CoreLogger logger = CoreLoggerFactory
			.getLogger(UserBehaviorFilter.class);

	private static String INTERCEPT_REGEX = "interceptRegex";
	private static String EXCLUE_REGEX = "exclueRegex";
	private static String INTERCEPT_LOCAL = "interceptLocal";
	private static Boolean INTERCEPTLOCAL = true;

	private static final String LOG_CONTENT_ADDRESS = "ADDRESS";
	private static final String LOG_CONTENT_PROXY = "PROXY";
	private static final String LOG_CONTENT_ROOTPATH = "ROOTPATH";
	private static final String LOG_CONTENT_RELATIVEPATH = "RELATIVEPATH ";
	private static final String LOG_CONTENT_METHOD = "METHOD";
	private static final String LOG_CONTENT_PARAM = "PARAM";
	private static final String LOG_CONTENT_REQUESTBODY = "REQUESTBODY";

	@Override
	public void destroy() {
		// TODO
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		// 过滤、验证并保存用户请求拦截信息
		doSaveBehavior(req, res);
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		INTERCEPT_REGEX = filterConfig.getInitParameter(INTERCEPT_REGEX);
		EXCLUE_REGEX = filterConfig.getInitParameter(EXCLUE_REGEX);
		INTERCEPT_LOCAL = filterConfig.getInitParameter(INTERCEPT_LOCAL);
		INTERCEPTLOCAL = Boolean.valueOf(INTERCEPT_LOCAL);
		HashMap<String, Object> debug = new HashMap<String, Object>();
		debug.put("INTERCEPT_REGEX", INTERCEPT_REGEX);
		debug.put("INTERCEPTLOCAL", INTERCEPTLOCAL);
		logger.consoleLog(debug);
	}

	/**
	 * 拦截用户请求
	 * 
	 * @param req
	 * @param res
	 */
	private void doSaveBehavior(HttpServletRequest req, HttpServletResponse res) {

		if (!INTERCEPTLOCAL) {
			if ("0:0:0:0:0:0:0:1".equals(getIpAddr(req))
					|| "127.0.0.1".equals(getIpAddr(req))) {
				return;
			}
		}

		HashMap<String, String> userBehavior = new HashMap<String, String>();
		// 获取用户请求路径
		String requestUrl = req.getRequestURL().toString();
		// 获取用户请求相对路径
		String requestUri = req.getRequestURI();
		// 获取用户请求根路径
		String rootUrl = requestUrl.substring(0,
				requestUrl.lastIndexOf(requestUri));

		if (!match(requestUrl, INTERCEPT_REGEX)) {
			// 不匹配拦截规则
			return;
		}
		if (match(requestUrl, EXCLUE_REGEX)) {
			// 匹配排除规则
			return;
		}
		userBehavior.put(LOG_CONTENT_ADDRESS, getIpAddr(req));
		userBehavior.put(LOG_CONTENT_PROXY, req.getRemoteAddr());
		userBehavior.put(LOG_CONTENT_ROOTPATH, rootUrl);
		userBehavior.put(LOG_CONTENT_RELATIVEPATH, requestUri);
		userBehavior.put(LOG_CONTENT_METHOD, req.getMethod());
		userBehavior.put(LOG_CONTENT_PARAM, formatString(req.getQueryString()));
		userBehavior.put(LOG_CONTENT_REQUESTBODY, this.buildXml(req));

		logger.accessLog(userBehavior);
	}

	/**
	 * 根据正则表达式验证
	 * 
	 * @param str
	 * @return
	 */
	private boolean match(String url, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(url);
		return matcher.find();
	}

	/**
	 * 获得用户真实IP
	 * 
	 * @param request
	 *            请求对象
	 * @return 真实IP地址
	 */
	public String getIpAddr(HttpServletRequest request) {
		// 单层nginx
		String ip = request.getHeader("X-Real-IP");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			// 多层nginx
			ip = request.getHeader("X-Forwarded-For");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 判断是否为null,如果为null,则返回空字符串
	 * 
	 * @param obj
	 * @return
	 */
	private String formatString(Object obj) {
		String str = "";
		if (obj != null) {
			str = obj.toString();
		}
		return str;
	}

	/**
	 * 解析请求body
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private String buildXml(HttpServletRequest request) {
		StringBuffer xml = new StringBuffer();
		Enumeration names = request.getParameterNames();
		xml.append("<?xml version=\"1.0\" standalone=\"yes\"?>");
		xml.append("<request>");
		String name;
		while (names.hasMoreElements()) {
			name = names.nextElement().toString();
			xml.append("<" + name + ">");
			xml.append(request.getParameter(name));
			xml.append("</" + name + ">");
		}
		xml.append("</request>");
		return xml.toString();
	}

}
