package com.mattdamon.core.exception;

import com.mattdamon.core.log.CoreLogger;
import com.mattdamon.core.log.CoreLoggerFactory;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * GeneralExceptionResolver.java
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date May 5, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 *
 */
public class GeneralExceptionResolver implements HandlerExceptionResolver {

	private static final CoreLogger logger = CoreLoggerFactory
			.getLogger(GeneralExceptionResolver.class);

	private static final String MAPPING_ACTION_REGEX = ".*\\.action";

	private static final String MAPPING_AJAX_REGEX = ".*\\.do";

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object obj, Exception ex) {

		String requestURL = request.getRequestURL().toString();

		GeneralException exception = null;
		if (ex instanceof GeneralException) {
			exception = (GeneralException) ex;
		} else {
			exception = new GeneralException(ex);
		}

		logger.systemLog(exception);

		if (mappingUrl(requestURL, MAPPING_ACTION_REGEX)) {
			return new ModelAndView("/WEB-INF/jsp/error/500.jsp", "error",
					exception.convertToJson());
		} else if (mappingUrl(requestURL, MAPPING_AJAX_REGEX)) {
			return new ModelAndView("/WEB-INF/jsp/error/transfer.jsp",
					"errorJson", exception.convertToJson());
		} else {
			return new ModelAndView("/WEB-INF/jsp/error/500.jsp", "error",
					exception.convertToJson());
		}

	}

	protected boolean mappingUrl(String requestURL, String regex) {
		return requestURL.matches(regex);
	}
}
