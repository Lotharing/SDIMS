package top.lothar.sdims.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import top.lothar.sdims.entity.Stock;

public interface StockDao {
	/*-------------------------------------------------库存管理（二次审核去调用订单更新模块）----------------------------------------------------*/
	/**
	 * 增加商品对应库存（商品和所属仓库）
	 * @param stock
	 * @return
	 */
	int insertStock(Stock stock);
	/**
	 * 更新库存（总数-进货增加，销售数-减少总数）业务层自动处理
	 * @param stock
	 * @return
	 */
	int updateStock(Stock stock);
	/**
	 * 删除库存记录(商品清除时候用)
	 * @param stockId
	 * @return
	 */
	int deleteStockById(@Param("stockId")long stockId,@Param("goodsId")long goodsId);
	/**
	 * 分页包括条件查询库存
	 * @return
	 */
	List<Stock> queryStockList(@Param("stockCondition")Stock stockCondition,@Param("rowIndex")int rowIndex, @Param("pageSize")int pageSize);
	/**
	 * 条件查询库存商品总数量
	 * @return
	 */
	int queryStockCount(@Param("stockCondition")Stock stockCondition);
	/**
	 * 订单入库时候，根据商品名和仓库名找到库存记录
	 * @param goodsName
	 * @param repositoryName
	 * @return
	 */
	Stock queryStockByGoodsNameAndRepositoryName(@Param("goodsName")String goodsName,@Param("repositoryName")String repositoryName);
}
