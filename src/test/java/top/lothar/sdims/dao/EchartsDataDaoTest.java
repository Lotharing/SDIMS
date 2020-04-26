package top.lothar.sdims.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javassist.expr.NewArray;
import top.lothar.sdims.BaseTest;
import top.lothar.sdims.entity.EchartsData;
import top.lothar.sdims.entity.PurchaseOrder;
import top.lothar.sdims.entity.SaleOrder;

public class EchartsDataDaoTest extends BaseTest{
	
	@Autowired
	private EchartsDataDao echartsDataDao;
	
	@Ignore
	public void test() {
		String startDate = "2019-04-01";
		String endDate = "2019-05-01";
		List<PurchaseOrder> purchaseOrderList = null;
		List<EchartsData> echartsDataList = new ArrayList<EchartsData>();
		for (int i = 1; i <= 12; i++) {
			EchartsData echartsData = new EchartsData();
			startDate = "2019-"+i+"-01";
			if (i==12) {
				endDate = "2019-"+i+"-31";
			}else {
				endDate = "2019-"+(i+1)+"-01";
			}	
			purchaseOrderList = echartsDataDao.queryPurchaseOrderListByDate(startDate, endDate);
			
			int totalCount = 0 ;
			double totalPrice = 0;
			for (PurchaseOrder purchaseOrder : purchaseOrderList) {
				totalCount = purchaseOrder.getCount()+totalCount;
				totalPrice = purchaseOrder.getTotalPrice()+totalPrice;
			}	
			echartsData.setTotalCount(totalCount);
			echartsData.setTotalPrice(totalPrice);
			echartsDataList.add(echartsData);
		}
		
		for (int i = 0; i < 12; i++) {
			System.out.println(echartsDataList.get(i).getTotalCount());
			System.out.println(echartsDataList.get(i).getTotalPrice());
		}
	}
	
	@Ignore
	public void testDate() {
		Date date = new Date();

		String now = new SimpleDateFormat("yyyy-MM-dd").format(date);

		System.out.println(now);
		
	}
	
	@Test
	public void testC() {
		String startDate = "2019-04-01";
		String endDate = "2019-05-01";
		List<SaleOrder> saleOrderList = null;
		List<EchartsData> echartsDataList = new ArrayList<EchartsData>();
		for (int i = 1; i <= 12; i++) {
			EchartsData echartsData = new EchartsData();
			startDate = "2019-"+i+"-01";
			if (i==12) {
				endDate = "2019-"+i+"-31";
			}else {
				endDate = "2019-"+(i+1)+"-01";
			}	
			saleOrderList = echartsDataDao.querySaleOrderListByDate(startDate, endDate);
			
			int totalCount = 0 ;
			double totalPrice = 0;
			for (SaleOrder saleOrder : saleOrderList) {
				totalCount = saleOrder.getCount()+totalCount;
				totalPrice = saleOrder.getTotalPrice()+totalPrice;
			}	
			echartsData.setTotalCount(totalCount);
			echartsData.setTotalPrice(totalPrice);
			echartsDataList.add(echartsData);
		}
		
		for (int i = 0; i < 12; i++) {
			System.out.println(echartsDataList.get(i).getTotalCount());
			System.out.println(echartsDataList.get(i).getTotalPrice());
		}
	}
}
