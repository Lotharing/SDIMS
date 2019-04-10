package top.lothar.sdims.web.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import top.lothar.sdims.dto.EmployeeExecution;
import top.lothar.sdims.entity.Employee;
import top.lothar.sdims.service.EmployeeService;
import top.lothar.sdims.util.HttpServletRequestUtil;
import top.lothar.sdims.util.PageBean;
/**
 * 员工
 * @author Lothar
 *
 */
@Controller
@RequestMapping("/admin")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	private PageBean<Employee> pageBean;
	/**
	 * 获取前台传递的request信息，同时根据信息从service中取出数据，并返回JSON格式
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getemployeelist",method=RequestMethod.GET)
	private Map<String, Object> getEmployeeList(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		//获取页码
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		//获取每页展示的数据量
		int pageSize = 8;
		//获取查询条件（员工姓名）
		String employeeName = HttpServletRequestUtil.getString(request, "employeeName");
		//获取查询条件（员工类别）
		String employeeType = HttpServletRequestUtil.getString(request, "employeeType");
		Employee employeeCondition = new Employee();
		employeeCondition.setName(employeeName);
		employeeCondition.setType(employeeType);
		//非空判断
		if (pageIndex > -1) {
			EmployeeExecution employeeExecution = employeeService.getEmployeeList(employeeCondition, pageIndex, pageSize);
			pageBean = new PageBean<Employee>();
			//总记录数
			pageBean.setAllRowCounts(employeeExecution.getCount());
			//仓库列表
			pageBean.setDatas(employeeExecution.getEmployeeList());
			//每页记录数
			pageBean.setPageSize(pageSize);
			//当前页
			pageBean.setCurPage(pageIndex);
			int sumPages = PageBean.getSumPages(employeeExecution.getCount(),pageSize);
			//总页数
			pageBean.setSumPages(sumPages);
			//定义数组(用于页码展示组件)
			int[] tempNum = new int[sumPages];
			for (int i = 0; i<sumPages; i++) {
				tempNum[i]=i+1;
			}
			//页码数字
			pageBean.setNavigatepageNums(tempNum);
			modelMap.put("success", true);
			modelMap.put("pageBean",pageBean);
			modelMap.put("stateInfo", employeeExecution.getStateInfo());
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "pageIndex or pageSize is Null");
		}
		return modelMap;
	}
	/**
	 * 获取前台传的json-employee信息，并调用service插入数据库
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/addemployee",method=RequestMethod.POST)
	private Map<String, Object> addEmployee(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		String employeeStr = HttpServletRequestUtil.getString(request, "employeeStr");
		//JSON转化后的员工存储对象
		Employee employee = null;
		//存放json的对象
		ObjectMapper objectMapper = new ObjectMapper();
		//接受json形式的Employee信息
		try {
			//使用ObjectMapper类把请求中的json信息存放在Employee实体类中
			employee = objectMapper.readValue(employeeStr, Employee.class);
		} catch (Exception e) {
			// TODO: handle exception
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		try {
			//非空判断
			if (employee!=null) {
				int effectNum = employeeService.addEmployee(employee);
				if (effectNum < 1) {
					modelMap.put("success", false);
					modelMap.put("errMsg", "插入失败");
				}else {
					modelMap.put("success", true);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		return modelMap;
	}
	/**
	 * 根据前台传的ID移除对应员工
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/removeemployee",method=RequestMethod.GET)
	private Map<String, Object> removeEmployee(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String,Object>();
		long employeeId = HttpServletRequestUtil.getLong(request, "employeeId");
		try {
			int effectNum = employeeService.removeEmployee(employeeId);
			if (effectNum < 1) {
				modelMap.put("success", false);
				modelMap.put("errMsg", "删除失败");
			}else {
				modelMap.put("success", true);
				modelMap.put("successMsg", "删除成功");
			}
		} catch (Exception e) {
			// TODO: handle exception
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		return modelMap;
	}
	/**
	 * 根据前台传过来的信息对应ID进行更新
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/modifyEmployee",method=RequestMethod.POST)
	private Map<String, Object> modifyEmployee(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String,Object>();
		//这里的数据要包含ID因为是根据ID进行更新
		String employeeStr = HttpServletRequestUtil.getString(request, "employeeStr");
		//JSON转化后的员工存储对象
		Employee employee = null;
		//存放json的对象
		ObjectMapper objectMapper = new ObjectMapper();
		//接受json形式的Employee信息
		try {
			//使用ObjectMapper类把请求中的json信息存放在Employee实体类中
			employee = objectMapper.readValue(employeeStr, Employee.class);
			//设置更新的时间
			employee.setUpdateTime(new Date());
		} catch (Exception e) {
			// TODO: handle exception
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		try {
			int effectNum = employeeService.modifyEmployee(employee);
			if (effectNum < 1) {
				modelMap.put("success", false);
				modelMap.put("errMsg", "更新员工失败");
			}else {
				modelMap.put("success", true);
			}
		} catch (Exception e) {
			// TODO: handle exception
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		return modelMap;
	}
	/**
	 * 根据前台传递的员工ID去查询信息并返回前台
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="getemployeebyid",method=RequestMethod.GET)
	private Map<String, Object> getEmployeeById(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Long employeeId = HttpServletRequestUtil.getLong(request, "employeeId");
		Employee employee = new Employee();
		try {
			employee = employeeService.getEmployeeById(employeeId);
			if (employee!=null) {
				modelMap.put("success", true);
				modelMap.put("employee", employee);
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "查询错误");
			}
		} catch (Exception e) {
			// TODO: handle exception
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		return modelMap;
	}
}
