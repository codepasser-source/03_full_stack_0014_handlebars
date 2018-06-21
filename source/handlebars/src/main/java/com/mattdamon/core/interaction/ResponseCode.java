package com.mattdamon.core.interaction;

/**
 * 
 * ResponseCode
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date Apr 15, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 *
 */
public interface ResponseCode {

	/********************** SYSTEM MESSAGE ***************************/
	/**
	 * 系统消息-操作成功。
	 */
	int RESPONSE_SUCCESS = 00000000;
	/**
	 * 系统消息-操作失败。
	 */
	int RESPONSE_FAILED = 00000001;

}