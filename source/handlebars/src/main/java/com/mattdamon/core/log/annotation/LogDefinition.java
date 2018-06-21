package com.mattdamon.core.log.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.mattdamon.core.log.annotation.enums.LogCategory;
import com.mattdamon.core.log.annotation.enums.LogLevel;

/**
 * 
 * LogDefinition
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date Apr 20, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface LogDefinition {

	LogLevel level() default LogLevel.DEBUG;

	LogCategory category() default LogCategory.CONSOLE;

}
