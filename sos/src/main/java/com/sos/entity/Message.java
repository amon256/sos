/**
 * Message.java
 * 2015年5月22日
 */
package com.sos.entity;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.sos.enums.MessageStatusEnum;
import com.sos.enums.MessageTypeEnum;

/**  
 * <b>功能：</b>Message.java<br/>
 * <b>描述：</b> 消息<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
@Document(collection="message")
public class Message extends CoreEntity {
	private static final long serialVersionUID = -2008289193007573185L;
	
	/**
	 * 消息接收人
	 */
	@DBRef
	@JsonIgnore
	private User receiver;

	/**
	 * 消息标题
	 */
	private String title;
	
	/**
	 * 消息内容
	 */
	private String content;
	
	/**
	 * 消息类型
	 */
	private MessageTypeEnum type;
	
	/**
	 * 消息状态
	 */
	private MessageStatusEnum status;
	
	/**
	 * 阅读状态
	 */
	private boolean read = false;
	
	/**
	 * 删除状态
	 */
	private boolean delete = false;
	
	/**
	 * 消息关联业务ID
	 */
	private String refId;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public MessageTypeEnum getType() {
		return type;
	}

	public void setType(MessageTypeEnum type) {
		this.type = type;
	}

	public MessageStatusEnum getStatus() {
		return status;
	}

	public void setStatus(MessageStatusEnum status) {
		this.status = status;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}
}
