/**
 * Friend.java
 * 2015年5月25日
 */
package com.sos.entity.vo;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.DBRef;

import com.sos.entity.User;

/**  
 * <b>功能：</b>Friend.java<br/>
 * <b>描述：</b> 好友对象<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
public class Friend {

	/**
	 * 好友对应的用户,关联到用户表
	 */
	@DBRef
	private User user;
	
	/**
	 * 备注名字
	 */
	private String descName;
	
	/**
	 * 是否紧急联系人
	 */
	private boolean emergencyContact = false;
	
	/**
	 * 添加时间
	 */
	private Date createTime;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getDescName() {
		return descName;
	}

	public void setDescName(String descName) {
		this.descName = descName;
	}

	public boolean isEmergencyContact() {
		return emergencyContact;
	}

	public void setEmergencyContact(boolean emergencyContact) {
		this.emergencyContact = emergencyContact;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
