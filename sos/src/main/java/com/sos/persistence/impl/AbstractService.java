/**
 * AbstractService.java
 * 2015年5月19日
 */
package com.sos.persistence.impl;

import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.WriteResult;
import com.sos.entity.CoreEntity;
import com.sos.persistence.CoreService;
import com.sos.util.EntityUtils;
import com.sos.util.MongoUtils;

/**  
 * <b>功能：</b>AbstractService.java<br/>
 * <b>描述：</b> Service抽象实现<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
public abstract class AbstractService<T extends CoreEntity> implements CoreService<T>{
	
	/**
	 * DBName
	 */
	private String dbName = "sos";
	
	/**
	 * Mongo实例
	 */
	@Autowired
	private Mongo mongo;
	
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
		WriteResult result = mongo.getDB(dbName).getCollection(getCollectionName()).insert(EntityUtils.getDBObject(entity));
		if(result.getN() > 0){
			//add success
		}
		return entity;
	}
	
	public boolean remove(T entity) {
		WriteResult result = mongo.getDB(dbName).getCollection(getCollectionName()).remove(EntityUtils.getDBObjectForRemove(entity));
		if(result.getN() > 0){
			return true;
		}
		return false;
	}
	
	public T get(String id){
		DBObject dbObject = new BasicDBObject("_id",id);
		dbObject = mongo.getDB(dbName).getCollection(getCollectionName()).findOne(dbObject);
		T result = null;
		if(dbObject != null){
			try{
				Class<T> entityClass = getCurrentClass();
				result = EntityUtils.dbObject2Bean(entityClass.newInstance(), dbObject);
			}catch(Exception e){
				throw new RuntimeException(e);
			}
		}
		return result;
	}
	
	@Override
	public List<T> getAll() {
		DBCursor cursor = mongo.getDB(dbName).getCollection(getCollectionName()).find();
		Class<T> clazz = getCurrentClass();
		return MongoUtils.dbCursor2List(clazz, cursor);
	}
	
	protected Class<T> getCurrentClass(){
		ParameterizedType parameterizedType = (ParameterizedType)this.getClass().getGenericSuperclass(); 
		@SuppressWarnings("unchecked")
		Class<T> entityClass= (Class<T>)(parameterizedType.getActualTypeArguments()[0]); 
		return entityClass;
	}
	
	protected DB getDB(){
		return mongo.getDB(getDbName());
	}
	
	protected DBCollection getDBCollection(){
		return getDB().getCollection(getCollectionName());
	}
	
	/**
	 * 数据集名称
	 */
	protected abstract String getCollectionName();
	
	public Mongo getMongo() {
		return mongo;
	}
	
	public String getDbName() {
		return dbName;
	}
}
