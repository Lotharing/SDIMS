package top.lothar.sdims.dao;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import top.lothar.sdims.BaseTest;
import top.lothar.sdims.entity.Customer;

public class SaleCustomerDaoTest extends BaseTest{
	
	@Autowired
	private SaleCustomerDao saleCustomerDao;
	
	@Ignore
	public void testAInsertSaleCustomer() {
		Customer customer = new Customer();
		customer.setName("伊滨钱村零售商");
		customer.setLinkName("关桐");
		customer.setMobile("456123789");
		customer.setAddress("钱村生活超市对面");
		customer.setCustomerDesc("零售商");
		customer.setUpdater("xiaoxiao");
		customer.setUpdateTime(new Date());
		int insertSaleCustomer = saleCustomerDao.insertSaleCustomer(customer);
		System.out.println(insertSaleCustomer);
	}
	
	@Ignore
	public void testBDeleteSaleCustomer() {
		long customerId = 3L;
		int deleteSaleCustomer = saleCustomerDao.deleteSaleCustomer(customerId);
		System.out.println(deleteSaleCustomer);
	}
	
	@Ignore
	public void testCUpdateSaleCustomer() {
		Customer customer = new Customer();
		customer.setCustomerId(2L);
		customer.setUpdateTime(new Date());
		int updateSaleCustomer = saleCustomerDao.updateSaleCustomer(customer);
		System.out.println(updateSaleCustomer);
	}
	
	@Ignore
	public void testDquerySaleCustomerById() {
		Customer querySaleCustomerById = saleCustomerDao.querySaleCustomerById(2L);
		System.out.println(querySaleCustomerById.getName());
	}
	
	@Ignore
	public void testEQuerySaleCustomerList() {
		List<Customer> customers = saleCustomerDao.querySaleCustomerList("伊滨李村零售商", 0, 5);
		for (Customer customer : customers) {
			System.out.println(customer.getName());
		}
	}
	
	@Ignore
	public void testFQuerySaleCustomerCount() {
		int querySaleCustomerCount = saleCustomerDao.querySaleCustomerCount("伊滨李村零售商");
		System.out.println(querySaleCustomerCount);
	}
	
	@Test
	public void testGetAllCustomerList() {
		List<Customer> queryAllCustomerList = saleCustomerDao.queryAllCustomerList();
		for (Customer customer : queryAllCustomerList) {
			System.out.println(customer.getCustomerId());
		}
	}
}
