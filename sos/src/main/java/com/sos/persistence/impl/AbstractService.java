/**
 * AbstractService.java
 * 2015年5月19日
 */
package com.sos.persistence.impl;

import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.sos.entity.CoreEntity;
import com.sos.persistence.CoreService;

/**  
 * <b>功能：</b>AbstractService.java<br/>
 * <b>描述：</b> Service抽象实现<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
public abstract class AbstractService<T extends CoreEntity> implements CoreService<T>{
	
	/**
	 * MongoTemplate实例
	 */
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public T add(T entity) {
		if(entity.getId() == null){
			entity.setId(UUID.randomUUID().toString().toLowerCase().replaceAll("-", ""));
		}
		if(entity.getCreateTime() == null){
			entity.setCreateTime(new Date());
		}
		if(entity.getLastUpdateTime() == null){
			entity.setLastUpdateTime(entity.getCreateTime());
		}
		mongoTemplate.insert(entity);
		return entity;
	}
	
	@Override
	public void update(T entity, Set<String> updateFields) {
		if(entity == null || entity.getId() == null || updateFields == null || updateFields.isEmpty()){
			return;
		}
		Update update = Update.update("lastUpdateTime", new Date());
		try{
			for(String field : updateFields){
				update.set(field, BeanUtils.getPropertyDescriptor(entity.getClass(), field).getReadMethod().invoke(entity, new Object[]{}));
			}
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		mongoTemplate.updateFirst(Query.query(Criteria.where("id").is(entity.getId())), update, entity.getClass());
	}
	
	@Override
	public boolean delete(T entity) {
		mongoTemplate.remove(entity);
		return true;
	}
	
	public T get(String id){
		Class<T> entityClass = getCurrentClass();
		return mongoTemplate.findById(id, entityClass);
	}
	
	@Override
	public List<T> getAll() {
		return mongoTemplate.findAll(getCurrentClass());
	}
	
	protected Class<T> getCurrentClass(){
		ParameterizedType parameterizedType = (ParameterizedType)this.getClass().getGenericSuperclass(); 
		@SuppressWarnings("unchecked")
		Class<T> entityClass= (Class<T>)(parameterizedType.getActualTypeArguments()[0]); 
		return entityClass;
	}
	
	/**
	 * 获取MongoTemplate
	 */
	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}
}
