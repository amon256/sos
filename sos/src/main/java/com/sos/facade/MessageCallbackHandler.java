/**
 * MessageCallbackHandler.java
 * 2015年5月26日
 */
package com.sos.facade;

import com.sos.enums.DecisionResultEnum;
import com.sos.enums.MessageTypeEnum;

/**  
 * <b>功能：</b>MessageCallbackHandler.java<br/>
 * <b>描述：</b> 消息处理回调<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
public interface MessageCallbackHandler {
	/**
	 * 回调接口
	 */
	public void callback(String refId,DecisionResultEnum decision);
	
	/**
	 * 获取消息类型
	 */
	public MessageTypeEnum getMessageType();
}
