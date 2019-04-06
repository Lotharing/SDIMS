package top.lothar.sdims.service;

import top.lothar.sdims.dto.TExecution;
import top.lothar.sdims.entity.PurchaseOrder;

public interface PurchaseOrderService {
	/**
	 * 添加采购单
	 * @param purchaseOrder
	 * @return
	 */
	int addPurchaseOrder(PurchaseOrder purchaseOrder);
	/**
	 * 删除采购单
	 * @param porderId
	 * @return
	 */
	int removePurchaseOrderById(long porderId);
	/**
	 * 更新采购单
	 * @param purchaseOrder
	 * @return
	 */
	int modifyPurchaseOrder(PurchaseOrder purchaseOrder);
	/**
	 * 根据订单ID查找采购单
	 * @param porderId
	 * @return
	 */
	PurchaseOrder getPurchaseOrderById(long porderId);
	/**
	 * 条件分页查询采购单(返回泛型订单列表)
	 * @param purchaseOrderCondition
	 * @return
	 */
	TExecution<PurchaseOrder> getPurchaseOrderList(PurchaseOrder purchaseOrderCondition,int pageIndex, int pageSize);
}
