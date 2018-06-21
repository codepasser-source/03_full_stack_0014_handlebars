package com.mattdamon.core.db.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.mattdamon.core.db.annotation.enums.GeneratedItem;
import com.mattdamon.core.db.annotation.enums.GeneratedStrategy;

/**
 * 
 * GeneratedValue.java
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date May 7, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.FIELD, ElementType.METHOD })
public @interface GeneratedValue {

	GeneratedItem generator();

	GeneratedStrategy strategy() default GeneratedStrategy.DEFAULT;
}
