/**
 * 创建商品管理Lable
 */
function getModifyPassword() {
	var modifyPasswordLable = $('#modifyPasswordManager').text();
	var item = {
			'id' : '16',
			'name' : modifyPasswordLable,
			'url' : 'modifypassword.html',
			'closable' : true
		};
		//调出模态框
		closableTab.addTab(item);
}
//清除文本框
function cleanInput() {
	$('#account').val("");
	$('#password').val("");
	$('#newPassword').val("");
}
//修改密码
function updatePassword() {
	var account = $('#account').val();
	var password = $('#password').val();
	var newPassword = $('#newPassword').val();
	if (account=="" || password=="" || newPassword=="") {
		alert("账户名/密码/新密码必须填写");
	}
	var modifyPassword = "/sdims/user/modifypassword?account="+account+"&password="+password+"&newPassword="+newPassword;
	//商品信息放置select中
	$.getJSON(modifyPassword, function(data) {
		if (data.success) {
			alert("修改成功");
			//返回登录页-注销session
			window.location.href="/sdims"; 
		}else{
			alert("修改失败，请联系管理员"+data.errMsg);
		}
	});
}