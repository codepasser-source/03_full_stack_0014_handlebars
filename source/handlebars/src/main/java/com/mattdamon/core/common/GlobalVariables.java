package com.mattdamon.core.common;

/**
 * 
 * GlobalVariables
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date Apr 15, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 *
 */
public interface GlobalVariables {

	// 目录符号
	static final String DIR_SEPARATOR = "/";
	// 空串
	static final String STRING_BLANK = "";

	// 页面最小容量
	static final int RECORD_PAGE_SIZE_MIN = 10;

	// 页面最大容量
	static final int RECORD_PAGE_SIZE_MAX = 100;

	// 页码最小值
	static final int RECORD_PAGE_NO_MIN = 1;

	// Log level :TRACE, DEBUG, INFO, WARN, ERROR
	static final String LOG_LEVEL_TRACE = "TRACE";
	static final String LOG_LEVEL_DEBUG = "DEBUG";
	static final String LOG_LEVEL_INFO = "INFO";
	static final String LOG_LEVEL_WARN = "WARN";
	static final String LOG_LEVEL_ERROR = "ERROR";

	// Log category
	static final String LOG_CATEGORY_CONSOLE = "[NEU-ECOMMERCE-CONSOLE-LOG]";
	static final String LOG_CATEGORY_SYSTEM = "[NEU-ECOMMERCE-SYSTEM-LOG]";
	static final String LOG_CATEGORY_ACCESS = "[NEU-ECOMMERCE-ACCESS-LOG]";
	static final String LOG_CATEGORY_CONTROLLER = "[NEU-ECOMMERCE-CONTROLLER-LOG]";
	static final String LOG_CATEGORY_SERVICE = "[NEU-ECOMMERCE-SERVICE-LOG]";

	// 日期格式
	static final String DATE_FORMAT_YMDHMS = "yyyy-MM-dd HH:mm:ss";
	static final String DATE_FORMAT_YMD = "yyyy-MM-dd";

	// ES index name
	static final String ES_INDEX_GOODS = "goods";
	static final String ES_INDEX_STORE = "stores";

}
