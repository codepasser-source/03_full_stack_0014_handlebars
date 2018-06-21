package com.mattdamon.core.env;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.mattdamon.core.log.CoreLogger;
import com.mattdamon.core.log.CoreLoggerFactory;
import com.mattdamon.core.utils.StringUtiler;

/**
 * 
 * ClientSessionInterceptor
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date May 14, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 *
 */
public class ClientSessionInterceptor extends EnvrionmentHandler implements
		HandlerInterceptor {

	private static final CoreLogger logger = CoreLoggerFactory
			.getLogger(ClientSessionInterceptor.class);

	private String interceptRegex;
	private String exclueRegex;

	public String getInterceptRegex() {
		return interceptRegex;
	}

	public void setInterceptRegex(String interceptRegex) {
		this.interceptRegex = interceptRegex;
	}

	public String getExclueRegex() {
		return exclueRegex;
	}

	public void setExclueRegex(String exclueRegex) {
		this.exclueRegex = exclueRegex;
	}

	/**
	 * 根据正则表达式验证
	 * 
	 * @param str
	 * @return
	 */
	private static boolean match(String url, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(url);
		return matcher.find();
	}

	/**
	 * 在业务处理器处理请求之前被调用 如果返回false 从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
	 * 
	 * 如果返回true 执行下一个拦截器,直到所有的拦截器都执行完毕 再执行被拦截的Controller 然后进入拦截器链,
	 * 从最后一个拦截器往回执行所有的postHandle() 接着再从最后一个拦截器往回执行所有的afterCompletion()
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object obj) throws Exception {

		String requestURL = request.getRequestURL().toString();
		logger.consoleLog(">requestURL:" + requestURL);
		logger.consoleLog(">interceptRegex:" + this.interceptRegex);
		logger.consoleLog(">exclueRegex:" + this.exclueRegex);

		if (!match(requestURL, this.interceptRegex)) {
			// 不匹配拦截规则
			return true;
		}
		if (match(requestURL, this.exclueRegex)) {
			// 匹配排除规则
			return true;
		}
		// cookie摄入Session ID
		if (StringUtiler.isNullOrEmpty(getSessionID())) {
			generatedSessionID(request, response);
		}

		return true;
	}

	/**
	 * 在业务处理器处理请求执行完成后,生成视图之前执行的动作
	 */
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object obj, ModelAndView modelAndView)
			throws Exception {
		// TODO
	}

	/**
	 * 在DispatcherServlet完全处理完请求后被调用
	 * 
	 * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object obj, Exception ex)
			throws Exception {
		// TODO
	}

}
