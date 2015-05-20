/**
 * UserService.java
 * 2015年5月19日
 */
package com.sos.persistence;

import java.util.List;

import com.sos.entity.User;

/**  
 * <b>功能：</b>UserService.java<br/>
 * <b>描述：</b> 对功能点的描述<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
public interface UserService extends CoreService<User>{
	
	/**
	 * 按电话号码查询
	 */
	public List<User> queryByMobile(String mobile);
	
	/**
	 * 按电子邮箱查询
	 */
	public List<User> queryByEmail(String email);
	
	/**
	 * 按QQ号码查询
	 */
	public List<User> queryByQQ(String qq);
}
