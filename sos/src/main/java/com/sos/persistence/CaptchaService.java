/**
 * CaptchaService.java
 * 2015年5月19日
 */
package com.sos.persistence;

import com.sos.entity.Captcha;

/**  
 * <b>功能：</b>CaptchaService.java<br/>
 * <b>描述：</b> 对功能点的描述<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
public interface CaptchaService extends CoreService<Captcha>{
	
	/**
	 * 创建并发送验证码
	 */
	public Captcha createAndSendCaptcha(String mobile);
	
	/**
	 * 根据手机号码校验验证码,并且失效时间未过
	 */
	public boolean checkExpireCaptcha(String mobile,String captcha);
	
	/**
	 * 将该号码下其它验证码失效掉
	 */
	public void invalidateOtherCaptcha(String mobile);

}
