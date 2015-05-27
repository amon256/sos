/**
 * MessageController.java
 * 2015年5月26日
 */
package com.sos.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sos.controller.vo.ResultObject;
import com.sos.entity.Message;
import com.sos.enums.DecisionResultEnum;
import com.sos.persistence.MessageService;

/**  
 * <b>功能：</b>MessageController.java<br/>
 * <b>描述：</b> 对功能点的描述<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
@RestController
@RequestMapping(value="message/*")
public class MessageController extends BaseController {

	@Autowired
	private MessageService messageService;
	
	/**
	 *查询消息列表
	 */
	@RequestMapping(value="list")
	public ResultObject list(String id){
		ResultObject ro = new ResultObject();
		Query query = Query.query(Criteria.where("receiver.$id").is(id).and("delete").is(false)).with(new Sort(Direction.DESC,"createTime")).limit(20);
		List<Message> messages = messageService.findList(query);
		ro.setData(messages);
		return ro;
	}
	
	
	/**
	 * 处理消息
	 */
	@RequestMapping(value="apply")
	public ResultObject apply(@RequestParam(value="id",required=true)String id,
			@RequestParam(value="messageId",required=true)String messageId,
			@RequestParam(value="decision",required=true)DecisionResultEnum decision){
		ResultObject ro = new ResultObject();
		Message message = messageService.findById(messageId);
		if(message != null){
			if(message.getReceiver().getId().equals(id)){
				messageService.apply(message, decision);
			}else{
				ro.fail();
				ro.setMsg("非法操作");
			}
		}else{
			ro.fail();
			ro.setMsg("消息己不存在");
		}
		return ro;
	}
	
	@RequestMapping(value="remove")
	public ResultObject remove(@RequestParam(value="id",required=true)String id,@RequestParam(value="messageIds",required=true)ArrayList<String> messageIds){
		ResultObject ro = new ResultObject();
		Query query = Query.query(Criteria.where("receiver.$id").is(id).and("id").in(messageIds));
		Update update = Update.update("delete", true);
		messageService.update(query, update);
		ro.setMsg("删除成功");
		return ro;
	}
}
