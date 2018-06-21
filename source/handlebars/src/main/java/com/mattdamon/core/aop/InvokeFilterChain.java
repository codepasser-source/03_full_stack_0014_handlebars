package com.mattdamon.core.aop;

import com.mattdamon.core.exception.GeneralException;

/**
 * 
 * InvokeFilterChain
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date Apr 16, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 *
 */
public interface InvokeFilterChain {

	void addFilter(InvokeFilter filter);

	Object doFilter(InvokeMethod invoke) throws GeneralException;

}
