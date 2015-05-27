/**
 * CoreService.java
 * 2015年5月19日
 */
package com.sos.persistence;

import java.util.Collection;
import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.sos.entity.CoreEntity;

/**  
 * <b>功能：</b>CoreService.java<br/>
 * <b>描述：</b> 对功能点的描述<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
public interface CoreService<T extends CoreEntity>{
	
	/**
	 * 增加实体
	 */
	public T add(T entity);
	
	/**
	 * 批量插入
	 */
	public void add(List<T> entityList);
	
	/**
	 * 更新数据
	 */
	public void update(T entity,Collection<String> updateFields);
	
	/**
	 * 自定义更新
	 */
	public void update(Query query,Update update);
	
	/**
	 * 删除实体
	 */
	public boolean delete(T Entity);
	
	/**
	 * 检查是否存在数据
	 */
	public boolean checkExists(Criteria criteria);
	
	/**
	 * 按ID查找
	 */
	public T findById(String id);
	
	/**
	 * 查询所有结果
	 */
	public List<T> findAll();
	
	/**
	 * 查询唯一对象
	 */
	public T findOne(Query query);
	
	/**
	 * 自定义查询
	 */
	public List<T> findList(Query query);
}
