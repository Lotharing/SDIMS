package top.lothar.sdims.web.sale;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/sale", method=RequestMethod.GET)
public class SaleViewController {
	/**
	 * 销售管理页(销售员专用路由)
	 * @return
	 */
	@RequestMapping(value="index", method=RequestMethod.GET)
	public String index() {
		return "sale/index";
	}
	/**
	 * 系统欢迎展示页
	 * @return
	 */
	@RequestMapping(value="welcome", method=RequestMethod.GET)
	public String welcome() {
		return "comment/welcome";
	}
	/*-------------------------------------------销售管理页----------------------------------------------*/
	/**
	 * 销售商管理页(销售员专用路由)
	 * @return
	 */
	@RequestMapping(value="salecustomer", method=RequestMethod.GET)
	public String saleCustomer() {
		return "sale/salecustomer";
	}
	/**
	 * 销售订单管理页(销售员专用路由)
	 * @return
	 */
	@RequestMapping(value="saleorder", method=RequestMethod.GET)
	public String saleOrder() {
		return "sale/saleorder";
	}
	/*-------------------------------------------统计分析页----------------------------------------------*/
	/**
	 * 销售统计
	 * @return
	 */
	@RequestMapping(value="salestatistics", method=RequestMethod.GET)
	public String saleStatistics() {
		return "statistics/salestatistics";
	}
	/*-------------------------------------------账户管理页----------------------------------------------*/
	/**
	 * 用户修改密码页(所有用户共有功能)
	 * @return
	 */
	@RequestMapping(value="modifypassword", method=RequestMethod.GET)
	public String modifyPassword() {
		return "comment/modifypassword";
	}
}
