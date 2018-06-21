package com.mattdamon.core.base;

/**
 * 
 * BasePagination
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date Apr 15, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 *
 */
public interface BasePagination {

	/**
	 * 设置数据总量
	 * 
	 * @param total
	 */
	void setTotal(int total);

	/**
	 * 设置页面容量
	 * 
	 * @param pageSize
	 */
	void setPageSize(int pageSize);

	/**
	 * 设置页码
	 * 
	 * @param pageNO
	 */
	void setPageNO(int pageNO);

	/**
	 * 获取数据起始
	 * 
	 * @return start
	 */
	int getStart();

	/**
	 * 获取数据结束
	 * 
	 * @return end
	 */
	int getEnd();

	/**
	 * 获取页面总数
	 * 
	 * @return page count
	 */
	int getPageCount();

}
