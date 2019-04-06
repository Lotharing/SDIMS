//总记录数，当前页数，用于删除，添加时候，跳转指定页
var totalRecord,currentPage;
/**
 * 创建仓库管理Lable
 */
function getRepo() {
	var repoLable = $('#repoManager').text();
	var item = {
			'id' : '4',
			'name' : repoLable,
			'url' : 'repo.html',
			'closable' : true
		};
		//调出模态框
		closableTab.addTab(item);
		//默认展示第一页
		to_page(1);
}
/**
 * 页面跳转
 * @param pageIndex
 * @returns
 */
function to_page(pageIndex,repositoryName) {
	$.ajax({
		url : "/sdims/admin/getrepositorylist",
		dataType : 'json',
		data : {pageIndex:pageIndex,repositoryName:repositoryName},
//		data : "pageIndex=" + pageIndex,
		tpye : "GET",
		success : function(data) {
			// 1.解析并显示员工数据
			getRepoInfoToTables(data);
			// 2.解析并显示分页信息（只用到记录全局属性）
			build_page_info(data);
			// 3.解析显示分页条
			build_page_nav(data);
		}

	})
}
/**
 * 解析显示分页信息
 * @param data
 * @returns
 */
function build_page_info(data) {
//	$("#page_info_area").empty();
//	$("#page_info_area").append("当前第" + data.pageBean.curPage+ "页,总共"+ data.pageBean.sumPages + "页,总共"+ data.pageBean.allRowCounts + "条记录");
	//最大页数
	totalRecord = data.pageBean.sumPages;
	//当前页数
	currentPage = data.pageBean.curPage;
}
/**
 * 解析显示分页条
 * @param result
 * @returns
 */
function build_page_nav(data) {
	$("#page_nav_area").empty();
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
			to_page(1);
		});
		prePageLi.click(function() {
			to_page(data.pageBean.curPage - 1);
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
			to_page(data.pageBean.curPage + 1);
		});
		lastPageLi.click(function() {
			to_page(data.pageBean.sumPages);
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
			to_page(item);
		});
		ul.append(numLi);
	});
	//添加下一页和末页 的提示
	ul.append(nextPageLi).append(lastPageLi);
	var navEle = $("<nav></nav>").append(ul);
	navEle.appendTo("#page_nav_area");
}
/**
 * 得到请求的信息并展示前台
 * @param data
 * @returns
 */
function getRepoInfoToTables(data) {
	$("#table-repo").empty();
	data.pageBean.datas.map(function(item, index) {
		//操作-编辑按钮
		var editBtn = $("<button></button>").addClass(
				"btn btn-primary btn-sm edit_repo_btn").append(
				$("<span></span>")
						.addClass("glyphicon glyphicon-pencil")).append(" ").append("编辑");
		//给编辑按钮添加一个自定义属性来标识当前编辑的员工
		editBtn.attr("edit-id", item.repoId);
		//操作-删除按钮
		var delBtn = $("<button></button>").addClass(
				"btn btn-danger btn-sm delete_repo_btn").append(
				$("<span></span>")
						.addClass("glyphicon glyphicon-trash")).append(" ").append("删除");
		//给删除按钮添加一个自定义属性来标识当前删除的员工
		delBtn.attr("del-id", item.repoId);
		//存放按钮的TD
		var btnTd = $("<td></td>").append(editBtn).append(" ").append(delBtn);
		var id = $("<td></td>").append(item.repoId);
		var name = $("<td></td>").append(item.name);
		var code = $("<td></td>").append(item.code);
		var address = $("<td></td>").append(item.address);
		var desc = $("<td></td>").append(item.repoDesc);
		var updater = $("<td></td>").append(item.updater);
		var updateTime = $("<td></td>").append(new Date(item.updateTime).format('Y-m-d H:i:s'));
		var employeeName = $("<td></td>").append(item.employee.name);
		$("<tr></tr>")
		.append(id)
		.append(name)
		.append(code)
		.append(address)
		.append(desc)
		.append(updater)
		.append(updateTime)
		.append(employeeName)
		.append(btnTd)
		.appendTo("#table-repo");
	});
}
/**
 * 仓库名称查询
 * @returns
 */
function searchRepo() {
	var repoName = $('#repoName').val();
	//1页，名称条件查询
	to_page(1,repoName);
}
/**
 * 添加模态框
 * @returns
 */
function addRepo() {
	$('#repoModalLabel').text("添加信息");
	$('.modal-dialog').css({"margin":"120px auto"});
	$('.modal-header').css({"background-color":"#449D44"});
	$('#repoModal').modal();
}
/**
 * 删除指定仓库
 * @returns
 */
$(document).on("click",".delete_repo_btn",function(){
	var repoId = $(this).attr("del-id");
	var delRepoUrl = "/sdims/admin/removerepository?repoId="+repoId;
	$.getJSON(delRepoUrl,function(data){
		if (data.success) {
			alert(data.successMsg);
			//返回当前页
			to_page(currentPage);
		}else {
			alert(data.errMsg);
		}
	});
});
/**
 * 创建按钮的时候绑定点击事件(编辑按钮)
 * @returns
 */
$(document).on("click",".edit_repo_btn",function(){
	var repoId = $(this).attr("edit-id");
	var repoInfoUrl = "/sdims/admin/getrepositorybyid?repoId="+repoId;
	$('#EditrepoModalLabel').text("编辑信息");
	$('.modal-dialog').css({"margin":"120px auto"});
	$('.modal-header').css({"background-color":"#449D44"});
	//根据ID获取的信息填充在模态框
	$.getJSON(repoInfoUrl,function(data){
		if (data.success) {
			$('#EditrepoNameInput').val(data.repository.name);
			$('#EditrepoCode').val(data.repository.code);
			$('#EditrepoAddress').val(data.repository.address);
			$('#EditrepoDesc').val(data.repository.repoDesc);
			$('#EditrepoUpdater').val(data.repository.updater);
			$('#EditrepoMan').val(data.repository.employee.employeeId);
			$('#EditRepoId').val(data.repository.repoId);
		}
	});
	$('#EditrepoModal').modal();
});