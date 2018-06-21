package com.mattdamon.core.log.annotation.enums;

/**
 * 
 * LogLevel.java
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date May 8, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 *
 */
public enum LogLevel {

	TRACE("TRACE", "TRACE"), DEBUG("DEBUG", "DEBUG"), INFO("INFO", "INFO"), WARN(
			"WARN", "WARN"), ERROR("ERROR", "ERROR");

	private final String value;

	private final String description;

	private LogLevel(String value, String description) {
		this.value = value;
		this.description = description;
	}

	public String value() {
		return this.value;
	}

	@Override
	public String toString() {
		return description;
	}
}
