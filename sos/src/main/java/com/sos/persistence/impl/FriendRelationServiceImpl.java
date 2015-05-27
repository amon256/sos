/**
 * FriendServiceImpl.java
 * 2015年5月22日
 */
package com.sos.persistence.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.sos.entity.FriendRelation;
import com.sos.entity.User;
import com.sos.entity.vo.Friend;
import com.sos.persistence.FriendRelationService;

/**  
 * <b>功能：</b>FriendServiceImpl.java<br/>
 * <b>描述：</b> 对功能点的描述<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
@Component
public class FriendRelationServiceImpl extends AbstractService<FriendRelation> implements
		FriendRelationService {

	@Override
	public void makeFriend(User self, User other) {
		List<User> temp = new ArrayList<User>(1);
		temp.add(other);
		saveFriend(self, temp);
		temp.clear();
		temp.add(self);
		saveFriend(other, temp);
	}
	
	@Override
	public void makeFriend(User self, List<User> others) {
		saveFriend(self, others);
		List<User> temp = new ArrayList<User>(1);
		temp.add(self);
		for(User other : others){
			saveFriend(other, temp);
 		}
		
	}
	
	private void saveFriend(User self,List<User> others){
		Query query = Query.query(Criteria.where("self.$id").is(self.getId()));
		FriendRelation friendRelation = findOne(query);
		if(friendRelation == null){
			friendRelation = new FriendRelation();
			friendRelation.setSelf(self);
			add(friendRelation);
		}
		Update update = new Update();
		for(User other : others){
			Friend friend = new Friend();
			friend.setEmergencyContact(false);
			friend.setUser(other);
			update.push("friends", friend);
		}
		update(query, update);
	}

}
