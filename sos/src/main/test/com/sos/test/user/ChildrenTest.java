package com.sos.test.user;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.sos.entity.User;
import com.sos.persistence.UserService;
import com.sos.test.TestBase;

public class ChildrenTest extends TestBase {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Test
	public void test(){
		User user = userService.findById("bda381e8f3f1436c9d44cbbd2a4f90a5");
		
		DemoMain d = new DemoMain();
		d.setUser(user);
		
		List<Children> children = new LinkedList<Children>();
		Children child = new Children();
		child.setDesc("备注名称");
		
		user = userService.findById("e804177ed826485bb7dfba2b535713a9");
		child.setUser(user);
		
		children.add(child);
		
		d.setChildren(children);
		
		mongoTemplate.insert(d);
	}

}
