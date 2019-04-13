package top.lothar.sdims.dao;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import top.lothar.sdims.BaseTest;
import top.lothar.sdims.entity.Employee;

public class EmployeeDaoTest extends BaseTest{
	
	@Autowired
	private EmployeeDao employeeDao;
	
	
	@Ignore
	public void testAQueryEmployeeList() {
		
		Employee employeeCondition = new Employee();
		
		Employee employeeConditionByName = new Employee();
		employeeConditionByName.setName("赵路通");
		
		Employee employeeConditionByType = new Employee();
		employeeConditionByType.setType("库存管理员");
		//测试空参数，取全部
		List<Employee> employeeList = employeeDao.queryEmployeeList(employeeCondition, 0, 4);
		System.out.println(employeeList.size());
		for (Employee employee : employeeList) {
			System.out.println(employee.getName());
		}
		//测试以名称取出
		List<Employee> employeeListByName = employeeDao.queryEmployeeList(employeeConditionByName, 0, 2);
		System.out.println(employeeListByName.size());
		for (Employee employee : employeeListByName) {
			System.out.println(employee.getName());
		}
		//测试以type条件取出
		List<Employee> employeeListByType = employeeDao.queryEmployeeList(employeeConditionByType, 0, 2);
		System.out.println(employeeListByType.size());
		for (Employee employee : employeeListByType) {
			System.out.println(employee.getName());
		}
	}
	@Ignore
	public void testBQueryEmployeeCount() {
		Employee employeeCondition = new Employee();
		
		Employee employeeConditionByName = new Employee();
		employeeConditionByName.setName("赵路通");
		
		Employee employeeConditionByType = new Employee();
		employeeConditionByType.setType("库存管理员");
		
		int effectNum = employeeDao.queryEmployeeCount(employeeCondition);
		System.out.println("共有："+effectNum+"条");
		
		int effectNumByName  = employeeDao.queryEmployeeCount(employeeConditionByName);
		System.out.println("姓名下有："+effectNumByName+"条");
		
		int effectNumByType = employeeDao.queryEmployeeCount(employeeConditionByType);
		System.out.println("类型下有："+effectNumByType+"条");
		
	}
	@Ignore
	public void testCInsertEmployee() {
		Employee employeeCondition = new Employee();
		employeeCondition.setName("徐鹏辉");
		employeeCondition.setCode("SD005");
		employeeCondition.setIdCard("410021199564625876");
		employeeCondition.setMobile("18438655526");
		employeeCondition.setSex(1);
		employeeCondition.setAddress("洛阳师范学院");
		employeeCondition.setEmail("809676295@qq.com");
		employeeCondition.setType("销售员");
		employeeCondition.setUpdateTime(new Date());
		int effectNum = employeeDao.insertEmployee(employeeCondition);
		System.out.println(effectNum);
	}
	@Ignore
	public void testDUpdateEmployee() {
		Employee employeeCondition = new Employee();
		employeeCondition.setEmployeeId(5L);
		employeeCondition.setType("销售员");
		employeeCondition.setUpdateTime(new Date());
		int effectNum = employeeDao.updateEmployee(employeeCondition);
		System.out.println(effectNum);
	}
	@Ignore
	public void testEDeleteEmployeeById() {
		long employeeId = 5L;
		int effectNum = employeeDao.deleteEmployeeById(employeeId);
		System.out.println(effectNum);
	}
	@Ignore
	public void testFQueryEmployeeById() {
		long employeeId = 4L;
		Employee employee = employeeDao.queryEmployeeById(employeeId);
		System.out.println(employee.getName());
	}
	@Test
	public void testGetAllEmployeeList() {
		List<Employee> queryAllEmployeeList = employeeDao.queryAllEmployeeList();
		for (Employee employee : queryAllEmployeeList) {
			System.out.println(employee.getEmployeeId());
		}
	}
}