/**
 * EntityUtils.java
 * 2015年5月19日
 */
package com.sos.util;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.sos.annotations.DBField;
import com.sos.entity.CoreEntity;

/**  
 * <b>功能：</b>EntityUtils.java<br/>
 * <b>描述：</b> 实体工具类<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
public class EntityUtils {
	private static final Logger logger = LoggerFactory.getLogger(EntityUtils.class);
	
	private static final Map<Class<?>,List<Field>> mongoFieldCache = new HashMap<Class<?>, List<Field>>();
	
	public static synchronized List<Field> getMongoFields(Class<?> clazz){
		if(clazz == null){
			return null;
		}
		List<Field> listField = mongoFieldCache.get(clazz);
		if(listField != null){
			return listField;
		}
		listField = new LinkedList<Field>();
		if(clazz.getSuperclass() != null){
			listField.addAll(EntityUtils.getMongoFields(clazz.getSuperclass()));
		}
		Field[] fields = clazz.getDeclaredFields();
		for(Field field : fields){
			DBField dbf = field.getAnnotation(DBField.class);
			if(dbf == null){
				continue;
			}
			listField.add(field);
		}
		return listField;
	}

	/**
	 * 实体转换为MongoDB对象
	 */
	public static DBObject getDBObject(CoreEntity entity){
		DBObject dbObject = new BasicDBObject();
		if(entity.getId() != null){
			dbObject.put("_id", entity.getId());
		}
		List<Field> fields = getMongoFields(entity.getClass());
		try{
			for(Field field : fields){
				DBField dbf = field.getAnnotation(DBField.class);
				if(dbf == null){
					continue;
				}
				if("_id".equals(dbf.name())){
					continue;
				}
				field.setAccessible(true);
				dbObject.put(dbf.name(), converValue(field.get(entity)));
			}
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		logger.debug("转换DBObject:{}",dbObject);
		return dbObject;
	}
	
	/**
	 * 实体转换为更新对象
	 */
	public static DBObject getDBObjectForUpdate(CoreEntity entity){
		DBObject dbObject = new BasicDBObject();
		if(entity.getId() != null){
			dbObject.put("_id", entity.getId());
		}
		List<Field> fields = getMongoFields(entity.getClass());
		try{
			for(Field field : fields){
				DBField dbf = field.getAnnotation(DBField.class);
				if("_id".equals(dbf.name())){
					continue;
				}
				if(!dbf.modify()){
					continue;
				}
				dbObject.put(dbf.name(), field.get(entity));
			}
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		logger.debug("转换DBObject:{}",dbObject);
		return dbObject;
	}
	
	public static DBObject getDBObjectForRemove(CoreEntity entity){
		DBObject dbObject = new BasicDBObject();
		if(entity.getId() != null){
			dbObject.put("_id", entity.getId());
		}else{
			throw new RuntimeException("id is empty");
		}
		logger.debug("转换DBObject:{}",dbObject);
		return dbObject;
	}
	
	public static <T> T dbObject2Bean(T bean,DBObject dbObject){
		if(bean != null && dbObject != null){
			List<Field> fields = getMongoFields(bean.getClass());
			ConverterRegister.registerConverter();
			try{
				for(Field field : fields){
					DBField dbf = field.getAnnotation(DBField.class);
					if("_id".equals(dbf.name())){
						BeanUtils.setProperty(bean, field.getName(), dbObject.get(dbf.name()).toString());
						continue;
					}
					BeanUtils.setProperty(bean, field.getName(), dbObject.get(dbf.name()));
				}
			}catch(Exception e){
				throw new RuntimeException(e);
			}
		}
		return bean;
	}
	
	public static Object converValue(Object value){
		if(value == null){
			return null;
		}else if(value instanceof Date){
			return DateUtils.format((Date)value, DateUtils.SIMPLE_PATTERN);
		}else if(value instanceof CoreEntity){
			return EntityUtils.getDBObject((CoreEntity) value);
		}else if(value.getClass().isEnum()){
			return value.toString();
		}else{
			return value;
		}
	}
}
