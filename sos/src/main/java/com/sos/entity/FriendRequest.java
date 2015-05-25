/**
 * FriendRequest.java
 * 2015年5月22日
 */
package com.sos.entity;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.sos.enums.DecisionResultEnum;

/**  
 * <b>功能：</b>FriendRequest.java<br/>
 * <b>描述：</b> 好友请求<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
@Document(collection="friendrequest")
public class FriendRequest extends CoreEntity {

	private static final long serialVersionUID = -7186971761069790944L;

	/**
	 * 请求人ID
	 */
	@DBRef
	private User from;
	
	/**
	 * 被请求人ID
	 */
	@DBRef
	private User to;
	
	
	/**
	 * 结果
	 */
	private DecisionResultEnum decisionResult;


	public User getFrom() {
		return from;
	}


	public void setFrom(User from) {
		this.from = from;
	}


	public User getTo() {
		return to;
	}


	public void setTo(User to) {
		this.to = to;
	}


	public DecisionResultEnum getDecisionResult() {
		return decisionResult;
	}


	public void setDecisionResult(DecisionResultEnum decisionResult) {
		this.decisionResult = decisionResult;
	}
	
}
