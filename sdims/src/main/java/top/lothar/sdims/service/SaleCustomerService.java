package top.lothar.sdims.service;

import top.lothar.sdims.dto.TExecution;
import top.lothar.sdims.entity.Customer;

public interface SaleCustomerService {
	/**
	 * 添加客户
	 * @param customer
	 * @return
	 */
	int addSaleCustomer(Customer customer);
	/**
	 * 删除客户
	 * @param customerId
	 * @return
	 */
	int removeSaleCusotmer(long customerId);
	/**
	 * 更新客户
	 * @param customer
	 * @return
	 */
	int modifySaleCustomer(Customer customer);
	/**
	 * 根据ID查询客户信息
	 * @param customerId
	 * @return
	 */
	Customer getCustomerById(long customerId);
	/**
	 * 得到客户列表，返回泛型（列表,总数,状态信息）
	 * @param customerName
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	TExecution<Customer> getSaleCustomerList(String customerName,int pageIndex,int pageSize);
}
