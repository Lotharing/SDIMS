package top.lothar.sdims.web.sale;

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
import top.lothar.sdims.entity.Goods;
import top.lothar.sdims.entity.PurchaseOrder;
import top.lothar.sdims.entity.Repository;
import top.lothar.sdims.entity.SaleOrder;
import top.lothar.sdims.service.SaleOrderService;
import top.lothar.sdims.util.HttpServletRequestUtil;
import top.lothar.sdims.util.PageBean;

@Controller
@RequestMapping("/sale")
public class SaleOrderController {
	
	@Autowired
	private SaleOrderService saleOrderService;
	
	private PageBean<SaleOrder> pageBean;
	/**
	 * 条件查询销售单列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getsaleorderlist",method=RequestMethod.GET)
	private Map<String, Object> getSaleOrderList(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 获取页码
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		// 获取每页展示的数据量
		int pageSize = 5;
		// 获取查询条件
		String goodsName = HttpServletRequestUtil.getString(request,"goodsName");
		String customerName = HttpServletRequestUtil.getString(request,"customerName");
		String repositoryName = HttpServletRequestUtil.getString(request,"repositoryName");
		String orderNumber = HttpServletRequestUtil.getString(request,"orderNumber");
		//通过订单经理/仓库管理审核的订单
		int checkState = HttpServletRequestUtil.getInt(request, "checkState");
		int stockState = HttpServletRequestUtil.getInt(request, "stockState");
		//组合查询条件
		SaleOrder saleOrderCondition = compactSaleOrderCondition(goodsName, customerName, repositoryName, orderNumber, checkState, stockState);
		// 非空判断
		if (pageIndex > -1) {
			TExecution<SaleOrder> saleOrderExecution = saleOrderService.getSaleOrderList(saleOrderCondition, pageIndex, pageSize);
			pageBean = new PageBean<SaleOrder>();
			// 总记录数
			pageBean.setAllRowCounts(saleOrderExecution.getCount());
			// 仓库列表
			pageBean.setDatas(saleOrderExecution.getData());
			// 每页记录数
			pageBean.setPageSize(5);
			// 当前页
			pageBean.setCurPage(pageIndex);
			int sumPages = PageBean.getSumPages(saleOrderExecution.getCount(), pageSize);
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
			modelMap.put("stateInfo", saleOrderExecution.getStateInfo());
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "pageIndex or pageSize is Null");
		}
		return modelMap;
	}
	/**
	 * 添加销售单信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/addsaleorder",method=RequestMethod.POST)
	private Map<String, Object> addSaleOrder(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		String saleOrderStr = HttpServletRequestUtil.getString(request, "saleOrderStr");
		//JSON转化后的销售单存储对象
		SaleOrder saleOrder = null;
		//存放json的对象
		ObjectMapper objectMapper = new ObjectMapper();
		//接受json形式的Employee信息
		try {
			//使用ObjectMapper类把请求中的json信息存放在repository实体类中
			saleOrder = objectMapper.readValue(saleOrderStr, SaleOrder.class);
		} catch (Exception e) {
			// TODO: handle exception
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		try {
			//非空判断
			if (saleOrder!=null) {
				int effectNum = saleOrderService.addSaleOrder(saleOrder);
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
	 * 根据前台传的ID移除对应销售单
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/removesaleorder",method=RequestMethod.GET)
	private Map<String, Object> removeSaleOrder(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String,Object>();
		long sorderId = HttpServletRequestUtil.getLong(request, "sorderId");
		try {
			int effectNum = saleOrderService.removeSaleOrder(sorderId);
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
	 * 根据前台传递的销售单ID去查询信息并返回前台
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="getsaleorderbyid",method=RequestMethod.GET)
	private Map<String, Object> getSaleOrderById(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Long sorderId = HttpServletRequestUtil.getLong(request, "sorderId");
		SaleOrder saleOrder = new SaleOrder();
		try {
			saleOrder = saleOrderService.getSaleOrderById(sorderId);
			if (saleOrder!=null) {
				modelMap.put("success", true);
				modelMap.put("saleOrder", saleOrder);
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
	@RequestMapping(value="/modifysaleorder",method=RequestMethod.POST)
	private Map<String, Object> modifySaleOrder(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String,Object>();
		//这里的数据要包含ID因为是根据ID进行更新
		String saleOrderStr = HttpServletRequestUtil.getString(request, "saleOrderStr");
		//JSON转化后的销售单存储对象
		SaleOrder saleOrder = null;
		//存放json的对象
		ObjectMapper objectMapper = new ObjectMapper();
		//接受json形式的saleOrder信息
		try {
			//使用ObjectMapper类把请求中的json信息存放在saleOrder实体类中
			saleOrder = objectMapper.readValue(saleOrderStr, SaleOrder.class);
		} catch (Exception e) {
			// TODO: handle exception
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		try {
			int effectNum = saleOrderService.modifySaleOrder(saleOrder);
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
	 * 判断对于查询销售单时候的筛选条件
	 * @param goodsName
	 * @param customerName
	 * @param repositoryName
	 * @param orderNumber
	 * @param checkState
	 * @return
	 */
	private SaleOrder compactSaleOrderCondition(String goodsName, String customerName, String repositoryName,String orderNumber,int checkState,int stockState) {
		SaleOrder saleOrderCondition = new SaleOrder();
		// 若有指定商品订单的要求则添加进去
		if (goodsName != null) {
			Goods goodsCondition = new Goods();
			goodsCondition.setName(goodsName);
			saleOrderCondition.setGoods(goodsCondition);
		}
		// 若有指定供应商订单的要求则添加进去
		if (customerName != null) {
			Customer customerCondition = new Customer();
			customerCondition.setName(customerName);
			saleOrderCondition.setCustomer(customerCondition);
		}
		// 若有指定仓库订单的要求则添加进去
		if (repositoryName != null) {
			Repository repositoryCondition = new Repository();
			repositoryCondition.setName(repositoryName);
			saleOrderCondition.setRepository(repositoryCondition);
		}
		// 若有指定订单号的要求则添加进去
		if (orderNumber != null) {
			saleOrderCondition.setOrderNumber(orderNumber);
		}
		// 若有指定订单经理审核通过/不通过的要求则添加进去
		if (checkState != -1L) {
			saleOrderCondition.setCheckState(checkState);
		}
		if (stockState != -1L) {
			saleOrderCondition.setStockState(stockState);
		}
		return saleOrderCondition;
	}
	
}
