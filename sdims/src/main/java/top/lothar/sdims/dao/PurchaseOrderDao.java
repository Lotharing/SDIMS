package top.lothar.sdims.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import top.lothar.sdims.entity.PurchaseOrder;

public interface PurchaseOrderDao {
	
	/*-------------------------------------------------采购订单管理----------------------------------------------------*/
	/**
	 * 增加采购订单
	 * @param purchaseOrder
	 * @return
	 */
	int insertPurchaseOrder(PurchaseOrder purchaseOrder);
	/**
	 * 通过ID删除指定采购订单
	 * @param porderId
	 * @return
	 */
	int deletePurchaseOrderById(@Param("porderId")long porderId,@Param("goodsId")long goodsId);
	/**
	 * 更新订单信息（包括订单管理员的审核，入库的审核）
	 * @param purchaseOrder
	 * @return
	 */
	int updatePurchaseOrder(PurchaseOrder purchaseOrder);
	/**
	 * 根据ID查询对应订单
	 * @param porderId
	 * @return
	 */
	PurchaseOrder queryPurchaseOrderById(long porderId);
	/**
	 * 分页条件查询采购订单（1.商品名称，供应商名称，仓库，单号，订单经理所批审核状态）（2.在库存管理员中筛选订单经理已经审批的订单，可查看自己待审批的订单）
	 * @param purchaseOrderCondition
	 * @param rowIndex
	 * @param pageSize
	 * @return
	 */
	List<PurchaseOrder> queryPurchaseOrderList(@Param("purchaseOrderCondition")PurchaseOrder purchaseOrderCondition,@Param("rowIndex")int rowIndex, @Param("pageSize")int pageSize);
	/**
	 * 根据条件查询对应数量
	 * @param purchaseOrderCondition
	 * @return
	 */
	int queryPurchaseOrderCount(@Param("purchaseOrderCondition")PurchaseOrder purchaseOrderCondition);
}
