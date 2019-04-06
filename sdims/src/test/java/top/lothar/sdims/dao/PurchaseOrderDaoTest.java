package top.lothar.sdims.dao;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import top.lothar.sdims.BaseTest;
import top.lothar.sdims.entity.Goods;
import top.lothar.sdims.entity.PurchaseOrder;
import top.lothar.sdims.entity.Repository;
import top.lothar.sdims.entity.Supplier;

public class PurchaseOrderDaoTest extends BaseTest{
	
	@Autowired
	private PurchaseOrderDao purchaseOrderDao;
	
	@Test
	public void testAInsertPurchaseOrder() {
		Goods goods = new Goods();
		goods.setGoodsId(1L);
		Supplier supplier = new Supplier();
		supplier.setSupplierId(1L);
		Repository repository = new Repository();
		repository.setRepoId(1L);
		PurchaseOrder purchaseOrder = new PurchaseOrder();
		purchaseOrder.setGoods(goods);
		purchaseOrder.setSupplier(supplier);
		purchaseOrder.setRepository(repository);
		purchaseOrder.setOrderDesc("采购50件");
		purchaseOrder.setCreateTime(new Date());
		int insertPurchaseOrder = purchaseOrderDao.insertPurchaseOrder(purchaseOrder);
		System.out.println(insertPurchaseOrder);
	}
	
	@Ignore
	public void testBDeletePurchaseOrderById() {
		long porderId = 0L;
		long goodsId = 1L;
		int deletePurchaseOrderById = purchaseOrderDao.deletePurchaseOrderById(porderId,goodsId);
		System.out.println(deletePurchaseOrderById);
	}
	
	@Ignore
	public void testCUpdatePurchaseOrder() {
		PurchaseOrder purchaseOrder = new PurchaseOrder();
		purchaseOrder.setPorderId(3L);
		
		Goods goods = new Goods();
		goods.setGoodsId(1L);
		purchaseOrder.setGoods(goods);
		purchaseOrder.setSupplierName("供应商");
		purchaseOrder.setStockTime(new Date());
		purchaseOrder.setCreateTime(new Date());
		int updatePurchaseOrder = purchaseOrderDao.updatePurchaseOrder(purchaseOrder);
		System.out.println(updatePurchaseOrder);
	}

	@Ignore
	public void testDQueryPurchaseOrderById() {
		PurchaseOrder queryPurchaseOrderById = purchaseOrderDao.queryPurchaseOrderById(1L);
		System.out.println(queryPurchaseOrderById.getGoods().getName());
	}

	@Ignore
	public void testEQueryPurchaseOrderList() {
		//商品名称条件查询
		Goods goods = new Goods();
		goods.setName("实木防盗门");
		//供应商名称查询
		Supplier supplier = new Supplier();
		supplier.setName("冠仕供应商");
		//仓库名称查询
		Repository repository = new Repository();
		repository.setName("洛阳洛龙区");
		
		
		PurchaseOrder purchaseOrderCondition = new PurchaseOrder();
//		purchaseOrderCondition.setGoods(goods);
//		purchaseOrderCondition.setSupplier(supplier);
//		purchaseOrderCondition.setRepository(repository);
//		purchaseOrderCondition.setOrderNumber("02");
		purchaseOrderCondition.setCheckState(1);
		List<PurchaseOrder> queryPurchaseOrderList = purchaseOrderDao.queryPurchaseOrderList(purchaseOrderCondition, 0, 10);
		for (PurchaseOrder purchaseOrder : queryPurchaseOrderList) {
			System.out.println(purchaseOrder.getSupplierName());
		}
		System.out.println(queryPurchaseOrderList.size());
	}
	
	@Ignore
	public void testFQueryPurchaseOrderCount() {
		//商品名称条件查询
		Goods goods = new Goods();
		goods.setName("实木防盗门");
		//供应商名称查询
		Supplier supplier = new Supplier();
		supplier.setName("冠仕供应商");
		//仓库名称查询
		Repository repository = new Repository();
		repository.setName("洛阳洛龙区");
		PurchaseOrder purchaseOrderCondition = new PurchaseOrder();
//		purchaseOrderCondition.setGoods(goods);
//		purchaseOrderCondition.setSupplier(supplier);
//		purchaseOrderCondition.setRepository(repository);
		purchaseOrderCondition.setOrderNumber("02");
//		purchaseOrderCondition.setCheckState(1);
		int queryPurchaseOrderCount = purchaseOrderDao.queryPurchaseOrderCount(purchaseOrderCondition);
		System.out.println(queryPurchaseOrderCount);
	}
}
