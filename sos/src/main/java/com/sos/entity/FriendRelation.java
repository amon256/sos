/**
 * Friend.java
 * 2015年5月22日
 */
package com.sos.entity;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.sos.entity.vo.Friend;


/**  
 * <b>功能：</b>Friend.java<br/>
 * <b>描述：</b> 好友关系<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
@Document(collection="friendrelation")
public class FriendRelation extends CoreEntity{
	private static final long serialVersionUID = 8081420335749844527L;

	/**
	 * 自己
	 */
	@DBRef
	@JsonIgnore
	@Indexed(unique=true)
	private User self;
	
	/**
	 * 好友列表
	 */
	private List<Friend> friends;

	public User getSelf() {
		return self;
	}

	public void setSelf(User self) {
		this.self = self;
	}

	public List<Friend> getFriends() {
		return friends;
	}

	public void setFriends(List<Friend> friends) {
		this.friends = friends;
	}
}
