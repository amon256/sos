/**
 * UserTrailService.java
 * 2015年5月27日
 */
package com.sos.persistence;

import com.sos.entity.User;
import com.sos.entity.UserTrail;
import com.sos.entity.vo.Point;

/**  
 * <b>功能：</b>UserTrailService.java<br/>
 * <b>描述：</b> 对功能点的描述<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
public interface UserTrailService extends CoreService<UserTrail> {
	
	/**
	 * 记录轨迹数据
	 */
	public void recordPoint(User user,Point point);

}
