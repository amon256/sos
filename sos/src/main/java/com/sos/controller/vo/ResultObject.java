/**
 * ResultObject.java
 * 2015年5月20日
 */
package com.sos.controller.vo;

import java.io.Serializable;

/**  
 * <b>功能：</b>ResultObject.java<br/>
 * <b>描述：</b> 请求结果<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
public class ResultObject implements Serializable {
	private static final long serialVersionUID = 1849641942389653778L;

	/**
	 * 请求结果状态,true为成功，false为失败
	 */
	private boolean status = true;
	
	/**
	 * 请求应答消息
	 */
	private String msg  = "请求执行成功";
	
	/**
	 * 请求应答数据主体
	 */
	private Object data;

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
