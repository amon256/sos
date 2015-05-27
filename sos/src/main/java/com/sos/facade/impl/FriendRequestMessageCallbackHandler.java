/**
 * FriendRequestMessageCallbackHandler.java
 * 2015年5月27日
 */
package com.sos.facade.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.sos.entity.FriendRelation;
import com.sos.entity.FriendRequest;
import com.sos.entity.Message;
import com.sos.enums.DecisionResultEnum;
import com.sos.enums.MessageTypeEnum;
import com.sos.facade.MessageCallbackHandler;
import com.sos.persistence.FriendRelationService;
import com.sos.persistence.FriendRequestService;
import com.sos.persistence.MessageService;
import com.sos.util.CollectionUtils;

/**  
 * <b>功能：</b>FriendRequestMessageCallbackHandler.java<br/>
 * <b>描述：</b> 好友请求处理<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
@Component(value="friendRequestMessageCallbackHandler")
public class FriendRequestMessageCallbackHandler implements MessageCallbackHandler {

	@Autowired
	private FriendRequestService friendRequestService;
	
	@Autowired
	private FriendRelationService friendRelationService;
	
	@Autowired
	private MessageService messageService;
	
	@Override
	public void callback(String refId, DecisionResultEnum decision) {
		FriendRequest freq = friendRequestService.findById(refId);
		if(freq != null){
			freq.setDecisionResult(decision);
			Set<String> updateFields = CollectionUtils.createSet(String.class, "decisionResult");
			friendRequestService.update(freq, updateFields );
			Query query = Query.query(Criteria.where("self.$id").is(freq.getFrom().getId()).and("friends.id").is(freq.getTo().getId()));
			List<FriendRelation> freqs = friendRelationService.findList(query);
			if(freqs == null || freqs.isEmpty()){
				friendRelationService.makeFriend(freq.getFrom(), freq.getTo());
				Message mess = new Message();
				mess.setTitle(freq.getTo().getNickName() + "己" + (DecisionResultEnum.AGREE.equals(decision)?"同意":"拒绝") + "你的好友申请");
				mess.setContent(mess.getTitle());
				mess.setType(MessageTypeEnum.FRIEND_ACK);
				mess.setReceiver(freq.getFrom());
				messageService.add(mess);
			}
		}
	}

	@Override
	public MessageTypeEnum getMessageType() {
		return MessageTypeEnum.FRIEND_REQ;
	}

}
