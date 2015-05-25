/**
 * FriendServiceImpl.java
 * 2015年5月22日
 */
package com.sos.persistence.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sos.entity.Friend;
import com.sos.entity.User;
import com.sos.persistence.FriendService;

/**  
 * <b>功能：</b>FriendServiceImpl.java<br/>
 * <b>描述：</b> 对功能点的描述<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
@Component
public class FriendServiceImpl extends AbstractService<Friend> implements
		FriendService {

	@Override
	public void makeFriend(User self, User other) {
		Friend friend = new Friend();
		List<Friend> friends = new LinkedList<Friend>();
		//add self - other
		friend.setSelf(self);
		friend.setOther(other);
		friends.add(friend);
		//add other - self
		friend = new Friend();
		friend.setOther(self);
		friend.setSelf(other);
		friends.add(friend);
		add(friends);
	}

}
