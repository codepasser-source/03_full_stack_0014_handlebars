package com.mattdamon.core.cache;

import com.alibaba.fastjson.JSONObject;
import com.mattdamon.core.base.BaseData;
import com.mattdamon.core.exception.GeneralException;

/**
 * 
 * BaseRedisDao
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date May 15, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 *
 */
public class BaseRedisDao<T extends BaseData> extends JedisClusterAdpter {

	/**
	 * 新增
	 * 
	 * @param key
	 * @param data
	 * @return
	 * @throws GeneralException
	 */
	public String add(String key, String data) throws GeneralException {
		return jedisCluster.set(key, data);
	}

	/**
	 * 新增
	 * 
	 * @param key
	 * @param data
	 * @return
	 * @throws GeneralException
	 */
	public String addObject(String key, T data) throws GeneralException {
		return jedisCluster.set(key, JSONObject.toJSONString(data));
	}

	/**
	 * 修改
	 * 
	 * @param key
	 * @param data
	 * @return
	 * @throws GeneralException
	 */
	public boolean update(String key, String data) throws GeneralException {
		jedisCluster.set(key, data);
		return false;
	}

	/**
	 * 修改
	 * 
	 * @param key
	 * @param data
	 * @return
	 * @throws GeneralException
	 */
	public boolean updateObject(String key, T data) throws GeneralException {
		jedisCluster.set(key, JSONObject.toJSONString(data));
		return false;
	}

	/**
	 * 获取
	 * 
	 * @param key
	 * @return
	 * @throws GeneralException
	 */
	public String get(String key) throws GeneralException {
		String data = jedisCluster.get(key);
		return data;
	}

	/**
	 * 获取
	 * 
	 * @param key
	 * @return
	 * @throws GeneralException
	 */
	public <R> R getObject(String key, Class<R> classze)
			throws GeneralException {
		String dataJson = jedisCluster.get(key);
		if (dataJson != null) {
			R data = (R) JSONObject.parseObject(dataJson, classze);
			return data;
		}
		return null;
	}

	/**
	 * 是否存在
	 * 
	 * @param key
	 * @return
	 */
	public Boolean isExist(String key) {
		return jedisCluster.exists(key);
	}

	/**
	 * 删除
	 * 
	 * @param key
	 * @return
	 * @throws GeneralException
	 */
	public Long delete(String key) throws GeneralException {
		return jedisCluster.del(key);
	}
}
