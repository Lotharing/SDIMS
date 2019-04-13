package top.lothar.sdims.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import top.lothar.sdims.entity.Goods;

public interface GoodsDao {
	/*-------------------------------------------------商品管理----------------------------------------------------*/
	/**
	  * 添加商品
	 * @param goods
	 * @return
	 */
	int insertGoods(Goods goods);
	/**
	  *  根据ID删除指定商品
	 * @param goodsId
	 * @return
	 */
	int deleteGoodsById(long goodsId);
	/**
	  * 更新商品
	 * @param goods
	 * @return
	 */
	int updateGoods(Goods goods);
	/**
	 * 根据商品Id查询商品
	 * @param goodsId
	 * @return
	 */
	Goods queryGoodsById(long goodsId);
	/**
	 * 查询所有的商品，在销售单和采购单创建时候select中填充商品信息
	 * @return
	 */
	List<Goods> queryAllGoodsList();
	/**
	  * 分页查询商品，模糊名（商品名称，商品编号，商品类别，商品品牌，商品规格，商品材质）
	 * @param goodsCondition
	 * @param rowIndex
	 * @param pageSize
	 * @return
	 */
	List<Goods> queryGoodsList(@Param("goodsCondition")Goods goodsCondition, @Param("rowIndex")int rowIndex, @Param("pageSize")int pageSize);
	/**
	  * 根据条件查询对应商品数量
	 * @param goodsCondition
	 * @return
	 */
	int queryGoodsCount(@Param("goodsCondition")Goods goodsCondition);
}
