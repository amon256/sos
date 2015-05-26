package com.sos.test.user;

import org.springframework.data.mongodb.core.mapping.DBRef;

import com.sos.entity.User;

public class Children {
	
	@DBRef
	private User user;
	
	private String desc;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
