package top.lothar.sdims.web.ordercheck;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/ordercheck", method=RequestMethod.GET)
public class OrderCheckViewController {
	/**
	 * 订单审核管理页(订单审核管理员专用路由)
	 * @return
	 */
	@RequestMapping(value="index", method=RequestMethod.GET)
	public String index() {
		return "ordercheck/index";
	}
	/**
	 * 系统欢迎展示页
	 * @return
	 */
	@RequestMapping(value="welcome", method=RequestMethod.GET)
	public String welcome() {
		return "comment/welcome";
	}
	/*-------------------------------------------订单审核管理页----------------------------------------------*/
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
