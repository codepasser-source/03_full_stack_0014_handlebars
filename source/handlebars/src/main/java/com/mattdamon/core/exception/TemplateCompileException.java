package com.mattdamon.core.exception;

/**
 * 
 * TemplateCompileException
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date Jun 9, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 */
public class TemplateCompileException extends GeneralRuntimeException {

	private static final long serialVersionUID = 3196735858881429614L;

	public TemplateCompileException() {
	}

	public TemplateCompileException(String message) {
		super(message);
	}

	public TemplateCompileException(String message, Throwable cause) {
		super(message, cause);
	}

	public TemplateCompileException(Throwable cause) {
		super(cause);
	}
}