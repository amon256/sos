/**
 * ContextServiceImpl.java
 * 2015年5月27日
 */
package com.sos.persistence.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.sos.config.Context;
import com.sos.persistence.ContextService;

/**  
 * <b>功能：</b>ContextServiceImpl.java<br/>
 * <b>描述：</b> 对功能点的描述<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
@Component
public class ContextServiceImpl implements ContextService {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public <T extends Context> void saveContext(T context) {
		mongoTemplate.save(context, Context.COLLECTION_NAME);
	}
	
	@Override
	public <T extends Context> T getContext(Class<T> type) {
		if(type == null || !Context.class.isAssignableFrom(type)){
			throw new IllegalArgumentException("非法参数");
		}
		Context ctx;
		try {
			ctx = type.newInstance();
			return mongoTemplate.findOne(Query.query(Criteria.where("id").is(ctx.getId())), type, Context.COLLECTION_NAME);
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
