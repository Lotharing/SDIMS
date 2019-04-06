//总记录数，当前页数，用于删除，添加时候，跳转指定页
var totalRecord,currentPage;
/**
 * 创建仓库管理Lable
 */
function getCustomer() {
	var saleCustomerLable = $('#customerManager').text();
	var item = {
			'id' : '8',
			'name' : saleCustomerLable,
			'url' : 'salecustomer.html',
			'closable' : true
		};
		//调出模态框
		closableTab.addTab(item);
		//默认展示第一页
		to_page_customer(1);
}
/**
 * 页面跳转
 * @param pageIndex
 * @returns
 */
function to_page_customer(pageIndex,customerName) {
	$.ajax({
		url : "/sdims/sale/getsalecustomerlist",
		dataType : 'json',
		data : {pageIndex:pageIndex,customerName:customerName},
		tpye : "GET",
		success : function(data) {
			// 1.解析并显示员工数据
			getCustomerInfoToTables(data);
			// 2.解析并显示分页信息（只用到记录全局属性）
			build_Customerpage_info(data);
			// 3.解析显示分页条
			build_Customerpage_nav(data);
		}

	})
}
/**
 * 得到请求的信息并展示前台
 * @param data
 * @returns
 */
function getCustomerInfoToTables(data) {
	$("#table-customer").empty();
	data.pageBean.datas.map(function(item, index) {
		//操作-编辑按钮
		var editBtn = $("<button></button>").addClass(
				"btn btn-primary btn-sm edit_customer_btn").append(
				$("<span></span>")
						.addClass("glyphicon glyphicon-pencil")).append(" ").append("编辑");
		//给编辑按钮添加一个自定义属性来标识当前编辑的员工
		editBtn.attr("edit-id", item.customerId);
		//操作-删除按钮
		var delBtn = $("<button></button>").addClass(
				"btn btn-danger btn-sm delete_customer_btn").append(
				$("<span></span>")
						.addClass("glyphicon glyphicon-trash")).append(" ").append("删除");
		//给删除按钮添加一个自定义属性来标识当前删除的员工
		delBtn.attr("del-id", item.customerId);
		//存放按钮的TD
		var btnTd = $("<td></td>").append(editBtn).append(" ").append(delBtn);
		var id = $("<td></td>").append(item.customerId);
		var name = $("<td></td>").append(item.name);
		var linkName = $("<td></td>").append(item.linkName);
		var mobile = $("<td></td>").append(item.mobile);
		var address = $("<td></td>").append(item.address);
		var customerDesc = $("<td></td>").append(item.customerDesc);
		var updater = $("<td></td>").append(item.updater);
		var updateTime = $("<td></td>").append(new Date(item.updateTime).format('Y-m-d H:i:s'));
		$("<tr></tr>")
		.append(id)
		.append(name)
		.append(linkName)
		.append(mobile)
		.append(address)
		.append(customerDesc)
		.append(updater)
		.append(updateTime)
		.append(btnTd)
		.appendTo("#table-customer");
	});
}
/**
 * 解析显示分页信息
 * @param data
 * @returns
 */
function build_Customerpage_info(data) {
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
function build_Customerpage_nav(data) {
	$("#page_nav_customer").empty();
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
			to_page_customer(1);
		});
		prePageLi.click(function() {
			to_page_customer(data.pageBean.curPage - 1);
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
			to_page_customer(data.pageBean.curPage + 1);
		});
		lastPageLi.click(function() {
			to_page_customer(data.pageBean.sumPages);
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
			to_page_customer(item);
		});
		ul.append(numLi);
	});
	//添加下一页和末页 的提示
	ul.append(nextPageLi).append(lastPageLi);
	var navEle = $("<nav></nav>").append(ul);
	navEle.appendTo("#page_nav_customer");
}/**
 * 条件查询
 * @returns
 */
function searchCustomer() {
	var customerName = $('#customerName').val();
	//1页，名称条件查询
	to_page_customer(1,customerName);
}
/**
 * 添加模态框
 * @returns
 */
function addCustomer() {
	$('#customerModalLabel').text("添加信息");
	$('.modal-dialog').css({"margin":"120px auto"});
	$('.modal-header').css({"background-color":"#449D44"});
	$('#customerModal').modal();
}
/**
 * 删除指定客户
 * @returns
 */
$(document).on("click",".delete_customer_btn",function(){
	var	customerId = $(this).attr("del-id");
	var delCustomerUrl = "/sdims/sale/removecustomer?customerId="+customerId;
	$.getJSON(delCustomerUrl,function(data){
		if (data.success) {
			alert(data.successMsg);
			//返回当前页
			to_page_customer(currentPage);
		}else {
			alert(data.errMsg);
		}
	});
});
/**
 * 创建按钮的时候绑定点击事件(编辑按钮)
 * @returns
 */
$(document).on("click",".edit_customer_btn",function(){
	var customerId = $(this).attr("edit-id");
	var customerInfoUrl = "/sdims/sale/getcustomerbyid?customerId="+customerId;
	$('#EditcustomerModalLabel').text("编辑信息");
	$('.modal-dialog').css({"margin":"120px auto"});
	$('.modal-header').css({"background-color":"#449D44"});
	//根据ID获取的信息填充在模态框
	$.getJSON(customerInfoUrl,function(data){
		if (data.success) {
			$('#customerNameEdit').val(data.customer.name);
			$('#customerManEdit').val(data.customer.linkName);
			$('#customerMobileEdit').val(data.customer.mobile);
			$('#customerAddressEdit').val(data.customer.address);
			$('#customerDescEdit').val(data.customer.customerDesc);
			$('#customerUpdaterEdit').val(data.customer.updater);
			$('#customerIdEdit').val(data.customer.customerId);
		}
	});
	$('#EditcustomerModal').modal();
});