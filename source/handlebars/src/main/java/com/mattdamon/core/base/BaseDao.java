package com.mattdamon.core.base;

import java.util.List;

import com.mattdamon.core.common.GlobalVariables;
import com.mattdamon.core.db.annotation.DataSource;

/**
 * 
 * BaseDao.java
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date May 5, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 *
 */
public interface BaseDao<T extends BaseModel, D extends BaseDto<T>> extends
		GlobalVariables {

	/**
	 * 单表插入记录
	 * 
	 * @param base
	 * @return
	 */
	@DataSource("master")
	int insert(T base);

	/**
	 * 单表更新记录
	 * 
	 * @param base
	 * @return
	 */
	@DataSource("master")
	int update(T base);

	/**
	 * 单表删除记录
	 * 
	 * @param base
	 * @return
	 */
	@DataSource("master")
	int delete(T base);

	/**
	 * 单表查询单一记录
	 * 
	 * @param base
	 * @return
	 */
	@DataSource("slave")
	T selectOne(T base);

	/**
	 * 单表查询记录
	 * 
	 * @param base
	 * @return
	 */
	@DataSource("slave")
	List<T> selectList(D base);

}
