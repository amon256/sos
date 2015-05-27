<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="bootstrap/3.3.4/css/bootstrap.min.css" rel="stylesheet">
<link href="bootstrap/3.3.4/css/bootstrap-theme.min.css" rel="stylesheet">
<script type="text/javascript" src="jquery/jquery.min.js"></script>
<script type="text/javascript" src="bootstrap/3.3.4/js/bootstrap.min.js"></script>
<title>HTML5</title>
<script type="text/javascript">
	var user = null;
	var mobilePattern = /^1[0-9]{10}$/;
	function inputButtonTimeout(btnId,timeout,msg,text){
		if(timeout < 0){
			$('#' + btnId).removeAttr('disabled').val(text);
		}else{
			$('#' + btnId).val(timeout + '秒后' + msg);
			timeout--;
			setTimeout("inputButtonTimeout('" + btnId +"'," + timeout +",'" + msg +"','" + text +"')",1000);
		}		
	}
	
	function changePanel(from,to,fn){
		$('#' + from).slideUp(100,'linear',function(){
			$('#' + to).slideDown(200,'linear',fn);
		});
	}
</script>
</head>
<body>
	<!-- 登录框 -->
	<div id="loginPanel" class="panel panel-default">
		<div class="panel-heading">手机号码登录</div>
		<div class="panel-body">
			<form id="loginForm" class="form-horizontal" onsubmit="return false">
				<div class="form-group">
					<label for="inputMobile" class="col-sm-2 control-label sr-only">手机号码：</label>
					<div class="col-sm-6">
						<input type="text" class="form-control" id="inputMobile" value="15019491687" placeholder="请输入您的手机号码">
					</div>
				</div>
				<div class="form-group">
					<label for="inputMobile" class="col-sm-2 control-label sr-only">验证码：</label>
					<div class="col-sm-4">
						<input type="text" class="form-control" id="inputCaptcha" placeholder="请输入您接收到的验证码">
					</div>
					<div class="col-sm-2 ">
						<input type="button" id="btn_getCaptcha" class="btn btn-default" value="获取验证码"/>
					</div>
				</div>
				<div class="form-group">
					<label for="inputMobile" class="col-sm-2 control-label sr-only">手机号码：</label>
					<div class="col-sm-6">
						<input type="button" id="btn_sign" class="btn btn-default" style="width:100%" value="登录"/>
					</div>
				</div>
			</form>
		</div>
		<script type="text/javascript">
			$(function(){
				$('#btn_getCaptcha').click(function(){
					var mobile = $('#inputMobile').val();
					if(mobile){
						if(mobilePattern.test(mobile)){
							$.ajax({
								url : 'captcha/sendSms',
								data : {mobile : mobile},
								type : 'post',
								dataType : 'json',
								beforeSend : function(){
									$('#btn_getCaptcha').val('60秒后重发').attr('disabled','disabled');
									inputButtonTimeout('btn_getCaptcha', 60, '重发', '重新获取验证码');
									return true;
								},
								success : function(json){
									if(json.status === true){
										
									}else{
										alert(json.msg);
									}
								},
								error : function(e){
									alert('服务器异常');
								}
							});
						}else{
							alert("手机号码不正确");
						}
					}else{
						alert("请输入您的手机号码");
					}
				});
				
				$('#btn_sign').click(function(){
					var mobile = $('#inputMobile').val();
					var captcha = $('#inputCaptcha').val();
					if(mobile == null || mobile.trim() == ''){
						alert("请输入您的手机号码");
						return;
					}
					if(!mobilePattern.test(mobile)){
						alert("手机号码格式不正确");
						return ;
					}
					if(captcha && /^[0-9]{4}$/.test(captcha)){
						$.ajax({
							url : 'user/login',
							data : {mobile : mobile,captcha : captcha},
							type : 'post',
							dataType : 'json',
							beforeSend : function(){
								$('#btn_sign').attr('disabled','disabled');
								return true;
							},
							success : function(json){
								if(json.status === true){
									updateMainUser(json.data);
									changePanel('loginPanel','mainPanel');
								}else{
									alert(json.msg);
									$('#btn_sign').removeAttr('disabled');
								}
							},
							error : function(e){
								$('#btn_sign').removeAttr('disabled');
								alert('服务器异常');
							}
						});
					}else{
						alert("请输入完整验证码");
					}
				});
			});
		</script>
	</div>
	
	<!-- 登录成功首页 -->
	<div id="mainPanel" class="panel panel-default" style="display: none;">
		<div class="panel-heading"><span data="nickName"></span></div>
		<div class="panel-body">
			<ul class="list-group">
			  <li class="list-group-item row"><label class="col-sm-2">昵称:</label><span class="col-sm-10" ><a href="javascript:editUser();" data="nickName"></a></span></li>
			  <li class="list-group-item row"><label class="col-sm-2">手机:</label><span class="col-sm-10" data="mobile"></span></li>
			  <li class="list-group-item row"><label class="col-sm-2">Q&nbsp;Q:</label><span class="col-sm-10" data="qq"></span></li>
			  <li class="list-group-item row"><label class="col-sm-2">邮箱:</label><span class="col-sm-10" data="email"></span></li>
			  <li class="list-group-item row"><label class="col-sm-2">性别:</label><span class="col-sm-10" data="sex" options="{'MAN' : '男' , 'WOMAN' : '女'}"></span></li>
			  <li class="list-group-item row"><label class="col-sm-2">好友列表:</label><span class="col-sm-10"><a href="javascript:showFriendList();">进入</a></span></li>
			  <li class="list-group-item row"><label class="col-sm-2">消息列表:</label><span class="col-sm-10"><a href="javascript:showMessageList();">进入</a></span></li>
			</ul>
		</div>
		<script type="text/javascript">
			function updateMainUser(data){
				user = data;
				$('#mainPanel').find('[data]').each(function(){
					var key = $(this).attr('data');
					if(user[key]){
						var value = user[key];
						if($(this).attr('options')){
							var o = eval('(' + $(this).attr('options') + ')' );
							value = o[value] || value;
						}
						$(this).html(value);
					}
				});
			}
			function editUser(){
				$('#editForm').find('input,select').each(function(){
					var name = $(this).attr('name');
					if(user[name]){
						var value = user[name];
						$(this).val(value);
					}
				});
				changePanel('mainPanel','editPanel');
			}
			
			function showFriendList(){
				loadFriendList(function(){
					changePanel('mainPanel', 'friendPanel');
				});
			}
			
			function showMessageList(){
				changePanel('mainPanel', 'messagePanel', loadMessage);
			}
		</script>
	</div>
	
	<!-- 修改个人信息 -->
	<div id="editPanel" class="panel panel-default" style="display: none;">
		<div class="panel-heading"><a href="javascript:changePanel('editPanel','mainPanel');" class="btn btn-primary btn-xs">返回</a>&nbsp;&nbsp;<label>个人信息修改</label></div>
		<div class="panel-body">
			<form id="editForm" onsubmit="return false" class="form-horizontal" >
				<div class="form-group">
					<label for="nickName" class="col-sm-2 control-label">昵称：</label>
					<div class="col-sm-6">
						<input type="text" class="form-control" name="nickName" id="nickName" value="" placeholder="昵称">
					</div>
				</div>
				<div class="form-group">
					<label for="qq" class="col-sm-2 control-label">QQ：</label>
					<div class="col-sm-6">
						<input type="text" class="form-control" name="qq" id="qq" value="" placeholder="昵称">
					</div>
				</div>
				<div class="form-group">
					<label for="email" class="col-sm-2 control-label">邮箱：</label>
					<div class="col-sm-6">
						<input type="email" class="form-control" name="email" id="email" value="" placeholder="昵称">
					</div>
				</div>
				<div class="form-group">
					<label for="sex" class="col-sm-2 control-label">性别：</label>
					<div class="col-sm-6">
						<select name="sex" id="sex" class="form-control">
							<option value="MAN">男</option>
							<option value="WOMAN">女</option>
						</select>
					</div>
				</div>
				<div class="form-group">
						<label for="btn_saveuser" class="col-sm-2 control-label sr-only">保存：</label>
						<div class="col-sm-6">
							<input type="button" id="btn_saveuser" class="btn btn-default" style="width:100%" value="保存"/>
						</div>
					</div>
			</form>
		</div>
		<script type="text/javascript">
			$(function(){
				$('#btn_saveuser').click(function(){
					var u = {id:user.id};
					$('#editForm').find('input,select').each(function(){
						var name = $(this).attr('name');
						u[name] = $(this).val();
					});
					$.post('user/update',u,function(json){
						if(json.status === true){
							alert("保存成功");
							updateMainUser(json.data);
							changePanel('editPanel', 'mainPanel');
						}else{
							alert(json.msg);
						}
					});
				});
			});
		</script>
	</div>
	
	<!-- 消息列表 -->
	<div id="messagePanel" class="panel panel-default" style="display: none;">
		<div class="panel-heading">
			<a href="javascript:changePanel('messagePanel','mainPanel');" class="btn btn-primary btn-xs">返回</a>&nbsp;&nbsp;
			<span>消息列表</span>
		</div>
		<div class="panel-body">
		</div>
		<script type="text/javascript">
			function loadMessage(){
				$.post('message/list',{id : user.id},function(json){
					if(json.status === true){
						var msgContainer = $('#messagePanel').find('.panel-body');
						msgContainer.empty();
						var messes = json.data;
						if(messes && messes.length > 0){
							for(var i = 0; i < messes.length; i++){
								var div = $('div[messageTemplate]').clone();
								div.removeAttr('messageTemplate');
								div.attr('id',messes[i].id);
								div.find('[data=title]').html(messes[i].title);
								var content = messes[i].content;
								content += '<div style="float : right;">';
								if(messes[i].type == 'FRIEND_REQ' && messes[i].status != 'HASDEAL'){
									content += '&nbsp;<button type="button" class="btn btn-success btn-xs" onclick="applyMessage(\''+messes[i].id+'\',\'AGREE\')">同意</button>&nbsp;<button type="button" class="btn btn-warning btn-xs" onclick="applyMessage(\''+messes[i].id+'\',\'OPPOSE\')">拒绝</button>';
								}
								content += '&nbsp;<button type="button" class="btn btn-default btn-xs" onclick="removeMessage(\''+messes[i].id+'\')">删除</button></div>';
								div.find('[data=content]').html(content);
								div.show();
								div.appendTo(msgContainer);
							}
						}
					}else{
						alert('加载消息列表失败');
					}
				});
			}
			
			function applyMessage(messageId,result){
				$('#' + messageId).find('button:lt(2)').attr('disabled','disabled');
				$.post('message/apply',{id : user.id, messageId : messageId,decision : result},function(json){
					alert("处理成功");
					$('#' + messageId).find('button:lt(2)').remove();
				});
			}
			
			function removeMessage(messageId){
				$.post('message/remove',{id : user.id, messageIds : messageId},function(json){
					$('#' + messageId).remove();
				});
			}
		</script>
		<div messageTemplate="true" class="panel panel-info" style="display:none;">
			<div class="panel-heading"><span data="title"></span></div>
			<div class="panel-body" data="content">
			</div>
		</div>
	</div>
	
	
	<!-- 好友列表 -->
	<div id="friendPanel" class="panel panel-default" style="display: none;">
		<div class="panel-heading">
			<a href="javascript:changePanel('friendPanel','mainPanel');" class="btn btn-primary btn-xs">返回</a>&nbsp;&nbsp;
			<span>好友列表</span>
			<span style="float: right;"><a href="javascript:changePanel('friendPanel','addFriendPanel')" class="btn btn-default btn-xs">添加</a></span>
		</div>
		<div class="panel-body">
			<ul class="list-group">
			  <li class="list-group-item row"><label class="col-sm-2">昵称:</label><span class="col-sm-10" ><a href="javascript:editUser();" data="nickName"></a></span></li>
			</ul>
		</div>
		<script type="text/javascript">
			function loadFriendList(fn){
				$.post('friend/list',{id:user.id},function(json){
					if(json.status === true){
						var friends = json.data;
						if(friends && friends.length > 0){
							var ctn = $('#friendPanel').find('.list-group');
							ctn.empty();
							for(var i = 0; i < friends.length; i++){
								var fr = friends[i];
								var nickName = fr.descName || fr.user.nickName || fr.mobile;
								var emergencyContact = fr.emergencyContact?'紧':'';
								$('<li class="list-group-item row"><label class="col-sm-2"><a href="javascript:void(0)">'+nickName+'</a></label><span class="col-sm-10" >'+fr.user.mobile+'</span><span>'+emergencyContact+'</span></li>')
									.appendTo(ctn)
									.find('a').click(function(){
										loadFiendInfo(fr);
										changePanel('friendPanel', 'friendInfoPanel');
									});
							}
						}
						if(fn && fn instanceof Function){
							fn.apply();
						}
					}else{
						alert(json.msg);
					}
				});
			}
		</script>
	</div>
	
	<!-- 好友信息 -->
	<div id="friendInfoPanel" class="panel panel-default" style="display: none;">
		<div class="panel-heading"><a href="javascript:changePanel('friendInfoPanel','friendPanel',loadFriendList);" class="btn btn-primary btn-xs">返回</a>&nbsp;&nbsp;<label data="nickName">好友信息</label></div>
		<div class="panel-body">
			<form id="friendEditForm" onsubmit="return false">
				<input type="hidden" name="friendId" id="friendId"/>
				<ul class="list-group">
				  <li class="list-group-item row"><label class="col-sm-2">昵称:</label><span class="col-sm-10" data="nickName"></span></li>
				  <li class="list-group-item row"><label class="col-sm-2">备注:</label><span class="col-sm-10"><input type="text" name="descName" style="width: 100px;" id="descName" /></span></li>
				  <li class="list-group-item row"><label class="col-sm-2">紧急联系人:</label><span class="col-sm-10"><input type="checkbox" name="emergencyContact" id="emergencyContact" /></span></li>
				  <li class="list-group-item row"><label class="col-sm-2">手机:</label><span class="col-sm-10" data="mobile"></span></li>
				  <li class="list-group-item row"><label class="col-sm-2">Q&nbsp;Q:</label><span class="col-sm-10" data="qq"></span></li>
				  <li class="list-group-item row"><label class="col-sm-2">邮箱:</label><span class="col-sm-10" data="email"></span></li>
				  <li class="list-group-item row"><label class="col-sm-2">性别:</label><span class="col-sm-10" data="sex" options="{'MAN' : '男' , 'WOMAN' : '女'}"></span></li>
				  <li class="list-group-item row"><input type="button" id="btn_savefriend" class="btn btn-default" style="width:100%" value="保存"/></li>
				</ul>
			</form>
		</div>
		<script type="text/javascript">
			function loadFiendInfo(friend){
				var u = friend.user;
				$('#friendInfoPanel').find('[data]').each(function(){
					var key = $(this).attr('data');
					if(u[key]){
						var value = u[key];
						if($(this).attr('options')){
							var o = eval('(' + $(this).attr('options') + ')' );
							value = o[value] || value;
						}
						$(this).html(value);
					}
				});
				$('#friendInfoPanel').find('#friendId').val(friend.user.id);
				$('#friendInfoPanel').find('#descName').val(friend.descName);
				if(friend.emergencyContact){
					$('#friendInfoPanel').find('#emergencyContact').attr('checked','checked');
				}
			}
			$(function(){
				$('#btn_savefriend').click(function(){
					var friendId = $('#friendInfoPanel').find('#friendId').val();
					var descName = $('#friendInfoPanel').find('#descName').val();
					var emergencyContact = $('#friendInfoPanel').find('#emergencyContact:checked').length > 0;
					$.post('friend/updateFriend',{id : user.id,descName : descName,friendId : friendId,emergencyContact : emergencyContact},function(json){
						if(json.status === true){
							alert('保存成功');
						}else{
							alert(json.msg);
						}
					});
				});
			});
		</script>
	</div>
	
	<!-- 手动添加好友 -->
	<div id="addFriendPanel" class="panel panel-default" style="display: none;">
		<div class="panel-heading">
			<a href="javascript:changePanel('addFriendPanel','friendPanel');" class="btn btn-primary btn-xs">返回</a>&nbsp;&nbsp;
			<span>添加好友</span>
		</div>
		<div class="panel-body">
			<form id="addFiendForm" onsubmit="return false" class="form-horizontal" >
				<div class="form-group">
					<label for="addMobile" class="col-sm-2 control-label src-only">手机：</label>
					<div class="col-sm-6">
						<input type="text" id="addMobile" class="form-control" name="mobile" value="" placeholder="手机号码">
					</div>
				</div>
				<div class="form-group">
					<label for="btn_addFriend" class="col-sm-2 control-label sr-only"></label>
					<div class="col-sm-6">
						<input type="button" id="btn_addFriend" class="btn btn-default" style="width:100%" value="请求加为好友"/>
					</div>
				</div>
				<div class="form-group">
					<label for="btn_addFriend" class="col-sm-2 control-label sr-only"></label>
					<div class="col-sm-6">
						<input type="button" id="btn_addFriendBatch" class="btn btn-primary" style="width:100%" value="从通讯录导入"/>
					</div>
				</div>
			</form>
		</div>
		<script type="text/javascript">
			$(function(){
				$('#btn_addFriend').click(function(){
					var mobile = $('#addMobile').val();
					if(mobile){
						if(mobile == user.mobile){
							alert('不能加自己为好友');
						}else if(!mobilePattern.test(mobile)){
							alert('手机号码格式不正确');
						}else{
							$.post('freq/single',{id : user.id,mobile : mobile},function(json){
								if(json.status === true){
									$('#addMobile').val('');
									alert('发送申请成功');
								}else{
									alert(json.msg);
								}
							});
						}
					}else{
						alert('请输入手机号码');
					}
				});
			});
		</script>
	</div>
	
	<!-- 电话本列表 -->
	<div id="bookPanel" class="panel panel-default" style="display: none;">
	</div>
</body>
</html>