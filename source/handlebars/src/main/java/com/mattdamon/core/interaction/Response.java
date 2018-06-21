package com.mattdamon.core.interaction;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import lombok.ToString;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.mattdamon.core.base.BaseData;
import com.mattdamon.core.base.BaseObject;

/**
 * 
 * Response
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date Apr 15, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 *
 */
@ToString
public class Response<T extends BaseData> extends BaseObject<T> {

	private static final long serialVersionUID = -3843021543220905954L;

	private Locale defaultLocale = Locale.CHINA;
	private static final String RESOURCE_NAME = "config.response";
	private static final String MESSAGE_PREFIX = "RESP";
	private static final String HEADER_DELIMITER = "-";
	private static final String MESSAGE_DELIMITER = ": ";
	// 消息类型
	private String category = null;
	// 消息代码
	private String code = null;
	// 消息代码
	private String msgCode = null;
	// 消息数据
	private T responseData = null;
	// 消息参数
	private Object[] arguments;

	public Response() {
		super();
	}

	/**
	 * 构造器
	 * 
	 * @param responseDescription
	 */
	public Response(final ResponseDescription responseDescription) {
		code = responseDescription.getCode().substring(2);
		msgCode = code;
		category = responseDescription.getCode().substring(0, 2);
	}

	/**
	 * 构造器
	 * 
	 * @param responseDescription
	 * @param responseData
	 */
	public Response(final ResponseDescription responseDescription,
			T responseData) {
		code = responseDescription.getCode().substring(2);
		msgCode = code;
		category = responseDescription.getCode().substring(0, 2);
		this.responseData = responseData;
	}

	/**
	 * 构造器
	 * 
	 * @param responseDescription
	 * @param args
	 */
	public Response(final ResponseDescription responseDescription,
			final Object... args) {
		code = responseDescription.getCode().substring(2);
		msgCode = code;
		category = responseDescription.getCode().substring(0, 2);

		arguments = args;
		if (args.length > 0) {
			if (args[0] instanceof Response) {
				@SuppressWarnings("unchecked")
				Response<T> message = (Response<T>) args[0];
				code = message.code;
				category = message.category;
				arguments = message.arguments;
			}
		}
	}

	/**
	 * 构造器
	 * 
	 * @param responseDescription
	 * @param responseData
	 * @param args
	 */
	public Response(final ResponseDescription responseDescription,
			T responseData, final Object... args) {
		code = responseDescription.getCode().substring(2);
		msgCode = code;
		category = responseDescription.getCode().substring(0, 2);
		this.responseData = responseData;

		arguments = args;
		if (args.length > 0) {
			if (args[0] instanceof Response) {
				@SuppressWarnings("unchecked")
				Response<T> message = (Response<T>) args[0];
				code = message.code;
				category = message.category;
				arguments = message.arguments;
			}
		}
	}

	/**
	 * 获取msgCode
	 * 
	 * @return msgCode
	 */
	public String getMsgCode() {
		return msgCode;
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
	 * 获取code
	 * 
	 * @return code
	 */
	public String getCode() {
		return MESSAGE_PREFIX + HEADER_DELIMITER + category + HEADER_DELIMITER
				+ code;
	}

	/**
	 * 获取message
	 * 
	 * @return message
	 */
	public String getMessage() {
		return getLocalizedMessage();
	}

	/**
	 * 获取bundel
	 * 
	 * @param locale
	 * @return locale
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

	/**
	 * 获取LocalizedMessage
	 * 
	 * @return LocalizedMessage
	 */
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
		message += code;
		String key = message;// MSG-00-000000
		message += MESSAGE_DELIMITER;
		String detailFormatString = null;
		detailFormatString = resourceBundle.getString(key);
		message += MessageFormat.format(detailFormatString, arguments);
		return message;
	}

	/**
	 * 获取defaultLocale
	 * 
	 * @return defaultLocale
	 */
	public final Locale getDefaultLocale() {
		return this.defaultLocale;
	}

	/**
	 * 获取responseData
	 * 
	 * @return responseData
	 */
	@SuppressWarnings("unchecked")
	public <D extends BaseData> D getResponseData() {
		return (D) this.responseData;
	}

	/**
	 * 设置responseData
	 * 
	 * @param responseData
	 */
	public void setResponseData(T responseData) {
		this.responseData = responseData;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mattdamon.core.base.BaseObject#convertToXml()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String convertToXml() {
		Document document = DocumentHelper.createDocument();
		try {
			Element root = document.addElement("response");

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

			Element msgCode = root.addElement("msgCode");
			msgCode.addText(this.getMsgCode());

			Element responseData = root.addElement("responseData");
			Document responseDataDoc = DocumentHelper
					.parseText(((BaseObject<BaseData>) this.getResponseData())
							.convertToXml());
			responseData.appendContent(responseDataDoc);

		} catch (Exception e) {
			// #IGNORE
			e.printStackTrace();
		}
		return document.asXML();
	}

}