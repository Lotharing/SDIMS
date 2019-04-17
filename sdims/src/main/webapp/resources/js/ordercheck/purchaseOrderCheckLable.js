//总记录数，当前页数，用于删除，添加时候，跳转指定页
var PordertotalRecord,PordercurrentPage;
/**
 * 创建采购单审核管理Lable
 */
function getPurchaseOrderCheck() {
	var purchaseOrderCheckLable = $('#purchaseOrderCheckLableName').text();
	var item = {
			'id' : '10',
			'name' : purchaseOrderCheckLable,
			'url' : 'purchaseordercheck.html',
			'closable' : true
		};
		//调出模态框
		closableTab.addTab(item);
		//默认展示第一页
		to_page_purchaseOrderCheck(1);
}
/**
 * 页面跳转
 * @param pageIndex
 * @returns
 */
function to_page_purchaseOrderCheck(pageIndex,checkOrderNumber,checkStateNumber) {
	$.ajax({
		url : "/sdims/ordercheck/getpurchaseorderchecklist",
		dataType : 'json',
		data : {
			pageIndex : pageIndex,
			checkOrderNumber : checkOrderNumber,
			checkStateNumber : checkStateNumber
		},
		tpye : "GET",
		success : function(data) {
			// 1.解析并显示采购单数据
			getPurchaseOrderCheckInfoToTables(data);
			// 2.解析并显示分页信息（只用到记录全局属性）
			build_PurchaseOrderCheckpage_info(data);
			// 3.解析显示分页条
			build_PurchaseOrderCheckpage_nav(data);
		}
	});
}
/**
 * 得到请求的信息并展示前台
 * @param data
 * @returns
 */
function getPurchaseOrderCheckInfoToTables(data) {
	$("#table-purchaseOrderCheck").empty();
	data.pageBean.datas.map(function(item, index) {
		//操作-编辑按钮
		var revokeCheckBtn = $("<button></button>").addClass(
				"btn btn-danger btn-xs pOrderRevokeCheck_btn").append(
				$("<span></span>")
						.addClass("glyphicon glyphicon-remove")).append(" ").append("撤销");
		//给按钮添加一个自定义属性来标识当前撤销审核的采购单
		revokeCheckBtn.attr("revoke-id", item.porderId);
		//操作-删除按钮
		var AdoptCheckBtn = $("<button></button>").addClass(
				"btn btn-primary btn-xs pOrderAdoptCheck_btn").append(
				$("<span></span>")
						.addClass("glyphicon glyphicon-ok")).append(" ").append("通过");
		//给按钮添加一个自定义属性来标识当前通过审核的采购单
		AdoptCheckBtn.attr("adopt-id", item.porderId);
		//存放按钮的TD
		var checkTd = $("<td></td>").append(revokeCheckBtn).append(" ").append(AdoptCheckBtn);
		var porderId = $("<td></td>").css("font-size","12px").append(item.porderId);
		var goodsName = $("<td></td>").css("font-size","12px").append(item.goods.name);
		var supplierName = $("<td></td>").css("font-size","12px").append(item.supplier.name);
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
		.append(checkTd)
		.appendTo("#table-purchaseOrderCheck");
	});
}
/**
 * 解析显示分页信息
 * @param data
 * @returns
 */
function build_PurchaseOrderCheckpage_info(data) {
	//最大页数
	PordertotalRecord = data.pageBean.sumPages;
	//当前页数
	PordercurrentPage = data.pageBean.curPage;
}
/**
 * 解析显示分页条
 * @param result
 * @returns
 */
function build_PurchaseOrderCheckpage_nav(data) {
	$("#page_nav_supplierOrderCheck").empty();
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
			to_page_purchaseOrderCheck(1);
		});
		prePageLi.click(function() {
			to_page_purchaseOrderCheck(data.pageBean.curPage - 1);
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
			to_page_purchaseOrderCheck(data.pageBean.curPage + 1);
		});
		lastPageLi.click(function() {
			to_page_purchaseOrderCheck(data.pageBean.sumPages);
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
			to_page_purchaseOrderCheck(item);
		});
		ul.append(numLi);
	});
	//添加下一页和末页 的提示
	ul.append(nextPageLi).append(lastPageLi);
	var navEle = $("<nav></nav>").append(ul);
	navEle.appendTo("#page_nav_supplierOrderCheck");
}
/**
 * 条件查询
 * @returns
 */
function searchPurchaseOrderOfCheck() {
	var checkOrderNumber = $('#purchaseOrderCheckNumber').val();
	var tempPurchaseOrderCheckState = $('#PurchaseOrderCheckState').val();
	var checkStateNumber;
	if (tempPurchaseOrderCheckState=="审核通过") {
		checkStateNumber=1;
	}else if (tempPurchaseOrderCheckState=="待审核") {
		checkStateNumber=0;
	}
	to_page_purchaseOrderCheck(1,checkOrderNumber,checkStateNumber);
}
/**
 * 审核通过采购单
 * @returns
 */
$(document).on("click",".pOrderAdoptCheck_btn",function(){
	var porderId = $(this).attr("adopt-id");
	var purchaseOrderCheckUrl = "/sdims/ordercheck/modifypurchaseordercheck?porderId="+porderId;
	//获取审核状态文字
	var checkStateDom = $(this).parent().prev().prev().prev();
	var checkFont = checkStateDom.text();
	//已审核的不需要再次去审核
	if (checkFont=="审核通过") {
		alert("已审核!!!");
	}else{
		$.getJSON(purchaseOrderCheckUrl,function(data){
			if (data.success) {
				alert(data.successMsg);
				//返回当前页
				to_page_purchaseOrderCheck(PordercurrentPage);
			}else {
				alert(data.errMsg);
			}
		});
	}
});
/**
 * 撤销通过采购单
 * @returns
 */
$(document).on("click",".pOrderRevokeCheck_btn",function(){
	var porderId = $(this).attr("revoke-id");
	var purchaseOrderRevokeUrl = "/sdims/ordercheck/modifypurchaseorderrevoke?porderId="+porderId;
	$.getJSON(purchaseOrderRevokeUrl,function(data){
		if (data.success) {
			alert(data.successMsg);
			//返回当前页
			to_page_purchaseOrderCheck(PordercurrentPage);
		}else {
			alert(data.errMsg);
		}
	});
});