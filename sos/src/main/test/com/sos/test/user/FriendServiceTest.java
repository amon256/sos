/**
 * FriendServiceTest.java
 * 2015年5月22日
 */
package com.sos.test.user;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.sos.entity.FriendRelation;
import com.sos.entity.User;
import com.sos.persistence.FriendRelationService;
import com.sos.persistence.UserService;
import com.sos.test.TestBase;

/**  
 * <b>功能：</b>FriendServiceTest.java<br/>
 * <b>描述：</b> 对功能点的描述<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
public class FriendServiceTest extends TestBase {

	@Autowired
	UserService userService;
	
	@Autowired
	FriendRelationService friendService;
	
	@Test
	public void makeFriend(){
		User self = userService.findById("6609b0adeea44ed19ca9d9c59ac399e0");
		System.out.println(self.getMobile());
		for(int i = 0 ; i < 10; i++){
			User other = userService.queryByMobile("1501949140" + i).get(0);
			if(other != null){
				friendService.makeFriend(self, other);
			}
		}
	}
	
	@Test
	public void queryAll(){
		long start = System.currentTimeMillis();
		List<FriendRelation> friends = friendService.findAll();
		System.out.println("耗时:" + (System.currentTimeMillis() - start) + "ms");
		for(FriendRelation f : friends){
			System.out.println(f.getSelf().getMobile() + ":" + f.getFriends().size());
		}
	}
	
	@Test
	public void query(){
		List<FriendRelation> friends = friendService.findList(Query.query(Criteria.where("self.$id").is("6609b0adeea44ed19ca9d9c59ac399e0")));
		for(FriendRelation f : friends){
			System.out.println(f.getSelf().getMobile() + ":" + f.getFriends().size());
		}
	}
	
	@Test
	public void update(){
		Query query = Query.query(Criteria.where("self.$id").is("6609b0adeea44ed19ca9d9c59ac399e0")
				.and("friends.user").is("ef5e8e3a3a9046f4be46e2a1b2dc79df"));
		Update update = new Update();
		update.set("friends.$.emergencyContact", true);
		friendService.update(query, update);
		
	}
}
