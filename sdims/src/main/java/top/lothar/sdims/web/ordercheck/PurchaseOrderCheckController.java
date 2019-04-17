package top.lothar.sdims.web.ordercheck;

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
import top.lothar.sdims.service.PurchaseOrderCheckService;
import top.lothar.sdims.service.PurchaseOrderService;
import top.lothar.sdims.util.HttpServletRequestUtil;
import top.lothar.sdims.util.PageBean;
/**
 * 采购单审核
 * @author Lothar
 *
 */
@Controller
@RequestMapping("/ordercheck")
public class PurchaseOrderCheckController {
	
	@Autowired
	private PurchaseOrderService purchaseOrderService;
	
	@Autowired
	private PurchaseOrderCheckService purchaseOrderCheckService;
	
	private PageBean<PurchaseOrder> pageBean;
	/**
	 * 条件查询采购单列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getpurchaseorderchecklist",method=RequestMethod.GET)
	private Map<String, Object> getPurchaseOrderCheckList(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 获取页码
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		// 获取每页展示的数据量
		int pageSize = 5;
		// 获取查询条件
		String checkOrderNumber = HttpServletRequestUtil.getString(request,"checkOrderNumber");
		int checkStateNumber = HttpServletRequestUtil.getInt(request, "checkStateNumber");
		//抛出checkStateNumber为null异常抛-1判断!=-1时候添加条件，否则不set此条件
		PurchaseOrder purchaseOrderCondition = compactPurchaseOrderCheckCondition(checkOrderNumber, checkStateNumber);
		// 非空判断
		if (pageIndex > -1) {
			TExecution<PurchaseOrder> purchaseOrderCheckExecution = purchaseOrderCheckService.getPurchaseOrderCheckList(purchaseOrderCondition, pageIndex, pageSize);
			pageBean = new PageBean<PurchaseOrder>();
			// 总记录数
			pageBean.setAllRowCounts(purchaseOrderCheckExecution.getCount());
			// 采购单列表
			pageBean.setDatas(purchaseOrderCheckExecution.getData());
			// 每页记录数
			pageBean.setPageSize(5);
			// 当前页
			pageBean.setCurPage(pageIndex);
			int sumPages = PageBean.getSumPages(purchaseOrderCheckExecution.getCount(), pageSize);
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
			modelMap.put("stateInfo", purchaseOrderCheckExecution.getStateInfo());
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "pageIndex or pageSize is Null");
		}
		return modelMap;
	}
	/**
	 * 根据前台传过来的信息对应ID通过审核
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/modifypurchaseordercheck",method=RequestMethod.GET)
	private Map<String, Object> modifyPurchaseOrderCheck(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String,Object>();
		//这里的数据要包含ID因为是根据ID进行更新
		long porderId = HttpServletRequestUtil.getLong(request, "porderId");
		//用户姓名从登陆后的session中获取
		User user = (User) request.getSession().getAttribute("loginUser");
		PurchaseOrder purchaseOrder = new PurchaseOrder();
		purchaseOrder.setPorderId(porderId);
		//后期session获取----------------------------------
		purchaseOrder.setCheckMan(user.getEmployee().getName());
		try {
			int effectNum = purchaseOrderCheckService.modifyPurchaseOrderCheck(purchaseOrder);
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
	 * 根据前台传过来的信息对应ID撤销审核
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/modifypurchaseorderrevoke",method=RequestMethod.GET)
	private Map<String, Object> modifyPurchaseOrderRevoke(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String,Object>();
		//这里的数据要包含ID因为是根据ID进行更新
		long porderId = HttpServletRequestUtil.getLong(request, "porderId");
		//用户姓名从登陆后的session中获取
		User user = (User) request.getSession().getAttribute("loginUser");
		PurchaseOrder purchaseOrder = new PurchaseOrder();
		purchaseOrder.setPorderId(porderId);
		//session中获取员工姓名
		purchaseOrder.setCheckMan(user.getEmployee().getName());
		try {
			//如果库管已经入库，则不能撤销
			/**
			 * 根据porderId查询采购单，的stock_state信息
			 * 如果为1则返回此订单已经入库
			 */
			PurchaseOrder currentPurchaseOrder = purchaseOrderService.getPurchaseOrderById(porderId);
			if (currentPurchaseOrder.getStockState()==1) {
				modelMap.put("success", false);
				modelMap.put("errMsg", "此订单已经入库，不能撤销！");
				return modelMap;
			}
			//审核通过更新订单
			int effectNum = purchaseOrderCheckService.modifyPurchaseOrderRevoke(purchaseOrder);
			if (effectNum < 1) {
				modelMap.put("success", false);
				modelMap.put("errMsg", "撤销审核失败");
			}else {
				modelMap.put("success", true);
				modelMap.put("successMsg", "撤销审核成功");
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
	 * 判断对于查询采购单时候的筛选条件
	 * @param orderNumber
	 * @param checkState
	 * @return
	 */
	private PurchaseOrder compactPurchaseOrderCheckCondition(String orderNumber,int checkState) {
		PurchaseOrder purchaseOrderCondition = new PurchaseOrder();
		// 若有指定订单号的要求则添加进去
		if (orderNumber != null) {
			purchaseOrderCondition.setOrderNumber(orderNumber);
		}
		// 若有指定订单经理审核通过
		if (checkState != -1L) {
			purchaseOrderCondition.setCheckState(checkState);
		}
		return purchaseOrderCondition;
	}
}
