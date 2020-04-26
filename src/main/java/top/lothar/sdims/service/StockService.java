package top.lothar.sdims.service;

import top.lothar.sdims.dto.TExecution;
import top.lothar.sdims.entity.Stock;

public interface StockService {
	/**
	 * 在采购单找不到对应（商品，仓库）时增加库存记录
	 * @param stock
	 * @return
	 */
	int addStock(Stock stock);
	/**
	 * 通过审核的订单，对库存的总数，销售量进行更改
	 * @param stock
	 * @return
	 */
	int modifyStock(Stock stock);
	/**
	 * 分页查询库存记录，返回泛型(Stock列表，count总数，stateInfo状态信息)
	 * @param stockCondition
	 * @param rowIndex
	 * @param pageSize
	 * @return
	 */
	TExecution<Stock> getStockList(Stock stockCondition,int pageIndex,int pageSize);
}
