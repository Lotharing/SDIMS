package top.lothar.sdims.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.lothar.sdims.dao.SaleOrderDao;
import top.lothar.sdims.dto.TExecution;
import top.lothar.sdims.entity.SaleOrder;
import top.lothar.sdims.service.SaleOrderCheckService;
import top.lothar.sdims.util.PageCalculator;

@Service
public class SaleOrderCheckServiceImpl implements SaleOrderCheckService {
	
	@Autowired
	private SaleOrderDao saleOrderDao;
	
	@Override
	public int modifySaleOrderCheck(SaleOrder saleOrder) {
		// TODO Auto-generated method stub
		saleOrder.setCheckState(1);
		saleOrder.setCheckResult("审核通过");
		saleOrder.setCheckTime(new Date());
		return saleOrderDao.updateSaleOrder(saleOrder);
	}

	@Override
	public int modifySaleOrderRevoke(SaleOrder saleOrder) {
		// TODO Auto-generated method stub
		saleOrder.setCheckState(0);
		saleOrder.setCheckResult("待审核");
		saleOrder.setCheckTime(new Date());
		return saleOrderDao.updateSaleOrder(saleOrder);
	}

	@Override
	public TExecution<SaleOrder> getPurchaseOrderCheckList(SaleOrder saleOrderCondition, int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
		List<SaleOrder> SaleOrderCheckList = saleOrderDao.querySaleOrderList(saleOrderCondition, rowIndex, pageSize);
		int count = saleOrderDao.querySaleOrderCount(saleOrderCondition);
		TExecution<SaleOrder> saleOrderCheckExecution = new TExecution<SaleOrder>();
		if (SaleOrderCheckList!=null) {
			saleOrderCheckExecution.setData(SaleOrderCheckList);
			saleOrderCheckExecution.setCount(count);
			saleOrderCheckExecution.setStateInfo("成功得到数据");
		}else {
			return new TExecution<SaleOrder>("数据获取失败");
		}
		return saleOrderCheckExecution;
	}

}
