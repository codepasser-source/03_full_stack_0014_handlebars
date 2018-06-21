package com.mattdamon.core.log;

import java.io.Serializable;

import com.mattdamon.core.aop.annotation.Extend;
import com.mattdamon.core.common.GlobalVariables;
import com.mattdamon.core.log.annotation.LogDefinition;
import com.mattdamon.core.log.annotation.enums.LogCategory;
import com.mattdamon.core.log.annotation.enums.LogLevel;

/**
 * CoreLogger
 *
 * @param <T>
 * @author <A>cheng.yy@neusoft.com</A>
 * @date Apr 17, 2015
 * @Copyright: © 2001-2015 东软集团股份有限公司
 */
public class CoreLogger implements GlobalVariables, CoreLoggerSupport {

	private Class<?> classze;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mattdamon.core.log.CoreLoggerSupport#getClassze()
	 */
	@Override
	public Class<?> getClassze() {
		return classze;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mattdamon.core.log.CoreLoggerSupport#setClassze(java.lang
	 * .Class)
	 */
	@Override
	public void setClassze(Class<?> classze) {
		this.classze = classze;
	}

	/**
	 * console log
	 *
	 * @param log
	 */
	@Extend(CoreLoggerWrap.class)
	@LogDefinition(level = LogLevel.INFO, category = LogCategory.CONSOLE)
	public <T> void consoleLog(Serializable log) {
	}

	/**
	 * controller log
	 *
	 * @param log
	 */
	@Extend(CoreLoggerWrap.class)
	@LogDefinition(level = LogLevel.INFO, category = LogCategory.CONTROLLER)
	public <T> void controllerLog(Serializable log) {
	}

	/**
	 * service log
	 *
	 * @param log
	 */
	@Extend(CoreLoggerWrap.class)
	@LogDefinition(level = LogLevel.INFO, category = LogCategory.SERVICE)
	public <T> void serviceLog(Serializable log) {
	}

	/**
	 * system log
	 *
	 * @param log
	 */
	@Extend(CoreLoggerWrap.class)
	@LogDefinition(level = LogLevel.ERROR, category = LogCategory.SYSTEM)
	public <T> void systemLog(Serializable log) {
	}

	/**
	 * access log
	 *
	 * @param log
	 */
	@Extend(CoreLoggerWrap.class)
	@LogDefinition(level = LogLevel.INFO, category = LogCategory.ACCESS)
	public <T> void accessLog(Serializable log) {
	}
}
