//总记录数，当前页数，用于删除，添加时候，跳转指定页
var intototalRecord,intostockcurrentPage;
/**
 * 创建入库管理Lable
 */
function getIntoStock() {
	var intoStockManagerName = $('#intoStockManagerName').text();
	var item = {
			'id' : '13',
			'name' : intoStockManagerName,
			'url' : 'intostock.html',
			'closable' : true
		};
		//调出模态框
		closableTab.addTab(item);
		//默认展示第一页
		to_page_intoStock(1);
}
/**
 * 页面跳转
 * @param pageIndex
 * @returns
 */
function to_page_intoStock(pageIndex,checkOrderNumber,stockStateNumber) {
	$.ajax({
		url : "/sdims/stock/getintopurchaseorderchecklist",
		dataType : 'json',
		data : {
			pageIndex : pageIndex,
			checkOrderNumber : checkOrderNumber,
			stockStateNumber : stockStateNumber
		},
		tpye : "GET",
		success : function(data) {
			// 1.解析并显示采购单数据
			getIntoStockPurchaseOrderCheckInfoToTables(data);
			// 2.解析并显示分页信息（只用到记录全局属性）
			build_IntoStockPurchaseOrderCheckpage_info(data);
			// 3.解析显示分页条
			build_IntoStockPurchaseOrderCheckpage_nav(data);
		}
	});
}
/**
 * 得到请求的信息并展示前台
 * @param data
 * @returns
 */
function getIntoStockPurchaseOrderCheckInfoToTables(data) {
	$("#table-intoStockPurchaseOrderCheck").empty();
	data.pageBean.datas.map(function(item, index) {
		//操作-删除按钮
		var AdoptCheckBtn = $("<button></button>").addClass(
				"btn btn-success btn-xs IntopOrderAdoptCheck_btn").append(
				$("<span></span>")
						.addClass("glyphicon glyphicon-ok")).append(" ").append("通过");
		//给按钮添加一个自定义属性来标识当前通过审核的采购单
		AdoptCheckBtn.attr("adopt-id", item.porderId);
		//存放按钮的TD
		var checkTd = $("<td></td>").append(AdoptCheckBtn);
		var porderId = $("<td></td>").append(item.porderId);
		var goodsName = $("<td></td>").append(item.goods.name);
		var supplierName = $("<td></td>").append(item.supplier.name);
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
		.append(stockState)
		.append(stockMan)
		.append(stockTime)
		.append(checkTd)
		.appendTo("#table-intoStockPurchaseOrderCheck");
	});
}
/**
 * 解析显示分页信息
 * @param data
 * @returns
 */
function build_IntoStockPurchaseOrderCheckpage_info(data) {
	//最大页数
	intototalRecord = data.pageBean.sumPages;
	//当前页数
	intostockcurrentPage = data.pageBean.curPage;
}
/**
 * 解析显示分页条
 * @param result
 * @returns
 */
function build_IntoStockPurchaseOrderCheckpage_nav(data) {
	$("#page_nav_intoStockPurchaseOrderCheck").empty();
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
			to_page_intoStock(1);
		});
		prePageLi.click(function() {
			to_page_intoStock(data.pageBean.curPage - 1);
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
			to_page_intoStock(data.pageBean.curPage + 1);
		});
		lastPageLi.click(function() {
			to_page_intoStock(data.pageBean.sumPages);
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
			to_page_intoStock(item);
		});
		ul.append(numLi);
	});
	//添加下一页和末页 的提示
	ul.append(nextPageLi).append(lastPageLi);
	var navEle = $("<nav></nav>").append(ul);
	navEle.appendTo("#page_nav_intoStockPurchaseOrderCheck");
}
/**
 * 条件查询
 * @returns
 */
function searchIntoStockPurchaseOrderOfCheck() {
	var checkOrderNumber = $('#intoStockPurchaseOrderCheckNumber').val();
	var tempIntoStockPurchaseOrderCheckState = $('#intoStockPurchaseOrderCheckState').val();
	var stockStateNumber;
	if (tempIntoStockPurchaseOrderCheckState=="审核通过") {
		stockStateNumber=1;
	}else if (tempIntoStockPurchaseOrderCheckState=="待审核") {
		stockStateNumber=0;
	}
	to_page_intoStock(1,checkOrderNumber,stockStateNumber);
}
/**
 * 审核通过采购单-入库
 * @returns
 */
$(document).on("click",".IntopOrderAdoptCheck_btn",function(){
	//当前采购单ID
	var porderId = $(this).attr("adopt-id");
	//请求通过审核订单
	var intoStockPurchaseOrderCheckUrl = "/sdims/stock/modifyintostockpurchaseordercheck?porderId="+porderId;
	//获取审核状态文字
	var checkStateDom = $(this).parent().prev().prev().prev();
	var checkFont = checkStateDom.text();
	//已审核的不需要再次去审核（修改库存）
	if (checkFont=="审核通过") {
		alert("已审核!!!");
	}else{
		$.getJSON(intoStockPurchaseOrderCheckUrl,function(data){
			if (data.success) {
				alert(data.successMsg);
				//返回当前页
				to_page_intoStock(intostockcurrentPage);
			}else {
				alert(data.errMsg);
			}
		});
	}
});