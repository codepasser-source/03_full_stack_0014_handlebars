package com.mattdamon.core.log;

import com.mattdamon.core.aop.ExtendUtils;

/**
 * CoreLoggerFactory
 *
 * @author <A>cheng.yy@neusoft.com</A>
 * @date Apr 20, 2015
 * @Copyright: © 2001-2015 东软集团股份有限公司
 */
public final class CoreLoggerFactory {

	private static Object lock = new Object();

	private static CoreLogger coreLogger;

	public static CoreLogger getLogger(Class<?> classze) {

		// 同步锁
		synchronized (lock) {
			coreLogger = ExtendUtils.extend(CoreLogger.class);
			if (classze != null) {
				coreLogger.setClassze(classze);
			} else {
				coreLogger.setClassze(CoreLoggerWrap.class);
			}
		}

		return coreLogger;
	}

}
