package com.mattdamon.core.aop;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import com.mattdamon.core.aop.annotation.Extend;
import com.mattdamon.core.exception.GeneralException;

/**
 * 
 * ExtendUtils
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date Apr 16, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 *
 */
public class ExtendUtils {

	private ExtendUtils() {
	}

	public static <T> T extend(Class<T> clazz) {
		Enhancer e = new Enhancer();
		e.setSuperclass(clazz);
		e.setCallback(new InvokeExtend(clazz));
		return clazz.cast(e.create());
	}

	private static class InvokeExtend implements MethodInterceptor {

		private Class<?> clazz;

		public InvokeExtend(Class<?> clazz) {
			this.clazz = clazz;
		}

		public Object intercept(Object obj, Method method, Object[] args,
				MethodProxy proxy) throws Throwable {

			if (!clazz.isAnnotationPresent(Extend.class)
					&& !method.isAnnotationPresent(Extend.class)) {
				return proxy.invokeSuper(obj, args);
			}

			List<Class<? extends InvokeFilter>> filterClazzs = new ArrayList<Class<? extends InvokeFilter>>();
			if (clazz.isAnnotationPresent(Extend.class)) {
				Extend clzExtend = clazz.getAnnotation(Extend.class);
				Collections.addAll(filterClazzs, clzExtend.value());
			}

			if (method.isAnnotationPresent(Extend.class)) {
				Extend methodExtend = method.getAnnotation(Extend.class);
				Collections.addAll(filterClazzs, methodExtend.value());
			}

			InvokeFilterChain chain = new CoreInvokeFilterChain(method,
					filterClazzs);
			chain.addFilter(new TargetInvoke());

			InvokeMethod invoke = new InvokeMethod(obj, method, args, proxy);
			return chain.doFilter(invoke);
		}
	}

	private static class TargetInvoke extends DefaultInvokeFilter {
		@Override
		public Object invoke() throws GeneralException {
			InvokeMethod method = getMethod();
			return method.invokeSuper();
		}
	}
}
