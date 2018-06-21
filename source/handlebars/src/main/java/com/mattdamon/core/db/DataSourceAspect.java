package com.mattdamon.core.db;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import com.mattdamon.core.base.BaseDto;
import com.mattdamon.core.base.BaseModel;
import com.mattdamon.core.db.annotation.DataSource;
import com.mattdamon.core.log.CoreLogger;
import com.mattdamon.core.log.CoreLoggerFactory;

/**
 * 
 * DataSourceAspect.java
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date May 5, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 *
 */
public class DataSourceAspect {

	private static final CoreLogger logger = CoreLoggerFactory
			.getLogger(DataSourceAspect.class);

	public void before(JoinPoint point) {
		Object target = point.getTarget();
		String method = point.getSignature().getName();

		Class<?>[] classz = target.getClass().getInterfaces();

		Class<?>[] parameterTypes = ((MethodSignature) point.getSignature())
				.getMethod().getParameterTypes();
		try {
			for (int i = 0; i < parameterTypes.length; i++) {
				if (parameterTypes[i].getSuperclass() != null) {
					if (parameterTypes[i].getSuperclass().getName()
							.equals(BaseModel.class.getName())) {
						parameterTypes[i] = BaseModel.class;
					} else if (parameterTypes[i].getSuperclass().getName()
							.equals(BaseDto.class.getName())) {
						parameterTypes[i] = BaseDto.class;
					}
				}
			}

			Method m = classz[0].getMethod(method, parameterTypes);
			if (m != null && m.isAnnotationPresent(DataSource.class)) {
				DataSource data = m.getAnnotation(DataSource.class);
				DynamicDataSourceHolder.putDataSource(data.value());
				logger.consoleLog(data.value());
			}
		} catch (Exception e) {
			// #IGNORE
			e.printStackTrace();
		}
	}
}
