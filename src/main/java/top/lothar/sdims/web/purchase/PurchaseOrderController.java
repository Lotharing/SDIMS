package top.lothar.sdims.web.purchase;

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
import top.lothar.sdims.entity.Goods;
import top.lothar.sdims.entity.PurchaseOrder;
import top.lothar.sdims.entity.Repository;
import top.lothar.sdims.entity.Supplier;
import top.lothar.sdims.service.PurchaseOrderService;
import top.lothar.sdims.util.HttpServletRequestUtil;
import top.lothar.sdims.util.PageBean;
/**
 * 采购订单
 * @author Lothar
 *
 */
@Controller
@RequestMapping("/purchase")
public class PurchaseOrderController {
	
	@Autowired
	private PurchaseOrderService purchaseOrderService;
	
	PageBean<PurchaseOrder> pageBean;
	/**
	 * 条件查询采购单列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getpurchaseorderlist",method=RequestMethod.GET)
	private Map<String, Object> getPurchaseOrderList(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 获取页码
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		// 获取每页展示的数据量
		int pageSize = 5;
		// 获取查询条件
		String goodsName = HttpServletRequestUtil.getString(request,"goodsName");
		String supplierName = HttpServletRequestUtil.getString(request,"supplierName");
		String repositoryName = HttpServletRequestUtil.getString(request,"repositoryName");
		String orderNumber = HttpServletRequestUtil.getString(request,"orderNumber");
		//通过订单经理/仓库管理审核的订单
		int checkState = HttpServletRequestUtil.getInt(request, "checkState");
		int stockState = HttpServletRequestUtil.getInt(request, "stockState");
		//组合查询条件
		PurchaseOrder purchaseOrderCondition = compactPurchaseOrderCondition(goodsName, supplierName, repositoryName, orderNumber, checkState,stockState);
		// 非空判断
		if (pageIndex > -1) {
			TExecution<PurchaseOrder> purchaseOrderExecution = purchaseOrderService.getPurchaseOrderList(purchaseOrderCondition, pageIndex, pageSize);
			pageBean = new PageBean<PurchaseOrder>();
			// 总记录数
			pageBean.setAllRowCounts(purchaseOrderExecution.getCount());
			// 仓库列表
			pageBean.setDatas(purchaseOrderExecution.getData());
			// 每页记录数
			pageBean.setPageSize(5);
			// 当前页
			pageBean.setCurPage(pageIndex);
			int sumPages = PageBean.getSumPages(purchaseOrderExecution.getCount(), pageSize);
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
			modelMap.put("stateInfo", purchaseOrderExecution.getStateInfo());
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "pageIndex or pageSize is Null");
		}
		return modelMap;
	}
	/**
	 * 添加采购单信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/addpurchaseorder",method=RequestMethod.POST)
	private Map<String, Object> addPurchaseOrder(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		String purchaseOrderStr = HttpServletRequestUtil.getString(request, "purchaseOrderStr");
		//JSON转化后的采购单存储对象
		PurchaseOrder purchaseOrder = null;
		//存放json的对象
		ObjectMapper objectMapper = new ObjectMapper();
		//接受json形式的Employee信息
		try {
			//使用ObjectMapper类把请求中的json信息存放在repository实体类中
			purchaseOrder = objectMapper.readValue(purchaseOrderStr, PurchaseOrder.class);
		} catch (Exception e) {
			// TODO: handle exception
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		try {
			//非空判断
			if (purchaseOrder!=null) {
				int effectNum = purchaseOrderService.addPurchaseOrder(purchaseOrder);
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
	 * 根据前台传的ID移除对应订单
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/removepurchaseorder",method=RequestMethod.GET)
	private Map<String, Object> removePurchaseOrder(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String,Object>();
		long porderId = HttpServletRequestUtil.getLong(request, "porderId");
		try {
			int effectNum = purchaseOrderService.removePurchaseOrderById(porderId);
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
	 * 根据前台传递的采购单ID去查询信息并返回前台
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="getpurchaseorderbyid",method=RequestMethod.GET)
	private Map<String, Object> getPurchaseOrderById(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Long porderId = HttpServletRequestUtil.getLong(request, "porderId");
		PurchaseOrder purchaseOrder = new PurchaseOrder();
		try {
			purchaseOrder = purchaseOrderService.getPurchaseOrderById(porderId);
			if (purchaseOrder!=null) {
				modelMap.put("success", true);
				modelMap.put("purchaseOrder", purchaseOrder);
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
	/**
	 * 根据前台传过来的信息对应ID进行更新
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/modifypurchaseorder",method=RequestMethod.POST)
	private Map<String, Object> modifyPurchaseOrder(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String,Object>();
		//这里的数据要包含ID因为是根据ID进行更新
		String purchaseOrderStr = HttpServletRequestUtil.getString(request, "purchaseOrderStr");
		//JSON转化后的采购单存储对象
		PurchaseOrder purchaseOrder = null;
		//存放json的对象
		ObjectMapper objectMapper = new ObjectMapper();
		//接受json形式purchaseOrder的信息
		try {
			//使用ObjectMapper类把请求中的json信息存放在purchaseOrder实体类中
			purchaseOrder = objectMapper.readValue(purchaseOrderStr, PurchaseOrder.class);
		} catch (Exception e) {
			// TODO: handle exception
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		try {
			int effectNum = purchaseOrderService.modifyPurchaseOrder(purchaseOrder);
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
	 * 判断对于查询订单时候的筛选条件
	 * @param goodsName
	 * @param supplierName
	 * @param repositoryName
	 * @param orderNumber
	 * @param checkState
	 * @return
	 */
	private PurchaseOrder compactPurchaseOrderCondition(String goodsName, String supplierName, String repositoryName,String orderNumber,int checkState,int stockState) {
		PurchaseOrder purchaseOrderCondition = new PurchaseOrder();
		// 若有指定商品订单的要求则添加进去
		if (goodsName != null) {
			Goods goodsCondition = new Goods();
			goodsCondition.setName(goodsName);
			purchaseOrderCondition.setGoods(goodsCondition);
		}
		// 若有指定供应商订单的要求则添加进去
		if (supplierName != null) {
			Supplier supplierCondition = new Supplier();
			supplierCondition.setName(supplierName);
			purchaseOrderCondition.setSupplier(supplierCondition);
		}
		// 若有指定仓库订单的要求则添加进去
		if (repositoryName != null) {
			Repository repositoryCondition = new Repository();
			repositoryCondition.setName(repositoryName);
			purchaseOrderCondition.setRepository(repositoryCondition);
		}
		// 若有指定订单号的要求则添加进去
		if (orderNumber != null) {
			purchaseOrderCondition.setOrderNumber(orderNumber);
		}
		// 若有指定订单经理审核通过/不通过的要求则添加进去
		if (checkState != -1L) {
			purchaseOrderCondition.setCheckState(checkState);
		}
		if (stockState != -1L) {
			purchaseOrderCondition.setStockState(stockState);
		}
		return purchaseOrderCondition;
	}
}
