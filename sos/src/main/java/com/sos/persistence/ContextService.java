/**
 * ContextService.java
 * 2015年5月27日
 */
package com.sos.persistence;

import com.sos.config.Context;

/**  
 * <b>功能：</b>ContextService.java<br/>
 * <b>描述：</b> 对功能点的描述<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
public interface ContextService {
	
	public <T extends Context> T getContext(Class<T> type);
	
	public <T extends Context> void saveContext(T context);
}
