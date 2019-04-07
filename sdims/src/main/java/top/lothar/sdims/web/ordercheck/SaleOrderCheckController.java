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
import top.lothar.sdims.entity.SaleOrder;
import top.lothar.sdims.service.SaleOrderCheckService;
import top.lothar.sdims.util.HttpServletRequestUtil;
import top.lothar.sdims.util.PageBean;
/**
 * 销售单审核
 * @author Lothar
 *
 */
@Controller
@RequestMapping("/ordercheck")
public class SaleOrderCheckController {
	
	@Autowired
	private SaleOrderCheckService saleOrderCheckService;
	
	PageBean<SaleOrder> pageBean;
	/**
	 * 条件查询销售单列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getsaleorderchecklist",method=RequestMethod.GET)
	private Map<String, Object> getSaleOrderCheckList(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 获取页码
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		// 获取每页展示的数据量
		int pageSize = 5;
		// 获取查询条件
		String checkOrderNumber = HttpServletRequestUtil.getString(request,"checkSaleOrderNumber");
		int checkStateNumber = HttpServletRequestUtil.getInt(request, "checkSaleOrderStateNumber");
		//抛出checkStateNumber为null异常抛-1判断!=-1时候添加条件，否则不set此条件
		SaleOrder saleOrderCondition = compactSaleOrderCheckCondition(checkOrderNumber, checkStateNumber);
		// 非空判断
		if (pageIndex > -1) {
			TExecution<SaleOrder> saleOrderCheckExecution = saleOrderCheckService.getPurchaseOrderCheckList(saleOrderCondition, pageIndex, pageSize);
			pageBean = new PageBean<SaleOrder>();
			// 总记录数
			pageBean.setAllRowCounts(saleOrderCheckExecution.getCount());
			// 采购单列表
			pageBean.setDatas(saleOrderCheckExecution.getData());
			// 每页记录数
			pageBean.setPageSize(5);
			// 当前页
			pageBean.setCurPage(pageIndex);
			int sumPages = PageBean.getSumPages(saleOrderCheckExecution.getCount(), pageSize);
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
			modelMap.put("stateInfo", saleOrderCheckExecution.getStateInfo());
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
	@RequestMapping(value="/modifysaleordercheck",method=RequestMethod.GET)
	private Map<String, Object> modifySaleOrderCheck(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String,Object>();
		//这里的数据要包含ID因为是根据ID进行更新
		long sorderId = HttpServletRequestUtil.getLong(request, "sorderId");
		SaleOrder saleOrder = new SaleOrder();
		saleOrder.setSorderId(sorderId);
		//后期session获取----------------------------------
		saleOrder.setCheckMan("xiaodan");
		try {
			int effectNum = saleOrderCheckService.modifySaleOrderCheck(saleOrder);
			if (effectNum < 1) {
				modelMap.put("success", false);
				modelMap.put("errMsg", "审核通过销售单失败");
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
	@RequestMapping(value="/modifysaleorderrevoke",method=RequestMethod.GET)
	private Map<String, Object> modifySaleOrderRevoke(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String,Object>();
		//这里的数据要包含ID因为是根据ID进行更新
		long sorderId = HttpServletRequestUtil.getLong(request, "sorderId");
		SaleOrder saleOrder = new SaleOrder();
		saleOrder.setSorderId(sorderId);
		//后期session获取----------------------------------
		saleOrder.setCheckMan("xiaodan");
		try {
			int effectNum = saleOrderCheckService.modifySaleOrderRevoke(saleOrder);
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
	 * 判断对于查询销售单时候的筛选条件
	 * @param orderNumber
	 * @param checkState
	 * @return
	 */
	private SaleOrder compactSaleOrderCheckCondition(String orderNumber,int checkState) {
		SaleOrder saleOrderCondition = new SaleOrder();
		// 若有指定订单号的要求则添加进去
		if (orderNumber != null) {
			saleOrderCondition.setOrderNumber(orderNumber);
		}
		// 若有指定订单经理审核通过
		if (checkState != -1L) {
			saleOrderCondition.setCheckState(checkState);
		}
		return saleOrderCondition;
	}
}
