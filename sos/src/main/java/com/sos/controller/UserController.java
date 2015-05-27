/**
 * UserController.java
 * 2015年5月20日
 */
package com.sos.controller;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sos.controller.vo.ResultObject;
import com.sos.entity.User;
import com.sos.persistence.CaptchaService;
import com.sos.persistence.UserService;
import com.sos.util.CollectionUtils;
import com.sos.util.StringUtil;

/**  
 * <b>功能：</b>UserController.java<br/>
 * <b>描述：</b> 用户controller,@RestController 仅服务于JSON<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
@RestController
@RequestMapping(value="user/*")
public class UserController extends BaseController{
	
	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CaptchaService captchaService;
	
	/**
	 *用户注册申请
	 */
	@RequestMapping(value="register")
	public ResultObject register(@RequestParam(required=true,value="mobile")String mobile,@RequestParam(required=true,value="captcha")String captcha){
		logger.info("接收到手机号码:[{}]注册申请，验证码为:[{}]",mobile,captcha);
		ResultObject resultObject = new ResultObject();
		mobile = mobile.trim();
		captcha = captcha.trim();
		if(StringUtil.isMobile(mobile)){
			String msg = validateForRegister(mobile, captcha);
			if(msg != null){
				resultObject.setStatus(false);
				resultObject.setMsg(msg);
			}else{
				User entity = new User();
				entity.setMobile(mobile);
				entity = userService.add(entity );
				resultObject.setMsg("注册成功");
				resultObject.setData(entity);
			}
		}else{
			resultObject.setStatus(false);
			resultObject.setMsg("手机格式不正确");
		}
		captchaService.invalidateOtherCaptcha(mobile);//请求无论成功还是失败，都需要将己有验证码失效掉
		logger.info("注册结果:[{}],原因:[{}]",resultObject.isStatus(),resultObject.getMsg());
		return resultObject;
	}
	
	/**
	 * 用户登录(免注册，如果数据库不存在，则自动注册)
	 */
	@RequestMapping(value="login")
	public ResultObject login(@RequestParam(required=true,value="mobile")String mobile,@RequestParam(required=true,value="captcha")String captcha){
		logger.info("接收到手机号码:[{}]登录申请，验证码为:[{}]",mobile,captcha);
		ResultObject resultObject = new ResultObject();
		mobile = mobile.trim();
		captcha = captcha.trim();
		if(StringUtil.isMobile(mobile)){
			String msg = validateForLogin(mobile, captcha);
			if(msg != null){
				resultObject.setStatus(false);
				resultObject.setMsg(msg);
			}else{
				List<User> users = userService.queryByMobile(mobile);
				User entity = null;
				if(users == null || users.isEmpty()){
					entity = new User();
					entity.setMobile(mobile);
					entity.setNickName(mobile);
					entity = userService.add(entity);
				}else{
					entity = users.get(0);
				}
				resultObject.setMsg("登录成功");
				resultObject.setData(entity);
			}
		}else{
			resultObject.setStatus(false);
			resultObject.setMsg("手机格式不正确");
		}
		captchaService.invalidateOtherCaptcha(mobile);//请求无论成功还是失败，都需要将己有验证码失效掉
		logger.info("登录结果:[{}],原因:[{}]",resultObject.isStatus(),resultObject.getMsg());
		return resultObject;
	}
	
	/**
	 * 用户更新
	 */
	@RequestMapping(value="update")
	public ResultObject update(User user){
		logger.info("接收到ID:[{}]更新信息",user.getId());
		ResultObject resultObject = new ResultObject();
		if(user.getId() != null){
			String msg = validateForUpdate(user);
			if(msg != null){
				resultObject.setStatus(false);
				resultObject.setMsg(msg);
			}else{
				Set<String> updateFields = CollectionUtils.createSet(String.class, "nickName","sex","birthday","qq","email");
				userService.update(user,updateFields);
				user = userService.findById(user.getId());
				resultObject.setMsg("修改成功");
				resultObject.setData(user);
			}
		}else{
			resultObject.setStatus(false);
			resultObject.setMsg("手机格式不正确");
		}
		logger.info("更新结果:[{}],原因:[{}]",resultObject.isStatus(),resultObject.getMsg());
		return resultObject;
	}
	
	private String validateForUpdate(User user) {
		//验证邮箱
		if(user.getEmail() != null){
			List<User> users = userService.queryByEmail(user.getEmail());
			if(!users.isEmpty() && !users.get(0).getId().equals(user.getId())){
				return "该邮箱己被使用";
			}
		}
		//验证qq
		if(user.getQq() != null){
			List<User> users = userService.queryByQQ(user.getQq());
			if(!users.isEmpty() && !users.get(0).getId().equals(user.getId())){
				return "该QQ己被使用";
			}
		}
		return null;
	}

	private String validateForLogin(String mobile,String captcha){
		//校验用户是否存在
//		if(userService.queryByMobile(mobile).isEmpty()){
//			return "该电话号码尚未注册";
//		}
		if(!captchaService.checkExpireCaptcha(mobile, captcha)){
			return "验证码不正确或己失效";
		}
		//校验验证码是否正确
		return null;
	}
	
	private String validateForRegister(String mobile,String captcha){
		//校验用户是否存在
		if(!userService.queryByMobile(mobile).isEmpty()){
			return "该电话号码己被注册";
		}
		if(!captchaService.checkExpireCaptcha(mobile, captcha)){
			return "验证码不正确或己失效";
		}
		//校验验证码是否正确
		return null;
	}
	
}
