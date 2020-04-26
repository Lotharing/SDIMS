//总记录数，当前页数，用于删除，添加时候，跳转指定页
var EmptotalRecord,EMpcurrentPage;
/*
 *onclick创建员工管理显示页面
 */
function getEmployee() {
	var employeeLable = $('#employeeManager').text();
	var item = {
			'id' : '3',
			'name' : employeeLable,
			'url' : 'employee.html',
			'closable' : true
		};
	closableTab.addTab(item);
	//加载角色类型选择框
	getRoleInfoToSelect();
	//默认展示第一页
	to_page_emp(1);
}
/*
 *角色类型载入选择框
 */
function getRoleInfoToSelect() {
	//员工角色信息
	var EmployeeroleUrl = "/sdims/admin/listrole";
	//员工角色信息放置select中
	$.getJSON(EmployeeroleUrl,function(data){
		$('#employeeType').empty();
		$('#employeeType').append($("<option></option>"));
		if (data.success) {
			data.roleList.map(function(item, index){
				var employeeRole = $('<option></option>').append(item.roleName);
				$('#employeeType').append(employeeRole);
			});
		}
	})
	
}
/**
 * 页面跳转
 * @param pageIndex
 * @returns
 */
function to_page_emp(pageIndex,employeeName,employeeType) {
	$.ajax({
		url : "/sdims/admin/getemployeelist",
		dataType : 'json',
		data : {pageIndex:pageIndex,employeeName:employeeName,employeeType:employeeType},
		tpye : "GET",
		success : function(data) {
			// 1.解析并显示员工数据
			getEmployeeInfoToTables(data);
			// 2.解析并显示分页信息（只用到记录全局属性）
			build_Emppage_info(data);
			// 3.解析显示分页条
			build_Emppage_nav(data);
		}

	})
}
/**
 * 根据url获取员工信息
 * @param employeeUrl
 * @returns
 */
function getEmployeeInfoToTables(data) {
	//展示员工数据（包括条件查询展示）
	$("#table-employee").empty();
	data.pageBean.datas.map(function(item, index) {
		//操作-编辑按钮
		var editBtn = $("<button></button>").addClass(
				"btn btn-primary btn-sm edit_btn").append(
				$("<span></span>").addClass("glyphicon glyphicon-pencil"))
				.append(" ").append("编辑");
		//给编辑按钮添加一个自定义属性来标识当前编辑的员工
		editBtn.attr("edit-id", item.employeeId);
		//操作-删除按钮
		var delBtn = $("<button></button>").addClass(
				"btn btn-danger btn-sm delete_btn").append(
				$("<span></span>").addClass("glyphicon glyphicon-trash"))
				.append(" ").append("删除");
		//给删除按钮添加一个自定义属性来标识当前删除的员工
		delBtn.attr("del-id", item.employeeId);
		//存放按钮的TD
		var btnTd = $("<td></td>").append(editBtn).append(" ").append(delBtn);
		//存放员工信息的TD
		var employeeId = $("<td></td>").append(item.employeeId);
		var employeeName = $("<td></td>").append(item.name);
		var employeeCode = $("<td></td>").append(item.code);
		var employeeIdCard = $("<td></td>").append(item.idCard);
		var employeeMobile = $("<td></td>").append(item.mobile);
		var employeeSex = $("<td></td>").append(item.sex == 1 ? "男" : "女");
		var employeeAddress = $("<td></td>").append(item.address);
		var employeeEmail = $("<td></td>").append(item.email);
		var employeeType = $("<td></td>").append(item.type);
		var employeeUpdateTime = $("<td></td>").append(
				new Date(item.updateTime).format('Y-m-d H:i:s'));
		$("<tr></tr>").append(employeeId).append(employeeName).append(
				employeeCode).append(employeeIdCard).append(employeeMobile)
				.append(employeeSex).append(employeeAddress).append(
						employeeEmail).append(employeeType).append(
						employeeUpdateTime).append(btnTd).appendTo(
						"#table-employee");
	});
}
/**
 * 解析显示分页信息
 * @param data
 * @returns
 */
function build_Emppage_info(data) {
	//最大页数
	EmptotalRecord = data.pageBean.sumPages;
	//当前页数
	EMpcurrentPage = data.pageBean.curPage;
}
/**
 * 解析显示分页条
 * @param result
 * @returns
 */
function build_Emppage_nav(data) {
	$("#Emp_page_nav_area").empty();
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
			to_page_emp(1);
		});
		prePageLi.click(function() {
			to_page_emp(data.pageBean.curPage - 1);
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
			to_page_emp(data.pageBean.curPage + 1);
		});
		lastPageLi.click(function() {
			to_page_emp(data.pageBean.sumPages);
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
			to_page_emp(item);
		});
		ul.append(numLi);
	});
	//添加下一页和末页 的提示
	ul.append(nextPageLi).append(lastPageLi);
	var navEle = $("<nav></nav>").append(ul);
	navEle.appendTo("#Emp_page_nav_area");
}
/**
 * 仓库名称/角色类型查询
 * @returns
 */
function searchEmployee() {
	//检索姓名
	var employeeName = $('#employeeName').val();
	//检索角色
	var employeeType = $('#employeeType').val();
	//1页，名称条件查询
	to_page_emp(1,employeeName,employeeType);
}
/**
 * 标签页添加按钮点击事件（获取角色信息）
 * @returns
 */
function addEmployee() {
	//员工角色信息
	var EmployeeroleUrlDialog = "/sdims/admin/listrole";
	$('#myModalLabel').text("添加信息");
	$('.modal-dialog').css({"margin":"120px auto"});
	$('.modal-header').css({"background-color":"#449D44"});
	//员工角色信息放置Dialog的select中
	$.getJSON(EmployeeroleUrlDialog,function(data){
		//防止模态框下拉数据累加
		$('#employeeTypeDialog').empty();
		$('#employeeTypeDialog').append($("<option></option>"));
		if (data.success) {
			data.roleList.map(function(item, index){
				var employeeRole = $('<option></option>').append(item.roleName);
				$('#employeeTypeDialog').append(employeeRole);
			});
		}
	});
	$('#myModal').modal();
}
/**
 * 创建按钮的时候绑定点击事件(删除按钮)-根据ID删除员工并刷新当前页
 * @returns
 */
$(document).on("click",".delete_btn",function(){
//	var tr = $(this).parent().parent();
	var employeeId = $(this).attr("del-id");
	var delEmployeeUrl = "/sdims/admin/removeemployee?employeeId="+employeeId;
	$.getJSON(delEmployeeUrl,function(data){
		if (data.success) {
			alert(data.successMsg);
			//删除成功，返回当前页
			to_page_emp(EMpcurrentPage);
		}else {
			alert(data.errMsg);
		}
	});
});
/**
 * 创建按钮的时候绑定点击事件(编辑按钮)-把数据放进模态框并展示
 * @returns
 */
$(document).on("click",".edit_btn",function(){
	var employeeEditId = $(this).attr("edit-id");
	var EmployeeInfoUrl = "/sdims/admin/getemployeebyid?employeeId="+employeeEditId;
	$('#EditModalLabel').text("编辑信息");
	$('.modal-dialog').css({"margin":"120px auto"});
	$('.modal-header').css({"background-color":"#449D44"});
	//根据ID获取的信息填充在模态框
	$.getJSON(EmployeeInfoUrl,function(data){
		$('#EditemployeeTypeDialog').append($("<option></option>"));
		if (data.success) {
			$('#EditemployeeNameInput').val(data.employee.name);
			$('#EditemployeeCode').val(data.employee.code);
			$('#EditemployeeIdCard').val(data.employee.idCard);
			$('#EditemployeeMobile').val(data.employee.mobile);
			$('#EditemployeeSexDialog').empty();
			$('#EditemployeeSexDialog').append(data.employee.sex=='1'?"<option>男</option>":"<option>女</option>");
			$('#EditemployeeAddress').val(data.employee.address);
			$('#EditemployeeEmail').val(data.employee.email);
			$('#EditemployeeId').val(employeeEditId);
		}
	});
	$('#EditModal').modal();
});