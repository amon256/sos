/**
 * MongoDBFactoryBean.java
 * 2015年5月19日
 */
package com.sos.util;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import com.mongodb.Mongo;
import com.mongodb.MongoOptions;
import com.mongodb.ServerAddress;

/**  
 * <b>功能：</b>MongoDBFactoryBean.java<br/>
 * <b>描述：</b> 对功能点的描述<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
public class MongoDBFactoryBean implements FactoryBean<Mongo>,InitializingBean, DisposableBean {
	
	private Mongo mongo;
	
	/**
	 * mongo地址
	 */
	private String host;
	
	/**
	 * mongo端口
	 */
	private int port = 27017;
	
	/**
	 * 每个host连接数
	 */
	private int connectionsPerHost = 5;
	/**
	 * 线程队列数(connectionsPerHost * threadsAllowedToBlockForConnectionMultiplier 即为线程总数，如果超出会抛异常)
	 */
	private int threadsAllowedToBlockForConnectionMultiplier = 5;
	/**
	 * 是否自动重连
	 */
	private boolean autoConnectRetry = true;
	/**
	 * 最大重连次数
	 */
	private int maxAutoConnectRetryTime = 0;
	
	/**
	 * 每次请求是否等待写到文件再返回 
	 */
	private boolean fsync = false;
	/**
	 * 最大等待时间毫秒
	 */
	private int maxWaitTime = 120000;
	
	/**
	 * 连接超时毫秒
	 */
	private int connectTimeout=10000;
	
	@Override
	public void destroy() throws Exception {
		mongo.close();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		ServerAddress serverAddress = new ServerAddress(host, port);
		MongoOptions mongoOptions = new MongoOptions();
		mongoOptions.autoConnectRetry = this.autoConnectRetry;
		mongoOptions.connectionsPerHost = this.connectionsPerHost;
		mongoOptions.connectTimeout = this.connectTimeout;
		mongoOptions.fsync = this.fsync;
		mongoOptions.maxAutoConnectRetryTime = this.maxAutoConnectRetryTime;
		mongoOptions.maxWaitTime = this.maxWaitTime;
		mongoOptions.threadsAllowedToBlockForConnectionMultiplier = this.threadsAllowedToBlockForConnectionMultiplier;
		this.mongo = new Mongo(serverAddress, mongoOptions);
	}

	@Override
	public Mongo getObject() throws Exception {
		return mongo;
	}

	@Override
	public Class<?> getObjectType() {
		return Mongo.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public Mongo getMongo() {
		return mongo;
	}

	public void setMongo(Mongo mongo) {
		this.mongo = mongo;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getConnectionsPerHost() {
		return connectionsPerHost;
	}

	public void setConnectionsPerHost(int connectionsPerHost) {
		this.connectionsPerHost = connectionsPerHost;
	}

	public int getThreadsAllowedToBlockForConnectionMultiplier() {
		return threadsAllowedToBlockForConnectionMultiplier;
	}

	public void setThreadsAllowedToBlockForConnectionMultiplier(
			int threadsAllowedToBlockForConnectionMultiplier) {
		this.threadsAllowedToBlockForConnectionMultiplier = threadsAllowedToBlockForConnectionMultiplier;
	}

	public boolean isAutoConnectRetry() {
		return autoConnectRetry;
	}

	public void setAutoConnectRetry(boolean autoConnectRetry) {
		this.autoConnectRetry = autoConnectRetry;
	}

	public int getMaxAutoConnectRetryTime() {
		return maxAutoConnectRetryTime;
	}

	public void setMaxAutoConnectRetryTime(int maxAutoConnectRetryTime) {
		this.maxAutoConnectRetryTime = maxAutoConnectRetryTime;
	}

	public boolean isFsync() {
		return fsync;
	}

	public void setFsync(boolean fsync) {
		this.fsync = fsync;
	}

	public int getMaxWaitTime() {
		return maxWaitTime;
	}

	public void setMaxWaitTime(int maxWaitTime) {
		this.maxWaitTime = maxWaitTime;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}
}
