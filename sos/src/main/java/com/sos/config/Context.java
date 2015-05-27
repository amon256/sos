/**
 * Context.java
 * 2015年5月27日
 */
package com.sos.config;

import org.springframework.data.annotation.Id;

/**  
 * <b>功能：</b>Context.java<br/>
 * <b>描述：</b> 对功能点的描述<br/>
 * <b>@author： </b>fengmengyue<br/>
 */

public abstract class Context {
	public static final String COLLECTION_NAME = "soscontext";

	@Id
	public abstract String getId();

}
