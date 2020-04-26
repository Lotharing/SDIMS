package top.lothar.sdims.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import top.lothar.sdims.dao.GoodsDao;
import top.lothar.sdims.dao.PurchaseOrderDao;
import top.lothar.sdims.dao.SaleOrderDao;
import top.lothar.sdims.dao.StockDao;
import top.lothar.sdims.dto.TExecution;
import top.lothar.sdims.entity.Goods;
import top.lothar.sdims.exceptions.GoodsOperationException;
import top.lothar.sdims.service.GoodsService;
import top.lothar.sdims.util.PageCalculator;
@Service
public class GoodsServiceImpl implements GoodsService {
	
	@Autowired
	private GoodsDao goodsDao;
	
	@Autowired
	private SaleOrderDao saleOrderDao;
	
	@Autowired
	private PurchaseOrderDao purchaseOrderDao;
	
	@Autowired
	private StockDao stockDao;
	
	@Override
	public int addGoods(Goods goods) {
		// TODO Auto-generated method stub
		return goodsDao.insertGoods(goods);
	}

	@Override
	@Transactional
	public int removeGoods(long goodsId) {
		// TODO Auto-generated method stub
		try {
			//删除销售单
			saleOrderDao.deleteSaleOrderById(0, goodsId);
			//删除采购单
			purchaseOrderDao.deletePurchaseOrderById(0, goodsId);
			//删除库存记录
			stockDao.deleteStockById(0, goodsId);
		} catch (Exception e) {
			// TODO: handle exception
			throw new GoodsOperationException("商品所关联信息删除失败"+e.getMessage());
		}
		//最后删除商品
		return goodsDao.deleteGoodsById(goodsId);
	}

	@Override
	public int modifyGoods(Goods goods) {
		// TODO Auto-generated method stub
		return goodsDao.updateGoods(goods);
	}

	@Override
	public Goods getGoodsById(long goodsId) {
		// TODO Auto-generated method stub
		return goodsDao.queryGoodsById(goodsId);
	}

	@Override
	public List<Goods> getAllGoodsList() {
		// TODO Auto-generated method stub
		return goodsDao.queryAllGoodsList();
	}
	
	@Override
	public TExecution<Goods> getGoodsList(Goods goodsCondition, int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
		List<Goods> goodsList = goodsDao.queryGoodsList(goodsCondition, rowIndex, pageSize);
		int count = goodsDao.queryGoodsCount(goodsCondition);
		TExecution<Goods> goodsExecution = new TExecution<Goods>();
		if (goodsList!=null) {
			goodsExecution.setCount(count);
			goodsExecution.setData(goodsList);
			goodsExecution.setStateInfo("成功得到商品");
		}else {
			return new TExecution<Goods>("商品获取失败");
		}
		return goodsExecution;
	}


}
