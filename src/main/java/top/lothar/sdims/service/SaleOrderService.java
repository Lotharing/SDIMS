package top.lothar.sdims.service;

import top.lothar.sdims.dto.TExecution;
import top.lothar.sdims.entity.SaleOrder;

public interface SaleOrderService {
	/**
	 * 添加销售单
	 * @param saleOrder
	 * @return
	 */
	int addSaleOrder(SaleOrder saleOrder);
	/**
	 * 删除销售单
	 * @param sorderId
	 * @return
	 */
	int removeSaleOrder(long sorderId);
	/**
	 * 更新销售单
	 * @param saleOrder
	 * @return
	 */
	int modifySaleOrder(SaleOrder saleOrder);
	/**
	 * 根据ID查询销售单
	 * @param sorderId
	 * @return
	 */
	SaleOrder getSaleOrderById(long sorderId);
	/**
	 * 条件分页查询销售单列表，泛型（列表，总数，状态）
	 * @param saleOrderCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	TExecution<SaleOrder> getSaleOrderList(SaleOrder saleOrderCondition,int pageIndex, int pageSize);
}
