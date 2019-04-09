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
import top.lothar.sdims.entity.SaleOrder;
import top.lothar.sdims.service.OutoStockService;
import top.lothar.sdims.util.HttpServletRequestUtil;
import top.lothar.sdims.util.PageBean;
/**
 * 出库
 * @author Lothar
 *
 */
@Controller
@RequestMapping("/stock")
public class OutoStockController {
	//库存不足识别码
	private static final int STOCK_COUNT_SATATE = 1415926;
	//没有对应库存元组识别码
	private static final int NULL_STOCK = 809676;
	@Autowired
	private OutoStockService outoStockService;
	
	private PageBean<SaleOrder> pageBean;
	
	/**
	 * 条件查询需入库采购单列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getoutosaleorderchecklist",method=RequestMethod.GET)
	private Map<String, Object> getOutoPurchaseOrderCheckList(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 获取页码
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		// 获取每页展示的数据量
		int pageSize = 5;
		// 获取查询条件
		String checkOrderNumber = HttpServletRequestUtil.getString(request,"checkSaleOrderNumber");
		int stockStateNumber = HttpServletRequestUtil.getInt(request, "stockStateNumber");
		//抛出checkStateNumber为null异常抛-1判断!=-1时候添加条件，否则不set此条件
		SaleOrder saleOrderCondition = compactSaleOrderCheckCondition(checkOrderNumber, stockStateNumber);
		// 非空判断
		if (pageIndex > -1) {
			TExecution<SaleOrder> outoStockSaleOrderCheckExecution = outoStockService.getOutoStockPurchaseOrderCheckList(saleOrderCondition, pageIndex, pageSize);
			pageBean = new PageBean<SaleOrder>();
			// 总记录数
			pageBean.setAllRowCounts(outoStockSaleOrderCheckExecution.getCount());
			// 采购单列表
			pageBean.setDatas(outoStockSaleOrderCheckExecution.getData());
			// 每页记录数
			pageBean.setPageSize(5);
			// 当前页
			pageBean.setCurPage(pageIndex);
			int sumPages = PageBean.getSumPages(outoStockSaleOrderCheckExecution.getCount(), pageSize);
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
			modelMap.put("stateInfo", outoStockSaleOrderCheckExecution.getStateInfo());
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "pageIndex or pageSize is Null");
		}
		return modelMap;
	}
	/**
	 * 根据前台传过来的信息对应ID通过审核-出库
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/modifyoutostocksaleordercheck",method=RequestMethod.GET)
	private Map<String, Object> modifyOutoStockSaleOrderCheck(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String,Object>();
		//这里的数据要包含ID因为是根据ID进行更新
		long sorderId = HttpServletRequestUtil.getLong(request, "sorderId");
		SaleOrder saleOrder = new SaleOrder();
		saleOrder.setSorderId(sorderId);
		//后期session获取----------------------------------入库审核人，库管
		saleOrder.setStockMan("xiaokudan");
		try {
			int effectNum = outoStockService.modifyOutoStockSaleOrderCheck(saleOrder);
			if (effectNum==STOCK_COUNT_SATATE) {
				modelMap.put("success", false);
				modelMap.put("errMsg", "审核失败-库存不足");
			}
			if (effectNum==NULL_STOCK) {
				modelMap.put("success", false);
				modelMap.put("errMsg", "审核失败-此仓库没有此商品,请通知销售员修改仓库");
			}
			if (effectNum < 1) {
				modelMap.put("success", false);
				modelMap.put("errMsg", "审核通过销售单失败");
			}else if(effectNum==1){
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
	 * 判断对于查询需入库销售单时候的筛选条件
	 * @param orderNumber
	 * @param checkState
	 * @return
	 */
	private SaleOrder compactSaleOrderCheckCondition(String checkOrderNumber,int stockStateNumber) {
		SaleOrder saleOrderCondition = new SaleOrder();
		//通过第一次审核的条件
		saleOrderCondition.setCheckState(1);
		// 若有指定订单号的要求则添加进去
		if (checkOrderNumber != null) {
			saleOrderCondition.setOrderNumber(checkOrderNumber);
		}
		// 若有指定订单经理审核通过
		if (stockStateNumber != -1L) {
			saleOrderCondition.setStockState(stockStateNumber);
		}
		return saleOrderCondition;
	}
}
