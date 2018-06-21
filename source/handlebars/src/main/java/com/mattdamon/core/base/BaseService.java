package com.mattdamon.core.base;

import com.mattdamon.core.common.GlobalVariables;
import com.mattdamon.core.exception.GeneralException;
import com.mattdamon.core.interaction.Response;
import com.mattdamon.core.interaction.ResponseDescription;
import com.mattdamon.core.interaction.ResponseService;

/**
 * BaseService
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date Apr 16, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 *
 */
public abstract class BaseService implements ResponseService<BaseData>,
		GlobalVariables {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mattdamon.core.service.ResponseServer#produce(com.neusoft
	 * .ecommerce.core.interaction.ResponseDescription)
	 */
	@Override
	public Response<BaseData> produce(ResponseDescription responseDescription)
			throws GeneralException {
		return new Response<BaseData>(responseDescription);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mattdamon.core.service.ResponseServer#produce(com.neusoft
	 * .ecommerce.core.interaction.ResponseDescription, T)
	 */
	@Override
	public Response<BaseData> produce(ResponseDescription responseDescription,
			BaseData responseData) throws GeneralException {
		return new Response<BaseData>(responseDescription, responseData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mattdamon.core.service.ResponseServer#produce(com.neusoft
	 * .ecommerce.core.interaction.ResponseDescription, java.lang.Object)
	 */
	@Override
	public Response<BaseData> produce(ResponseDescription responseDescription,
			Object... args) throws GeneralException {
		return new Response<BaseData>(responseDescription, args);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mattdamon.core.service.ResponseServer#produce(com.neusoft
	 * .ecommerce.core.interaction.ResponseDescription, T, java.lang.Object)
	 */
	@Override
	public Response<BaseData> produce(ResponseDescription responseDescription,
			BaseData responseData, Object... args) throws GeneralException {
		return new Response<BaseData>(responseDescription, responseData, args);
	}
}
