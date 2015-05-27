/**
 * FriendRequestServiceImpl.java
 * 2015年5月22日
 */
package com.sos.persistence.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sos.entity.FriendRequest;
import com.sos.entity.Message;
import com.sos.enums.MessageTypeEnum;
import com.sos.persistence.FriendRelationService;
import com.sos.persistence.FriendRequestService;
import com.sos.persistence.MessageService;

/**  
 * <b>功能：</b>FriendRequestServiceImpl.java<br/>
 * <b>描述：</b> 好友申请实现<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
@Component
public class FriendRequestServiceImpl extends AbstractService<FriendRequest> implements FriendRequestService {

	@Autowired
	private MessageService messageService;
	
	@Autowired
	private FriendRelationService friendRelationService;
	
	@Override
	public FriendRequest add(FriendRequest entity) {
		entity = super.add(entity);
		//发送消息
		Message mess = new Message();
		mess.setTitle("收到来自["+entity.getFrom().getNickName()+"]的好友申请");
		mess.setContent(entity.getFrom().getNickName() + "(" + entity.getFrom().getMobile() + ")请求加你为好友");
		mess.setRefId(entity.getId());
		mess.setType(MessageTypeEnum.FRIEND_REQ);
		mess.setReceiver(entity.getTo());
		messageService.add(mess);
		return entity;
	}
	
	@Override
	public void add(List<FriendRequest> entityList) {
		super.add(entityList);
		//发送消息
		List<Message> messes = new LinkedList<Message>();
		for(FriendRequest freq : entityList){
			Message mess = new Message();
			mess.setTitle("收到新的好友申请");
			mess.setContent(freq.getFrom().getNickName() + "请求加你为好友");
			mess.setRefId(freq.getId());
			mess.setType(MessageTypeEnum.FRIEND_REQ);
			mess.setReceiver(freq.getTo());
			messes.add(mess);
		}
		messageService.add(messes);
	}
}
