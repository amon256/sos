/**
 * StringUtil.java
 * 2015年5月20日
 */
package com.sos.util;

/**  
 * <b>功能：</b>StringUtil.java<br/>
 * <b>描述：</b> 对功能点的描述<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
public class StringUtil {

	/**
	 *校验是否手机号码
	 */
	public static boolean isMobile(String mobile){
		return mobile.trim().matches("1[0-9]{10}");
	}
}
