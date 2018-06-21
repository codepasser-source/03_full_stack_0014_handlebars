package com.mattdamon.core.db.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * DataSource.java
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date May 5, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DataSource {
	String value();
}
