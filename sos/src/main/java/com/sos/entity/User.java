/**
 * User.java
 * 2015年5月19日
 */
package com.sos.entity;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import com.sos.enums.SexEnum;
import com.sos.util.serializer.DateOnlySerializer;

/**  
 * <b>功能：</b>User.java<br/>
 * <b>描述：</b> 用户<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
@Document(collection="user")
public class User extends CoreEntity {
	private static final long serialVersionUID = 433489837920569985L;
	
	/**
	 * 手机
	 */
	@Indexed(unique=true)
	private String mobile;
	
	/**
	 * QQ
	 */
	private String qq;
	
	/**
	 * 邮箱
	 */
	private String email;
	
	/**
	 * 昵称
	 */
	private String nickName;
	
	/**
	 * 头像地址
	 */
	private String photoUrl;
	
	/**
	 * 性别
	 */
	private SexEnum sex;
	
	/**
	 * 生日
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonSerialize(using=DateOnlySerializer.class)
	private Date birthday;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public SexEnum getSex() {
		return sex;
	}

	public void setSex(SexEnum sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
}
