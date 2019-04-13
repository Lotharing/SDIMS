package top.lothar.sdims.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import top.lothar.sdims.entity.Supplier;

public interface PurchaseSupplierDao {

	/*-------------------------------------------------供应商管理----------------------------------------------------*/
	/**
	 * 添加供应商
	 * @param supplier
	 * @return
	 */
	int insertPurchaseSupplie(Supplier supplier);
	/**
	 * 根据供应商ID删除指定供应商
	 * @param supplierId
	 * @return
	 */
	int deletePurchaseSupplieById(long supplierId);
	/**
	 * 更改供应商
	 * @param supplier
	 * @return
	 */
	int updatePurchaseSupplie(Supplier supplier);
	/**
	 * 根据ID查询对应的供应商
	 * @param supplierId
	 * @return
	 */
	Supplier queryPurchaseSupplierById(long supplierId);
	/**
	 * 查询所有供应商，在生成采购订单时候，渲染select
	 * @return
	 */
	List<Supplier> queryAllSupplierList();
	/**
	 * 查询所有供应商(名称查询)
	 * @return
	 */
	List<Supplier> queryPurchaseSupplierList(@Param("supplierName")String supplierName,@Param("rowIndex")int rowIndex, @Param("pageSize")int pageSize);
	/**
	 * 查询供应商总数量（分页信息计算）
	 * @return
	 */
	int queryPurchaseSupplierCount(@Param("supplierName")String supplierName);
}
