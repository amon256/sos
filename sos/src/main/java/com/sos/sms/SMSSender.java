/**
 * SMSSender.java
 * 2015年5月27日
 */
package com.sos.sms;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;

import com.sos.config.SMSContext;
import com.sos.config.SystemConfig;

/**  
 * <b>功能：</b>SMSSender.java<br/>
 * <b>描述：</b> 对功能点的描述<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
public class SMSSender {
	
	private static final Logger logger = LoggerFactory.getLogger(SMSSender.class);
	
	private SMSSender(){
	}
	
	public static void sendSMS(String mobile,String msg){
		logger.debug("准备给[{}]发送短信：{}",mobile,msg);
		SMSContext ctx = SystemConfig.getContext(SMSContext.class);
		if(ctx == null || !ctx.validate()){
			logger.debug("短信参数未设置或未完整,不发送短信。");
			return;
		}
		String charset = "utf-8";
		if(ctx.getCharset() != null){
			charset = Charset.forName(ctx.getCharset()).displayName();
		}
		if(HttpMethod.GET.name().equalsIgnoreCase(ctx.getMethod())){
			//如果配置为get方式
			sendGet(ctx, mobile, msg,charset);
		}else{
			//默认post
			sendPost(ctx, mobile, msg,charset);
		}
	}
	
	private static void sendPost(SMSContext ctx,String mobile,String msg,String charset){
		List<NameValuePair> params = new LinkedList<NameValuePair>();
		params.add(new BasicNameValuePair(ctx.getUnameKey(), ctx.getUsername()));
		params.add(new BasicNameValuePair(ctx.getPwdKey(),ctx.getPassword()));
		params.add(new BasicNameValuePair(ctx.getReceiverKey(),mobile));
		params.add(new BasicNameValuePair(ctx.getMsgKey(),msg));
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(ctx.getUrl());
		try {
			post.setEntity(new UrlEncodedFormEntity(params, charset));
			HttpResponse response = client.execute(post);
			HttpEntity entity = response.getEntity();
			String result = EntityUtils.toString(entity, "utf-8");
			logger.debug("发送短信返回内容:{}",result);
			System.out.println(result);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private static void sendGet(SMSContext ctx,String mobile,String msg,String charset){
		HttpClient client = HttpClientBuilder.create().build();
		StringBuilder url = new StringBuilder(ctx.getUrl());
		if(url.indexOf("?") < 0){
			url.append("?");
		}
		try {
			url.append(ctx.getUnameKey()).append("=").append(ctx.getUsername())
				.append(ctx.getPwdKey()).append("=").append(ctx.getPassword())
				.append(ctx.getReceiverKey()).append("=").append(mobile)
				.append(ctx.getMsgKey()).append("=").append(URLEncoder.encode(msg, charset));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		try {
			HttpResponse response = client.execute(new HttpGet(url.toString()));
			HttpEntity entity = response.getEntity();
			String result = EntityUtils.toString(entity, charset);
			logger.debug("发送短信返回内容:{}",result);
			System.out.println(result);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
