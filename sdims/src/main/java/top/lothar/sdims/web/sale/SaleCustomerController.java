package top.lothar.sdims.web.sale;

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
import top.lothar.sdims.dto.TExecution;
import top.lothar.sdims.entity.Customer;
import top.lothar.sdims.service.SaleCustomerService;
import top.lothar.sdims.util.HttpServletRequestUtil;
import top.lothar.sdims.util.PageBean;
/**
 * 客户
 * @author Lothar
 *
 */
@Controller
@RequestMapping("/sale")
public class SaleCustomerController {
	
	@Autowired
	private SaleCustomerService saleCustomerService;
	
	private PageBean<Customer> pageBean;
	/**
	 * 条件查询客户列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getsalecustomerlist",method=RequestMethod.GET)
	private Map<String, Object> getSaleCustomerList(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 获取页码
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		// 获取每页展示的数据量
		int pageSize = 5;
		// 获取查询条件
		String customerName = HttpServletRequestUtil.getString(request, "customerName");
		// 非空判断
		if (pageIndex > -1) {
			TExecution<Customer> customerExecution = saleCustomerService.getSaleCustomerList(customerName, pageIndex, pageSize);
			pageBean = new PageBean<Customer>();
			// 总记录数
			pageBean.setAllRowCounts(customerExecution.getCount());
			// 仓库列表
			pageBean.setDatas(customerExecution.getData());
			// 每页记录数
			pageBean.setPageSize(5);
			// 当前页
			pageBean.setCurPage(pageIndex);
			int sumPages = PageBean.getSumPages(customerExecution.getCount(), pageSize);
			// 总页数
			pageBean.setSumPages(sumPages);
			// 定义数组(用于页码展示组件)
			int[] tempNum = new int[sumPages];
			for (int i = 0; i < sumPages; i++) {
				tempNum[i] = i + 1;
			}
			// 页码数字
			pageBean.setNavigatepageNums(tempNum);
			modelMap.put("success", true);
			modelMap.put("pageBean", pageBean);
			modelMap.put("stateInfo", customerExecution.getStateInfo());
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "pageIndex or pageSize is Null");
		}
		return modelMap;
	}
	/**
	 * 添加客户信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/addcustomer",method=RequestMethod.POST)
	private Map<String, Object> addCustomer(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		String customerStr = HttpServletRequestUtil.getString(request, "customerStr");
		//JSON转化后的客户存储对象
		Customer customer = null;
		//存放json的对象
		ObjectMapper objectMapper = new ObjectMapper();
		//接受json形式的Customer信息
		try {
			//使用ObjectMapper类把请求中的json信息存放在repository实体类中
			customer = objectMapper.readValue(customerStr, Customer.class);
		} catch (Exception e) {
			// TODO: handle exception
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		try {
			//非空判断
			if (customer!=null) {
				int effectNum = saleCustomerService.addSaleCustomer(customer);
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
	 * 根据前台传的ID移除对应客户
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/removecustomer",method=RequestMethod.GET)
	private Map<String, Object> removeCustomer(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String,Object>();
		long customerId = HttpServletRequestUtil.getLong(request, "customerId");
		try {
			int effectNum = saleCustomerService.removeSaleCusotmer(customerId);
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
	 * 根据前台传递的供应商ID去查询信息并返回前台
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="getcustomerbyid",method=RequestMethod.GET)
	private Map<String, Object> getCustomerById(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Long customerId = HttpServletRequestUtil.getLong(request, "customerId");
		Customer customer = new Customer();
		try {
			customer = saleCustomerService.getCustomerById(customerId);
			if (customer!=null) {
				modelMap.put("success", true);
				modelMap.put("customer", customer);
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "查询错误");
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
	@RequestMapping(value="/modifycustomer",method=RequestMethod.POST)
	private Map<String, Object> modifySupplie(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String,Object>();
		//这里的数据要包含ID因为是根据ID进行更新
		String customerStr = HttpServletRequestUtil.getString(request, "customerStr");
		//JSON转化后的客户存储对象
		Customer customer = null;
		//存放json的对象
		ObjectMapper objectMapper = new ObjectMapper();
		//接受json形式的Supplier信息
		try {
			//使用ObjectMapper类把请求中的json信息存放在customer实体类中
			customer = objectMapper.readValue(customerStr, Customer.class);
			//设置更新的时间
			customer.setUpdateTime(new Date());
		} catch (Exception e) {
			// TODO: handle exception
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		try {
			int effectNum = saleCustomerService.modifySaleCustomer(customer);
			if (effectNum < 1) {
				System.out.println(effectNum);
				modelMap.put("success", false);
				modelMap.put("errMsg", "更新客户失败");
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
}
