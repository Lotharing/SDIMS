package top.lothar.sdims.web.purchase;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/purchase", method=RequestMethod.GET)
public class PurchaseViewController {
	/**
	 * 供应商管理页(采购管理员专用路由)
	 * @return
	 */
	@RequestMapping(value="purchasesupplier", method=RequestMethod.GET)
	public String purchaseSupplier() {
		return "purchase/purchaseSupplier";
	}
	/**
	 * 采购订单管理页
	 * @return
	 */
	@RequestMapping(value="purchaseorder", method=RequestMethod.GET)
	public String purchaseOrder() {
		return "purchase/purchaseorder";
	}
}
