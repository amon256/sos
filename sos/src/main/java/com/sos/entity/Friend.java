/**
 * Friend.java
 * 2015年5月22日
 */
package com.sos.entity;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


/**  
 * <b>功能：</b>Friend.java<br/>
 * <b>描述：</b> 对功能点的描述<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
@Document(collection="friend")
public class Friend extends CoreEntity{
	private static final long serialVersionUID = 8081420335749844527L;

	/**
	 * 好友关系左
	 */
	@DBRef
	@JsonIgnore
	private User self;
	
	/**
	 * 好友关系右
	 */
	@DBRef
	private User other;
	
	/**
	 * 备注名字
	 */
	private String descName;
	
	/**
	 * 是否紧急联系人
	 */
	private boolean emergencyContact = false;

	public User getSelf() {
		return self;
	}

	public void setSelf(User self) {
		this.self = self;
	}

	public User getOther() {
		return other;
	}

	public void setOther(User other) {
		this.other = other;
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
	
	
}
