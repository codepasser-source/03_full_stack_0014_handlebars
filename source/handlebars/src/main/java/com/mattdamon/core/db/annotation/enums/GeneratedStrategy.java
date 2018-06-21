package com.mattdamon.core.db.annotation.enums;

/**
 * 
 * 
 * GeneratedItem.java
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date May 8, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 *
 */
public enum GeneratedStrategy {

	DEFAULT("DEFAULT", "DEFAULT"), UUID("UUID", "UUID");

	private final String value;

	private final String description;

	private GeneratedStrategy(String value, String description) {
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
