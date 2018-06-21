package com.mattdamon.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * UnAuthorize401Exception
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date Jun 9, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnAuthorize401Exception extends GeneralRuntimeException {

	private static final long serialVersionUID = 4533328463556533417L;

	public UnAuthorize401Exception() {
	}

	public UnAuthorize401Exception(String message) {
		super(message);
	}

	public UnAuthorize401Exception(String message, Throwable cause) {
		super(message, cause);
	}

	public UnAuthorize401Exception(Throwable cause) {
		super(cause);
	}
}
