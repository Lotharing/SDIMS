package top.lothar.sdims.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.lothar.sdims.dao.EmployeeDao;
import top.lothar.sdims.dto.EmployeeExecution;
import top.lothar.sdims.entity.Employee;
import top.lothar.sdims.service.EmployeeService;
import top.lothar.sdims.util.PageCalculator;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeDao employeeDao;
	
	@Override
	public EmployeeExecution getEmployeeList(Employee employeeCondition, int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
		List<Employee> employeeList = employeeDao.queryEmployeeList(employeeCondition, rowIndex, pageSize);
		int count = employeeDao.queryEmployeeCount(employeeCondition);
		//临时存储数据封装对象（作用：不需要写两个方法去对数据，数量的获取，只需要一个实体携带两个数据返回）
		EmployeeExecution employeeExecution = new EmployeeExecution();
		if (employeeList!=null) {
			employeeExecution.setEmployeeList(employeeList);
			employeeExecution.setCount(count);
			employeeExecution.setStateInfo("成功得到数据");
		}else {
			return new EmployeeExecution("没有对应数据");
		}
		return employeeExecution;
	}
	
	@Override
	public List<Employee> getAllEmployeeList() {
		// TODO Auto-generated method stub
		return employeeDao.queryAllEmployeeList();
	}
	
	@Override
	public int addEmployee(Employee employee) {
		// TODO Auto-generated method stub
		return employeeDao.insertEmployee(employee);
	}

	@Override
	public int removeEmployee(long employeeId) {
		// TODO Auto-generated method stub
		return employeeDao.deleteEmployeeById(employeeId);
	}

	@Override
	public int modifyEmployee(Employee employee) {
		// TODO Auto-generated method stub
		return employeeDao.updateEmployee(employee);
	}

	@Override
	public Employee getEmployeeById(long employeeId) {
		// TODO Auto-generated method stub
		return employeeDao.queryEmployeeById(employeeId);
	}

}
