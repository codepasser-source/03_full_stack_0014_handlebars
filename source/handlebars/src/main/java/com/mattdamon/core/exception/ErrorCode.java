package com.mattdamon.core.exception;

/**
 * 
 * ErrorCode
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date Apr 15, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 *
 */
public interface ErrorCode {

	/********************** SYSTEM ERROR ***************************/
	/**
	 * 系统错误-未知异常。
	 */
	int ERROR_OTHER = 10000000;

	/**
	 * 参数个数错误。
	 */
	int ERROR_METHOD_PARAMETER_NUMBER = 10000001;

	/**
	 * 参数值错误。
	 */
	int ERROR_METHOD_PARAMETER_VALUE = 10000002;

	/**
	 * 数据库异常.
	 */
	int ERROR_SQLEXCEPTION_OCCURRED = 10000003;

	/**
	 * 网络连接异常.
	 */
	int ERROR_HTTPEXCEPTION_OCCURRED = 10000004;

	/**
	 * ES服务节点为空
	 */
	int ERROR_SEARCH_SERVER_NODE_EMPTY = 10000005;

	/**
	 * ES批量插入错误
	 */
	int ERROR_SEARCH_BULK_INSERT_OCCURRED = 10000006;

	/**
	 * ES
	 */
	int ERROR_SEARCH_RANGE_QUERY_OCCURRED = 10000007;

	/********************** CUSTOMER ERROR ***************************/

}
