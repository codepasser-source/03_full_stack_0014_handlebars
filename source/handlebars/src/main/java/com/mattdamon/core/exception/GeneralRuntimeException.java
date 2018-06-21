package com.mattdamon.core.exception;

/**
 * 
 * GeneralRuntimeException
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date Jun 9, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 */
public class GeneralRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 5287205175840676327L;

	public GeneralRuntimeException() {
		super();
	}

	public GeneralRuntimeException(Throwable cause) {
		super(cause);
	}

	public GeneralRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public GeneralRuntimeException(String message) {
		super(message);
	}
}
