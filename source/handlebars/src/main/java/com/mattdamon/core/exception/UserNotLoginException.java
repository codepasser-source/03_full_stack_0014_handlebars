package com.mattdamon.core.exception;

/**
 * 
 * UserNotLoginException
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date Jun 9, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 */
public class UserNotLoginException extends GeneralRuntimeException {

	private static final long serialVersionUID = -4699696761538260968L;

	public UserNotLoginException() {
	}

	public UserNotLoginException(String message) {
		super(message);
	}

	public UserNotLoginException(Throwable cause) {
		super(cause);
	}

	public UserNotLoginException(String message, Throwable cause) {
		super(message, cause);
	}
}
