/**
 * UserServiceTest.java
 * 2015年5月19日
 */
package com.sos.test.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sos.entity.User;
import com.sos.enums.SexEnum;
import com.sos.persistence.UserService;
import com.sos.persistence.impl.UserServiceImpl;
import com.sos.test.TestBase;

/**  
 * <b>功能：</b>UserServiceTest.java<br/>
 * <b>描述：</b> 对功能点的描述<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
public class UserServiceTest extends TestBase {

	@Autowired
	private UserService userService;
	
	@Test
	public void testAdd() throws ParseException{
		User entity = new User();
		SimpleDateFormat sdf  = new SimpleDateFormat("yyyyMMdd");
		entity.setBirthday(sdf.parse("19831024"));
		entity.setCreateTime(new Date());
		entity.setEmail("amon256@126.com");
		entity.setLastUpdateTime(new Date());
		entity.setMobile("15019491687");
		entity.setNickName("风梦月");
		entity.setQq("81443194");
		entity.setSex(SexEnum.MAN);
		userService.add(entity);
	}
	
	@Test
	public void testQuery(){
		List<User> users = userService.queryByMobile("211");
		for(User u : users){
			System.out.println(u.getId() + "," + u.getNickName());
		}
	}
	
	@Test
	public void testDrop(){
		UserServiceImpl impl = (UserServiceImpl)userService;
		impl.getMongo().getDB("sos").getCollection("users").drop();
	}
}
