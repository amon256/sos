/**
 * Field.java
 * 2015年5月19日
 */
package com.sos.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**  
 * <b>功能：</b>Field.java<br/>
 * <b>描述：</b>字段<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
@Target(value=ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DBField {
	/**
	 * 名称
	 */
	String name();
	/**
	 * 是否可更新
	 */
	boolean modify() default true;
}
