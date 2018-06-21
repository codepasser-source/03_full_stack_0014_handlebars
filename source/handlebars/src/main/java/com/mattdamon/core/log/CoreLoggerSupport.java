package com.mattdamon.core.log;

/**
 * 
 * CoreLoggerSupport
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date Apr 20, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 *
 */
public interface CoreLoggerSupport {

	/**
	 * get log classze
	 * 
	 * @return
	 */
	Class<?> getClassze();

	/**
	 * set log classze
	 * 
	 * @param classze
	 */
	void setClassze(Class<?> classze);

}