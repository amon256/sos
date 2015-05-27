/**
 * MessageService.java
 * 2015年5月22日
 */
package com.sos.persistence;

import com.sos.entity.Message;
import com.sos.enums.DecisionResultEnum;

/**  
 * <b>功能：</b>MessageService.java<br/>
 * <b>描述：</b> 对功能点的描述<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
public interface MessageService extends CoreService<Message> {
	
	/**
	 * 处理消息
	 */
	public void apply(Message message,DecisionResultEnum decision);
}
