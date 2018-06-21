package com.mattdamon.core.aop;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mattdamon.core.exception.GeneralException;

/**
 * 
 * CoreInvokeFilterChain
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date Apr 16, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 *
 */
public class CoreInvokeFilterChain implements InvokeFilterChain {

	private static final Log LOG = LogFactory
			.getLog(CoreInvokeFilterChain.class);

	private List<InvokeFilter> filters = new ArrayList<InvokeFilter>();

	private int currentFilterIndex = 0;

	public CoreInvokeFilterChain(Method method,
			Collection<Class<? extends InvokeFilter>> clazzs)
			throws GeneralException {
		try {
			for (Class<? extends InvokeFilter> clazz : clazzs) {
				InvokeFilter filter = clazz.newInstance();
				filters.add(filter);
			}
		} catch (InstantiationException e) {
			LOG.error(e);
			throw new GeneralException(e);
		} catch (IllegalAccessException e) {
			LOG.error(e);
			throw new GeneralException(e);
		}
	}

	@Override
	public void addFilter(InvokeFilter filter) {
		filters.add(filter);
	}

	@Override
	public Object doFilter(InvokeMethod method) throws GeneralException {
		Object retValue = null;
		if (currentFilterIndex < filters.size()) {
			InvokeFilter filter = filters.get(currentFilterIndex++);

			filter.init(method, this);
			try {
				Class<? extends InvokeFilter> clzFilter = filter.getClass();
				Method m = method.getMethod();
				try {
					Method filtMethod = clzFilter.getMethod(m.getName(),
							m.getParameterTypes());
					Object[] args = method.getArgs();
					retValue = filtMethod.invoke(filter, args);
				} catch (NoSuchMethodException e) {
					retValue = filter.invoke();
				}
			} catch (SecurityException e) {
				LOG.error(e);
				throw new GeneralException(e);
			} catch (IllegalAccessException e) {
				LOG.error(e);
				throw new GeneralException(e);
			} catch (IllegalArgumentException e) {
				LOG.error(e);
				throw new GeneralException(e);
			} catch (InvocationTargetException e) {
				Throwable target = e.getTargetException();
				LOG.error(target);
				throw new GeneralException(target);
			}
		}

		return retValue;
	}

}
