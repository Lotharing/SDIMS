package top.lothar.sdims.web.sale;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/sale", method=RequestMethod.GET)
public class SaleViewController {
	/**
	 * 销售商管理页(销售管理员专用路由)
	 * @return
	 */
	@RequestMapping(value="salecustomer", method=RequestMethod.GET)
	public String saleCustomer() {
		return "sale/salecustomer";
	}
	/**
	 * 销售订单管理页
	 * @return
	 */
	@RequestMapping(value="saleorder", method=RequestMethod.GET)
	public String saleOrder() {
		return "sale/saleorder";
	}
}
