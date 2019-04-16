package top.lothar.sdims.web.purchase;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import top.lothar.sdims.dto.TExecution;
import top.lothar.sdims.entity.Supplier;
import top.lothar.sdims.service.PurchaseSupplierService;
import top.lothar.sdims.util.HttpServletRequestUtil;
import top.lothar.sdims.util.PageBean;
/**
 * 采购商
 * @author Lothar
 *
 */
@Controller
@RequestMapping("/purchase")
public class PurchaseSupplierController {
	
	@Autowired
	private PurchaseSupplierService purchaseSupplierService;
	
	private PageBean<Supplier> pageBean;
	/**
	 * 条件查询供应商列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getpurchasesupplierlist",method=RequestMethod.GET)
	private Map<String, Object> getPurchaseSupplierList(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 获取页码
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		// 获取每页展示的数据量
		int pageSize = 5;
		// 获取查询条件
		String supplierName = HttpServletRequestUtil.getString(request, "supplierName");
		// 非空判断
		if (pageIndex > -1) {
			TExecution<Supplier> supplierExecution = purchaseSupplierService.getPurchaseSupplierList(supplierName, pageIndex, pageSize);
			pageBean = new PageBean<Supplier>();
			// 总记录数
			pageBean.setAllRowCounts(supplierExecution.getCount());
			// 仓库列表
			pageBean.setDatas(supplierExecution.getData());
			// 每页记录数
			pageBean.setPageSize(5);
			// 当前页
			pageBean.setCurPage(pageIndex);
			int sumPages = PageBean.getSumPages(supplierExecution.getCount(), pageSize);
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
			modelMap.put("stateInfo", supplierExecution.getStateInfo());
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "pageIndex or pageSize is Null");
		}
		return modelMap;
	}
	/**
	 * 得到所有供应商信息，展示在前台添加采购单的select中，用与创建采购单
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getallsupplierlist",method=RequestMethod.GET)
	private Map<String, Object> getAllSupplierList(){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<Supplier> allSupplierList = null;
		try {
			allSupplierList = purchaseSupplierService.getAllSupplierList();
		} catch (Exception e) {
			// TODO: handle exception
			modelMap.put("success", false);
			e.printStackTrace();
		}
		modelMap.put("success", true);
		modelMap.put("allSupplierList", allSupplierList);
		return modelMap;
	}
	/**
	 * 添加供应商信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/addsupplier",method=RequestMethod.POST)
	private Map<String, Object> addSupplier(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		String supplierStr = HttpServletRequestUtil.getString(request, "supplierStr");
		//JSON转化后的供应商存储对象
		Supplier supplier = null;
		//存放json的对象
		ObjectMapper objectMapper = new ObjectMapper();
		//接受json形式的Supplier信息
		try {
			//使用ObjectMapper类把请求中的json信息存放在repository实体类中
			supplier = objectMapper.readValue(supplierStr, Supplier.class);
		} catch (Exception e) {
			// TODO: handle exception
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		try {
			//非空判断
			if (supplier!=null) {
				int effectNum = purchaseSupplierService.addPurchaseSupplier(supplier);
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
	 * 根据前台传的ID移除对应供应商
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/removesupplier",method=RequestMethod.GET)
	private Map<String, Object> removeSupplier(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String,Object>();
		long supplierId = HttpServletRequestUtil.getLong(request, "supplierId");
		try {
			int effectNum = purchaseSupplierService.removePurchaseSupplier(supplierId);
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
	@RequestMapping(value="getsupplierbyid",method=RequestMethod.GET)
	private Map<String, Object> getSupplierById(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Long supplierId = HttpServletRequestUtil.getLong(request, "supplierId");
		Supplier supplier = new Supplier();
		try {
			supplier = purchaseSupplierService.getPurchaseSupplierById(supplierId);
			if (supplier!=null) {
				modelMap.put("success", true);
				modelMap.put("supplier", supplier);
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
	@RequestMapping(value="/modifysupplier",method=RequestMethod.POST)
	private Map<String, Object> modifySupplie(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String,Object>();
		//这里的数据要包含ID因为是根据ID进行更新
		String supplierStr = HttpServletRequestUtil.getString(request, "supplierStr");
		//JSON转化后的供应商存储对象
		Supplier supplier = null;
		//存放json的对象
		ObjectMapper objectMapper = new ObjectMapper();
		//接受json形式的Supplier信息
		try {
			//使用ObjectMapper类把请求中的json信息存放在supplier实体类中
			supplier = objectMapper.readValue(supplierStr, Supplier.class);
			//设置更新的时间
			supplier.setUpdateTime(new Date());
		} catch (Exception e) {
			// TODO: handle exception
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		try {
			int effectNum = purchaseSupplierService.modifyPurchaseSupplier(supplier);
			if (effectNum < 1) {
				modelMap.put("success", false);
				modelMap.put("errMsg", "更新供应商失败");
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
