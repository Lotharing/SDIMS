package top.lothar.sdims.web.stock;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.lothar.sdims.dto.TExecution;
import top.lothar.sdims.entity.PurchaseOrder;
import top.lothar.sdims.entity.User;
import top.lothar.sdims.service.IntoStockService;
import top.lothar.sdims.util.HttpServletRequestUtil;
import top.lothar.sdims.util.PageBean;

/**
 * 入库
 * @author Lothar
 *
 */
@Controller
@RequestMapping("/stock")
public class IntoStockController {
	//查询经过一次审核，需审核入库订单
	//更新二次审核状态，并修改库存
	@Autowired
	private IntoStockService intoStockService;
	
	private PageBean<PurchaseOrder> pageBean;
	/**
	 * 条件查询需入库采购单列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getintopurchaseorderchecklist",method=RequestMethod.GET)
	private Map<String, Object> getIntoPurchaseOrderCheckList(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 获取页码
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		// 获取每页展示的数据量
		int pageSize = 5;
		// 获取查询条件
		String checkOrderNumber = HttpServletRequestUtil.getString(request,"checkOrderNumber");
		int stockStateNumber = HttpServletRequestUtil.getInt(request, "stockStateNumber");
		//抛出checkStateNumber为null异常抛-1判断!=-1时候添加条件，否则不set此条件
		PurchaseOrder purchaseOrderCondition = compactPurchaseOrderCheckCondition(checkOrderNumber, stockStateNumber);
		// 非空判断
		if (pageIndex > -1) {
			TExecution<PurchaseOrder> intoStockPurchaseOrderCheckExecution = intoStockService.getIntoStockPurchaseOrderCheckList(purchaseOrderCondition, pageIndex, pageSize);
			pageBean = new PageBean<PurchaseOrder>();
			// 总记录数
			pageBean.setAllRowCounts(intoStockPurchaseOrderCheckExecution.getCount());
			// 采购单列表
			pageBean.setDatas(intoStockPurchaseOrderCheckExecution.getData());
			// 每页记录数
			pageBean.setPageSize(5);
			// 当前页
			pageBean.setCurPage(pageIndex);
			int sumPages = PageBean.getSumPages(intoStockPurchaseOrderCheckExecution.getCount(), pageSize);
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
			modelMap.put("stateInfo", intoStockPurchaseOrderCheckExecution.getStateInfo());
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "pageIndex or pageSize is Null");
		}
		return modelMap;
	}
	/**
	 * 根据前台传过来的信息对应ID通过审核-入库
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/modifyintostockpurchaseordercheck",method=RequestMethod.GET)
	private Map<String, Object> modifyIntoStockPurchaseOrderCheck(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String,Object>();
		//这里的数据要包含ID因为是根据ID进行更新
		long porderId = HttpServletRequestUtil.getLong(request, "porderId");
		PurchaseOrder purchaseOrder = new PurchaseOrder();
		purchaseOrder.setPorderId(porderId);
		//后期session获取库管
		//用户姓名从登陆后的session中获取
		User user = (User) request.getSession().getAttribute("loginUser");
		purchaseOrder.setStockMan(user.getEmployee().getName());
		try {
			int effectNum = intoStockService.modifyIntoStockPurchaseOrderCheck(purchaseOrder);
			if (effectNum < 1) {
				modelMap.put("success", false);
				modelMap.put("errMsg", "审核通过采购单失败");
			}else {
				modelMap.put("success", true);
				modelMap.put("successMsg", "审核成功");
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
	 * 判断对于查询需入库采购单时候的筛选条件
	 * @param orderNumber
	 * @param checkState
	 * @return
	 */
	private PurchaseOrder compactPurchaseOrderCheckCondition(String orderNumber,int stockStateNumber) {
		PurchaseOrder purchaseOrderCondition = new PurchaseOrder();
		//通过第一次审核的条件
		purchaseOrderCondition.setCheckState(1);
		// 若有指定订单号的要求则添加进去
		if (orderNumber != null) {
			purchaseOrderCondition.setOrderNumber(orderNumber);
		}
		// 若有指定订单经理审核通过
		if (stockStateNumber != -1L) {
			purchaseOrderCondition.setStockState(stockStateNumber);
		}
		return purchaseOrderCondition;
	}
}
