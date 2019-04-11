//总记录数，当前页数，用于删除，添加时候，跳转指定页
var outototalRecord,outostokcurrentPage;
/**
 * 创建入库管理Lable
 */
function getOutoStock() {
	var outoStockManagerName = $('#outoStockManagerName').text();
	var item = {
			'id' : '14',
			'name' : outoStockManagerName,
			'url' : 'outostock.html',
			'closable' : true
		};
		//调出模态框
		closableTab.addTab(item);
		//默认展示第一页
		to_page_outoStock(1);
}
/**
 * 页面跳转
 * @param pageIndex
 * @returns
 */
function to_page_outoStock(pageIndex,checkSaleOrderNumber,stockStateNumber) {
	$.ajax({
		url : "/sdims/stock/getoutosaleorderchecklist",
		dataType : 'json',
		data : {
			pageIndex : pageIndex,
			checkSaleOrderNumber : checkSaleOrderNumber,
			stockStateNumber : stockStateNumber
		},
		tpye : "GET",
		success : function(data) {
			// 1.解析并显示采购单数据
			getOutoStockSaleOrderCheckInfoToTables(data);
			// 2.解析并显示分页信息（只用到记录全局属性）
			build_OutoStockSaleOrderCheckpage_info(data);
			// 3.解析显示分页条
			build_OutoStockSaleOrderCheckpage_nav(data);
		}
	});
}
/**
 * 得到请求的信息并展示前台
 * @param data
 * @returns
 */
function getOutoStockSaleOrderCheckInfoToTables(data) {
	$("#table-outoStockSaleOrderCheck").empty();
	data.pageBean.datas.map(function(item, index) {
		//操作-删除按钮
		var AdoptCheckBtn = $("<button></button>").addClass(
				"btn btn-success btn-xs OutopOrderAdoptCheck_btn").append(
				$("<span></span>")
						.addClass("glyphicon glyphicon-ok")).append(" ").append("通过");
		//给按钮添加一个自定义属性来标识当前通过审核的采购单
		AdoptCheckBtn.attr("adopt-id", item.sorderId);
		//存放按钮的TD
		var checkTd = $("<td></td>").append(AdoptCheckBtn);
		var sorderId = $("<td></td>").append(item.sorderId);
		var goodsName = $("<td></td>").append(item.goods.name);
		var customerName = $("<td></td>").append(item.customer.name);
		var repositoryName = $("<td></td>").append(item.repository.name);
		var orderNumber = $("<td></td>").append(item.orderNumber);
		var count = $("<td></td>").append(item.count);
		var unitPrice = $("<td></td>").append(item.unitPrice);
		var totalPrice = $("<td></td>").append(item.totalPrice);
		var orderDesc = $("<td></td>").append(item.orderDesc);
		var createTime = $("<td></td>").append(new Date(item.createTime).format('Y-m-d H:i:s'));
		var creater = $("<td></td>").append(item.creater);
		var stockState = $("<td></td>").append(item.stockState == 1 ? "审核通过" : "待审核");
		var stockMan = $("<td></td>").append(item.stockMan);
		if (item.stockTime==null) {
			var stockTime = $("<td></td>").append();
		}else{
			var stockTime = $("<td></td>").append(new Date(item.stockTime).format('Y-m-d H:i:s'));
		}
		$("<tr></tr>")
		.append(sorderId)
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
		.append(stockState)
		.append(stockMan)
		.append(stockTime)
		.append(checkTd)
		.appendTo("#table-outoStockSaleOrderCheck");
	});
}
/**
 * 解析显示分页信息
 * @param data
 * @returns
 */
function build_OutoStockSaleOrderCheckpage_info(data) {
	//最大页数
	outototalRecord = data.pageBean.sumPages;
	//当前页数
	outostokcurrentPage = data.pageBean.curPage;
}
/**
 * 解析显示分页条
 * @param result
 * @returns
 */
function build_OutoStockSaleOrderCheckpage_nav(data) {
	$("#page_nav_outoStockSaleOrderCheck").empty();
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
			to_page_outoStock(1);
		});
		prePageLi.click(function() {
			to_page_outoStock(data.pageBean.curPage - 1);
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
			to_page_outoStock(data.pageBean.curPage + 1);
		});
		lastPageLi.click(function() {
			to_page_outoStock(data.pageBean.sumPages);
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
			to_page_outoStock(item);
		});
		ul.append(numLi);
	});
	//添加下一页和末页 的提示
	ul.append(nextPageLi).append(lastPageLi);
	var navEle = $("<nav></nav>").append(ul);
	navEle.appendTo("#page_nav_outoStockSaleOrderCheck");
}
/**
 * 条件查询
 * @returns
 */
function searchOutoStockPurchaseOrderOfCheck() {
	var checkOrderNumber = $('#outoStockSaleOrderCheckNumber').val();
	var tempOutoStockSaleOrderCheckState = $('#outoStockSaleOrderCheckState').val();
	var stockStateNumber;
	if (tempOutoStockSaleOrderCheckState=="审核通过") {
		stockStateNumber=1;
	}else if (tempOutoStockSaleOrderCheckState=="待审核") {
		stockStateNumber=0;
	}
	to_page_outoStock(1,checkOrderNumber,stockStateNumber);
}/**
 * 审核通过销售单-出库
 * @returns
 */
$(document).on("click",".OutopOrderAdoptCheck_btn",function(){
	//当前销售单ID
	var sorderId = $(this).attr("adopt-id");
	//请求通过审核订单
	var outoStockSaleOrderCheckUrl = "/sdims/stock/modifyoutostocksaleordercheck?sorderId="+sorderId;
	//获取审核状态文字
	var checkStateDom = $(this).parent().prev().prev().prev();
	var checkFont = checkStateDom.text();
	//已审核的不需要再次去审核（修改库存）
	if (checkFont=="审核通过") {
		alert("已审核!!!");
	}else{
		$.getJSON(outoStockSaleOrderCheckUrl,function(data){
			if (data.success) {
				alert(data.successMsg);
				//返回当前页
				to_page_outoStock(outostokcurrentPage);
			}else {
				alert(data.errMsg);
			}
		});
	}
});