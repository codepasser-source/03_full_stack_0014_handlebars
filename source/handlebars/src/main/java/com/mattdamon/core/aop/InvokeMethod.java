package com.mattdamon.core.aop;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodProxy;

import com.mattdamon.core.exception.GeneralException;

/**
 * 
 * InvokeMethod
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date Apr 16, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 *
 */
public class InvokeMethod {

	private Object caller;
	private Method method;
	private Object[] args;
	private MethodProxy proxy;

	public InvokeMethod(Object caller, Method method, Object[] args,
			MethodProxy proxy) {
		this.caller = caller;
		this.args = args;
		this.method = method;
		this.proxy = proxy;
	}

	public Object getCaller() {
		return caller;
	}

	public Method getMethod() {
		return method;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}

	public Object invokeSuper() throws GeneralException {
		try {
			return proxy.invokeSuper(caller, args);
		} catch (Throwable e) {
			throw new GeneralException(e);
		}
	}

}