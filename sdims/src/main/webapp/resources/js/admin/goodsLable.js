//总记录数，当前页数，用于删除，添加时候，跳转指定页
var GoodstotalRecord,GoodscurrentPage;
/**
 * 创建商品管理Lable
 */
function getGoods() {
	var goodsLable = $('#goodsManager').text();
	var item = {
			'id' : '5',
			'name' : goodsLable,
			'url' : 'goods.html',
			'closable' : true
		};
		//调出模态框
		closableTab.addTab(item);
		//默认展示第一页
		to_page_goods(1);
}
/**
 * 页面跳转
 * @param pageIndex
 * @returns
 */
function to_page_goods(pageIndex,name,code,type,brand,standard,material) {
	$.ajax({
		url : "/sdims/admin/getgoodslist",
		dataType : 'json',
		data : {
			pageIndex : pageIndex,
			name : name,
			code : code,
			type : type,
			brand : brand,
			standard : standard,
			material : material
		},
		tpye : "GET",
		success : function(data) {
			// 1.解析并显示员工数据
			getGoodsInfoToTables(data);
			// 2.解析并显示分页信息（只用到记录全局属性）
			build_goodspage_info(data);
			// 3.解析显示分页条
			build_goodspage_nav(data);
		}

	})
}
/**
 * 得到请求的信息并展示前台
 * @param data
 * @returns
 */
function getGoodsInfoToTables(data) {
	$("#table-goods").empty();
	data.pageBean.datas.map(function(item, index) {
		//操作-编辑按钮
		var editBtn = $("<button></button>").addClass(
				"btn btn-primary btn-sm edit_goods_btn").append(
				$("<span></span>")
						.addClass("glyphicon glyphicon-pencil")).append(" ").append("编辑");
		//给编辑按钮添加一个自定义属性来标识当前编辑的员工
		editBtn.attr("edit-id", item.goodsId);
		//操作-删除按钮
		var delBtn = $("<button></button>").addClass(
				"btn btn-danger btn-sm delete_goods_btn").append(
				$("<span></span>")
						.addClass("glyphicon glyphicon-trash")).append(" ").append("删除");
		//给删除按钮添加一个自定义属性来标识当前删除的员工
		delBtn.attr("del-id", item.goodsId);
		//存放按钮的TD
		var btnTd = $("<td></td>").append(editBtn).append(" ").append(delBtn);
		var id = $("<td></td>").append(item.goodsId);
		var name = $("<td></td>").append(item.name);
		var code = $("<td></td>").append(item.code);
		var type = $("<td></td>").append(item.type);
		var brand = $("<td></td>").append(item.brand);
		var standard = $("<td></td>").append(item.standard);
		var material = $("<td></td>").append(item.material);
		var buyPrice = $("<td></td>").append(item.buyPrice);
		var salePrice = $("<td></td>").append(item.salePrice);
		var goodsDesc = $("<td></td>").append(item.goodsDesc);
		var updateTime = $("<td></td>").append(new Date(item.updateTime).format('Y-m-d H:i:s'));
		var picture = $("<td></td>").append(item.picture);
		$("<tr></tr>")
		.append(id)
		.append(name)
		.append(code)
		.append(type)
		.append(brand)
		.append(standard)
		.append(material)
		.append(buyPrice)
		.append(salePrice)
		.append(goodsDesc)
		.append(updateTime)
		.append(picture)
		.append(btnTd)
		.appendTo("#table-goods");
	});
}
/**
 * 解析显示分页信息
 * @param data
 * @returns
 */
function build_goodspage_info(data) {
	//最大页数
	GoodstotalRecord = data.pageBean.sumPages;
	//当前页数
	GoodscurrentPage = data.pageBean.curPage;
}
/**
 * 解析显示分页条
 * @param result
 * @returns
 */
function build_goodspage_nav(data) {
	$("#page_nav_goods").empty();
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
			to_page_goods(1);
		});
		prePageLi.click(function() {
			to_page_goods(data.pageBean.curPage - 1);
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
			to_page_goods(data.pageBean.curPage + 1);
		});
		lastPageLi.click(function() {
			to_page_goods(data.pageBean.sumPages);
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
			to_page_goods(item);
		});
		ul.append(numLi);
	});
	//添加下一页和末页 的提示
	ul.append(nextPageLi).append(lastPageLi);
	var navEle = $("<nav></nav>").append(ul);
	navEle.appendTo("#page_nav_goods");
}
/**
 * 条件查询
 * @returns
 */
function searchGoods() {
	var name = $('#goodsName').val();
	var code = $('#goodsCode').val();
	var type = $('#goodsType').val();
	var brand = $('#goodsBrand').val();
	var standard = $('#goodsStandard').val();
	var material = $('#goodsMaterial').val();
	//1页，名称条件查询
	to_page_goods(1,name,code,type,brand,standard,material);
}
/**
 * 添加模态框
 * @returns
 */
function addGoods() {
	$('#goodsModalLabel').text("添加信息");
	$('.modal-dialog').css({"margin":"60px auto"});
	$('.modal-header').css({"background-color":"#449D44"});
	$('#goodsModal').modal();
}
/**
 * 删除指定仓库
 * @returns
 */
$(document).on("click",".delete_goods_btn",function(){
	var goodsId = $(this).attr("del-id");
	var delGoodsUrl = "/sdims/admin/removegoods?goodsId="+goodsId;
	$.getJSON(delGoodsUrl,function(data){
		if (data.success) {
			alert(data.successMsg);
			//返回当前页
			to_page_goods(GoodscurrentPage);
		}else {
			alert(data.errMsg);
		}
	});
});
/**
 * 创建按钮的时候绑定点击事件(编辑按钮)
 * @returns
 */
$(document).on("click",".edit_goods_btn",function(){
	var goodsId = $(this).attr("edit-id");
	var goodsInfoUrl = "/sdims/admin/getgoodsbyid?goodsId="+goodsId;
	$('#EditgoodsModalLabel').text("编辑信息");
	$('.modal-dialog').css({"margin":"60px auto"});
	$('.modal-header').css({"background-color":"#449D44"});
	//根据ID获取的信息填充在模态框
	$.getJSON(goodsInfoUrl,function(data){
		if (data.success) {
			$('#goodsIdEdit').val(data.goods.goodsId);
			$('#goodsNameEdit').val(data.goods.name);
			$('#goodsCodeEdit').val(data.goods.code);
			$('#goodsTypeEdit').val(data.goods.type);
			$('#goodsBrandEdit').val(data.goods.brand);
			$('#goodsStandardEdit').val(data.goods.standard);
			$('#goodsMaterialEdit').val(data.goods.material);
			$('#goodsBuyPriceEdit').val(data.goods.buyPrice);
			$('#goodsSalePriceEdit').val(data.goods.salePrice);
			$('#goodsDescEdit').val(data.goods.goodsDesc);
			$('#goodsPictureEdit').val(data.goods.picture);
		}
	});
	$('#EditgoodsModal').modal();
});