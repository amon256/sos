/**
 * FriendService.java
 * 2015年5月22日
 */
package com.sos.persistence;

import com.sos.entity.Friend;
import com.sos.entity.User;

/**  
 * <b>功能：</b>FriendService.java<br/>
 * <b>描述：</b> 对功能点的描述<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
public interface FriendService extends CoreService<Friend> {
	
	/**
	 * 结成好友关系
	 */
	public void makeFriend(User self,User other);
}
