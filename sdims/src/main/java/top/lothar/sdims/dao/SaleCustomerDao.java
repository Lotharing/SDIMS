package top.lothar.sdims.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import top.lothar.sdims.entity.Customer;

public interface SaleCustomerDao {

	/*--------------------------------------------------客户管理----------------------------------------------------*/
	/**
	 * 添加客户（下级经销商）
	 * @param customer
	 * @return
	 */
	int insertSaleCustomer(Customer customer);
	/**
	 * 根据客户（下级经销商)ID删除对应客户
	 * @param customerId
	 * @return
	 */
	int deleteSaleCustomer(long customerId);
	/**
	 * 更新客户（下级经销商)信息
	 * @param customer
	 * @return
	 */
	int updateSaleCustomer(Customer customer);
	/**
	 * 根据ID查询客户信息
	 * @param customerId
	 * @return
	 */
	Customer querySaleCustomerById(long customerId);
	/**
	 * 得到所有客户列表，用于前台销售单创建时候，select中的客户信息
	 * @return
	 */
	List<Customer> queryAllCustomerList();
	/**
	 * 分页查询所有客户（下级经销商)信息
	 * 根据客户名称（下级经销商)查询
	 * @param rowIndex
	 * @param pageSize
	 * @return
	 */
	List<Customer> querySaleCustomerList(@Param("customerName")String customerName,@Param("rowIndex")int rowIndex, @Param("pageSize")int pageSize);
	/**
	 * 查询所有客户（下级经销商)数量，用于分页数据
	 * @return
	 */
	int querySaleCustomerCount(@Param("customerName")String customerName);
}
