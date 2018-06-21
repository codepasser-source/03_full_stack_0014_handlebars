package com.mattdamon.core.interaction;

import java.text.DecimalFormat;

/**
 * 
 * ResponseDescription
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date Apr 15, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 *
 */
public final class ResponseDescription implements ResponseCode {

	private static final DecimalFormat FORMATTER = new DecimalFormat("00000000");
	private final String code;

	/**
	 * 创建ResponseDescription
	 * 
	 * @param code
	 * @return
	 */
	private static ResponseDescription create(final int code) {
		return new ResponseDescription(FORMATTER.format(code));
	}

	/**
	 * 构造器
	 * 
	 * @param formatErrorCode
	 */
	private ResponseDescription(final String formatErrorCode) {
		this.code = formatErrorCode;
	}

	/**
	 * default构造器(屏蔽)
	 */
	private ResponseDescription() {
		// Default constructor should not be used.
		this(null);
	}

	/**
	 * 获取code
	 * 
	 * @return
	 */
	public String getCode() {
		return code;
	}

	/********************** SYSTEM MESSAGE ***************************/

	/**
	 * 系统消息-操作成功。
	 */
	public static final ResponseDescription RESPONSE_SUCCESS_1 = create(RESPONSE_SUCCESS);

	/**
	 * 系统消息-操作失败。
	 */
	public static final ResponseDescription RESPONSE_FAILED_2 = create(RESPONSE_FAILED);

}
