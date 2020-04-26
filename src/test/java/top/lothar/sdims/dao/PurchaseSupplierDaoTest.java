package top.lothar.sdims.dao;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import top.lothar.sdims.BaseTest;
import top.lothar.sdims.entity.Supplier;

public class PurchaseSupplierDaoTest extends BaseTest{
	
	@Autowired
	private PurchaseSupplierDao purchaseSupplierDao;
	
	@Ignore
	public void testAInsertPurchaseSupplie() {
		Supplier supplier = new Supplier();
		supplier.setName("真是强供销商");
		supplier.setLinkName("刘雷");
		supplier.setMobile("123456789");
		supplier.setAddress("广东省真强公司");
		supplier.setSupplierDesc("很好");
		supplier.setUpdateTime(new Date());
		int insertPurchaseSupplie = purchaseSupplierDao.insertPurchaseSupplie(supplier);
		System.out.println(insertPurchaseSupplie);
	}
	
	@Ignore
	public void testBDeletePurchaseSupplieById() {
		long supplierId = 3L;
		int deletePurchaseSupplieById = purchaseSupplierDao.deletePurchaseSupplieById(supplierId);
		System.out.println(deletePurchaseSupplieById);
	}
	
	@Ignore
	public void testCUpdatePurchaseSupplie() {
		Supplier supplier = new Supplier();
		supplier.setSupplierId(2L);
		supplier.setUpdateTime(new Date());
		int updatePurchaseSupplie = purchaseSupplierDao.updatePurchaseSupplie(supplier);
		System.out.println(updatePurchaseSupplie);
	}
	
	@Ignore
	public void testDQueryPurchaseSupplierById() {
		long supplierId = 2L;
		Supplier queryPurchaseSupplierById = purchaseSupplierDao.queryPurchaseSupplierById(supplierId);
		System.out.println(queryPurchaseSupplierById.getName()+queryPurchaseSupplierById.getLinkName());
	}
	
	@Ignore
	public void testEQueryPurchaseSupplierList() {
		List<Supplier> suppliers = purchaseSupplierDao.queryPurchaseSupplierList("冠仕供应商", 0, 10);
		for (Supplier supplier : suppliers) {
			System.out.println(supplier.getName());
		}
	}
	
	@Ignore
	public void testFQueryPurchaseSupplierCount() {
		int queryPurchaseSupplierCount = purchaseSupplierDao.queryPurchaseSupplierCount("冠仕供应商");
		System.out.println(queryPurchaseSupplierCount);
	}
	
	@Test
	public void testGetAllSupplierList() {
		List<Supplier> queryAllSupplierList = purchaseSupplierDao.queryAllSupplierList();
		for (Supplier supplier : queryAllSupplierList) {
			System.out.println(supplier.getName());
		}
	}
}
