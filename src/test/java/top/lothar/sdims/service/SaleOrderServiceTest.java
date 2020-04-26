package top.lothar.sdims.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import top.lothar.sdims.BaseTest;
import top.lothar.sdims.dto.TExecution;
import top.lothar.sdims.entity.SaleOrder;

public class SaleOrderServiceTest extends BaseTest{
	
	@Autowired
	private SaleOrderService saleOrderService;
	@Test
	public void test() {
		SaleOrder saleOrder = new SaleOrder();
		TExecution<SaleOrder> saleOrderExecution = saleOrderService.getSaleOrderList(saleOrder, 1, 5);
		System.out.println(saleOrderExecution.getData().get(0).getGoods().getName());
	}
}
