package com.mattdamon.core.log;

import java.util.HashMap;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 *
 * LogInterceptor
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date Apr 15, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 *
 */
public class LogAspect {

	private static final String LOG_CONTENT_RESULT = "RESULT";
	private static final String LOG_CONTENT_ARGS = "ARGS";
	private static final String LOG_CONTENT_TARGET = "TARGET";
	private static final String LOG_CONTENT_PROCESSING = "PROCESSING";

	// controllerLog
	public Object controllerInterceptor(ProceedingJoinPoint pjp)
			throws Throwable {
		HashMap<String, Object> info = new HashMap<String, Object>();
		info.put(LOG_CONTENT_PROCESSING, "BEFOR");
		info.put(LOG_CONTENT_TARGET, pjp.getTarget().toString());
		CoreLogger logger = CoreLoggerFactory.getLogger(pjp.getTarget()
				.getClass());

		logger.controllerLog(info);

		Object result = pjp.proceed();

		info.clear();
		info.put(LOG_CONTENT_PROCESSING, "END");
		info.put(LOG_CONTENT_TARGET, pjp.getTarget().toString());

		logger.controllerLog(info);

		return result;
	}

	// serviceLog
	public Object serviceInterceptor(ProceedingJoinPoint pjp) throws Throwable {
		HashMap<String, Object> info = new HashMap<String, Object>();
		info.put(LOG_CONTENT_PROCESSING, "BEFOR");
		info.put(LOG_CONTENT_TARGET, pjp.getTarget().toString());
		info.put(LOG_CONTENT_ARGS, pjp.getArgs());

		CoreLogger logger = CoreLoggerFactory.getLogger(pjp.getTarget()
				.getClass());
		logger.serviceLog(info);

		Object result = pjp.proceed();

		info.clear();
		info.put(LOG_CONTENT_PROCESSING, "END");
		info.put(LOG_CONTENT_TARGET, pjp.getTarget().toString());
		info.put(LOG_CONTENT_RESULT, result);

		logger.serviceLog(info);

		return result;
	}
}