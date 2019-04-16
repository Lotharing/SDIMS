package top.lothar.sdims.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.lothar.sdims.dao.SaleOrderDao;
import top.lothar.sdims.dao.StockDao;
import top.lothar.sdims.dto.TExecution;
import top.lothar.sdims.entity.SaleOrder;
import top.lothar.sdims.entity.Stock;
import top.lothar.sdims.service.OutoStockService;
import top.lothar.sdims.util.PageCalculator;

@Service
public class OutoStockServiceImpl implements OutoStockService{
	
	@Autowired
	private SaleOrderDao saleOrderDao;
	
	@Autowired
	private StockDao stockDao;

	@Override
	@Transactional
	public int modifyOutoStockSaleOrderCheck(SaleOrder saleOrder) {
		// TODO Auto-generated method stub
		saleOrder.setStockState(1);
		saleOrder.setStockTime(new Date());
		/**
		 * 审核通过销售单，减少库存业务逻辑
		 *1.根据ID获取销售单商品，商品-仓库--数量--单个售价
		 *2.true---根据商品，仓库，找到对应的库存记录元组（并获取元组信息，包括ID）
		 * 	 false--如果没有找到这条元组库存记录，返回此仓库没有此商品，请通知销售员修改仓库
		 *3.获取元组总数量，销售量，计算出库存量，if(true 库存量>=当前销售单量)-》  总销售量+当前订单销售量set进总销售量，  if(false)->return 1415926
		 *6.根据stockId,去更新库存数据
		 */
		//当前销售单
		SaleOrder currentSaleOrder = saleOrderDao.querySaleOrderById(saleOrder.getSorderId());
		//当前订单商品名称
		String goodsName = currentSaleOrder.getGoods().getName();
		//当前订单仓库名称
		String repositoryName = currentSaleOrder.getRepository().getName();
		//订单销售量
		int orderSaleCount = currentSaleOrder.getCount();
		//根据商品名和仓库名去查询库存记录元组
		Stock currentStock = stockDao.queryStockByGoodsNameAndRepositoryName(goodsName, repositoryName);
		if (currentStock!=null) {
			//更新库存指定元组ID主键
			long stockId = currentStock.getStockId();
			//获取出库前总数量
			int stockTotalCount = currentStock.getTotalCount();
			//获取元组销售量
			int stockSaleCount = currentStock.getSaleCount();
			//出库后总销售量
			int stockSaleCountAddOrderSaleCount;
			//计算库存剩余量
			int stockRemainder = stockTotalCount-stockSaleCount;
			if (stockRemainder>=orderSaleCount) {
				stockSaleCountAddOrderSaleCount=stockSaleCount+orderSaleCount;
				Stock stock = new Stock();
				stock.setStockId(stockId);
				stock.setSaleCount(stockSaleCountAddOrderSaleCount);
				stock.setUpdateTime(new Date());
				int updateStock = stockDao.updateStock(stock);
				if (updateStock<1) {
					//更新失败->审核失败
					return 0;
				}
			}else {
				//库存不足
				return 1415926;
			}
		}else {
			//没有此库存元组(此仓库没有该商品)
			return 809676;
		}
		//更新采购单信息
		return saleOrderDao.updateSaleOrder(saleOrder);
	}

	@Override
	public TExecution<SaleOrder> getOutoStockPurchaseOrderCheckList(SaleOrder saleOrderCondition, int pageIndex,int pageSize) {
		// TODO Auto-generated method stub
		int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
		List<SaleOrder> saleOrderCheckList = saleOrderDao.querySaleOrderList(saleOrderCondition, rowIndex, pageSize);
		int count = saleOrderDao.querySaleOrderCount(saleOrderCondition);
		TExecution<SaleOrder> saleOrderCheckExecution = new TExecution<SaleOrder>();
		if (saleOrderCheckList!=null) {
			saleOrderCheckExecution.setData(saleOrderCheckList);
			saleOrderCheckExecution.setCount(count);
			saleOrderCheckExecution.setStateInfo("成功得到数据");
		}else {
			return new TExecution<SaleOrder>("数据获取失败");
		}
		return saleOrderCheckExecution;
	}
}
