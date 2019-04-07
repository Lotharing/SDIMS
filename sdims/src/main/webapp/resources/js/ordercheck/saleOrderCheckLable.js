//总记录数，当前页数，用于删除，添加时候，跳转指定页
var totalRecord,currentPage;
/**
 * 创建采购单审核管理Lable
 */
function getSaleOrderCheck() {
	var saleOrderCheckLable = $('#saleOrderCheckLableName').text();
	var item = {
			'id' : '11',
			'name' : saleOrderCheckLable,
			'url' : 'saleordercheck.html',
			'closable' : true
		};
		//调出模态框
		closableTab.addTab(item);
		//默认展示第一页
		to_page_saleOrderCheck(1);
}
/**
 * 页面跳转
 * @param pageIndex
 * @returns
 */
function to_page_saleOrderCheck(pageIndex,checkSaleOrderNumber,checkSaleOrderStateNumber) {
	$.ajax({
		url : "/sdims/ordercheck/getsaleorderchecklist",
		dataType : 'json',
		data : {
			pageIndex : pageIndex,
			checkSaleOrderNumber : checkSaleOrderNumber,
			checkSaleOrderStateNumber : checkSaleOrderStateNumber
		},
		tpye : "GET",
		success : function(data) {
			// 1.解析并显示采购单数据
			getSaleOrderCheckInfoToTables(data);
			// 2.解析并显示分页信息（只用到记录全局属性）
			build_SaleOrderCheckpage_info(data);
			// 3.解析显示分页条
			build_SaleOrderCheckpage_nav(data);
		}
	});
}
/**
 * 得到请求的信息并展示前台
 * @param data
 * @returns
 */
function getSaleOrderCheckInfoToTables(data) {
	$("#table-saleOrderCheck").empty();
	data.pageBean.datas.map(function(item, index) {
		//操作-编辑按钮
		var revokeCheckBtn = $("<button></button>").addClass(
				"btn btn-danger btn-xs sOrderRevokeCheck_btn").append(
				$("<span></span>")
						.addClass("glyphicon glyphicon-remove")).append(" ").append("撤销");
		//给按钮添加一个自定义属性来标识当前撤销审核的采购单
		revokeCheckBtn.attr("revoke-id", item.sorderId);
		//操作-删除按钮
		var AdoptCheckBtn = $("<button></button>").addClass(
				"btn btn-primary btn-xs sOrderAdoptCheck_btn").append(
				$("<span></span>")
						.addClass("glyphicon glyphicon-ok")).append(" ").append("通过");
		//给按钮添加一个自定义属性来标识当前通过审核的采购单
		AdoptCheckBtn.attr("adopt-id", item.sorderId);
		//存放按钮的TD
		var checkTd = $("<td></td>").append(revokeCheckBtn).append(" ").append(AdoptCheckBtn);
		var porderId = $("<td></td>").css("font-size","12px").append(item.sorderId);
		var goodsName = $("<td></td>").css("font-size","12px").append(item.goods.name);
		var customerName = $("<td></td>").css("font-size","12px").append(item.customer.name);
		var repositoryName = $("<td></td>").css("font-size","12px").append(item.repository.name);
		var orderNumber = $("<td></td>").css("font-size","12px").append(item.orderNumber);
		var count = $("<td></td>").css("font-size","12px").append(item.count);
		var unitPrice = $("<td></td>").css("font-size","12px").append(item.unitPrice);
		var totalPrice = $("<td></td>").css("font-size","12px").append(item.totalPrice);
		var orderDesc = $("<td></td>").css("font-size","12px").append(item.orderDesc);
		var createTime = $("<td></td>").css("font-size","12px").append(new Date(item.createTime).format('Y-m-d H:i:s'));
		var creater = $("<td></td>").css("font-size","12px").append(item.creater);
		var checkState = $("<td></td>").css("font-size","12px").append(item.checkState == 1 ? "审核通过" : "待审核");
		var checkMan = $("<td></td>").css("font-size","12px").append(item.checkMan);
		if (item.checkTime==null) {
			var checkTime = $("<td></td>").css("font-size","12px").append();
		}else{
			var checkTime = $("<td></td>").css("font-size","12px").append(new Date(item.checkTime).format('Y-m-d H:i:s'));
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
		.append(checkTd)
		.appendTo("#table-saleOrderCheck");
	});
}
/**
 * 解析显示分页信息
 * @param data
 * @returns
 */
function build_SaleOrderCheckpage_info(data) {
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
function build_SaleOrderCheckpage_nav(data) {
	$("#page_nav_saleOrderCheck").empty();
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
			to_page_saleOrderCheck(1);
		});
		prePageLi.click(function() {
			to_page_saleOrderCheck(data.pageBean.curPage - 1);
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
			to_page_saleOrderCheck(data.pageBean.curPage + 1);
		});
		lastPageLi.click(function() {
			to_page_saleOrderCheck(data.pageBean.sumPages);
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
			to_page_saleOrderCheck(item);
		});
		ul.append(numLi);
	});
	//添加下一页和末页 的提示
	ul.append(nextPageLi).append(lastPageLi);
	var navEle = $("<nav></nav>").append(ul);
	navEle.appendTo("#page_nav_saleOrderCheck");
}
/**
 * 条件查询
 * @returns
 */
function searchSaleOrderOfCheck() {
	var checkSaleOrderNumber = $('#saleOrderCheckNumber').val();
	var tempSaleOrderCheckState = $('#SaleOrderCheckState').val();
	var checkSaleOrderStateNumber;
	if (tempSaleOrderCheckState=="审核通过") {
		checkSaleOrderStateNumber=1;
	}else if (tempSaleOrderCheckState=="待审核") {
		checkSaleOrderStateNumber=0;
	}
	to_page_saleOrderCheck(1,checkSaleOrderNumber,checkSaleOrderStateNumber);
}
/**
 * 审核通过销售单
 * @returns
 */
$(document).on("click",".sOrderAdoptCheck_btn",function(){
	var sorderId = $(this).attr("adopt-id");
	var saleOrderCheckUrl = "/sdims/ordercheck/modifysaleordercheck?sorderId="+sorderId;
	$.getJSON(saleOrderCheckUrl,function(data){
		if (data.success) {
			alert(data.successMsg);
			//返回当前页
			to_page_saleOrderCheck(currentPage);
		}else {
			alert(data.errMsg);
		}
	});
});
/**
 * 撤销通过销售单
 * @returns
 */
$(document).on("click",".sOrderRevokeCheck_btn",function(){
	var sorderId = $(this).attr("revoke-id");
	var saleOrderRevokeUrl = "/sdims/ordercheck/modifysaleorderrevoke?sorderId="+sorderId;
	$.getJSON(saleOrderRevokeUrl,function(data){
		if (data.success) {
			alert(data.successMsg);
			//返回当前页
			to_page_saleOrderCheck(currentPage);
		}else {
			alert(data.errMsg);
		}
	});
});