package com.mattdamon.core.search;

import java.util.Map;
import java.util.Set;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.collect.ImmutableList;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import com.google.common.net.HostAndPort;
import com.mattdamon.core.exception.ErrorDescription;
import com.mattdamon.core.exception.GeneralException;

/**
 * 
 * 
 * SearchManager.java
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date May 11, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 *
 */
public class SearchClientFactory {

	// cluster name
	private static final String ES_SETTING_CLUSTER_NAME = "cluster.name";
	// 是否只作为客户端，即不存储索引数据，默认值为false
	private static final String ES_SETTING_NODE_CLIENT = "node.client";
	// 是否持有索引数据，默认值为true
	private static final String ES_SETTING_NODE_DATA = "node.data";
	// 是否为本地节点，本地节点是指在JVM级别中的同级 默认值true
	private static final String ES_SETTING_NODE_LOCAL = "node.local";

	// ES Client
	private static TransportClient client;

	private static Object lock = new Object();

	// ES client 设置
	private static Map<String, String> settings;

	// ES服务器节点
	private static Set<HostAndPort> nodes;

	public static Map<String, String> getSettings() {
		return settings;
	}

	public static void setSettings(Map<String, String> settings) {
		SearchClientFactory.settings = settings;
	}

	public static Set<HostAndPort> getNodes() {
		return nodes;
	}

	public static void setNodes(Set<HostAndPort> nodes) {
		SearchClientFactory.nodes = nodes;
	}

	/**
	 * @return client
	 */
	public static TransportClient getClient() throws GeneralException {

		if (client == null) {
			// 同步锁
			synchronized (lock) {
				if (settings == null || settings.isEmpty()) {
					settings.put(ES_SETTING_CLUSTER_NAME, "elasticsearch");
					settings.put(ES_SETTING_NODE_CLIENT, "false");
					settings.put(ES_SETTING_NODE_DATA, "true");
					settings.put(ES_SETTING_NODE_LOCAL, "true");
				}
				Settings setting = ImmutableSettings.settingsBuilder()
						.put(settings).build();
				client = new TransportClient(setting);
				if (nodes == null || nodes.isEmpty()) {
					throw new GeneralException(
							ErrorDescription.ERROR_SEARCH_SERVER_NODE_EMPTY_0);
				}
				// 添加节点
				for (HostAndPort hostAndPort : nodes) {
					client.addTransportAddress(new InetSocketTransportAddress(
							hostAndPort.getHostText(), hostAndPort.getPort()));
				}
			}
		}
		return client;
	}

	public static void closeClient() throws GeneralException {
		if (client != null) {
			synchronized (lock) {
				client.close();
				client = null;
			}
		}
	}

	public static int checkNode() throws GeneralException {
		if (client != null) {
			synchronized (lock) {
				ImmutableList<DiscoveryNode> nodes = client.connectedNodes();
				return nodes.size();
			}
		} else {
			return 0;
		}
	}

}