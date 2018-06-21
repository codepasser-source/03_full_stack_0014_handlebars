package com.mattdamon.core.properties;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.ConvertUtils;

import com.mattdamon.core.common.GlobalVariables;
import com.mattdamon.core.exception.ErrorDescription;
import com.mattdamon.core.exception.GeneralException;
import com.mattdamon.core.properties.annotation.Property;
import com.mattdamon.core.utils.StringUtiler;

/**
 * 
 * PropertiesHandler
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date Apr 15, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 *
 */
public final class PropertiesHandler implements GlobalVariables {

	private static Map<String, NullableProperties> propertiesMap = new HashMap<String, NullableProperties>();

	private static Map<String, File> propertiesFileMap = new HashMap<String, File>();

	private static Map<String, Long> lastModifyTimeMap = new HashMap<String, Long>();

	@SuppressWarnings("unchecked")
	public static final synchronized <T> T getProperties(Class<T> clazz)
			throws GeneralException {
		// create instance for type T.
		T instance;
		try {
			Constructor<? extends Object> constructor = clazz.getConstructor();
			instance = (T) constructor.newInstance();
		} catch (Exception e1) {
			throw new GeneralException(e1);
		}
		// check annotation
		if (!clazz
				.isAnnotationPresent(com.mattdamon.core.properties.annotation.Properties.class)) {
			return instance;
		}
		// read properties file.
		com.mattdamon.core.properties.annotation.Properties annotationProperties = clazz
				.getAnnotation(com.mattdamon.core.properties.annotation.Properties.class);
		String propertiesName = annotationProperties.value();
		String dir = annotationProperties.dir();

		NullableProperties properties = propertiesMap.get(propertiesName);
		File propertiesFile = propertiesFileMap.get(propertiesName);
		Long lastModifyTime = lastModifyTimeMap.get(propertiesName);
		if (properties == null || propertiesFile == null
				|| propertiesFile.lastModified() > lastModifyTime) {
			propertiesFile = getPropertiesFile(propertiesName, dir);
			propertiesFileMap.put(propertiesName, propertiesFile);

			lastModifyTime = propertiesFile.lastModified();
			lastModifyTimeMap.put(propertiesName, lastModifyTime);

			properties = (NullableProperties) readProperties(propertiesFile,
					dir);
			propertiesMap.put(propertiesName, properties);
		} else {
			properties = propertiesMap.get(propertiesName);
		}

		// find set method which has Property annotation.
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			int mod = method.getModifiers();
			if (!Modifier.isPublic(mod)
					|| !method.isAnnotationPresent(Property.class)) {
				continue;
			}

			Class<?>[] parameterTypes = method.getParameterTypes();
			if (parameterTypes.length != 1) {
				throw new GeneralException(
						ErrorDescription.ERROR_METHOD_PARAMETER_NUMBER_2,
						method.getName(), 1);
			}

			Property annotationProperty = method.getAnnotation(Property.class);
			String key = annotationProperty.key();
			String defaultValue = annotationProperty.defaultValue();
			String propertyValue = properties.getProperty(key, defaultValue);
			try {
				method.invoke(instance,
						ConvertUtils.convert(propertyValue, parameterTypes[0]));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}

		if (instance instanceof Properties) {
			Properties props = (Properties) instance;
			props.putAll(properties);
		}

		return instance;
	}

	private static Properties readProperties(File file, String defaultDir)
			throws GeneralException {
		BufferedInputStream bis = null;
		Properties prop = null;
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			if (loader == null) {
				loader = PropertiesHandler.class.getClassLoader();
			}
			// when junit run jar properties read
			if (StringUtiler.isNullOrEmpty(defaultDir)) {
				bis = new BufferedInputStream(loader.getResourceAsStream(file
						.getName()));
			} else {
				bis = new BufferedInputStream(
						loader.getResourceAsStream(defaultDir + DIR_SEPARATOR
								+ file.getName()));
			}
			prop = new NullableProperties();
			prop.load(bis);
		} catch (SecurityException ex2) {
			throw new GeneralException(ex2);
		} catch (IOException ex3) {
			throw new GeneralException(ex3);
		} finally {
			try {
				if (bis != null) {
					bis.close();
				}
			} catch (IOException ex) {
			}
		}
		return prop;
	}

	private static File getPropertiesFile(String filename, String defaultDir)
			throws GeneralException {

		defaultDir = processPath(defaultDir);

		File file = new File(defaultDir, filename);
		if (!file.exists()) {
			try {
				ClassLoader loader = Thread.currentThread()
						.getContextClassLoader();
				if (loader == null) {
					loader = PropertiesHandler.class.getClassLoader();
				}
				if (loader != null) {
					URL url = file.isAbsolute() ? loader.getResource(filename)
							: loader.getResource(file.getPath());
					if (url != null) {
						String newFilename = URLDecoder.decode(url.getFile(),
								"ISO-8859-1");
						file = new File(newFilename);
					}
				}
				if (!file.exists()) {
					// when file in the jar,an absolute file to be create;
					file = new File(DIR_SEPARATOR + defaultDir + DIR_SEPARATOR
							+ filename);
				}
			} catch (SecurityException e) {
				throw new GeneralException(e);
			} catch (IOException ex2) {
				throw new GeneralException(ex2);
			}
		}

		return file;
	}

	private static String processPath(String defaultDir) {
		Pattern pattern = Pattern.compile("\\$\\{(\\p{Alpha}+[\\w\\.]*)\\}");
		Matcher matcher = pattern.matcher(defaultDir);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			String variable = matcher.group(1);
			String value = System.getProperty(variable);
			if (value != null) {
				value = value.replaceAll("\\\\", "\\\\\\\\");
				matcher.appendReplacement(sb, value);
			}
		}

		matcher.appendTail(sb);
		return sb.toString();
	}

	public static class NullableProperties extends Properties {

		private static final long serialVersionUID = 8591137352668286302L;

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Properties#getProperty(java.lang.String,
		 * java.lang.String)
		 */
		@Override
		public String getProperty(String key, String defaultValue) {
			String origin = super.getProperty(key, defaultValue);
			if (origin != null && origin.trim().length() == 0) {
				// 空白
				return defaultValue;
			}
			return origin;
		}
	}
}
