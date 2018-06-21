package com.mattdamon.core.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.ToString;

/**
 * 
 * BaseDto
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date Apr 15, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 *
 */
@ToString
@XmlRootElement
public abstract class BaseDto<T extends BaseModel> extends BaseObject<T>
		implements BasePagination {

	private static final long serialVersionUID = 7996545740737977461L;

	/**
	 * model 数据对象
	 */
	private List<T> modelData;

	/**
	 * 其他数据
	 */
	private HashMap<String, Object> flexibleData;

	public List<T> getModelData() {
		if (modelData == null) {
			modelData = new ArrayList<T>();
		}
		return modelData;
	}

	public void setModelData(List<T> modelData) {
		this.modelData = modelData;
	}

	public void addModelData(T model) {
		if (model == null) {
			return;
		}
		this.getModelData().add(model);
	}

	public void clearModelData() {
		this.getModelData().clear();
	}

	public HashMap<String, Object> getFlexibleData() {
		if (flexibleData == null) {
			flexibleData = new HashMap<String, Object>();
		}
		return flexibleData;
	}

	public void setFlexibleData(HashMap<String, Object> flexibleData) {
		this.flexibleData = flexibleData;
	}

	public void addFlexibleData(String key, Object data) {
		if (key == null || "".equals(key) || data == null) {
			return;
		}
		this.getFlexibleData().put(key, data);
	}

	public void clearFlexibleData() {
		this.getFlexibleData().clear();
	}

	// 数据总量
	private int total;
	// 页面容量
	private int pageSize;
	// 页码
	private int pageNO;

	/**
	 * 获取数据总量
	 * 
	 * @return
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * 获取页面容量
	 * 
	 * @return
	 */
	public int getPageSize() {
		if (pageSize < RECORD_PAGE_SIZE_MIN) {
			return RECORD_PAGE_SIZE_MIN;
		} else if (pageSize > RECORD_PAGE_SIZE_MAX) {
			return RECORD_PAGE_SIZE_MAX;
		}
		return pageSize;
	}

	/**
	 * 获取页码
	 * 
	 * @return
	 */
	public int getPageNO() {
		if (pageNO < RECORD_PAGE_NO_MIN) {
			return RECORD_PAGE_NO_MIN;
		} else if (pageNO > this.getPageCount()) {
			return this.getPageCount();
		}
		return pageNO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mattdamon.core.base.BasePagination#setTotal(int)
	 */
	@Override
	public void setTotal(int total) {
		this.total = total;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mattdamon.core.base.BasePagination#setPageSize(int)
	 */
	@Override
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mattdamon.core.base.BasePagination#setPageNO(int)
	 */
	@Override
	public void setPageNO(int pageNO) {
		this.pageNO = pageNO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mattdamon.core.base.BasePagination#getStart()
	 */
	@Override
	public int getStart() {
		return (this.getPageNO() - 1) * this.getPageSize();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mattdamon.core.base.BasePagination#getEnd()
	 */
	@Override
	public int getEnd() {
		int end = ((this.getPageNO() * this.getPageSize()) - 1);
		if (end < this.getTotal()) {
			return end;
		} else {
			return this.getTotal() - 1;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mattdamon.core.base.BasePagination#getPageCount()
	 */
	@Override
	public int getPageCount() {
		int remainder = (this.getTotal() % this.getPageSize());
		if (remainder == 0) {
			return (this.getTotal() / this.getPageSize());
		} else {
			return ((this.getTotal() / this.getPageSize()) + 1);
		}
	}
}
