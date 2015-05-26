/**
 * FriendController.java
 * 2015年5月22日
 */
package com.sos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sos.controller.vo.ResultObject;
import com.sos.entity.FriendRelation;
import com.sos.entity.User;
import com.sos.persistence.FriendRelationService;
import com.sos.persistence.UserService;

/**  
 * <b>功能：</b>FriendController.java<br/>
 * <b>描述：</b> 朋友Controller<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
@RestController
@RequestMapping(value="friend/*")
public class FriendRelationController extends BaseController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FriendRelationService friendRelationService;

	/**
	 * 查询好友列表
	 */
	@RequestMapping(value="list")
	public ResultObject friends(@RequestParam(value="id",required=true)String id){
		ResultObject ro = new ResultObject();
		if(id == null){
			User user = userService.findById(id);
			if(user != null){
				FriendRelation friendRelation = friendRelationService.findOne(Query.query(Criteria.where("self.id").is(id)));
				ro.setMsg("好友列表加载成功");
				ro.setData(friendRelation == null?null : friendRelation.getFriends());
			}else{
				ro.fail();
				ro.setMsg("用户不存在");
			}
		}else{
			ro.fail();
			ro.setMsg("请求参数异常:ID不能为空");
		}
		return ro;
	}
	
	/**
	 * 设置紧急联系人 
	 * TODO 是否只需要一个紧急联系人 (其它联系人置为false)
	 */
	@RequestMapping(value="list")
	public ResultObject emergency(@RequestParam(value="id",required=true)String id,@RequestParam(value="friendId",required=true)String friendId){
		ResultObject ro = new ResultObject();
		Query query = Query.query(Criteria.where("self.$id").is(id).and("friends.user").is(friendId));
		Update update = new Update();
		update.set("friends.$.emergencyContact", true);
		friendRelationService.update(query, update);
		return ro;
	}
}
