package com.mattdamon.core.interaction;

import com.mattdamon.core.base.BaseData;
import com.mattdamon.core.exception.GeneralException;

/**
 * ResponseService
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date Apr 16, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 *
 */
public interface ResponseService<T extends BaseData> {

	/**
	 * 产出数据
	 * 
	 * @param responseDescription
	 * @return
	 * @throws GeneralException
	 */
	Response<T> produce(ResponseDescription responseDescription)
			throws GeneralException;

	/**
	 * 产出数据
	 * 
	 * @param responseDescription
	 * @param responseData
	 * @return
	 * @throws GeneralException
	 */
	Response<T> produce(ResponseDescription responseDescription, T responseData)
			throws GeneralException;

	/**
	 * 产出数据
	 * 
	 * @param responseDescription
	 * @param args
	 * @return
	 * @throws GeneralException
	 */
	Response<T> produce(ResponseDescription responseDescription, Object... args)
			throws GeneralException;

	/**
	 * 产出数据
	 * 
	 * @param responseDescription
	 * @param responseData
	 * @param args
	 * @return
	 * @throws GeneralException
	 */
	Response<T> produce(ResponseDescription responseDescription,
			T responseData, Object... args) throws GeneralException;

}