/**
 * UserTrialServiceTest.java
 * 2015年5月27日
 */
package com.sos.test.user;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.sos.entity.User;
import com.sos.entity.UserTrail;
import com.sos.entity.vo.Point;
import com.sos.persistence.UserService;
import com.sos.persistence.UserTrailService;
import com.sos.test.TestBase;

/**  
 * <b>功能：</b>UserTrialServiceTest.java<br/>
 * <b>描述：</b> 对功能点的描述<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
public class UserTrailServiceTest extends TestBase {

	@Autowired
	private UserTrailService trailService;
	
	@Autowired
	private UserService userService;
	
	@Test
	public void test(){
		User user = userService.findById("d544708ff06645d09593258fe57b93c5");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -100);
		Point point = new Point();
		long begin = System.currentTimeMillis();
		for(int i = 0; i < 10000; i++){
			point.setRecordTime(cal.getTime());
			point.setData(new BigDecimal[]{new BigDecimal("122.111"),new BigDecimal("45.3232")});
			trailService.recordPoint(user, point);
			cal.add(Calendar.MINUTE, 5);
		}
		System.out.println("耗时:" + (System.currentTimeMillis() - begin) + "ms");
	}
	
	@Test
	public void query(){
		long begin = System.currentTimeMillis();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -90);
		Query query = Query.query(Criteria.where("points.recordTime").lte(cal.getTime()));
		List<UserTrail> trails = trailService.findList(query);
		System.out.println("耗时:" + (System.currentTimeMillis() - begin) + "ms");
		for(UserTrail t : trails){
			System.out.println(t.getUser().getMobile());
		}
	}
}
