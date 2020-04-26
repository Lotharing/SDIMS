//总记录数，当前页数，用于删除，添加时候，跳转指定页
var PStotalRecord,PScurrentPage;
/**
 * 创建供应商管理Lable
 */
function getPurchase() {
	var purchaseSupplierLable = $('#purchaseManager').text();
	var item = {
			'id' : '6',
			'name' : purchaseSupplierLable,
			'url' : 'purchasesupplier.html',
			'closable' : true
		};
		//调出模态框
		closableTab.addTab(item);
		//默认展示第一页
		to_page_supplier(1);
}
/**
 * 页面跳转
 * @param pageIndex
 * @returns
 */
function to_page_supplier(pageIndex,supplierName) {
	$.ajax({
		url : "/sdims/purchase/getpurchasesupplierlist",
		dataType : 'json',
		data : {pageIndex:pageIndex,supplierName:supplierName},
		tpye : "GET",
		success : function(data) {
			// 1.解析并显示员工数据
			getSupplierInfoToTables(data);
			// 2.解析并显示分页信息（只用到记录全局属性）
			build_Supploerpage_info(data);
			// 3.解析显示分页条
			build_Supplierpage_nav(data);
		}

	})
}
/**
 * 得到请求的信息并展示前台
 * @param data
 * @returns
 */
function getSupplierInfoToTables(data) {
	$("#table-supplier").empty();
	data.pageBean.datas.map(function(item, index) {
		//操作-编辑按钮
		var editBtn = $("<button></button>").addClass(
				"btn btn-primary btn-sm edit_supplier_btn").append(
				$("<span></span>")
						.addClass("glyphicon glyphicon-pencil")).append(" ").append("编辑");
		//给编辑按钮添加一个自定义属性来标识当前编辑的员工
		editBtn.attr("edit-id", item.supplierId);
		//操作-删除按钮
		var delBtn = $("<button></button>").addClass(
				"btn btn-danger btn-sm delete_supplier_btn").append(
				$("<span></span>")
						.addClass("glyphicon glyphicon-trash")).append(" ").append("删除");
		//给删除按钮添加一个自定义属性来标识当前删除的员工
		delBtn.attr("del-id", item.supplierId);
		//存放按钮的TD
		var btnTd = $("<td></td>").append(editBtn).append(" ").append(delBtn);
		var id = $("<td></td>").append(item.supplierId);
		var name = $("<td></td>").append(item.name);
		var linkName = $("<td></td>").append(item.linkName);
		var mobile = $("<td></td>").append(item.mobile);
		var address = $("<td></td>").append(item.address);
		var supplierDesc = $("<td></td>").append(item.supplierDesc);
		var updater = $("<td></td>").append(item.updater);
		var updateTime = $("<td></td>").append(new Date(item.updateTime).format('Y-m-d H:i:s'));
		$("<tr></tr>")
		.append(id)
		.append(name)
		.append(linkName)
		.append(mobile)
		.append(address)
		.append(supplierDesc)
		.append(updater)
		.append(updateTime)
		.append(btnTd)
		.appendTo("#table-supplier");
	});
}
/**
 * 解析显示分页信息
 * @param data
 * @returns
 */
function build_Supploerpage_info(data) {
	//最大页数
	PStotalRecord = data.pageBean.sumPages;
	//当前页数
	PScurrentPage = data.pageBean.curPage;
}
/**
 * 解析显示分页条
 * @param result
 * @returns
 */
function build_Supplierpage_nav(data) {
	$("#page_nav_supplier").empty();
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
			to_page_supplier(1);
		});
		prePageLi.click(function() {
			to_page_supplier(data.pageBean.curPage - 1);
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
			to_page_supplier(data.pageBean.curPage + 1);
		});
		lastPageLi.click(function() {
			to_page_supplier(data.pageBean.sumPages);
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
			to_page_supplier(item);
		});
		ul.append(numLi);
	});
	//添加下一页和末页 的提示
	ul.append(nextPageLi).append(lastPageLi);
	var navEle = $("<nav></nav>").append(ul);
	navEle.appendTo("#page_nav_supplier");
}
/**
 * 条件查询
 * @returns
 */
function searchSupplier() {
	var supplierName = $('#supplierName').val();
	//1页，名称条件查询
	to_page_supplier(1,supplierName);
}
/**
 * 添加模态框
 * @returns
 */
function addSupplier() {
	$('#supplierModalLabel').text("添加信息");
	$('.modal-dialog').css({"margin":"120px auto"});
	$('.modal-header').css({"background-color":"#449D44"});
	$('#supplierModal').modal();
}
/**
 * 删除指定供销商
 * @returns
 */
$(document).on("click",".delete_supplier_btn",function(){
	var	supplierId = $(this).attr("del-id");
	var delSupplierUrl = "/sdims/purchase/removesupplier?supplierId="+supplierId;
	$.getJSON(delSupplierUrl,function(data){
		if (data.success) {
			alert(data.successMsg);
			//返回当前页
			to_page_supplier(PScurrentPage);
		}else {
			alert(data.errMsg);
		}
	});
});
/**
 * 创建按钮的时候绑定点击事件(编辑按钮)
 * @returns
 */
$(document).on("click",".edit_supplier_btn",function(){
	var supplierId = $(this).attr("edit-id");
	var supplierInfoUrl = "/sdims/purchase/getsupplierbyid?supplierId="+supplierId;
	$('#EditsupplierModalLabel').text("编辑信息");
	$('.modal-dialog').css({"margin":"120px auto"});
	$('.modal-header').css({"background-color":"#449D44"});
	//根据ID获取的信息填充在模态框
	$.getJSON(supplierInfoUrl,function(data){
		if (data.success) {
			$('#supplierNameEdit').val(data.supplier.name);
			$('#supplierManEdit').val(data.supplier.linkName);
			$('#supplierMobileEdit').val(data.supplier.mobile);
			$('#supplierAddressEdit').val(data.supplier.address);
			$('#supplierDescEdit').val(data.supplier.supplierDesc);
			$('#supplierUpdaterEdit').val(data.supplier.updater);
			$('#supplierIdEdit').val(data.supplier.supplierId);
		}
	});
	$('#EditsupplierModal').modal();
});