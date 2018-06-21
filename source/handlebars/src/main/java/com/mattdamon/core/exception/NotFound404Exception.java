package com.mattdamon.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * NotFound404Exception
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date Jun 9, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFound404Exception extends GeneralRuntimeException {

	private static final long serialVersionUID = 275717926006869280L;

	public NotFound404Exception() {
	}

	public NotFound404Exception(Throwable cause) {
		super(cause);
	}

	public NotFound404Exception(String message, Throwable cause) {
		super(message, cause);
	}

	public NotFound404Exception(String message) {
		super(message);
	}
}
