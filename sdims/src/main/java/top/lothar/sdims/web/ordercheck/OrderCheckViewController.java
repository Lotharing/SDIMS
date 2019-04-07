package top.lothar.sdims.web.ordercheck;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/ordercheck", method=RequestMethod.GET)
public class OrderCheckViewController {
	/**
	 * 采购单审核管理页(订单经理专用路由)
	 * @return
	 */
	@RequestMapping(value="purchaseordercheck", method=RequestMethod.GET)
	public String purchaseOrderCheck() {
		return "ordercheck/purchaseordercheck";
	}
	/**
	 * 销售单审核管理页(订单经理专用路由)
	 * @return
	 */
	@RequestMapping(value="saleordercheck", method=RequestMethod.GET)
	public String saleOrderCheck() {
		return "ordercheck/saleordercheck";
	}
}
