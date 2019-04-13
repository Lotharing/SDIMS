package top.lothar.sdims.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.lothar.sdims.dao.PurchaseSupplierDao;
import top.lothar.sdims.dto.TExecution;
import top.lothar.sdims.entity.Supplier;
import top.lothar.sdims.service.PurchaseSupplierService;
import top.lothar.sdims.util.PageCalculator;
@Service
public class PurchaseSupplierServiceImpl implements PurchaseSupplierService {
	
	@Autowired
	private PurchaseSupplierDao purchaseSupplierDao;
	
	@Override
	public int addPurchaseSupplier(Supplier supplier) {
		// TODO Auto-generated method stub
		return purchaseSupplierDao.insertPurchaseSupplie(supplier);
	}

	@Override
	public int removePurchaseSupplier(long supplierId) {
		// TODO Auto-generated method stub
		return purchaseSupplierDao.deletePurchaseSupplieById(supplierId);
	}

	@Override
	public int modifyPurchaseSupplier(Supplier supplier) {
		// TODO Auto-generated method stub
		return purchaseSupplierDao.updatePurchaseSupplie(supplier);
	}

	@Override
	public Supplier getPurchaseSupplierById(long supplierId) {
		// TODO Auto-generated method stub
		return purchaseSupplierDao.queryPurchaseSupplierById(supplierId);
	}
	
	@Override
	public List<Supplier> getAllSupplierList() {
		// TODO Auto-generated method stub
		return purchaseSupplierDao.queryAllSupplierList();
	}

	@Override
	public TExecution<Supplier> getPurchaseSupplierList(String supplierName, int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
		List<Supplier> purchaseSupplierList = purchaseSupplierDao.queryPurchaseSupplierList(supplierName, rowIndex, pageSize);
		int count = purchaseSupplierDao.queryPurchaseSupplierCount(supplierName);
		TExecution<Supplier> supplierExecution = new TExecution<Supplier>();
		if (purchaseSupplierList!=null) {
			supplierExecution.setData(purchaseSupplierList);
			supplierExecution.setCount(count);
			supplierExecution.setStateInfo("成功得到数据");
		}else {
			return new TExecution<Supplier>("数据获取失败");
		}
		return supplierExecution;
	}

}
