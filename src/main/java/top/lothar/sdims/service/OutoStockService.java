package top.lothar.sdims.service;

import top.lothar.sdims.dto.TExecution;
import top.lothar.sdims.entity.SaleOrder;

public interface OutoStockService {
	/**
	 * 通过入库销售单审核状态
	 * @param purchaseOrder
	 * @return
	 */
	int modifyOutoStockSaleOrderCheck(SaleOrder saleOrder);
	/**
	 * 条件分页查询销售单(返回泛型销售单列表)
	 * @param purchaseOrderCondition
	 * @return
	 */
	TExecution<SaleOrder> getOutoStockPurchaseOrderCheckList(SaleOrder saleOrderCondition,int pageIndex, int pageSize);
}
