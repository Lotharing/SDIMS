package top.lothar.sdims.service;

import top.lothar.sdims.dto.EmployeeExecution;
import top.lothar.sdims.entity.Employee;

public interface EmployeeService {
	/**
	 * 根据条件分页查询（只使用Name和Type进行查询）对应员工，分页查询所有的员工
	 * @param employeeCondition
	 * @param pageIndex	处理pageIndex，转化为rowIndex，便于Dao使用Limit数据
	 * @param pageSize
	 * @return
	 */
	EmployeeExecution getEmployeeList(Employee employeeCondition,int pageIndex,int pageSize);
	/**
	 * 添加员工信息
	 * @param employee
	 * @return
	 */
	int addEmployee(Employee employee);
	/**
	 * 删除员工信息
	 * @param employeeId
	 * @return
	 */
	int removeEmployee(long employeeId);
	/**
	 * 更新员工信息
	 * @param employee
	 * @return
	 */
	int modifyEmployee(Employee employee);
	/**
	 * 根据ID查询员工信息
	 * @param employeeId
	 * @return
	 */
	Employee getEmployeeById(long employeeId);
}
