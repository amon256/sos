/**
 * CaptchaController.java
 * 2015年5月20日
 */
package com.sos.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sos.controller.vo.ResultObject;
import com.sos.entity.Captcha;
import com.sos.persistence.CaptchaService;
import com.sos.util.StringUtil;

/**  
 * <b>功能：</b>CaptchaController.java<br/>
 * <b>描述：</b> 验证码controller<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
@Controller
@RequestMapping(value="captcha/*")
public class CaptchaController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(CaptchaController.class);

	@Autowired
	private CaptchaService captchaService;
	
	/**
	 * 请求发送短信验证码
	 */
	@RequestMapping(value="sendSms")
	@ResponseBody
	public ResultObject sendSms(@RequestParam(required=true,value="mobile")String mobile){
		logger.info("手机号码:[{}]请求发送验证码.",mobile);
		ResultObject result = new ResultObject();
		if(StringUtil.isMobile(mobile)){
			try{
				Captcha captcha = captchaService.createAndSendCaptcha(mobile);
				logger.info("给手机:[{}]发送验证码:[{}]成功",captcha.getMobile(),captcha.getCaptcha());
			}catch(Exception e){
				logger.error("请求发送短信失败", e);
				result.setStatus(false);
				result.setMsg("发送短信失败");
			}
		}else{
			result.setStatus(false);
			result.setMsg("手机号码不正确");
		}
		return result;
	}
}
