package top.lothar.sdims.service;


import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import top.lothar.sdims.BaseTest;
import top.lothar.sdims.dto.TExecution;
import top.lothar.sdims.entity.Goods;

public class GoodsServiceTest extends BaseTest{
	
	@Autowired
	private GoodsService goodsService;
	@Ignore
	public void testDeleteGoodsById() {
		long goodsId = 3L;
		int removeGoods = goodsService.removeGoods(goodsId);
		System.out.println(removeGoods);
	}
	
	@Ignore
	public void testQueryGoodsList() {
		TExecution<Goods> goodsExecution = goodsService.getGoodsList(null, 1, 5);
		System.out.println(goodsExecution.getData().size());
		System.out.println(goodsExecution.getData().get(1).getName());
	}
}
