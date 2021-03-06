/**
 * CoreEntity.java
 * 2015年5月19日
 */
package com.sos.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;

/**  
 * <b>功能：</b>CoreEntity.java<br/>
 * <b>描述：</b> 实体基类<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
public abstract class CoreEntity implements Serializable {
	private static final long serialVersionUID = 6746849041996849949L;
	
	/**
	 * ID
	 */
	@Id
	private String id;

	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 最后更新时间
	 */
	private Date lastUpdateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
}
