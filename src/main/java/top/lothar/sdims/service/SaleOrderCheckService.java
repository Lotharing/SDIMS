package top.lothar.sdims.service;

import top.lothar.sdims.dto.TExecution;
import top.lothar.sdims.entity.SaleOrder;

public interface SaleOrderCheckService {
	/**
	 * 通过销售单审核状态
	 * @param saleOrder
	 * @return
	 */
	int modifySaleOrderCheck(SaleOrder saleOrder);
	/**
	 * 撤销销售单审核状态
	 * @param saleOrder
	 * @return
	 */
	int modifySaleOrderRevoke(SaleOrder saleOrder);
	/**
	 *  条件分页查询销售单(返回泛型销售单列表)
	 * @param saleOrderCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	TExecution<SaleOrder> getPurchaseOrderCheckList(SaleOrder saleOrderCondition,int pageIndex, int pageSize);
}
