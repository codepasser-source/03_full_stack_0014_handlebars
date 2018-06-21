package com.mattdamon.core.aop;

import com.mattdamon.core.exception.GeneralException;

/**
 * 
 * DefaultInvokeFilter
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date Apr 16, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 *
 */
public abstract class DefaultInvokeFilter implements InvokeFilter {

	private InvokeMethod method;
	private InvokeFilterChain filterChain;

	@Override
	public void init(InvokeMethod method, InvokeFilterChain chain) {
		this.method = method;
		this.filterChain = chain;
	}

	public InvokeMethod getMethod() {
		return method;
	}

	public InvokeFilterChain getFilterChain() {
		return filterChain;
	}

	@Override
	public Object invoke() throws GeneralException {
		return filterChain.doFilter(method);
	}
}