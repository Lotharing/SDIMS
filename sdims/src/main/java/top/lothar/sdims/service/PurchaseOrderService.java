package top.lothar.sdims.service;

import top.lothar.sdims.dto.TExecution;
import top.lothar.sdims.entity.PurchaseOrder;

public interface PurchaseOrderService {
	/**
	 * 添加订单
	 * @param purchaseOrder
	 * @return
	 */
	int addPurchaseOrder(PurchaseOrder purchaseOrder);
	/**
	 * 删除订单
	 * @param porderId
	 * @return
	 */
	int removePurchaseOrderById(long porderId);
	/**
	 * 更新订单
	 * @param purchaseOrder
	 * @return
	 */
	int modifyPurchaseOrder(PurchaseOrder purchaseOrder);
	/**
	 * 根据订单ID查找订单
	 * @param porderId
	 * @return
	 */
	PurchaseOrder getPurchaseOrderById(long porderId);
	/**
	 * 条件查询订单(返回泛型订单列表)
	 * @param purchaseOrderCondition
	 * @return
	 */
	TExecution<PurchaseOrder> getPurchaseOrderList(PurchaseOrder purchaseOrderCondition,int pageIndex, int pageSize);
}
