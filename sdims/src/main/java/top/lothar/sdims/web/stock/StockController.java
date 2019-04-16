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
import top.lothar.sdims.entity.Goods;
import top.lothar.sdims.entity.Repository;
import top.lothar.sdims.entity.Stock;
import top.lothar.sdims.service.StockService;
import top.lothar.sdims.util.HttpServletRequestUtil;
import top.lothar.sdims.util.PageBean;
/**
 * 库存
 * @author Lothar
 *
 */
@Controller
@RequestMapping("/stock")
public class StockController {
	
	@Autowired
	private StockService stockService;
	
	private PageBean<Stock> pageBean;
	/**
	 * 条件查询库存列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getstocklist",method=RequestMethod.GET)
	private Map<String, Object> getStockList(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 获取页码
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		// 获取每页展示的数据量
		int pageSize = 8;
		// 获取查询条件商品名
		String goodsName = HttpServletRequestUtil.getString(request, "goodsName");
		//获取仓库名
		String reositoryName = HttpServletRequestUtil.getString(request, "repositoryName");
		//组合条件
		Stock stockCondition = compactStockCondition(goodsName, reositoryName);
		// 非空判断
		if (pageIndex > -1) {
			TExecution<Stock> stockExecution = stockService.getStockList(stockCondition, pageIndex, pageSize);
			pageBean = new PageBean<Stock>();
			// 总记录数
			pageBean.setAllRowCounts(stockExecution.getCount());
			// 仓库列表
			pageBean.setDatas(stockExecution.getData());
			// 每页记录数
			pageBean.setPageSize(8);
			// 当前页
			pageBean.setCurPage(pageIndex);
			int sumPages = PageBean.getSumPages(stockExecution.getCount(), pageSize);
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
			modelMap.put("stateInfo", stockExecution.getStateInfo());
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "pageIndex or pageSize is Null");
		}
		return modelMap;
	}
	
	/**
	 * 判断对于查询库存时候的筛选条件
	 * @param orderNumber
	 * @param checkState
	 * @return
	 */
	private Stock compactStockCondition(String goodsName,String reositoryName) {
		Stock stockCondition = new Stock();
		// 若有指定商品名的要求则添加进去
		if (goodsName != null) {
			Goods goods = new Goods();
			goods.setName(goodsName);
			stockCondition.setGoods(goods);
		}
		// 若有指定仓库名则添加进去
		if (reositoryName != null) {
			Repository repository = new Repository();
			repository.setName(reositoryName);
			stockCondition.setRepository(repository);
		}
		return stockCondition;
	}
}
