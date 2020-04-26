package top.lothar.sdims.dao;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import top.lothar.sdims.BaseTest;
import top.lothar.sdims.entity.Customer;
import top.lothar.sdims.entity.Goods;
import top.lothar.sdims.entity.Repository;
import top.lothar.sdims.entity.SaleOrder;

public class SaleOrderDaoTest extends BaseTest{
	
	@Autowired
	private SaleOrderDao saleOrderDao;
	
	@Test
	public void testAInsertSaleOrder() {
		Goods goods = new Goods();
		goods.setGoodsId(2L);
		Customer customer = new Customer();
		customer.setCustomerId(1L);
		Repository repository = new Repository();
		repository.setRepoId(1L);
		SaleOrder saleOrder = new SaleOrder();
		saleOrder.setGoods(goods);
		saleOrder.setCustomer(customer);
		saleOrder.setRepository(repository);
		saleOrder.setCreateTime(new Date());
		saleOrder.setCheckState(0);
		saleOrder.setCheckResult("待审核");
		int insertSaleOrder = saleOrderDao.insertSaleOrder(saleOrder);
		System.out.println(insertSaleOrder);
	}
	
	@Ignore
	public void testBDeleteSaleOrderById() {
		long sorderId = 0L;
		long goodsId = 2L;
		int deleteSaleOrderById = saleOrderDao.deleteSaleOrderById(sorderId,goodsId);
		System.out.println(deleteSaleOrderById);
	}
	
	@Ignore
	public void testCUpdateSaleOrder() {
		Goods goods = new Goods();
		goods.setGoodsId(2L);
		Customer customer = new Customer();
		customer.setCustomerId(2L);
		Repository repository = new Repository();
		repository.setRepoId(3L);
		SaleOrder saleOrder = new SaleOrder();
		saleOrder.setSorderId(2L);
		saleOrder.setGoods(goods);
		saleOrder.setCustomer(customer);
		saleOrder.setRepository(repository);
		saleOrder.setCustomerName("伊滨赵村经销商");
		saleOrder.setCreateTime(new Date());
		saleOrder.setCheckState(1);
		saleOrder.setCheckResult("审核通过");
		int updateSaleOrder = saleOrderDao.updateSaleOrder(saleOrder);
		System.out.println(updateSaleOrder);
	}
	
	@Ignore
	public void testDQuerySaleOrderById() {
		SaleOrder querySaleOrderById = saleOrderDao.querySaleOrderById(2L);
		System.out.println(querySaleOrderById.getCustomer().getName());
	}

	@Ignore
	public void testEQuerySaleOrderList() {
		SaleOrder saleOrderCondition = new SaleOrder();
		//商品名称条件查询
		Goods goods = new Goods();
		goods.setName("实木防盗门");
		//销售商商名称查询
		Customer customer = new Customer();
		customer.setName("伊滨李村零售商");
		//仓库名称查询
		Repository repository = new Repository();
		repository.setName("洛阳洛龙区");
	
//		saleOrderCondition.setRepository(repository);
//		saleOrderCondition.setCustomer(customer);
//		saleOrderCondition.setGoods(goods);
//		saleOrderCondition.setOrderNumber("20190315123456");
		saleOrderCondition.setCheckState(1);
		List<SaleOrder> querySaleOrderList = saleOrderDao.querySaleOrderList(saleOrderCondition, 0, 5);
		for (SaleOrder saleOrder : querySaleOrderList) {
			System.out.println(saleOrder.getCustomerName());
		}
	}
	
	@Ignore
	public void testFQuerySaleOrderCount() {
		SaleOrder saleOrderCondition = new SaleOrder();
		//商品名称条件查询
		Goods goods = new Goods();
		goods.setName("实木防盗门");
		//销售商商名称查询
		Customer customer = new Customer();
		customer.setName("伊滨李村零售商");
		//仓库名称查询
		Repository repository = new Repository();
		repository.setName("洛阳洛龙区");
	
//		saleOrderCondition.setRepository(repository);
//		saleOrderCondition.setCustomer(customer);
//		saleOrderCondition.setGoods(goods);
//		saleOrderCondition.setOrderNumber("20190315123456");
		saleOrderCondition.setCheckState(1);
		int querySaleOrderCount = saleOrderDao.querySaleOrderCount(saleOrderCondition);
		System.out.println(querySaleOrderCount);
	}
}
