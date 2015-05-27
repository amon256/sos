/**
 * SMSSendTest.java
 * 2015年5月27日
 */
package com.sos.test.user;

import org.junit.Test;

import com.sos.config.SMSContext;
import com.sos.config.SystemConfig;
import com.sos.sms.SMSSender;
import com.sos.test.TestBase;

/**  
 * <b>功能：</b>SMSSendTest.java<br/>
 * <b>描述：</b> 对功能点的描述<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
public class SMSSendTest extends TestBase {
	
	@Test
	public void saveContext(){
		SMSContext ctx = new SMSContext();
		ctx.setUrl("http://106.ihuyi.cn/webservice/sms.php?method=Submit");
		ctx.setUsername("cf_amon256");
		ctx.setPassword("amon256");
		ctx.setMethod("POST");
		ctx.setMsgKey("content");
		ctx.setPwdKey("password");
		ctx.setUnameKey("account");
		ctx.setReceiverKey("mobile");
		ctx.setProvider("互亿无线");
		ctx.setMaxTimePerMobilePerDay(2);
		SystemConfig.saveContext(ctx);
	}
	
	@Test
	public void testSend(){
		SMSSender.sendSMS("15019491687", "您的验证码是：1234。请不要把验证码泄露给其他人。");
	}

}
