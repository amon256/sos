/**
 * TestBase.java
 * 2015年5月19日
 */
package com.sos.test;

import junit.framework.TestCase;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**  
 * <b>功能：</b>TestBase.java<br/>
 * <b>描述：</b> 对功能点的描述<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
@RunWith(SpringJUnit4ClassRunner.class) // 整合 
@ContextConfiguration(locations="classpath:applicationContext.xml") // 加载配置
public abstract class TestBase extends TestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		System.out.println(System.getProperty("user.dir"));
	}
}
