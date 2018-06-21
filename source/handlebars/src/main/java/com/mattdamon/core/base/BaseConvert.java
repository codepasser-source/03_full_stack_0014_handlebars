package com.mattdamon.core.base;

import java.io.Serializable;

/**
 * 
 * BaseConvert
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date Apr 15, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 *
 */
public interface BaseConvert extends Serializable {

	/**
	 * 转换Json
	 * 
	 * @return json string
	 */
	String convertToJson();

	/**
	 * 转换XML
	 * 
	 * @return xml
	 */
	String convertToXml();
}
