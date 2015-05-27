/**
 * UserTrailServiceImpl.java
 * 2015年5月27日
 */
package com.sos.persistence.impl;

import java.util.Date;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.sos.entity.User;
import com.sos.entity.UserTrail;
import com.sos.entity.vo.Point;
import com.sos.persistence.UserTrailService;
import com.sos.util.CollectionUtils;

/**  
 * <b>功能：</b>UserTrailServiceImpl.java<br/>
 * <b>描述：</b> 对功能点的描述<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
@Component
public class UserTrailServiceImpl extends AbstractService<UserTrail> implements UserTrailService {
	
	/**
	 * 每条UserTrail记录最多保存一万个描点
	 */
	private static final int MAX_POINT_PER_RECORD = 10000;

	@Override
	public void recordPoint(User user, Point point) {
		//用户活动记录，并且描点数量小于MAX_POINT_PER_RECORD
		Query query = Query.query(Criteria.where("user.$id").is(user.getId()).and("active").is(true).and("pointCount").lt(MAX_POINT_PER_RECORD));
		long existsSize = getMongoTemplate().count(query, UserTrail.class);
		if(existsSize == 0){
			//更新旧记录为非活动 
			Query updateQuery = Query.query(Criteria.where("user.$id").is(user.getId()).and("active").is(true));
			Update update = Update.update("active", false).set("lastUpdateTime", new Date());
			update(updateQuery, update);
			//新增记录
			UserTrail trail = new UserTrail();
			trail.setUser(user);
			trail.setActive(true);
			trail.setPointCount(1);;
			trail.setPoints(CollectionUtils.createList(Point.class, point));
			add(trail);
		}else{
			Update update = Update.update("lastUpdateTime", new Date())
					.inc("pointCount", 1)
					.push("points", point);
			update(query, update);
		}
	}

}
