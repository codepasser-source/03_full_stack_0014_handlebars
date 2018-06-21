package com.mattdamon.core.exception;

/**
 * 
 * RenderException
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date Jun 9, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 */
public class RenderException extends GeneralRuntimeException {

	private static final long serialVersionUID = 683377994664362172L;

	public RenderException() {

	}

	public RenderException(String message) {
		super(message);
	}

	public RenderException(String message, Throwable cause) {
		super(message, cause);
	}

	public RenderException(Throwable cause) {
		super(cause);
	}
}
