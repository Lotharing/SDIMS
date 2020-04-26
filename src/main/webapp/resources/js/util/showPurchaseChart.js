//默认引入js文件时，执行函数
var chartOutChar = null;
//-----------------折线图数据--------------------
option = {
	backgroundColor : '#F5FFFA',
	title : {
		text : '采购统计',
		top : 3,
		left : 10
	},
	tooltip : {
		trigger : 'axis'
	},
	// legend: {
	// top:32,
	// left:'center',
	// data:['111','222']
	// },
	grid : {
		top : 60,
		right : 70,
		bottom : 30,
		left : 60
	},
	toolbox : {
		show : true,
		top : 10,
		right : 10,
		feature : {
			mark : {
				show : true
			},
			magicType : {
				show : true,
				type : [ 'line', 'bar' ]
			},
			restore : {
				show : true
			},
			saveAsImage : {
				show : true
			}
		}
	},
	calculable : true,
	xAxis : [ {
		type : 'category',
		data : [ '1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月',
				'11月', '12月' ]
	} ],
	yAxis : [
	// 左侧Y轴
	{
		type : 'value',
		name : "采\n购\n量\n数\n︵\n件\n︶",
		nameLocation : "center",
		nameGap : 35,
		nameRotate : 0,
		splitLine:{ //去掉背景水平网格线
		 show:false
		},
		min : 0,
		max : 2000,
		nameTextStyle : {
			fontSize : 15,
		},
		// 默认以千分位显示，不想用的可以在这加一段
		axisLabel : { // 调整左侧Y轴刻度， 直接按对应数据显示
			show : true,
			showMinLabel : true,
			showMaxLabel : true,
			formatter : function(value) {
				return value;
			}
		}
	},
	// 右侧Y轴
	{
		type : 'value',
		name : "采\n购\n金\n额\n︵\n元\n︶",
		nameLocation : "center",
		nameGap : 55,
		nameRotate : 0,
		min : 0,
		max : 999999,
		nameTextStyle : {
			fontSize : 15,
		},
		// 默认以千分位显示，不想用的可以在这加一段
		axisLabel : { // 调整右侧Y轴刻度， 直接按对应数据显示
			show : true,
			showMinLabel : true,
			showMaxLabel : true,
			formatter : function(value) {
				return value;
			}
		}
	} ],
	series : [
	// 依据左侧Y轴展示
	{
		name : '采购量',
		type : 'line',
		smooth : true,
		yAxisIndex : 0,
		data : [],
		itemStyle : {
			normal : {
				label : {
					show : true
				}
			}
		},
	},
	// 依据右侧Y轴展示
	{
		name : '采购金额',
		type : 'line',
		smooth : true,
		yAxisIndex : 1,
		data : [],
		itemStyle : {
			normal : {
				label : {
					show : true
				}
			}
		},
	} ]
};
$(function() {
	chartOutChar = echarts.init(document.getElementById('main'));
	// 展示加载提示
	chartOutChar.showLoading({
		text : "采购数据正在努力加载..."
	});
	// 设置图表信息
	chartOutChar.setOption(option);
	// 隐藏加载提示
	chartOutChar.hideLoading();
	// 请求数据并修改option值进行展示
	getChartData();
});
// 从后台得到数据
function getChartData() {
	// 各个月份总数量数组
	var totalCountData = [];
	// 各个月份总采购额数组
	var totalPriceData = [];
	// 后台数据地址
	var echartsDataUrl = "/sdims/admin/getechartsdatalist";
	// 获得图表的options对象
	var options = chartOutChar.getOption();
	// 通过Ajax获取数据
	$.ajax({
		type : "get",
		async : false, // 同步执行
		url : echartsDataUrl,
		data : {},
		dataType : "json", // 返回数据形式为json
		success : function(data) {
			if (data.success) {
				$.each(data.echartsDataList, function(index, item) {
					totalCountData[index] = item.totalCount;
					totalPriceData[index] = item.totalPrice;
				})
				options.series[0].data = totalCountData;
				options.series[1].data = totalPriceData;
				// //隐藏加载提示，展示新的option
				chartOutChar.hideLoading();
				chartOutChar.setOption(options);
			} else {
				alert("获取数据失败");
			}
		}
	});
}