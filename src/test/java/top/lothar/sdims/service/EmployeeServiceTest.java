package top.lothar.sdims.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import top.lothar.sdims.BaseTest;
import top.lothar.sdims.dto.EmployeeExecution;
import top.lothar.sdims.entity.Employee;

public class EmployeeServiceTest extends BaseTest{
	
	@Autowired
	private EmployeeService employeeService;
	
	@Test
	public void testGetEmployeeList() {
		Employee employeeCondition = new Employee();
		
		Employee employeeConditionByName = new Employee();
		employeeConditionByName.setName("赵路通");
		
		Employee employeeConditionByType = new Employee();
		employeeConditionByType.setType("库存管理员");
		
		EmployeeExecution employeeExecution = employeeService.getEmployeeList(employeeCondition, 1,4);
		System.out.println(employeeExecution.getCount());
		List<Employee> employeeList = employeeExecution.getEmployeeList();
		for (Employee employee : employeeList) {
			System.out.println(employee.getName());
		}
		
		System.out.println("----------------------");
		
		EmployeeExecution employeeExecutionByName = employeeService.getEmployeeList(employeeConditionByName, 1, 2);
		System.out.println(employeeExecutionByName.getCount());
		List<Employee> employeeListByName = employeeExecutionByName.getEmployeeList();
		for (Employee employee : employeeListByName) {
			System.out.println(employee.getName());
		}
		
		System.out.println("----------------------");
		
		EmployeeExecution employeeExecutionByType = employeeService.getEmployeeList(employeeConditionByType, 1, 2);
		System.out.println(employeeExecutionByType.getCount());
		List<Employee> employeeListByType = employeeExecutionByType.getEmployeeList();
		for (Employee employee : employeeListByType) {
			System.out.println(employee.getName());
		}
	}
}
