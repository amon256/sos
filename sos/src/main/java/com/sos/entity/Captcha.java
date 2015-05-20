/**
 * Captcha.java
 * 2015年5月19日
 */
package com.sos.entity;

import java.util.Date;

import com.sos.annotations.DBField;
import com.sos.enums.BooleanEnum;

/**  
 * <b>功能：</b>Captcha.java<br/>
 * <b>描述：</b> 验证码<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
public class Captcha extends CoreEntity {
	private static final long serialVersionUID = 6571172372854308894L;

	/**
	 * 手机号码
	 */
	@DBField(name="mobile",modify=false)
	private String mobile;
	
	/**
	 * 验证码
	 */
	@DBField(name="captcha",modify=false)
	private String captcha;
	
	/**
	 * 失效时间
	 */
	@DBField(name="invalidateTime")
	private Date invalidateTime;
	
	/**
	 * 是否有效
	 */
	@DBField(name="effective")
	private BooleanEnum effective;
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public Date getInvalidateTime() {
		return invalidateTime;
	}

	public void setInvalidateTime(Date invalidateTime) {
		this.invalidateTime = invalidateTime;
	}

	public BooleanEnum getEffective() {
		return effective;
	}

	public void setEffective(BooleanEnum effective) {
		this.effective = effective;
	}
}
