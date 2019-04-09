//总记录数，当前页数，用于删除，添加时候，跳转指定页
var totalRecord,UsercurrentPage;
/**
 * 创建商品管理Lable
 */
function getUser() {
	var userLable = $('#userManager').text();
	var item = {
			'id' : '15',
			'name' : userLable,
			'url' : 'user.html',
			'closable' : true
		};
		//调出模态框
		closableTab.addTab(item);
		//加载角色选择框
		getRoleInfoToUserSelect();
		//默认展示第一页
		to_page_user(1);
}
/*
 *角色类型载入选择框
 */
function getRoleInfoToUserSelect() {
	//员工角色信息
	var EmployeeroleUrl = "/sdims/admin/listrole";
	//员工角色信息放置select中
	$.getJSON(EmployeeroleUrl,function(data){
		$('#employeeTypeOfUser').empty();
		$('#employeeTypeOfUser').append($("<option></option>"));
		if (data.success) {
			data.roleList.map(function(item, index){
				var employeeRole = $('<option></option>').append(item.roleName);
				$('#employeeTypeOfUser').append(employeeRole);
			});
		}
	});
}
/**
 * 页面跳转
 * @param pageIndex
 * @returns
 */
function to_page_user(pageIndex,account,roleName) {
	$.ajax({
		url : "/sdims/user/getuserlist",
		dataType : 'json',
		data : {pageIndex:pageIndex,account:account,roleName:roleName},
		tpye : "GET",
		success : function(data) {
			// 1.解析并显示员工数据
			getUserInfoToTables(data);
			// 2.解析并显示分页信息（只用到记录全局属性）
			build_Userpage_info(data);
			// 3.解析显示分页条
			build_Userpage_nav(data);
		}

	});
}
/**
 * 根据url获取员工信息
 * @param employeeUrl
 * @returns
 */
function getUserInfoToTables(data) {
	//展示员工数据（包括条件查询展示）
	$("#table-user").empty();
	data.pageBean.datas.map(function(item, index) {
		//操作-删除按钮
		var delBtn = $("<button></button>").addClass(
				"btn btn-danger btn-xs delete_btn").append(
				$("<span></span>").addClass("glyphicon glyphicon-trash"))
				.append(" ").append("删除");
		//给删除按钮添加一个自定义属性来标识当前删除的员工
		delBtn.attr("del-id", item.userId);
		//存放按钮的TD
		var btnTd = $("<td></td>").append(delBtn);
		//存放员工信息的TD
		var userId = $("<td></td>").append(item.userId);
		var account = $("<td></td>").append(item.account);
		var roleName = $("<td></td>").append(item.roleName);
		var employeeName = $("<td></td>").append(item.employee.name);
		var employeeCode = $("<td></td>").append(item.employee.code);
		var roleDesc = $("<td></td>").append(item.role.roleDesc);
		var userUpdateTime = $("<td></td>").append(new Date(item.updateTime).format('Y-m-d H:i:s'));
		$("<tr></tr>")
		.append(userId)
		.append(account)
		.append(roleName)
		.append(employeeName)
		.append(employeeCode)
		.append(roleDesc)
		.append(userUpdateTime)
		.append(btnTd)
		.appendTo("#table-user");
	});
}
/**
 * 解析显示分页信息
 * @param data
 * @returns
 */
function build_Userpage_info(data) {
	//最大页数
	totalRecord = data.pageBean.sumPages;
	//当前页数
	UsercurrentPage = data.pageBean.curPage;
}
/**
 * 解析显示分页条
 * @param result
 * @returns
 */
function build_Userpage_nav(data) {
	$("#user_page_nav").empty();
	var ul = $("<ul></ul>").addClass("pagination");
	
	var firstPageLi = $("<li></li>").append(
			$("<a></a>").append("首页").attr("href", "#"));
	
	var prePageLi = $("<li></li>").append(
			$("<a></a>").append("&laquo;"));
	//第一页不能往前翻
	if (data.pageBean.curPage == 1) {
		firstPageLi.addClass("disabled");
		prePageLi.addClass("disabled");
	} else {
		//为元素添加点击翻页的事件
		firstPageLi.click(function() {
			to_page_user(1);
		});
		prePageLi.click(function() {
			to_page_user(data.pageBean.curPage - 1);
		});
	}
	var nextPageLi = $("<li></li>").append(
			$("<a></a>").append("&raquo;"));
	
	var lastPageLi = $("<li></li>").append(
			$("<a></a>").append("末页").attr("href", "#"));
	//最后一页不能往下翻
	if (data.pageBean.curPage == data.pageBean.sumPages) {
		nextPageLi.addClass("disabled");
		lastPageLi.addClass("disabled");
	} else {
		nextPageLi.click(function() {
			to_page_user(data.pageBean.curPage + 1);
		});
		lastPageLi.click(function() {
			to_page_user(data.pageBean.sumPages);
		});
	}
	//添加首页和前一页 的提示
	ul.append(firstPageLi).append(prePageLi);
	$.each(data.pageBean.navigatepageNums, function(index,
			item) {
		var numLi = $("<li></li>").append($("<a></a>").append(item));
		if (data.pageBean.curPage == item) {
			numLi.addClass("active");
		}
		numLi.click(function() {
			to_page_user(item);
		});
		ul.append(numLi);
	});
	//添加下一页和末页 的提示
	ul.append(nextPageLi).append(lastPageLi);
	var navEle = $("<nav></nav>").append(ul);
	navEle.appendTo("#user_page_nav");
}
/**
 * 账户/角色类型查询
 * @returns
 */
function searchUser() {
	//检索账户信息
	var account = $('#employeeAccount').val();
	//检索角色
	var roleName = $('#employeeTypeOfUser').val();
	//1页，名称条件查询
	to_page_user(1,account,roleName);
}
/**
 * 标签页添加按钮点击事件（获取角色信息）
 * @returns
 */
function addUser() {
	$('#UserModalLabel').text("添加信息");
	$('.modal-dialog').css({"margin":"200px auto"});
	$('.modal-header').css({"background-color":"#449D44"});
	//加载员工信息
	getEmployeeInfo();
	//加载角色类型
	getUserRoleType();
	$('#userModal').modal();
}
/**
 * 绑定员工信息
 * @returns
 */
function getEmployeeInfo() {
	//员工信息
	var EmployeeInfoUrlSelect = "/sdims/admin/getemployeelist?pageIndex="+1;
	//员工信息放置Dialog的select中
	$.getJSON(EmployeeInfoUrlSelect,function(data){
		//防止模态框下拉数据累加
		$('#employeeInfoInput').empty();
		$('#employeeInfoInput').append($("<option></option>"));
		if (data.success) {
			data.pageBean.datas.map(function(item, index){
				//员工ID和Name都绑定在option上
				var employeeNameInfo = $("<option value="+item.employeeId+">"+item.name+"</option>");
				$('#employeeInfoInput').append(employeeNameInfo);
			});
		}
	});
}
/**
 * 绑定用户角色
 * @returns
 */
function getUserRoleType() {
	//员工角色信息
	var EmployeeRoleUrlSelect = "/sdims/admin/listrole";
	//员工角色信息放置Dialog的select中
	$.getJSON(EmployeeRoleUrlSelect,function(data){
		//防止模态框下拉数据累加
		$('#roleTypeInput').empty();
		$('#roleTypeInput').append($("<option></option>"));
		if (data.success) {
			data.roleList.map(function(item, index){
				//角色的ID和Name都绑定在option上
				var employeeRole = $("<option value="+item.roleId+">"+item.roleName+"</option>");
				$('#roleTypeInput').append(employeeRole);
			});
		}
	});
}