package top.lothar.sdims.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import top.lothar.sdims.entity.Employee;

public interface EmployeeDao {
	/*-------------------------------------------------员工管理----------------------------------------------------*/
	/**
	  * 添加员工
	 * @param employee
	 * @return
	 */
	int insertEmployee(Employee employee);
	/**
	  * 根据员工ID删除指定员工
	 * @param employeeId
	 * @return
	 */
	int deleteEmployeeById(long employeeId);
	/**
	  * 更新员工信息
	 * @param employee
	 * @return
	 */
	int updateEmployee(Employee employee);
	/**
	 * 根据Id查询需要编辑的员工信息
	 * @param employeeId
	 * @return
	 */
	Employee queryEmployeeById(long employeeId);
	/**
	 * 根据条件分页查询（只使用Name和Type进行查询）对应员工，分页查询所有的员工
	 * @param rowIndex
	 * @param pageSize
	 * @return
	 */
	List<Employee> queryEmployeeList(@Param("employeeCondition")Employee employeeCondition,@Param("rowIndex")int rowIndex, @Param("pageSize")int pageSize);
	/**
	 * 
	 * 查询所有员工列表-用于添加用户时候对员工信息绑定数据加载select
	 * @return
	 */
	List<Employee> queryAllEmployeeList();
	/**
	  * 查询员工数量
	 * @return
	 */
	int queryEmployeeCount(@Param("employeeCondition")Employee employeeCondition);
}
