/**
 * SystemConfig.java
 * 2015年5月27日
 */
package com.sos.config;

import com.sos.persistence.ContextService;
import com.sos.spring.WebApplicationContextAware;

/**  
 * <b>功能：</b>SystemConfig.java<br/>
 * <b>描述：</b> 对功能点的描述<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
public class SystemConfig {
	
	public static <T extends Context> T getContext(Class<T> type){
		return WebApplicationContextAware.getApplicationContext().getBean(ContextService.class).getContext(type);
	}
	
	public static <T extends Context> void saveContext(T context){
		WebApplicationContextAware.getApplicationContext().getBean(ContextService.class).saveContext(context);
	}
}
