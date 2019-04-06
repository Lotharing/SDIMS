package top.lothar.sdims.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import top.lothar.sdims.entity.SaleOrder;

public interface SaleOrderDao {
	/*-------------------------------------------------销售订单管理----------------------------------------------------*/
	/**
	 * 增加销售订单
	 * @param customerOrder
	 * @return
	 */
	int insertSaleOrder(SaleOrder saleOrder);
	/**
	 * 通过ID删除指定销售订单/根据商品ID删除指定销售订单
	 * @param sorderId
	 * @return
	 */
	int deleteSaleOrderById(@Param("sorderId")long sorderId,@Param("goodsId")long goodsId);
	/**
	 * 更新销售订单
	 * @param saleOrder
	 * @return
	 */
	int updateSaleOrder(SaleOrder saleOrder);
	/**
	 * 根据ID查询销售订单
	 * @param sorderId
	 * @return
	 */
	SaleOrder querySaleOrderById(long sorderId);
	/**
	 * 分页条件查询销售订单（商品名称，客户名称，仓库，单号，审核状态）
	 * @param saleOrderCondition
	 * @param rowIndex
	 * @param pageSize
	 * @return
	 */
	List<SaleOrder> querySaleOrderList(@Param("saleOrderCondition")SaleOrder saleOrderCondition,@Param("rowIndex")int rowIndex, @Param("pageSize")int pageSize);
	/**
	 * 根据对应信息查询数量
	 * @param saleOrderCondition
	 * @return
	 */
	int querySaleOrderCount(@Param("saleOrderCondition")SaleOrder saleOrderCondition);
}
