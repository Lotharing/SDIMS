package top.lothar.sdims.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import top.lothar.sdims.dao.GoodsDao;
import top.lothar.sdims.dao.PurchaseOrderDao;
import top.lothar.sdims.dao.StockDao;
import top.lothar.sdims.dto.TExecution;
import top.lothar.sdims.entity.Goods;
import top.lothar.sdims.entity.PurchaseOrder;
import top.lothar.sdims.entity.Repository;
import top.lothar.sdims.entity.Stock;
import top.lothar.sdims.service.IntoStockService;
import top.lothar.sdims.util.PageCalculator;
/**
 * 入库审核
 * @author Lothar
 *
 */
@Service
public class IntoStockServiceImpl implements IntoStockService {
	
	@Autowired
	private PurchaseOrderDao purchaseOrderDao;
	
	@Autowired
	private StockDao stockDao;
	
	@Autowired
	private GoodsDao goodsDao;
	
	@Override
	@Transactional
	public int modifyIntoStockPurchaseOrderCheck(PurchaseOrder purchaseOrder) {
		// TODO Auto-generated method stub
		purchaseOrder.setStockState(1);
		purchaseOrder.setStockTime(new Date());
		/**
		 * 通过订单入库业务逻辑实现
		 * 1.根据ID获取采购单商品，商品-仓库--数量--单个进价
		 * 2.true---根据商品，仓库，找到对应的库存记录元组（并获取元组信息，包括ID）
		 * 	 false--如果没有找到这条元组库存记录，那么就根据商品（Id），仓库(Id) ,采购数量（库存量），销售量（默认0）进价（订单中获得进价），售价（从商品表中获得），计算（成本，总值），更新时间，建立库存元组
		 * 3.获取元组的数量，并把新增的加上，得到当前总数，并set进数量
		 * 4.当前总数*进价-》库存成本并set进库存成本
		 * 5.当前总数*售价-》库存总值并set进库存总值
		 * 6.根据stockId,去更新库存数据
		 */
		//当前采购单
		PurchaseOrder currentPurchaseOrder = purchaseOrderDao.queryPurchaseOrderById(purchaseOrder.getPorderId());
		//当前订单商品名称
		String goodsName = currentPurchaseOrder.getGoods().getName();
		//当前订单仓库名称
		String repositoryName = currentPurchaseOrder.getRepository().getName();
		//根据商品名和仓库名去查询库存记录元组
		Stock currentStock = stockDao.queryStockByGoodsNameAndRepositoryName(goodsName, repositoryName);
		if (currentStock!=null) {
			//更新库存指定元组ID主键
			long stockId = currentStock.getStockId();
			//获取入库前总数量
			int stockTotalCount = currentStock.getTotalCount();
			//*增加库存数量（入库后总数量）
			int currentStockTotalCount = stockTotalCount+currentPurchaseOrder.getCount();
			//*当前库存成本: 采购单价*采购数量+入库前库存总价
			Double currentTotalBuyPrice = currentPurchaseOrder.getUnitPrice()*currentPurchaseOrder.getCount()+currentStock.getTotalBuyPrice();
			//*当前库存总值: 总数量*销售价
			Double currentTotalSalePrice = currentStockTotalCount*currentStock.getSalePrice();
			Stock newStock = new Stock();
			newStock.setStockId(stockId);
			newStock.setTotalCount(currentStockTotalCount);
			newStock.setTotalBuyPrice(currentTotalBuyPrice);
			newStock.setTotalSalePrice(currentTotalSalePrice);
			newStock.setUpdateTime(new Date());
			int updateStock = stockDao.updateStock(newStock);
			//如果失败给前台状态值，判断审核失败，并事务回滚
			if (updateStock<1) {
				return 0;
			}
		}else {
			//*采购单中商品ID
			long goodsId = currentPurchaseOrder.getGoods().getGoodsId();
			//*采购单中仓库ID
			long repositoryId = currentPurchaseOrder.getRepository().getRepoId();
			//*采购量=库存量(新库存记录)
			int currentStockTotalCount = currentPurchaseOrder.getCount();
			//*销售量(默认0)
			int currentStockSaleCount = 0;
			//*进价（采购单中获取，因为是当前最新进价）
			Double currentStockBuyPrice = currentPurchaseOrder.getUnitPrice();
			//*售价从商品表中获取(强转Double类型)因为数据库一开始设计为String了，很尴尬
			Goods currentGoods = goodsDao.queryGoodsById(goodsId);
			String salePrice = currentGoods.getSalePrice();
			Double currentStockSalePrice = Double.parseDouble(salePrice);
			//*库存成本:采购单价*采购数量+入库前库存总价
			Double currentStockTotalBuyPrice = currentStockBuyPrice*currentStockTotalCount;
			//*当前库存总值: 总数量*销售价
			Double currentStockTotalSalePrice = currentStockTotalCount*currentStockSalePrice;
			Stock stock = new Stock();
			stock.setGoods(currentGoods);
			Repository repository = new Repository();
			repository.setRepoId(repositoryId);
			stock.setRepository(repository);
			stock.setTotalCount(currentStockTotalCount);
			stock.setSaleCount(currentStockSaleCount);
			stock.setBuyPrice(currentStockBuyPrice);
			stock.setSalePrice(currentStockSalePrice);
			stock.setTotalBuyPrice(currentStockTotalBuyPrice);
			stock.setTotalSalePrice(currentStockTotalSalePrice);
			stock.setUpdateTime(new Date());
			int insertStock = stockDao.insertStock(stock);
			if (insertStock<1) {
				return 0;
			}
		}
		//更新审核单信息
		return purchaseOrderDao.updatePurchaseOrder(purchaseOrder);
	}

	@Override
	public TExecution<PurchaseOrder> getIntoStockPurchaseOrderCheckList(PurchaseOrder purchaseOrderCondition,int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
		List<PurchaseOrder> purchaseOrderCheckList = purchaseOrderDao.queryPurchaseOrderList(purchaseOrderCondition, rowIndex, pageSize);
		int count = purchaseOrderDao.queryPurchaseOrderCount(purchaseOrderCondition);
		TExecution<PurchaseOrder> purchaseOrderCheckExecution = new TExecution<PurchaseOrder>();
		if (purchaseOrderCheckList!=null) {
			purchaseOrderCheckExecution.setData(purchaseOrderCheckList);
			purchaseOrderCheckExecution.setCount(count);
			purchaseOrderCheckExecution.setStateInfo("成功得到数据");
		}else {
			return new TExecution<PurchaseOrder>("数据获取失败");
		}
		return purchaseOrderCheckExecution;
	}

}
