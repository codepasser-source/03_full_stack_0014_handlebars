package com.mattdamon.core.exception;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import lombok.ToString;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.mattdamon.core.base.BaseConvert;

/**
 * 
 * GeneralException
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date Apr 15, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 *
 */
@ToString
public class GeneralException extends Exception implements BaseConvert {

	private static final long serialVersionUID = 1633213333563068730L;

	/**
	 * local
	 */
	private Locale defaultLocale = Locale.CHINA;
	/**
	 * resource name
	 */
	private static final String RESOURCE_NAME = "config.exception";
	/**
	 * message prefix
	 */
	private static final String MESSAGE_PREFIX = "ERROR";

	/**
	 * header 分隔符
	 */
	private static final String HEADER_DELIMITER = "-";
	/**
	 * 文字分隔符
	 */
	private static final String MESSAGE_DELIMITER = ": ";

	/**
	 * 异常消息类型
	 */
	private String category = null;

	/**
	 * 异常代码
	 */
	private String errorCode = null;
	/**
	 * 异常参数
	 */
	private Object[] arguments;

	/**
	 * 构造器
	 * 
	 * @param errorDescription
	 *            错误定义
	 */
	public GeneralException(final ErrorDescription errorDescription) {
		errorCode = errorDescription.getErrorCode().substring(2);
		category = errorDescription.getErrorCode().substring(0, 2);
	}

	/**
	 * 构造器
	 * 
	 * @param errorDescription
	 *            错误定义
	 * @param args
	 *            参数
	 */
	public GeneralException(final ErrorDescription errorDescription,
			final Object... args) {
		errorCode = errorDescription.getErrorCode().substring(2);
		category = errorDescription.getErrorCode().substring(0, 2);
		arguments = args;
		if (args.length > 0) {
			if (args[0] instanceof GeneralException) {
				GeneralException exception = (GeneralException) args[0];
				errorCode = exception.errorCode;
				category = exception.category;
				arguments = exception.arguments;
			}
		}
	}

	/**
	 * 构造器
	 * 
	 * @param throwable
	 *            Throwable
	 */
	public GeneralException(final Throwable throwable) {
		this(ErrorDescription.ERROR_OTHER_1, throwable);
	}

	/**
	 * 获取category
	 * 
	 * @return category
	 */
	public final String getCategory() {
		return category;
	}

	/**
	 * 获取errorCode
	 * 
	 * @return errorCode
	 */
	public final String getErrorCode() {
		return errorCode;
	}

	/**
	 * 获取code
	 * 
	 * @return code
	 */
	public String getCode() {
		return MESSAGE_PREFIX + HEADER_DELIMITER + category + HEADER_DELIMITER
				+ errorCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		return getLocalizedMessage();
	}

	/**
	 * 获取Bundel
	 * 
	 * @param locale
	 * @return Bundel
	 */
	private ResourceBundle getBundle(final Locale locale) {
		ResourceBundle resourceBundle = ResourceBundle.getBundle(getBaseName(),
				locale);
		if (resourceBundle.getLocale().getLanguage()
				.equals(locale.getLanguage())) {
			return resourceBundle;
		}

		return ResourceBundle.getBundle(getBaseName());
	}

	/**
	 * 获取baseName
	 * 
	 * @return baseName
	 */
	private String getBaseName() {
		return RESOURCE_NAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Throwable#getLocalizedMessage()
	 */
	@Override
	public final String getLocalizedMessage() {
		return getLocalizedMessage(getDefaultLocale());
	}

	/**
	 * 获取LocalizedMessage
	 * 
	 * @param locale
	 * @return LocalizedMessage
	 */
	public final String getLocalizedMessage(final Locale locale) {
		ResourceBundle resourceBundle = this.getBundle(locale);
		String resouceBundleLanguage = resourceBundle.getLocale().getLanguage();

		if (resouceBundleLanguage.length() == 0) {
			resouceBundleLanguage = Locale.getDefault().getLanguage();
		}

		if (!resouceBundleLanguage.equals(locale.getLanguage())) {
			return null;
		}
		String message = "";
		message += MESSAGE_PREFIX;
		message += HEADER_DELIMITER;
		message += category;
		message += HEADER_DELIMITER;
		message += errorCode;
		String key = message;// ERROR-10000001
		message += MESSAGE_DELIMITER;
		String detailFormatString = null;
		detailFormatString = resourceBundle.getString(key);
		message += MessageFormat.format(detailFormatString, arguments);
		return message;
	}

	/**
	 * 获取local
	 * 
	 * @return local
	 */
	public final Locale getDefaultLocale() {
		return this.defaultLocale;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mattdamon.core.base.BaseConvert#convertToJson()
	 */
	@Override
	public String convertToJson() {
		PropertyFilter filter = new PropertyFilter() {
			// 过滤不需要的字段
			public boolean apply(Object source, String name, Object value) {
				return !"suppressed".equals(name);
			}
		};
		SerializeWriter sw = new SerializeWriter();
		JSONSerializer serializer = new JSONSerializer(sw);
		serializer.getPropertyFilters().add(filter);
		serializer.write(this);
		return sw.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mattdamon.core.base.BaseConvert#convertToXml()
	 */
	@Override
	public String convertToXml() {
		Document document = DocumentHelper.createDocument();
		try {
			Element root = document.addElement("generalException");

			Element category = root.addElement("category");
			category.addText(this.getCategory());

			Element code = root.addElement("code");
			code.addText(this.getCode());

			Element defaultLocale = root.addElement("defaultLocale");
			defaultLocale.addText(this.getDefaultLocale().toString());

			Element localizedMessage = root.addElement("localizedMessage");
			localizedMessage.addText(this.getLocalizedMessage());

			Element message = root.addElement("message");
			message.addText(this.getMessage());

			Element errorCode = root.addElement("errorCode");
			errorCode.addText(this.getErrorCode());

			// StackTraceElement
			StackTraceElement[] stackTraceArray = this.getStackTrace();
			for (int index = 0; index < stackTraceArray.length; index++) {
				Element stackTrace = root.addElement("stackTrace");
				Element className = stackTrace.addElement("className");
				className.addText(stackTraceArray[index].getClassName());
				Element fileName = stackTrace.addElement("fileName");
				fileName.addText(stackTraceArray[index].getFileName());
				Element lineNumber = stackTrace.addElement("lineNumber");
				lineNumber.addText("" + stackTraceArray[index].getLineNumber());
				Element methodName = stackTrace.addElement("methodName");
				methodName.addText(stackTraceArray[index].getMethodName());
				Element nativeMethod = stackTrace.addElement("nativeMethod");
				nativeMethod.addText(""
						+ stackTraceArray[index].isNativeMethod());
			}

		} catch (Exception e) {
			// #IGNORE
			e.printStackTrace();
		}
		return document.asXML();
	}

}
