//总记录数，当前页数，用于删除，添加时候，跳转指定页
var StocktotalRecord,StockcurrentPage;
/**
 * 创建仓库管理Lable
 */
function getStock() {
	var stockManagerName = $('#stockManagerName').text();
	var item = {
			'id' : '12',
			'name' : stockManagerName,
			'url' : 'stock.html',
			'closable' : true
		};
		//调出模态框
		closableTab.addTab(item);
		//默认展示第一页
		to_page_stock(1);
}
/**
 * 页面跳转
 * @param pageIndex
 * @returns
 */
function to_page_stock(pageIndex,goodsName,repositoryName) {
	$.ajax({
		url : "/sdims/stock/getstocklist",
		dataType : 'json',
		data : {
			pageIndex : pageIndex,
			goodsName : goodsName,
			repositoryName : repositoryName,
		},
		tpye : "GET",
		success : function(data) {
			// 1.解析并显示员工数据
			getStockInfoToTables(data);
			// 2.解析并显示分页信息（只用到记录全局属性）
			build_Stock_info(data);
			// 3.解析显示分页条
			build_Stock_nav(data);
		}
	});
}
/**
 * 得到请求的信息并展示前台
 * @param data
 * @returns
 */
function getStockInfoToTables(data) {
	$("#table-stock").empty();
	data.pageBean.datas.map(function(item, index) {
		var stockId = $("<td></td>").append(item.stockId);
		var goodsName = $("<td></td>").append(item.goods.name);
		var repositoryName = $("<td></td>").append(item.repository.name);
		var totalCount = $("<td></td>").append(item.totalCount);
		var saleCount = $("<td></td>").append(item.saleCount);
		var buyPrice = $("<td></td>").append(item.buyPrice);
		var salePrice = $("<td></td>").append(item.salePrice);
		var totalBuyPrice = $("<td></td>").append(item.totalBuyPrice);
		var totalSalePrice = $("<td></td>").append(item.totalSalePrice);
		var updateTime = $("<td></td>").append(new Date(item.updateTime).format('Y-m-d H:i:s'));
		$("<tr></tr>")
		.append(stockId)
		.append(goodsName)
		.append(repositoryName)
		.append(totalCount)
		.append(saleCount)
		.append(buyPrice)
		.append(salePrice)
		.append(totalBuyPrice)
		.append(totalSalePrice)
		.append(updateTime)
		.appendTo("#table-stock");
	});
}
/**
 * 解析显示分页信息
 * @param data
 * @returns
 */
function build_Stock_info(data) {
	//最大页数
	StocktotalRecord = data.pageBean.sumPages;
	//当前页数
	StockcurrentPage = data.pageBean.curPage;
}
/**
 * 解析显示分页条
 * @param result
 * @returns
 */
function build_Stock_nav(data) {
	$("#page_nav_stock").empty();
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
			to_page_stock(1);
		});
		prePageLi.click(function() {
			to_page_stock(data.pageBean.curPage - 1);
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
			to_page_stock(data.pageBean.curPage + 1);
		});
		lastPageLi.click(function() {
			to_page_stock(data.pageBean.sumPages);
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
			to_page_stock(item);
		});
		ul.append(numLi);
	});
	//添加下一页和末页 的提示
	ul.append(nextPageLi).append(lastPageLi);
	var navEle = $("<nav></nav>").append(ul);
	navEle.appendTo("#page_nav_stock");
}
/**
 * 条件查询
 * @returns
 */
function searchStock() {
	var goodsName = $('#stockGoodsName').val();
	var repositoryName = $('#stockRepositoryName').val();
	//1页,名称条件查询
	to_page_stock(1,goodsName,repositoryName);
}