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

import com.sos.entity.Friend;
import com.sos.entity.User;
import com.sos.persistence.FriendService;
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
	FriendService friendService;
	
	@Test
	public void makeFriend(){
		User self = userService.findById("bda381e8f3f1436c9d44cbbd2a4f90a5");
		System.out.println(self.getMobile());
		for(int i = 0 ; i < 10; i++){
			User other = userService.queryByMobile("1501949040" + i).get(0);
			if(other != null){
				friendService.makeFriend(self, other);
			}
		}
	}
	
	@Test
	public void queryAll(){
		List<Friend> friends = friendService.findAll();
		for(Friend f : friends){
			System.out.println(f.getOther().getMobile());
		}
	}
	
	@Test
	public void query(){
		List<Friend> friends = friendService.findList(Query.query(Criteria.where("self.$id").is("bda381e8f3f1436c9d44cbbd2a4f90a5")));
		for(Friend f : friends){
			System.out.println(f.getOther().getMobile());
		}
	}
}
