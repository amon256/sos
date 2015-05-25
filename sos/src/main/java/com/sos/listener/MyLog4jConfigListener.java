/**
 * MyLog4jConfigListener.java
 * 2015年5月25日
 */
package com.sos.listener;

import java.io.File;

import javax.servlet.ServletContextEvent;

import org.springframework.web.util.Log4jConfigListener;

import com.sos.util.MyRollingFileAppender;

/**  
 * <b>功能：</b>MyLog4jConfigListener.java<br/>
 * <b>描述：</b> 对功能点的描述<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
public class MyLog4jConfigListener extends Log4jConfigListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {
		//设置log4j日志根目录
		String root = System.getProperty("user.dir") + File.separator + "logs";
		File rootDir = new File(root);
		if(!rootDir.exists()){
			rootDir.mkdirs();
		}
		MyRollingFileAppender.setRoot(root);
		super.contextInitialized(event);
	}
}
