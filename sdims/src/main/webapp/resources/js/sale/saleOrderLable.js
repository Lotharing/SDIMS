//总记录数，当前页数，用于删除，添加时候，跳转指定页
var SOtotalRecord,SOcurrentPage;
/**
 * 创建销售单单管理Lable
 */
function getSaleOrder() {
	var saleOrderLable = $('#saleOrderManager').text();
	var item = {
			'id' : '9',
			'name' : saleOrderLable,
			'url' : 'saleorder.html',
			'closable' : true
		};
		//调出模态框
		closableTab.addTab(item);
		//默认展示第一页
		to_page_sOrder(1);
}
/**
 * 页面跳转
 * @param pageIndex
 * @returns
 */
function to_page_sOrder(pageIndex,goodsName,customerName,repositoryName,orderNumber,checkState,stockState) {
	$.ajax({
		url : "/sdims/sale/getsaleorderlist",
		dataType : 'json',
		data : {
			pageIndex : pageIndex,
			goodsName : goodsName,
			customerName : customerName,
			repositoryName : repositoryName,
			orderNumber : orderNumber,
			checkState : checkState,
			stockState : stockState
		},
		tpye : "GET",
		success : function(data) {
			// 1.解析并显示员工数据
			getSaleOrderInfoToTables(data);
			// 2.解析并显示分页信息（只用到记录全局属性）
			build_SaleOrderpage_info(data);
			// 3.解析显示分页条
			build_saleOrderpage_nav(data);
		}
	});
}
/**
 * 得到请求的信息并展示前台
 * @param data
 * @returns
 */
function getSaleOrderInfoToTables(data) {
	$("#table-saleOrder").empty();
	data.pageBean.datas.map(function(item, index) {
		//操作-编辑按钮
		var editBtn = $("<button></button>").addClass(
				"btn btn-primary btn-xs edit_sOrder_btn").append(
				$("<span></span>")
						.addClass("glyphicon glyphicon-pencil")).append(" ").append("编辑");
		//给编辑按钮添加一个自定义属性来标识当前编辑的员工
		editBtn.attr("edit-id", item.sorderId);
		//操作-删除按钮
		var delBtn = $("<button></button>").addClass(
				"btn btn-danger btn-xs delete_sOrder_btn").append(
				$("<span></span>")
						.addClass("glyphicon glyphicon-trash")).append(" ").append("删除");
		//给删除按钮添加一个自定义属性来标识当前删除的员工
		delBtn.attr("del-id", item.sorderId);
		//存放按钮的TD
		var btnTd = $("<td></td>").append(editBtn).append(" ").append(delBtn);
		var porderId = $("<td></td>").css("font-size","10px").append(item.sorderId);
		var goodsName = $("<td></td>").css("font-size","10px").append(item.goods.name);
		var customerName = $("<td></td>").css("font-size","10px").append(item.customer.name);
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
		.append(customerName)
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
		.appendTo("#table-saleOrder");
	});
}
/**
 * 解析显示分页信息
 * @param data
 * @returns
 */
function build_SaleOrderpage_info(data) {
	//最大页数
	SOtotalRecord = data.pageBean.sumPages;
	//当前页数
	SOcurrentPage = data.pageBean.curPage;
}
/**
 * 解析显示分页条
 * @param result
 * @returns
 */
function build_saleOrderpage_nav(data) {
	$("#page_nav_saleOrder").empty();
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
			to_page_sOrder(1);
		});
		prePageLi.click(function() {
			to_page_sOrder(data.pageBean.curPage - 1);
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
			to_page_sOrder(data.pageBean.curPage + 1);
		});
		lastPageLi.click(function() {
			to_page_sOrder(data.pageBean.sumPages);
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
			to_page_sOrder(item);
		});
		ul.append(numLi);
	});
	//添加下一页和末页 的提示
	ul.append(nextPageLi).append(lastPageLi);
	var navEle = $("<nav></nav>").append(ul);
	navEle.appendTo("#page_nav_saleOrder");
}
/**
 * 条件查询
 * @returns
 */
function searchSaleOrder() {
	var goodsName = $('#saleOrderGoodsName').val();
	var customerName = $('#saleOrderCustomerName').val();
	var repositoryName = $('#saleOrderRepositoryName').val();
	var orderNumber = $('#saleOrderNumber').val();
	var tempOrderState = $('#SaleOrderState').val();
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
	to_page_sOrder(1,goodsName,customerName,repositoryName,orderNumber,checkState,stockState);
}
/**
 * 添加模态框
 * @returns
 */
function addSaleOrder() {
	$('#saleOrderModalLabel').text("添加信息");
	$('.modal-dialog').css({"margin":"120px auto"});
	$('.modal-header').css({"background-color":"#449D44"});
	//模态框载入商品
	getGoodsInfoToSelect();
	//模态框载入供应商
	getCustomerInfoToSelect()
	//模态框载入仓库
	getRepositoryInfoToSelect();
	$('#saleOrderModal').modal();
}
/*
 *商品载入选择框
 */
function getGoodsInfoToSelect() {
	//商品信息
	var goodsUrl = "/sdims/admin/getallgoodslist";
	//商品信息放置select中
	$.getJSON(goodsUrl,function(data){
		$('#saleOrderGoods').empty();
		$('#saleOrderGoods').append($("<option></option>"));
		if (data.success) {
			data.allGoodsList.map(function(item, index){
				var goodsItem = $("<option value="+item.goodsId+">"+item.name+"</option>");
				$('#saleOrderGoods').append(goodsItem);
			});
		}
	});
}
/*
 *客户载入选择框
 */
function getCustomerInfoToSelect() {
	var customerUrl = "/sdims/sale/getallcustomerlist";
	//商品信息放置select中
	$.getJSON(customerUrl,function(data){
		$('#saleOrderCustomer').empty();
		$('#saleOrderCustomer').append($("<option></option>"));
		if (data.success) {
			data.allCustomerList.map(function(item, index){
				var customerItem = $("<option value="+item.customerId+">"+item.name+"</option>");
				$('#saleOrderCustomer').append(customerItem);
			});
		}
	});
}
/*
 *仓库载入选择框
 */
function getRepositoryInfoToSelect() {
	var repositoryUrl = "/sdims/admin/getallrepositorylist";
	//商品信息放置select中
	$.getJSON(repositoryUrl,function(data){
		$('#saleOrderRepository').empty();
		$('#saleOrderRepository').append($("<option></option>"));
		if (data.success) {
			data.allRepositoryList.map(function(item, index){
				var repositoryItem = $("<option value="+item.repoId+">"+item.name+"</option>");
				$('#saleOrderRepository').append(repositoryItem);
			});
		}
	});
}
/**
 * 删除指定销售单
 * @returns
 */
$(document).on("click",".delete_sOrder_btn",function(){
	var sorderId = $(this).attr("del-id");
	var delSaleOrderUrl = "/sdims/sale/removesaleorder?sorderId="+sorderId;
	$.getJSON(delSaleOrderUrl,function(data){
		if (data.success) {
			alert(data.successMsg);
			//返回当前页
			to_page_sOrder(SOcurrentPage);
		}else {
			alert(data.errMsg);
		}
	});
});
/**
 * 创建按钮的时候绑定点击事件(编辑按钮)
 * @returns
 */
$(document).on("click",".edit_sOrder_btn",function(){
	var sorderId = $(this).attr("edit-id");
	var saleOrderInfoUrl = "/sdims/sale/getsaleorderbyid?sorderId="+sorderId;
	$('#EditsaleOrderModalLabel').text("编辑信息");
	$('.modal-dialog').css({"margin":"120px auto"});
	$('.modal-header').css({"background-color":"#449D44"});
	//根据ID获取的信息填充在模态框
	$.getJSON(saleOrderInfoUrl,function(data){
		if (data.success) {
			$('#EditSaleOrderGoods').append("<option value='"+data.saleOrder.goods.goodsId+"'>"+data.saleOrder.goods.name+"</option>");
			$('#EditSaleOrderCustomer').append("<option value='"+data.saleOrder.customer.customerId+"'>"+data.saleOrder.customer.name+"</option>");
			$('#EditSaleOrderRepository').append("<option value='"+data.saleOrder.repository.repoId+"'>"+data.saleOrder.repository.name+"</option>");
			$('#EditSaleOrderCount').val(data.saleOrder.count);
			$('#EditSaleOrderUnitPrice').val(data.saleOrder.unitPrice);
			$('#EditSaleOrderDesc').val(data.saleOrder.orderDesc);
			$('#EditSaleOrderCreater').val(data.saleOrder.creater);
			$('#EditSaleOrderId').val(data.saleOrder.sorderId);
		}
	});
	$('#EditsaleOrderModal').modal();
});