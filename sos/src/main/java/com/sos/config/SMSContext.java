/**
 * SMSContext.java
 * 2015年5月27日
 */
package com.sos.config;

import org.springframework.data.annotation.Id;
import org.springframework.util.StringUtils;


/**  
 * <b>功能：</b>SMSContext.java<br/>
 * <b>描述：</b> 短信配置<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
public class SMSContext extends Context{
	
	@Id
	private String id  = "SMS";
	
	/**
	 * 每个号码每天最多发送短信条数
	 */
	private int maxTimePerMobilePerDay;
	
	/**
	 * 接口供应商
	 */
	private String provider;
	
	/**
	 * 接口url
	 */
	private String url;
	
	/**
	 * 接口用户名
	 */
	private String username;
	
	/**
	 * 接口密码
	 */
	private String password;
	
	/**
	 * 字符集
	 */
	private String charset;
	
	/**
	 * 提交方式，POST/GET
	 */
	private String method;
	
	/**
	 * 发送用户名key
	 */
	private String unameKey;
	
	/**
	 * 发送密码key
	 */
	private String pwdKey;
	
	/**
	 * 接收号码key
	 */
	private String receiverKey;
	
	/**
	 * 接收人分隔符
	 */
	private String receiverSplit;
	
	/**
	 * 短信内容key
	 */
	private String msgKey;
	
	@Override
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
	}
	
	public boolean validate(){
		if(StringUtils.hasLength(url) && StringUtils.hasLength(username) 
				&& StringUtils.hasLength(password) && StringUtils.hasLength(unameKey) 
				&& StringUtils.hasLength(pwdKey) && StringUtils.hasLength(receiverKey) 
				&& StringUtils.hasLength(msgKey)){
			return true;
		}
		return false;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getUnameKey() {
		return unameKey;
	}

	public void setUnameKey(String unameKey) {
		this.unameKey = unameKey;
	}

	public String getPwdKey() {
		return pwdKey;
	}

	public void setPwdKey(String pwdKey) {
		this.pwdKey = pwdKey;
	}

	public String getReceiverKey() {
		return receiverKey;
	}

	public void setReceiverKey(String receiverKey) {
		this.receiverKey = receiverKey;
	}

	public String getReceiverSplit() {
		return receiverSplit;
	}

	public void setReceiverSplit(String receiverSplit) {
		this.receiverSplit = receiverSplit;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getMsgKey() {
		return msgKey;
	}

	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public int getMaxTimePerMobilePerDay() {
		return maxTimePerMobilePerDay;
	}

	public void setMaxTimePerMobilePerDay(int maxTimePerMobilePerDay) {
		this.maxTimePerMobilePerDay = maxTimePerMobilePerDay;
	}

}
