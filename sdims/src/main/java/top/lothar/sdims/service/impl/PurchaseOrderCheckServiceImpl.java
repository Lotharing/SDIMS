package top.lothar.sdims.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.lothar.sdims.dao.PurchaseOrderDao;
import top.lothar.sdims.dto.TExecution;
import top.lothar.sdims.entity.PurchaseOrder;
import top.lothar.sdims.service.PurchaseOrderCheckService;
import top.lothar.sdims.util.PageCalculator;

@Service
public class PurchaseOrderCheckServiceImpl implements PurchaseOrderCheckService {
	
	@Autowired
	private PurchaseOrderDao purchaseOrderDao;

	@Override
	public int modifyPurchaseOrderCheck(PurchaseOrder purchaseOrder) {
		// TODO Auto-generated method stub
		purchaseOrder.setCheckState(1);
		purchaseOrder.setCheckResult("审核通过");
		purchaseOrder.setCheckTime(new Date());
		return purchaseOrderDao.updatePurchaseOrder(purchaseOrder);
	}

	@Override
	public int modifyPurchaseOrderRevoke(PurchaseOrder purchaseOrder) {
		// TODO Auto-generated method stub
		purchaseOrder.setCheckState(0);
		purchaseOrder.setCheckResult("待审核");
		purchaseOrder.setCheckTime(new Date());
		return purchaseOrderDao.updatePurchaseOrder(purchaseOrder);
	}
	
	@Override
	public TExecution<PurchaseOrder> getPurchaseOrderCheckList(PurchaseOrder purchaseOrderCondition, int pageIndex,int pageSize) {
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
