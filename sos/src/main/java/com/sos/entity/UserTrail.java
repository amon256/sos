/**
 * UserTrail.java
 * 2015年5月27日
 */
package com.sos.entity;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.sos.entity.vo.Point;

/**  
 * <b>功能：</b>UserTrail.java<br/>
 * <b>描述：</b> 用户轨迹<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
@Document(collection="usertrail")
public class UserTrail extends CoreEntity {
	private static final long serialVersionUID = 2805011543279476627L;
	
	/**
	 *  用户
	 */
	@DBRef
	@JsonIgnore
	private User user;
	
	/**
	 * 活动记录
	 */
	private Boolean active;
	
	/**
	 * 轨迹数据
	 */
	private List<Point> points;

	/**
	 * 轨迹数据数
	 */
	private Integer pointCount;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Point> getPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Integer getPointCount() {
		return pointCount;
	}

	public void setPointCount(Integer pointCount) {
		this.pointCount = pointCount;
	}
}
