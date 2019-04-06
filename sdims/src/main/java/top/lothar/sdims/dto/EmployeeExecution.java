package top.lothar.sdims.dto;

import java.util.List;

import top.lothar.sdims.entity.Employee;
/**
 * 雇员信息，以及分页数据临时存储对象,以及状态信息
 * @author Lothar
 *
 */
public class EmployeeExecution {
	
	private String stateInfo;
	
	private int count;
	
	private List<Employee> employeeList;
	/**
	 * 无参构造
	 */
	public EmployeeExecution() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * 失败构造器
	 * @param stateInfo
	 */
	public EmployeeExecution(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<Employee> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<Employee> employeeList) {
		this.employeeList = employeeList;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}
	
	
}
