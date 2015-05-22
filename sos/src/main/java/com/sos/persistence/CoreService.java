/**
 * CoreService.java
 * 2015年5月19日
 */
package com.sos.persistence;

import java.util.List;
import java.util.Set;

import com.sos.entity.CoreEntity;

/**  
 * <b>功能：</b>CoreService.java<br/>
 * <b>描述：</b> 对功能点的描述<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
public interface CoreService<T extends CoreEntity> {
	
	/**
	 * 增加实体
	 */
	public T add(T entity);
	
	/**
	 * 更新数据
	 */
	public void update(T entity,Set<String> updateFields);
	
	/**
	 * 删除实体
	 */
	public boolean delete(T Entity);
	
	/**
	 * 按ID查找
	 */
	public T get(String id);
	
	/**
	 * 查询所有结果
	 */
	public List<T> getAll();
}
