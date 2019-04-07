package top.lothar.sdims.service;

import top.lothar.sdims.dto.TExecution;
import top.lothar.sdims.entity.PurchaseOrder;

public interface PurchaseOrderCheckService {
	/**
	 * 通过采购单审核状态
	 * @param purchaseOrder
	 * @return
	 */
	int modifyPurchaseOrderCheck(PurchaseOrder purchaseOrder);
	/**
	 * 撤销采购单审核状态
	 * @param purchaseOrder
	 * @return
	 */
	int modifyPurchaseOrderRevoke(PurchaseOrder purchaseOrder);
	/**
	 * 条件分页查询采购单(返回泛型销售单列表)
	 * @param purchaseOrderCondition
	 * @return
	 */
	TExecution<PurchaseOrder> getPurchaseOrderCheckList(PurchaseOrder purchaseOrderCondition,int pageIndex, int pageSize);
}
