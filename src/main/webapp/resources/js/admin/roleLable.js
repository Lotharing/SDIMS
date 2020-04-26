/*
 *onclick创建角色管理显示页面
 */
function getRole() {
	//得到左侧导航题目
	var roleLable = $('#roleManager').text();
	var item = {
		'id' : '2',
		'name' : roleLable,
		'url' : 'role.html',
		'closable' : true
	};
	closableTab.addTab(item);
	//获取后台信息，并展示前台
	getRoleInfoToTable();
}
/*
 *得到角色信息放在table中
 */
function getRoleInfoToTable() {
	//防止数据累加
	$('#table-role').empty();
	//角色信息请求地址
	var roleUrl = "/sdims/admin/listrole";
	$.getJSON(roleUrl, function(data) {
		if (data.success) {
			data.roleList.map(function(item, index) {
				var roleId = $("<td></td>").append(item.roleId);
				var roleName = $("<td></td>").append(item.roleName);
				var roleCreateTime = $("<td></td>").append(new Date(item.createTime).format('Y-m-d H:i:s'));
				var roleDesc = $("<td></td>").append(item.roleDesc);
				$("<tr></tr>").append(roleId).append(roleName).append(
						roleCreateTime).append(roleDesc)
						.appendTo("#table-role");
			});
		}
	});
}
