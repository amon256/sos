/**
 * FriendRequestController.java
 * 2015年5月22日
 */
package com.sos.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sos.controller.vo.ResultObject;
import com.sos.entity.FriendRelation;
import com.sos.entity.FriendRequest;
import com.sos.entity.User;
import com.sos.entity.vo.Friend;
import com.sos.enums.DecisionResultEnum;
import com.sos.persistence.FriendRequestService;
import com.sos.persistence.FriendRelationService;
import com.sos.persistence.UserService;
import com.sos.util.CollectionUtils;

/**  
 * <b>功能：</b>FriendRequestController.java<br/>
 * <b>描述：</b> 对功能点的描述<br/>
 * <b>@author： </b>fengmengyue<br/>
 */
@RestController
@RequestMapping(value="freq/*")
public class FriendRequestController extends BaseController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FriendRequestService friendRequestService;
	
	@Autowired
	private FriendRelationService friendRelationService;

	/**
	 * 好友申请(批量)
	 */
	@RequestMapping(value="batch")
	public ResultObject batch(@RequestParam(value="id",required=true)String id,@RequestParam(value="mobiles",required=true) ArrayList<String> mobiles){
		ResultObject ro = new ResultObject();
		User user = userService.findById(id);
		if(user != null){
			//根据手机号码获取己有用户，用于判断目标手机号码是否己注册
			List<User> users = getUsersByMobile(mobiles);
			Set<String> unReg = getUnRegList(users, mobiles);//未注册列表
			mobiles.removeAll(unReg);//排除未注册手机号码
			//获取用户己有好友列表，用于判断好友是否己存在
			FriendRelation friendRelation = friendRelationService.findOne(Query.query(Criteria.where("self.$id").is(user.getId())));
			Set<String> isFriend = getIsFriend(friendRelation,mobiles);
			mobiles.removeAll(isFriend);
			//获取好友申请列表，用于判断是否己提交申请未处理
			List<FriendRequest> friendRequestList = friendRequestService.findList(Query.query(Criteria.where("from.$id").is(user.getId()).and("decisionResult").nin(DecisionResultEnum.AGREE,DecisionResultEnum.OPPOSE)));
			Set<String> hasRequest = getHasRequest(friendRequestList,mobiles);
			mobiles.removeAll(hasRequest);
			if(!mobiles.isEmpty()){
				List<FriendRequest> freqs = new LinkedList<FriendRequest>();
				for(User other : users){
					if(mobiles.contains(other.getMobile())){
						FriendRequest freq = new FriendRequest();
						freq.setFrom(user);
						freq.setTo(other);
						freqs.add(freq);
					}
				}
				if(!freqs.isEmpty()){
					friendRequestService.add(freqs);
				}
				
			}
			Map<String,Object> resultData = new HashMap<String,Object>();
			resultData.put("unReg", unReg);
			resultData.put("isFriend", isFriend);
			resultData.put("send", mobiles);
			resultData.put("hasRequest", hasRequest);
			ro.setData(resultData);
			ro.setMsg("成功发出" + mobiles.size() + "个好友申请");
		}else{
			ro.fail();
			ro.setMsg("用户不存在");
		}
		return ro;
	}
	
	private Set<String> getHasRequest(List<FriendRequest> friendRequestList,ArrayList<String> mobiles) {
		Set<String> result = new HashSet<String>();
		if(friendRequestList != null && !friendRequestList.isEmpty()){
			for(FriendRequest freq : friendRequestList){
				if(mobiles.contains(freq.getTo().getMobile())){
					result.add(freq.getTo().getMobile());
				}
			}
		}
		return result;
	}

	/**
	 * 好友申请(单个)
	 */
	@RequestMapping(value="single")
	public ResultObject single(@RequestParam(value="id",required=true)String id,@RequestParam(value="mobile",required=true)String mobile){
		ResultObject ro = new ResultObject();
		User user = userService.findById(id);
		if(user != null){
			List<User> others = userService.queryByMobile(mobile);
			if(others == null || others.isEmpty()){
				ro.fail();
				ro.setMsg("该号码未注册");
			}else{
				User other = others.get(0);
				List<FriendRelation> friends = friendRelationService.findList(Query.query(Criteria.where("self.$id").is(user.getId()).and("other.$id").is(other.getId())));
				if(friends.isEmpty()){
						List<FriendRequest> friendRequest =  friendRequestService.findList(Query.query(Criteria.where("from.$id").is(user.getId())
									.and("to.$id").is(other.getId())
									.and("decisionResult").nin(DecisionResultEnum.AGREE,DecisionResultEnum.OPPOSE)));
						if(friendRequest == null || friendRequest.isEmpty()){
							FriendRequest freq = new FriendRequest();
							freq.setFrom(user);
							freq.setTo(other);
							friendRequestService.add(freq);
							ro.setMsg("成功发送好友申请");
						}else{
							ro.fail();
							ro.setMsg("请勿重复发送申请");
						}
				}else{
					ro.setMsg("你们已经是好友，不需要重复申请");
				}
			}
		}else{
			ro.fail();
			ro.setMsg("用户不存在");
		}
		return ro;
	}
	
	/**
	 * 同意好友申请
	 */
	@RequestMapping(value="apply")
	public ResultObject apply(String id,String freq,DecisionResultEnum result){
		ResultObject ro = new ResultObject();
		User user = userService.findById(id);
		if(user != null){
			FriendRequest friendRequest = friendRequestService.findById(freq);
			if(friendRequest == null || friendRequest.getDecisionResult() != null){
				ro.fail();
				ro.setMsg("该申请不存在或己处理");
			}else{
				if(user.getId().equals(friendRequest.getTo().getId())){
					if(result == DecisionResultEnum.AGREE){
						friendRelationService.makeFriend(friendRequest.getFrom(), friendRequest.getTo());
					}
					Set<String> fields = CollectionUtils.createSet(String.class, "decisionResult");
					friendRequest.setDecisionResult(result);
					friendRequestService.update(friendRequest, fields);
					ro.setMsg("操作成功");
				}else{
					ro.fail();
					ro.setMsg("非法操作");
				}
			}
		}else{
			ro.fail();
			ro.setMsg("用户不存在");
		}
		return ro;
	}
	
	private Set<String> getIsFriend(FriendRelation friendRelation, List<String> mobiles) {
		Set<String> result = new LinkedHashSet<String>();
		if(friendRelation != null){
			List<Friend> friends = friendRelation.getFriends();
			if(friends != null){
				for(String mobile : mobiles){
					if(mobile != null){
						for(Friend friend : friends){
							if(mobile.equals(friend.getUser().getMobile())){
								result.add(mobile);
							}
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * 从电话号码列表中获取未注册的号码列表
	 */
	private Set<String> getUnRegList(List<User> users,List<String> mobiles){
		Set<String> res = new LinkedHashSet<String>();
		Set<String> existsUserMobiles = new HashSet<String>();
		for(User user : users){
			existsUserMobiles.add(user.getMobile());
		}
		for(String mobile : mobiles){
			if(!existsUserMobiles.contains(mobile)){
				res.add(mobile);
			}
		}
		return res;
	}
	
	private List<User> getUsersByMobile(List<String> mobiles){
		if(mobiles == null || mobiles.isEmpty()){
			return new LinkedList<User>();
		}
		return userService.findList(Query.query(Criteria.where("mobile").in(mobiles)));
	}
}
