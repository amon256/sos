/**
 * UserServiceImpl.java
 * 2015年5月19日
 */
package com.sos.persistence.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.sos.entity.User;
import com.sos.persistence.UserService;
import com.sos.util.MongoUtils;

/**  
 * <b>功能：</b>UserServiceImpl.java<br/>
 * <b>描述：</b> 对功能点的描述<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
@Component
public class UserServiceImpl extends AbstractService<User> implements UserService {

	@Override
	protected String getCollectionName() {
		return "users";
	}

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
		DBObject ref = new BasicDBObject();
		ref.put(fieldName, fieldValue);
		DBCursor cursor = getDBCollection().find(ref);
		return MongoUtils.dbCursor2List(User.class, cursor);
	}
}
