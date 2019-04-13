//总记录数，当前页数，用于删除，添加时候，跳转指定页
var PtotalRecord,PcurrentPage;
/**
 * 创建采购单管理Lable
 */
function getPurchaseOrder() {
	var purchaseOrderLable = $('#purchaseOrderManager').text();
	var item = {
			'id' : '7',
			'name' : purchaseOrderLable,
			'url' : 'purchaseorder.html',
			'closable' : true
		};
		//调出模态框
		closableTab.addTab(item);
		//默认展示第一页
		to_page_pOrder(1);
}
/*
 *商品载入选择框
 */
function getGoodsInfoToPurchaseSelect() {
	//商品信息
	var goodsUrl = "/sdims/admin/getallgoodslist";
	//商品信息放置select中
	$.getJSON(goodsUrl,function(data){
		$('#purchaseOrderGoods').empty();
		$('#purchaseOrderGoods').append($("<option></option>"));
		if (data.success) {
			data.allGoodsList.map(function(item, index){
				var goodsItem = $("<option value="+item.goodsId+">"+item.name+"</option>");
				$('#purchaseOrderGoods').append(goodsItem);
			});
		}
	});
}
/*
 *供应商载入选择框
 */
function getSupplierInfoToPurchaseSelect() {
	var supplierUrl = "/sdims/purchase/getallsupplierlist";
	//商品信息放置select中
	$.getJSON(supplierUrl,function(data){
		$('#purchaseOrderSupplier').empty();
		$('#purchaseOrderSupplier').append($("<option></option>"));
		if (data.success) {
			data.allSupplierList.map(function(item, index){
				var supplierItem = $("<option value="+item.supplierId+">"+item.name+"</option>");
				$('#purchaseOrderSupplier').append(supplierItem);
			});
		}
	});
}
/*
 *仓库载入选择框
 */
function getRepositoryInfoToPurchaseSelect() {
	var repositoryUrl = "/sdims/admin/getallrepositorylist";
	//商品信息放置select中
	$.getJSON(repositoryUrl,function(data){
		$('#purchaseOrderRepository').empty();
		$('#purchaseOrderRepository').append($("<option></option>"));
		if (data.success) {
			data.allRepositoryList.map(function(item, index){
				var repositoryItem = $("<option value="+item.repoId+">"+item.name+"</option>");
				$('#purchaseOrderRepository').append(repositoryItem);
			});
		}
	});
}
/**
 * 页面跳转
 * @param pageIndex
 * @returns
 */
function to_page_pOrder(pageIndex,goodsName,supplierName,repositoryName,orderNumber,checkState,stockState) {
	$.ajax({
		url : "/sdims/purchase/getpurchaseorderlist",
		dataType : 'json',
		data : {
			pageIndex : pageIndex,
			goodsName : goodsName,
			supplierName : supplierName,
			repositoryName : repositoryName,
			orderNumber : orderNumber,
			checkState : checkState,
			stockState : stockState
		},
		type : "GET",
		success : function(data) {
			// 1.解析并显示员工数据
			getPurchaseOrderInfoToTables(data);
			// 2.解析并显示分页信息（只用到记录全局属性）
			build_PurchaseOrderpage_info(data);
			// 3.解析显示分页条
			build_PurchaseOrderpage_nav(data);
		}
	});
}
/**
 * 得到请求的信息并展示前台
 * @param data
 * @returns
 */
function getPurchaseOrderInfoToTables(data) {
	$("#table-supplierOrder").empty();
	data.pageBean.datas.map(function(item, index) {
		//操作-编辑按钮
		var editBtn = $("<button></button>").addClass(
				"btn btn-primary btn-xs edit_pOrder_btn").append(
				$("<span></span>")
						.addClass("glyphicon glyphicon-pencil")).append(" ").append("编辑");
		//给编辑按钮添加一个自定义属性来标识当前编辑的员工
		editBtn.attr("edit-id", item.porderId);
		//操作-删除按钮
		var delBtn = $("<button></button>").addClass(
				"btn btn-danger btn-xs delete_pOrder_btn").append(
				$("<span></span>")
						.addClass("glyphicon glyphicon-trash")).append(" ").append("删除");
		//给删除按钮添加一个自定义属性来标识当前删除的员工
		delBtn.attr("del-id", item.porderId);
		//存放按钮的TD
		var btnTd = $("<td></td>").append(editBtn).append(" ").append(delBtn);
		var porderId = $("<td></td>").css("font-size","10px").append(item.porderId);
		var goodsName = $("<td></td>").css("font-size","10px").append(item.goods.name);
		var supplierName = $("<td></td>").css("font-size","10px").append(item.supplier.name);
		var repositoryName = $("<td></td>").css("font-size","10px").append(item.repository.name);
		var orderNumber = $("<td></td>").css("font-size","10px").append(item.orderNumber);
		var count = $("<td></td>").css("font-size","10px").append(item.count);
		var unitPrice = $("<td></td>").css("font-size","10px").append(item.unitPrice);
		var totalPrice = $("<td></td>").css("font-size","10px").append(item.totalPrice);
		var orderDesc = $("<td></td>").css("font-size","10px").append(item.orderDesc);
		var createTime = $("<td></td>").css("font-size","10px").append(new Date(item.createTime).format('Y-m-d H:i:s'));
		var creater = $("<td></td>").css("font-size","10px").append(item.creater);
		var checkState = $("<td></td>").css("font-size","10px").append(item.checkState == 1 ? "审核通过" : "待审核");
		var checkMan = $("<td></td>").css("font-size","10px").append(item.checkMan);
		if (item.checkTime==null) {
			var checkTime = $("<td></td>").css("font-size","10px").append();
		}else{
			var checkTime = $("<td></td>").css("font-size","10px").append(new Date(item.checkTime).format('Y-m-d H:i:s'));
		}
		var stockState = $("<td></td>").css("font-size","10px").append(item.stockState == 1 ?  "审核通过" : "待审核");
		var stockMan = $("<td></td>").css("font-size","10px").append(item.stockMan);
		if (item.stockTime==null) {
			var stockTime = $("<td></td>").css("font-size","10px").append();
		}else{
			var stockTime = $("<td></td>").css("font-size","10px").append(new Date(item.stockTime).format('Y-m-d H:i:s'));
		}
		$("<tr></tr>")
		.append(porderId)
		.append(goodsName)
		.append(supplierName)
		.append(repositoryName)
		.append(orderNumber)
		.append(count)
		.append(unitPrice)
		.append(totalPrice)
		.append(orderDesc)
		.append(createTime)
		.append(creater)
		.append(checkState)
		.append(checkMan)
		.append(checkTime)
		.append(stockState)
		.append(stockMan)
		.append(stockTime)
		.append(btnTd)
		.appendTo("#table-supplierOrder");
	});
}
/**
 * 解析显示分页信息
 * @param data
 * @returns
 */
function build_PurchaseOrderpage_info(data) {
	//最大页数
	PtotalRecord = data.pageBean.sumPages;
	//当前页数
	PcurrentPage = data.pageBean.curPage;
}
/**
 * 解析显示分页条
 * @param result
 * @returns
 */
function build_PurchaseOrderpage_nav(data) {
	$("#page_nav_supplierOrder").empty();
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
			to_page_pOrder(1);
		});
		prePageLi.click(function() {
			to_page_pOrder(data.pageBean.curPage - 1);
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
			to_page_pOrder(data.pageBean.curPage + 1);
		});
		lastPageLi.click(function() {
			to_page_pOrder(data.pageBean.sumPages);
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
			to_page_pOrder(item);
		});
		ul.append(numLi);
	});
	//添加下一页和末页 的提示
	ul.append(nextPageLi).append(lastPageLi);
	var navEle = $("<nav></nav>").append(ul);
	navEle.appendTo("#page_nav_supplierOrder");
}
/**
 * 条件查询
 * @returns
 */
function searchSupplierOrder() {
	var goodsName = $('#supplierOrderGoodsName').val();
	var supplierName = $('#supplierOrderSupplierName').val();
	var repositoryName = $('#supplierOrderRepositoryName').val();
	var orderNumber = $('#supplierOrderNumber').val();
	var tempOrderState = $('#PurchaseOrderState').val();
	var checkState,stockState;
	if (tempOrderState=="审核通过") {
		//二次审核都通过
		checkState=1;
		stockState=1;
	}else if (tempOrderState=="待审核") {
		//二次没通过，（一次没过，二次一定没有审核）
		stockState=0;
	}
	//1页，名称条件查询
	to_page_pOrder(1,goodsName,supplierName,repositoryName,orderNumber,checkState,stockState);
}
/**
 * 添加模态框
 * @returns
 */
function addSupplierOrder() {
	$('#purchaseOrderModalLabel').text("添加信息");
	$('.modal-dialog').css({"margin":"120px auto"});
	$('.modal-header').css({"background-color":"#449D44"});
	//模态框载入商品
	getGoodsInfoToPurchaseSelect();
	//模态框载入供应商
	getSupplierInfoToPurchaseSelect()
	//模态框载入仓库
	getRepositoryInfoToPurchaseSelect();
	$('#purchaseOrderModal').modal();
}
/**
 * 删除指定采购单
 * @returns
 */
$(document).on("click",".delete_pOrder_btn",function(){
	var porderId = $(this).attr("del-id");
	var delPurchaseOrderUrl = "/sdims/purchase/removepurchaseorder?porderId="+porderId;
	$.getJSON(delPurchaseOrderUrl,function(data){
		if (data.success) {
			alert(data.successMsg);
			//返回当前页
			to_page_pOrder(PcurrentPage);
		}else {
			alert(data.errMsg);
		}
	});
});
/**
 * 创建按钮的时候绑定点击事件(编辑按钮)
 * @returns
 */
$(document).on("click",".edit_pOrder_btn",function(){
	var porderId = $(this).attr("edit-id");
	var purchaseOrderInfoUrl = "/sdims/purchase/getpurchaseorderbyid?porderId="+porderId;
	$('#EditpurchaseOrderModalLabel').text("编辑信息");
	$('.modal-dialog').css({"margin":"120px auto"});
	$('.modal-header').css({"background-color":"#449D44"});
	//根据ID获取的信息填充在模态框
	$.getJSON(purchaseOrderInfoUrl,function(data){
		if (data.success) {
			$('#EditpurchaseOrderGoods').append("<option value='"+data.purchaseOrder.goods.goodsId+"'>"+data.purchaseOrder.goods.name+"</option>");
			$('#EditpurchaseOrderSupplier').append("<option value='"+data.purchaseOrder.supplier.supplierId+"'>"+data.purchaseOrder.supplier.name+"</option>");
			$('#EditpurchaseOrderRepository').append("<option value='"+data.purchaseOrder.repository.repoId+"'>"+data.purchaseOrder.repository.name+"</option>");
			$('#EditpurchaseOrderCount').val(data.purchaseOrder.count);
			$('#EditpurchaseOrderUnitPrice').val(data.purchaseOrder.unitPrice);
			$('#EditpurchaseOrderDesc').val(data.purchaseOrder.orderDesc);
			$('#EditpurchaseOrderCreater').val(data.purchaseOrder.creater);
			$('#EditpurchaseOrderId').val(data.purchaseOrder.porderId);
		}
	});
	$('#EditpurchaseOrderModal').modal();
});