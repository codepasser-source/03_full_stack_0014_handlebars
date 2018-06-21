package com.mattdamon.core.cache;

import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.JedisCluster;

/**
 * 
 * 
 * JedisClusterAdpter
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date May 15, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 *
 */
public abstract class JedisClusterAdpter {

	@Autowired
	protected JedisCluster jedisCluster;

}
