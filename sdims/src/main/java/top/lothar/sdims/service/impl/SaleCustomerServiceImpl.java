package top.lothar.sdims.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.lothar.sdims.dao.SaleCustomerDao;
import top.lothar.sdims.dto.TExecution;
import top.lothar.sdims.entity.Customer;
import top.lothar.sdims.service.SaleCustomerService;
import top.lothar.sdims.util.PageCalculator;

@Service
public class SaleCustomerServiceImpl implements SaleCustomerService {
	
	@Autowired
	private SaleCustomerDao saleCustomerDao;

	@Override
	public int addSaleCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return saleCustomerDao.insertSaleCustomer(customer);
	}

	@Override
	public int removeSaleCusotmer(long customerId) {
		// TODO Auto-generated method stub
		return saleCustomerDao.deleteSaleCustomer(customerId);
	}

	@Override
	public int modifySaleCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return saleCustomerDao.updateSaleCustomer(customer);
	}

	@Override
	public Customer getCustomerById(long customerId) {
		// TODO Auto-generated method stub
		return saleCustomerDao.querySaleCustomerById(customerId);
	}

	@Override
	public TExecution<Customer> getSaleCustomerList(String customerName, int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
		List<Customer> saleCustomerList = saleCustomerDao.querySaleCustomerList(customerName, rowIndex, pageSize);
		int count = saleCustomerDao.querySaleCustomerCount(customerName);
		TExecution<Customer> customerExecution = new TExecution<Customer>();
		if (saleCustomerList!=null) {
			customerExecution.setData(saleCustomerList);
			customerExecution.setCount(count);
			customerExecution.setStateInfo("成功得到数据");
		}else {
			return new TExecution<Customer>("数据获取失败");
		}
		return customerExecution;
	}

}
