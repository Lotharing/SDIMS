/*
 *onclick创建角色管理显示页面
 */
function PurchaseStatistics() {
	//得到左侧导航题目
	var PurchaseStatisticsName = $('#PurchaseStatisticsManager').text();
	var item = {
		'id' : '16',
		'name' : PurchaseStatisticsName,
		'url' : 'purchasestatistics.html',
		'closable' : true
	};
	closableTab.addTab(item);
}