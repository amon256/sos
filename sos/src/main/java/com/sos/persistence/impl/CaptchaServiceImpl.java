/**
 * CaptchaServiceImpl.java
 * 2015年5月20日
 */
package com.sos.persistence.impl;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.sos.entity.Captcha;
import com.sos.enums.BooleanEnum;
import com.sos.persistence.CaptchaService;

/**  
 * <b>功能：</b>CaptchaServiceImpl.java<br/>
 * <b>描述：</b> 对功能点的描述<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
@Component
public class CaptchaServiceImpl extends AbstractService<Captcha> implements
		CaptchaService {

	@Override
	public Captcha createAndSendCaptcha(String mobile) {
		Captcha captcha = new Captcha();
		captcha.setEffective(BooleanEnum.TRUE);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, 20);//验证码20分钟内有效
		captcha.setInvalidateTime(cal.getTime());
		captcha.setMobile(mobile);
		captcha.setCaptcha(createCaptcha());
		invalidateOtherCaptcha(mobile);//失效己有数据
		captcha = super.add(captcha);
		sendSms(captcha);//发送验证码
		return captcha;
	}
	
	@Override
	public boolean checkExpireCaptcha(String mobile,String captcha) {
		Query query = Query.query(Criteria.where("mobile").is(mobile)
				.and("effective").is(BooleanEnum.TRUE)
				.and("captcha").is(captcha)
				.and("invalidateTime").gte(new Date()));
		Captcha result = getMongoTemplate().findOne(query, Captcha.class);
		return result != null;
	}
	
	private String createCaptcha(){
		Random ran = new Random();
		Integer v = ran.nextInt(9999);
		DecimalFormat decFormat = new DecimalFormat("0000");
		return decFormat.format(v);
	}

	@Override
	public void invalidateOtherCaptcha(String mobile){
//		DBObject query = new BasicDBObject();
//		query.put("mobile", mobile);
//		query.put("effective", BooleanEnum.TRUE.name());
//		DBObject update = new BasicDBObject();
//		update.put("mobile", mobile);
//		update.put("effective", BooleanEnum.FALSE.name());
//		getDBCollection().update(query, new BasicDBObject("$set", update), true, true);
	}
	
	/**
	 * 发送短信
	 */
	private boolean sendSms(Captcha captcha){
		//TODO
		return true;
	}
}
