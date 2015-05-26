/**
 * UserServiceTest.java
 * 2015年5月19日
 */
package com.sos.test.user;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sos.entity.User;
import com.sos.enums.SexEnum;
import com.sos.persistence.UserService;
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
		SimpleDateFormat sdf  = new SimpleDateFormat("yyyyMMdd");
		DecimalFormat df = new DecimalFormat("000");
		List<User> users = new LinkedList<User>();
		long start = System.currentTimeMillis();
		for(int i = 0 ; i < 1000; i++){
			User entity = new User();
			entity.setBirthday(sdf.parse("19831024"));
			entity.setEmail("amon256@126.com");
			entity.setMobile("15019491" + df.format(i));
			entity.setNickName("风梦月");
			entity.setQq("81443194");
			entity.setSex(SexEnum.MAN);
			users.add(entity);
		}
		userService.add(users);
		System.out.println("耗时:" + (System.currentTimeMillis() - start) + "ms");
	}
	
	@Test
	public void testQuery(){
		List<User> users = userService.findAll();
		for(User u : users){
			System.out.println(u.getId() + "," + u.getNickName() + "," + u.getMobile());
		}
	}
}
