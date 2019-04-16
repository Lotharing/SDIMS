package top.lothar.sdims.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import top.lothar.sdims.entity.PurchaseOrder;
import top.lothar.sdims.entity.SaleOrder;

public interface EchartsDataDao {
	
	/**
	 * 根据已入库和起始时间查询采购单（采购量，采购金额）
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<PurchaseOrder> queryPurchaseOrderListByDate(@Param("startDate")String startDate,@Param("endDate")String endDate);
	/**
	 * 根据已出库和起始时间查询销售单（销售量，销售金额）
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<SaleOrder> querySaleOrderListByDate(@Param("startDate")String startDate,@Param("endDate")String endDate);
}
