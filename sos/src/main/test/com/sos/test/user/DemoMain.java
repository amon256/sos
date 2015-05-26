/**
 * DemoMain.java
 * 2015年5月25日
 */
package com.sos.test.user;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.sos.entity.User;

/**  
 * <b>功能：</b>DemoMain.java<br/>
 * <b>描述：</b> 对功能点的描述<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
@Document(collection="demomain")
public class DemoMain {

	@Id
	private String id;
	
	@DBRef
	private User user;
	
	private List<Children> children;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Children> getChildren() {
		return children;
	}

	public void setChildren(List<Children> children) {
		this.children = children;
	}
	
	
}
