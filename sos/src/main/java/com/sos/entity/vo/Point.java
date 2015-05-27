/**
 * Point.java
 * 2015年5月27日
 */
package com.sos.entity.vo;

import java.math.BigDecimal;
import java.util.Date;

/**  
 * <b>功能：</b>Point.java<br/>
 * <b>描述：</b> 经纬度数据<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
public class Point {

	/**
	 * 经纬度数据,第一位经度lng，第二位纬度lat
	 */
	private BigDecimal[] data;
	
	/**
	 * 记录时间
	 */
	private Date recordTime;

	public BigDecimal[] getData() {
		return data;
	}

	public void setData(BigDecimal[] data) {
		this.data = data;
	}

	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}
}
