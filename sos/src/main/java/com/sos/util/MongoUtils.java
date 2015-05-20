/**
 * MongoUtils.java
 * 2015年5月20日
 */
package com.sos.util;

import java.util.LinkedList;
import java.util.List;

import com.mongodb.DBCursor;

/**  
 * <b>功能：</b>MongoUtils.java<br/>
 * <b>描述：</b> 对功能点的描述<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
public class MongoUtils {

	public static <T> List<T> dbCursor2List(Class<T> clazz,DBCursor dbCursor){
		List<T> dataList = new LinkedList<T>();
		try {
			while(dbCursor.hasNext()){
					dataList.add(EntityUtils.dbObject2Bean(clazz.newInstance(), dbCursor.next()));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
		return dataList;
	}
}
