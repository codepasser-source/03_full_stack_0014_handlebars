package com.mattdamon.core.properties;

import com.mattdamon.core.properties.annotation.Properties;
import com.mattdamon.core.properties.annotation.Property;

import lombok.ToString;

/**
 * 
 * CoreProperties
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date Apr 15, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 *
 */
@ToString
@Properties(dir = "config", value = "core.properties")
public class CoreProperties {

	// 环境
	private String envrionment;
	// 一级域名
	private String domain;
	// session 模式(local|cluster)
	private String sessionMode;
	// session key
	private String sessionKey;
	// 系统文件目录
	private String fileRoot;
	// 记录limit
	private int recordLimit;

	public String getEnvrionment() {
		return envrionment;
	}

	@Property(key = "system.envrionment", defaultValue = "")
	public void setEnvrionment(String envrionment) {
		this.envrionment = envrionment;
	}

	public String getDomain() {
		return domain;
	}

	@Property(key = "core.base.domain", defaultValue = "")
	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getSessionMode() {
		return sessionMode;
	}

	@Property(key = "core.session.mode", defaultValue = "")
	public void setSessionMode(String sessionMode) {
		this.sessionMode = sessionMode;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	@Property(key = "core.session.key", defaultValue = "")
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public String getFileRoot() {
		return fileRoot;
	}

	@Property(key = "core.file.root", defaultValue = "")
	public void setFileRoot(String fileRoot) {
		this.fileRoot = fileRoot;
	}

	public int getRecordLimit() {
		return recordLimit;
	}

	@Property(key = "core.record.limit", defaultValue = "10000")
	public void setRecordLimit(int recordLimit) {
		this.recordLimit = recordLimit;
	}

}
