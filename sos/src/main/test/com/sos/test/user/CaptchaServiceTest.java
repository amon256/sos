/**
 * CaptchaServiceTest.java
 * 2015年5月20日
 */
package com.sos.test.user;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sos.entity.Captcha;
import com.sos.persistence.CaptchaService;
import com.sos.persistence.impl.CaptchaServiceImpl;
import com.sos.test.TestBase;

/**  
 * <b>功能：</b>CaptchaServiceTest.java<br/>
 * <b>描述：</b> 对功能点的描述<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
public class CaptchaServiceTest extends TestBase {
	
	@Autowired
	CaptchaService captchaService;

	@Test
	public void sendCaptcha(){
		Captcha cap = captchaService.createAndSendCaptcha("15019491687");
		System.out.println(cap.getMobile() + ":" + cap.getCaptcha());
	}
	
	@Test
	public void queryAll(){
		List<Captcha> caps = captchaService.getAll();
		for(Captcha cap : caps){
			System.out.println(cap.getMobile() + ":" + cap.getCaptcha() + ":" + cap.getEffective());
		}
	}
	
	@Test
	public void drop(){
		CaptchaServiceImpl impl = (CaptchaServiceImpl)captchaService;
		impl.getMongo().getDB("sos").getCollection("captchas").drop();
	}
}
