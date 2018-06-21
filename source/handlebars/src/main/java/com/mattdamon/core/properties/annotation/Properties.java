package com.mattdamon.core.properties.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * Properties
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date Apr 15, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface Properties {

	/**
	 * properties file name
	 * 
	 * @return
	 */
	String value();

	/**
	 * properties directory
	 * 
	 * @return
	 */
	String dir() default "";
}
