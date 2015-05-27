/**
 * MessageServiceImpl.java
 * 2015年5月22日
 */
package com.sos.persistence.impl;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sos.entity.Message;
import com.sos.enums.DecisionResultEnum;
import com.sos.enums.MessageStatusEnum;
import com.sos.facade.MessageCallbackHandler;
import com.sos.persistence.MessageService;
import com.sos.spring.WebApplicationContextAware;
import com.sos.util.CollectionUtils;

/**  
 * <b>功能：</b>MessageServiceImpl.java<br/>
 * <b>描述：</b> 对功能点的描述<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
@Component
public class MessageServiceImpl extends AbstractService<Message> implements MessageService {
	private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

	@Override
	public void apply(Message message, DecisionResultEnum decision) {
		message.setStatus(MessageStatusEnum.HASDEAL);
		Set<String> updateFields = CollectionUtils.createSet(String.class, "status");
		update(message, updateFields);
		
		if(message.getRefId() != null){
			logger.debug("消息处理回调,type:{},refId:{},decision:{}",message.getType(),message.getRefId(),decision);
			String[] beanNames = WebApplicationContextAware.getApplicationContext().getBeanNamesForType(MessageCallbackHandler.class, true, true);
			if(beanNames != null){
				for(String beanName : beanNames){
					logger.debug("找到bean:[{}]对消息处理",beanName);
					WebApplicationContextAware.getApplicationContext().getBean(beanName, MessageCallbackHandler.class).callback(message.getRefId(), decision);
				}
			}
		}
	}
}
