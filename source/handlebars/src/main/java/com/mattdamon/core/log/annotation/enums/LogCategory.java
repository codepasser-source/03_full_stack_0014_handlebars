package com.mattdamon.core.log.annotation.enums;

/**
 * 
 * LogCategory.java
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date May 8, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 *
 */
public enum LogCategory {

	CONSOLE("[NEU-ECOMMERCE-CONSOLE-LOG]", "[NEU-ECOMMERCE-CONSOLE-LOG]"), CONTROLLER(
			"[NEU-ECOMMERCE-CONTROLLER-LOG]", "[NEU-ECOMMERCE-CONTROLLER-LOG]"), SERVICE(
			"[NEU-ECOMMERCE-SERVICE-LOG]", "[NEU-ECOMMERCE-SERVICE-LOG]"), SYSTEM(
			"[NEU-ECOMMERCE-SYSTEM-LOG]", "[NEU-ECOMMERCE-SYSTEM-LOG]"), ACCESS(
			"[NEU-ECOMMERCE-ACCESS-LOG]", "[NEU-ECOMMERCE-ACCESS-LOG]");

	private final String value;

	private final String description;

	private LogCategory(String value, String description) {
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
