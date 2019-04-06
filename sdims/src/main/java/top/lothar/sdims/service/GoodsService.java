package top.lothar.sdims.service;

import top.lothar.sdims.dto.TExecution;
import top.lothar.sdims.entity.Goods;

public interface GoodsService {
	/**
	 * 添加商品
	 * @param goods
	 * @return
	 */
	int addGoods(Goods goods);
	/**
	 * 删除商品
	 * @param goodsId
	 * @return
	 */
	int removeGoods(long goodsId);
	/**
	 * 更新商品
	 * @param goods
	 * @return
	 */
	int modifyGoods(Goods goods);
	/**
	 * 根据商品Id得到商品
	 * @param goodsId
	 * @return
	 */
	Goods getGoodsById(long goodsId);
	/**
	 * 条件查询商品列表，泛型返回商品集合
	 * @param goodsCondition
	 * @param rowIndex
	 * @param pageSize
	 * @return
	 */
	TExecution<Goods> getGoodsList(Goods goodsCondition,int pageIndex,int pageSize);
}
