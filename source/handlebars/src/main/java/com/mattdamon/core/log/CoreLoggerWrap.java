package com.mattdamon.core.log;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.mattdamon.core.aop.DefaultInvokeFilter;
import com.mattdamon.core.aop.InvokeFilterChain;
import com.mattdamon.core.aop.InvokeMethod;
import com.mattdamon.core.common.GlobalVariables;
import com.mattdamon.core.exception.GeneralException;
import com.mattdamon.core.log.annotation.LogDefinition;
import com.mattdamon.core.log.annotation.enums.LogCategory;
import com.mattdamon.core.log.annotation.enums.LogLevel;

/**
 * 
 * CoreLoggerWrap
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date Apr 20, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 *
 */
public class CoreLoggerWrap extends DefaultInvokeFilter implements
		GlobalVariables {

	private static final String LOG_CATEGORY = "CATEGORY";
	private static final String LOG_CONTENT = "CONTENT";

	private InvokeMethod invokeMethod;
	private Object caller;
	private Method method;

	@Override
	public void init(InvokeMethod invokeMethod, InvokeFilterChain chain) {
		super.init(invokeMethod, chain);
		this.invokeMethod = invokeMethod;
		this.method = invokeMethod.getMethod();
		this.caller = invokeMethod.getCaller();
	}

	@Override
	public Object invoke() throws GeneralException {
		Object retValue = null;
		try {
			// 获取caller classze
			Class<?> classze = CoreLoggerWrap.class;
			Method[] methods = caller.getClass().getMethods();
			for (int i = 0; i < methods.length; i++) {
				Method methodItem = methods[i];
				int mod = methodItem.getModifiers();
				if (Modifier.isPublic(mod)
						&& "getClassze".equals(methodItem.getName())) {
					try {
						classze = (Class<?>) methodItem.invoke(caller);
					} catch (Exception e) {
						// #IGNORE
					}
					break;
				}
			}

			// 获取参数
			Object[] args = this.invokeMethod.getArgs();
			Serializable log = null;
			if (args.length > 0) {
				log = (Serializable) args[0];
			}

			String logLevel = LogLevel.DEBUG.value();
			String logCategory = LogCategory.CONSOLE.value();

			// 获取@LogDefinition
			if (this.method.isAnnotationPresent(LogDefinition.class)) {
				LogDefinition logDefinition = this.method
						.getAnnotation(LogDefinition.class);
				logLevel = logDefinition.level().value();
				logCategory = logDefinition.category().value();
			}

			Logger logger = LoggerFactory.getLogger(classze);
			HashMap<String, Serializable> logMap = new HashMap<String, Serializable>();
			logMap.put(LOG_CATEGORY, logCategory);
			logMap.put(LOG_CONTENT, log);

			switch (logLevel) {
			case LOG_LEVEL_TRACE:
				logger.trace(JSONObject.toJSONString(logMap));
				break;
			case LOG_LEVEL_DEBUG:
				logger.debug(JSONObject.toJSONString(logMap));
				break;
			case LOG_LEVEL_INFO:
				logger.info(JSONObject.toJSONString(logMap));
				break;
			case LOG_LEVEL_WARN:
				logger.warn(JSONObject.toJSONString(logMap));
				break;
			case LOG_LEVEL_ERROR:
				logger.error(JSONObject.toJSONString(logMap));
				break;
			default:
				break;
			}

			retValue = super.invoke();
		} catch (GeneralException e) {
			throw e;
		}
		return retValue;
	}
}
