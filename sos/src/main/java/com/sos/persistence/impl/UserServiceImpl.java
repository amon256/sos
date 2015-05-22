/**
 * UserServiceImpl.java
 * 2015年5月19日
 */
package com.sos.persistence.impl;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.sos.entity.User;
import com.sos.persistence.UserService;

/**  
 * <b>功能：</b>UserServiceImpl.java<br/>
 * <b>描述：</b> 对功能点的描述<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
@Component
public class UserServiceImpl extends AbstractService<User> implements UserService {

	@Override
	public List<User> queryByEmail(String email) {
		return queryBySingleField("email", email);
	}
	
	@Override
	public List<User> queryByMobile(String mobile) {
		return queryBySingleField("mobile", mobile);
	}
	
	@Override
	public List<User> queryByQQ(String qq) {
		return queryBySingleField("qq", qq);
	}
	
	private List<User> queryBySingleField(String fieldName,Object fieldValue){
		return getMongoTemplate().find(Query.query(Criteria.where(fieldName).is(fieldValue)), User.class);
	}

}
