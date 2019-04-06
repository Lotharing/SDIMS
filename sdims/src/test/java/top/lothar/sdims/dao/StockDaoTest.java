package top.lothar.sdims.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import top.lothar.sdims.BaseTest;
import top.lothar.sdims.entity.Goods;
import top.lothar.sdims.entity.Repository;
import top.lothar.sdims.entity.Stock;

public class StockDaoTest extends BaseTest{
	
	@Autowired
	private StockDao stockDao;
	
	@Ignore
	public void testAInsertStock() {
		Stock stock = new Stock();
		Goods goods = new Goods();
		goods.setGoodsId(2L);
		Repository repository = new Repository();
		repository.setRepoId(3L);
		stock.setGoods(goods);
		stock.setRepository(repository);
		stock.setUpdateTime(new Date());
		int insertStock = stockDao.insertStock(stock);
		System.out.println(insertStock);
	}
	
	@Ignore
	public void testBQueryStockList() {
		Stock stockConditon = new Stock();
		Goods goods = new Goods();
		goods.setName("烤漆防盗门");
		Repository repository = new Repository();
		repository.setName("平顶山仓库");
//		stockConditon.setGoods(goods);
		stockConditon.setRepository(repository);
		List<Stock> queryStockList = stockDao.queryStockList(stockConditon,0,5);
		for (Stock stock : queryStockList) {
			System.out.println(stock.getUpdateTime());
		}
	}
	
	@Ignore
	public void testCQueryStockCount() {
		Stock stockConditon = new Stock();
		Goods goods = new Goods();
		goods.setName("烤漆防盗门");
		Repository repository = new Repository();
		repository.setName("洛阳洛龙区");
//		stockConditon.setGoods(goods);
		stockConditon.setRepository(repository);
		int queryStockCount = stockDao.queryStockCount(stockConditon);
		System.out.println(queryStockCount);
	}
	
	@Ignore
	public void testDUpdateStock() {
		Stock stock = new Stock();
		Goods goods = new Goods();
		goods.setGoodsId(1L);
		Repository repository = new Repository();
		repository.setRepoId(1L);
		stock.setGoods(goods);
		stock.setRepository(repository);
		stock.setStockId(4L);
		stock.setTotalCount(100);
		stock.setUpdateTime(new Date());
		int updateStock = stockDao.updateStock(stock);
		System.out.println(updateStock);
	}
	@Ignore
	public void testEDeleteStock() {
		long goodsId = 2L;
		long stockId = 0L;
		int deleteStockById = stockDao.deleteStockById(stockId, goodsId);
		System.out.println(deleteStockById);
	}
}
