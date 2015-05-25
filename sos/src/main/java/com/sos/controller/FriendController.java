/**
 * FriendController.java
 * 2015年5月22日
 */
package com.sos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sos.controller.vo.ResultObject;
import com.sos.entity.Friend;
import com.sos.entity.User;
import com.sos.persistence.FriendService;
import com.sos.persistence.UserService;

/**  
 * <b>功能：</b>FriendController.java<br/>
 * <b>描述：</b> 朋友Controller<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
@RestController
@RequestMapping(value="friend/*")
public class FriendController extends BaseController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FriendService friendService;

	public ResultObject friends(String id){
		ResultObject ro = new ResultObject();
		if(id == null){
			User user = userService.findById(id);
			if(user != null){
				List<Friend> friends = friendService.findList(Query.query(Criteria.where("self.id").is(id)));
				ro.setMsg("好友列表加载成功");
				ro.setData(friends);
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
}
