/*
 *onclick创建角色管理显示页面
 */
function SaleStatistics() {
	//得到左侧导航题目
	var SaleStatisticsName = $('#SaleStatisticsManager').text();
	var item = {
		'id' : '17',
		'name' : SaleStatisticsName,
		'url' : 'salestatistics.html',
		'closable' : true
	};
	closableTab.addTab(item);
}