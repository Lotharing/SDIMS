package top.lothar.sdims.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.lothar.sdims.dao.PurchaseOrderDao;
import top.lothar.sdims.dao.PurchaseSupplierDao;
import top.lothar.sdims.dto.TExecution;
import top.lothar.sdims.entity.PurchaseOrder;
import top.lothar.sdims.entity.Supplier;
import top.lothar.sdims.service.PurchaseOrderService;
import top.lothar.sdims.util.OrderNumberUtil;
import top.lothar.sdims.util.PageCalculator;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {
	
	@Autowired
	private PurchaseOrderDao purchaseOrderDao;
	
	@Autowired
	private PurchaseSupplierDao purchaseSupplierDao;
	
	@Override
	public int addPurchaseOrder(PurchaseOrder purchaseOrder) {
		// TODO Auto-generated method stub	session获取创建者未实现
		
		//1.生成订单随机数时间+五位随机
		OrderNumberUtil orderNumberUtil = new OrderNumberUtil();
		String orderNumber = orderNumberUtil.getRandomFileName();
		//2.根据供应商Id查询供应商Name
		Long supplierId = purchaseOrder.getSupplier().getSupplierId();
		Supplier supplier = purchaseSupplierDao.queryPurchaseSupplierById(supplierId);
		String supplierName =supplier.getName();
		//3.需要根据前台的count和unitPrice计算TotalPrice
		int count = purchaseOrder.getCount();
		Double unitPrice = purchaseOrder.getUnitPrice();
		Double totalPrice = (double) (count*unitPrice);
		//4.设置初始化信息
		purchaseOrder.setSupplierName(supplierName);
		purchaseOrder.setOrderNumber(orderNumber);
		purchaseOrder.setTotalPrice(totalPrice);
		purchaseOrder.setCreateTime(new Date());
		purchaseOrder.setCheckState(0);
		purchaseOrder.setCheckResult("待审核");
		purchaseOrder.setStockState(0);
		return purchaseOrderDao.insertPurchaseOrder(purchaseOrder);
	}

	@Override
	public int removePurchaseOrderById(long porderId) {
		// TODO Auto-generated method stub
		return purchaseOrderDao.deletePurchaseOrderById(porderId, 0);
	}

	@Override
	public int modifyPurchaseOrder(PurchaseOrder purchaseOrder) {
		// TODO Auto-generated method stub		后期可能会对商品，供应商，仓库进行业务更新
		//重新计算总价，防止修改数量或者单价
		int count = purchaseOrder.getCount();
		Double unitPrice = purchaseOrder.getUnitPrice();
		Double totalPrice = (double) (count*unitPrice);
		purchaseOrder.setTotalPrice(totalPrice);
		return purchaseOrderDao.updatePurchaseOrder(purchaseOrder);
	}

	@Override
	public PurchaseOrder getPurchaseOrderById(long porderId) {
		// TODO Auto-generated method stub
		return purchaseOrderDao.queryPurchaseOrderById(porderId);
	}

	@Override
	public TExecution<PurchaseOrder> getPurchaseOrderList(PurchaseOrder purchaseOrderCondition,int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
		List<PurchaseOrder> purchaseOrderList = purchaseOrderDao.queryPurchaseOrderList(purchaseOrderCondition, rowIndex, pageSize);
		int count = purchaseOrderDao.queryPurchaseOrderCount(purchaseOrderCondition);
		TExecution<PurchaseOrder> purchaseOrderExecution = new TExecution<PurchaseOrder>();
		if (purchaseOrderList!=null) {
			purchaseOrderExecution.setData(purchaseOrderList);
			purchaseOrderExecution.setCount(count);
			purchaseOrderExecution.setStateInfo("成功得到数据");
		}else {
			return new TExecution<PurchaseOrder>("数据获取失败");
		}
		return purchaseOrderExecution;
	}

}
