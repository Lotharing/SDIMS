package top.lothar.sdims.service;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import top.lothar.sdims.BaseTest;
import top.lothar.sdims.entity.Customer;

public class SaleCustomerServiceTest extends BaseTest{
	
	@Autowired
	private SaleCustomerService saleCustomerService;
	
	@Test
	public void test() {
		Customer customer = new Customer();
		customer.setCustomerId(6L);
		customer.setUpdateTime(new Date());
		int modifySaleCustomer = saleCustomerService.modifySaleCustomer(customer);
		System.out.println(modifySaleCustomer);
		
	}
}
