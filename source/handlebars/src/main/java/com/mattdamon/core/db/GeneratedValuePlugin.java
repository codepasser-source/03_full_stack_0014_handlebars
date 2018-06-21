package com.mattdamon.core.db;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import com.mattdamon.core.db.annotation.GeneratedValue;
import com.mattdamon.core.db.annotation.enums.GeneratedItem;
import com.mattdamon.core.db.annotation.enums.GeneratedStrategy;

/**
 * 
 * AuditingInterceptor.java
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date May 7, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 *
 */
@Intercepts({ @Signature(type = Executor.class, method = "update", args = {
		MappedStatement.class, Object.class }) })
public class GeneratedValuePlugin implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {

		MappedStatement mappedStatement = (MappedStatement) invocation
				.getArgs()[0];
		SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
		Object parameter = invocation.getArgs()[1];

		Class<?> parameterClass = parameter.getClass();
		Class<?> parameterSupperClass = parameterClass.getSuperclass();
		List<Field> fieldList = new ArrayList<Field>();
		if (parameterSupperClass != null) {
			Field[] fields = parameterSupperClass.getDeclaredFields();
			for (int index = 0; index < fields.length; index++) {
				fieldList.add(fields[index]);
			}
		}
		Field[] fields = parameterClass.getDeclaredFields();
		for (int index = 0; index < fields.length; index++) {
			fieldList.add(fields[index]);
		}

		Date currentDate = new Date();
		// INSERT
		if (SqlCommandType.INSERT == sqlCommandType && fieldList != null
				&& fieldList.size() > 0) {
			for (Field field : fieldList) {
				if (field.isAnnotationPresent(GeneratedValue.class)) {
					GeneratedValue generatedValue = field
							.getAnnotation(GeneratedValue.class);
					field.setAccessible(true);
					// ID
					if (generatedValue.generator().equals(GeneratedItem.ID)) {
						if (generatedValue.strategy().equals(
								GeneratedStrategy.UUID)) {
							field.set(parameter, UUID.randomUUID().toString()
									.replace("-", ""));
						}
					} else
					// CREATE_AT
					if (generatedValue.generator().equals(
							GeneratedItem.CREATEAT)) {
						field.set(parameter, currentDate);
					} else
					// IS_DELETE
					if (generatedValue.generator().equals(GeneratedItem.DELETE)) {
						field.set(parameter, false);
					}
					field.setAccessible(false);
				}
			}
		} else
		// UPDATE
		if (SqlCommandType.UPDATE == sqlCommandType && fieldList != null
				&& fieldList.size() > 0) {
			for (Field field : fieldList) {
				if (field.isAnnotationPresent(GeneratedValue.class)) {
					GeneratedValue generatedValue = field
							.getAnnotation(GeneratedValue.class);
					field.setAccessible(true);
					// ID
					if (generatedValue.generator().equals(
							GeneratedItem.UPDATEAT)) {
						field.set(parameter, currentDate);
					}
					field.setAccessible(false);
				}
			}
		} else
		// DELETE
		if (SqlCommandType.DELETE == sqlCommandType && fieldList != null
				&& fieldList.size() > 0) {
			for (Field field : fieldList) {
				if (field.isAnnotationPresent(GeneratedValue.class)) {
					GeneratedValue generatedValue = field
							.getAnnotation(GeneratedValue.class);
					field.setAccessible(true);
					// IS_DELETE
					if (generatedValue.generator().equals(GeneratedItem.DELETE)) {
						field.set(parameter, true);
					}
					field.setAccessible(false);
				}
			}
		}

		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		if (target instanceof Executor) {
			return Plugin.wrap(target, this);
		} else {
			return target;
		}
	}

	@Override
	public void setProperties(Properties properties) {
	}

}
