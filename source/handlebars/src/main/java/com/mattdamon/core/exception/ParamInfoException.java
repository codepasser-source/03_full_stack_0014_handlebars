package com.mattdamon.core.exception;

/**
 * 
 * ParamInfoException
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date Jun 9, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 */
public class ParamInfoException extends GeneralRuntimeException {

	private static final long serialVersionUID = 3196735858881429614L;

	public ParamInfoException() {
	}

	public ParamInfoException(String message) {
		super(message);
	}

	public ParamInfoException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParamInfoException(Throwable cause) {
		super(cause);
	}
}