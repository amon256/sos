/**
 * BaseController.java
 * 2015年5月20日
 */
package com.sos.controller;

import java.text.DecimalFormat;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sos.controller.vo.ResultObject;

/**  
 * <b>功能：</b>BaseController.java<br/>
 * <b>描述：</b> 对功能点的描述<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
public abstract class BaseController {
	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
	/**
	 * 异常处理
	 */
	@ExceptionHandler
	@ResponseBody
	public ResultObject exceptionHandler(HttpServletRequest request,Exception ex){
		String errorCode = createErrorCode();
		String path = request.getRequestURI();
		logger.error("处理请求[{}]时发生异常,异常码:{}", path,errorCode);
		logger.error("异常信息:", ex);
		ResultObject obj = new ResultObject();
		obj.setStatus(false);
		obj.setMsg("服务器错误,错误码:" + errorCode);
		obj.setData(ex.getMessage());
		return obj;
	}
	
	/**
	 *创建六位的错误码
	 */
	private String createErrorCode(){
		Random random = new Random();
		DecimalFormat df = new DecimalFormat("000000");
		return df.format(random.nextInt(999999));
	}
}
