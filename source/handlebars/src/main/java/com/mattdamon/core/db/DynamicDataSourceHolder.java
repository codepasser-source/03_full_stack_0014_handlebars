package com.mattdamon.core.db;

/**
 * 
 * DynamicDataSourceHolder.java
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date May 5, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 *
 */
public class DynamicDataSourceHolder {

	public static final ThreadLocal<String> holder = new ThreadLocal<String>();

	public static void putDataSource(String name) {
		holder.set(name);
	}

	public static String getDataSouce() {
		return holder.get();
	}
}
