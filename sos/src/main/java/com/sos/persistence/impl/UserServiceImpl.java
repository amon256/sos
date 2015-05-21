/**
 * UserServiceImpl.java
 * 2015年5月19日
 */
package com.sos.persistence.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.sos.entity.User;
import com.sos.persistence.UserService;
import com.sos.util.DateUtils;
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
	public void update(User user) {
		//只更新qq，邮箱，昵称
		DBObject query = new BasicDBObject("_id",user.getId());
		DBObject update = new BasicDBObject()
			.append("qq", user.getQq())
			.append("email", user.getEmail())
			.append("nickName", user.getNickName())
			.append("sex", user.getSex().name())
			.append("birthday", DateUtils.format(user.getBirthday(), DateUtils.DATE_ONLY_PATTERN))
			.append("lastUpdateTime", DateUtils.format(new Date(), DateUtils.SIMPLE_PATTERN));
		getDBCollection().update(query, new BasicDBObject("$set",update),true,true);
	}
	
	@Override
	public User add(User entity) {
		entity.setId(UUID.randomUUID().toString().toLowerCase().replaceAll("-", ""));
		return super.add(entity);
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
