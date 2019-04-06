package top.lothar.sdims.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/admin", method=RequestMethod.GET)
public class AdminViewController {
	/**
	 * 管理员主页
	 * @return
	 */
	@RequestMapping(value="index", method=RequestMethod.GET)
	public String adminIndex() {
		return "admin/index";
	}
	/**
	 * 系统欢迎展示页
	 * @return
	 */
	@RequestMapping(value="welcome", method=RequestMethod.GET)
	public String welcome() {
		return "comment/welcome";
	}
	/**
	 * 角色信息展示页
	 * @return
	 */
	@RequestMapping(value="role", method=RequestMethod.GET)
	public String role() {
		return "admin/role";
	}
	/**
	 * 员工展示页
	 * @return
	 */
	@RequestMapping(value="employee", method=RequestMethod.GET)
	public String employee() {
		return "admin/employee";
	}
	/**
	 * 仓库展示页(角色共有部分)
	 * @return
	 */
	@RequestMapping(value="repo", method=RequestMethod.GET)
	public String repo() {
		return "comment/repo";
	}
	/**
	 * 商品展示页
	 * @return
	 */
	@RequestMapping(value="goods", method=RequestMethod.GET)
	public String goods() {
		return "admin/goods";
	}
	/**
	 * 供应商管理页
	 * @return
	 */
	@RequestMapping(value="purchasesupplier", method=RequestMethod.GET)
	public String purchaseSupplier() {
		return "purchase/purchasesupplier";
	}
	/**
	 * 采购订单管理页
	 * @return
	 */
	@RequestMapping(value="purchaseorder", method=RequestMethod.GET)
	public String purchaseOrder() {
		return "purchase/purchaseorder";
	}
	/**
	 * 销售商管理页(销售管理员专用路由)
	 * @return
	 */
	@RequestMapping(value="salecustomer", method=RequestMethod.GET)
	public String saleCustomer() {
		return "sale/salecustomer";
	}
}
