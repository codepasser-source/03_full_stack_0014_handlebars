package com.mattdamon.core.base;

import java.io.IOException;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import lombok.ToString;

import com.alibaba.fastjson.JSONObject;
import com.mattdamon.core.common.GlobalVariables;

/**
 * 
 * BaseObject
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date Apr 15, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 *
 */
@ToString
public abstract class BaseObject<T extends BaseData> implements BaseConvert,
		BaseData, GlobalVariables {

	private static final long serialVersionUID = -3819761883876803955L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mattdamon.core.base.BaseConvert#convertToJson()
	 */
	@Override
	public String convertToJson() {
		return JSONObject.toJSONString(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mattdamon.core.base.BaseConvert#convertToXml()
	 */
	@Override
	public String convertToXml() {

		StringWriter writer = new StringWriter();
		String xml = null;
		try {
			JAXBContext context = JAXBContext.newInstance(this.getClass());
			Marshaller mar = context.createMarshaller();
			mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			mar.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			mar.marshal(this, writer);
			xml = writer.toString();
		} catch (JAXBException e) {
			// #IGNORE
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.flush();
				try {
					writer.close();
				} catch (IOException e) {
					// #IGNORE
				}
			}
		}
		return xml;
	}

}
