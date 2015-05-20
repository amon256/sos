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

}
