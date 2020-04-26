package top.lothar.sdims.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.lothar.sdims.dao.StockDao;
import top.lothar.sdims.dto.TExecution;
import top.lothar.sdims.entity.Stock;
import top.lothar.sdims.service.StockService;
import top.lothar.sdims.util.PageCalculator;

@Service
public class StockServiceImpl implements StockService {
	
	@Autowired
	private StockDao stockDao;
	
	@Override
	public int addStock(Stock stock) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int modifyStock(Stock stock) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TExecution<Stock> getStockList(Stock stockCondition, int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
		List<Stock> stockList = stockDao.queryStockList(stockCondition, rowIndex, pageSize);
		int count = stockDao.queryStockCount(stockCondition);
		TExecution<Stock> stockExecution = new TExecution<Stock>();
		if (stockList!=null) {
			stockExecution.setData(stockList);
			stockExecution.setCount(count);
			stockExecution.setStateInfo("成功得到数据");
		}else {
			return new TExecution<Stock>("数据获取失败");
		}
		return stockExecution;
	}

}
