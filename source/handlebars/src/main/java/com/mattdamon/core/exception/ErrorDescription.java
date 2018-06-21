package com.mattdamon.core.exception;

import java.text.DecimalFormat;

/**
 * 
 * ErrorDescription
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date Apr 15, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 *
 */
public final class ErrorDescription implements ErrorCode {

	private static final DecimalFormat FORMATTER = new DecimalFormat("00000000");

	// error code
	private final String errorCode;

	/**
	 * 创建ErrorDescription
	 * 
	 * @param errorCode
	 * @return ErrorDescription
	 */
	private static ErrorDescription create(final int errorCode) {
		return new ErrorDescription(FORMATTER.format(errorCode));
	}

	/**
	 * 构造器
	 * 
	 * @param formatErrorCode
	 */
	private ErrorDescription(final String formatErrorCode) {
		this.errorCode = formatErrorCode;
	}

	/**
	 * default构造器(屏蔽)
	 */
	private ErrorDescription() {
		// Default constructor should not be used.
		this(null);
	}

	/**
	 * 获取errorCode
	 * 
	 * @return errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/********************** SYSTEM ERROR ***************************/

	/**
	 * 系统错误-未知异常。
	 */
	public static final ErrorDescription ERROR_OTHER_1 = create(ERROR_OTHER);

	/**
	 * 参数个数错误。
	 */
	public static final ErrorDescription ERROR_METHOD_PARAMETER_NUMBER_2 = create(ERROR_METHOD_PARAMETER_NUMBER);

	/**
	 * 参数值错误。
	 */
	public static final ErrorDescription ERROR_METHOD_PARAMETER_VALUE_2 = create(ERROR_METHOD_PARAMETER_VALUE);

	/**
	 * 数据库异常.
	 */
	public static final ErrorDescription ERROR_SQLEXCEPTION_OCCURRED_1 = create(ERROR_SQLEXCEPTION_OCCURRED);

	/**
	 * 网络连接异常.
	 */
	public static final ErrorDescription ERROR_HTTPEXCEPTION_OCCURRED_2 = create(ERROR_HTTPEXCEPTION_OCCURRED);

	/**
	 * ES服务节点为空
	 */
	public static final ErrorDescription ERROR_SEARCH_SERVER_NODE_EMPTY_0 = create(ERROR_SEARCH_SERVER_NODE_EMPTY);

	/**
	 * ES批量插入数据错误
	 */
	public static final ErrorDescription ERROR_SEARCH_BULK_INSERT_OCCURRED_1 = create(ERROR_SEARCH_BULK_INSERT_OCCURRED);

	/**
	 * ES[区间搜索]必须传递两个值，但是只传递了一个值.
	 */
	public static final ErrorDescription ERROR_SEARCH_RANGE_QUERY_OCCURRED_0 = create(ERROR_SEARCH_RANGE_QUERY_OCCURRED);

	/********************** CUSTOMER ERROR ***************************/

}
