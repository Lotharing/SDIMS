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
	/*-------------------------------------------基础信息页----------------------------------------------*/
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
	/*-------------------------------------------采购管理页----------------------------------------------*/
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
	/*-------------------------------------------销售管理页----------------------------------------------*/
	/**
	 * 销售商管理页
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
	/*-------------------------------------------订单审核管理页----------------------------------------------*/
	/**
	 * 采购单审核管理页
	 * @return
	 */
	@RequestMapping(value="purchaseordercheck", method=RequestMethod.GET)
	public String purchaseOrderCheck() {
		return "ordercheck/purchaseordercheck";
	}
	/**
	 * 销售单审核管理页
	 * @return
	 */
	@RequestMapping(value="saleordercheck", method=RequestMethod.GET)
	public String saleOrderCheck() {
		return "ordercheck/saleordercheck";
	}
	/*-------------------------------------------库存管理页----------------------------------------------*/
	/**
	 * 仓库管理页(仓库管理员专用路由)
	 * @return
	 */
	@RequestMapping(value="stock", method=RequestMethod.GET)
	public String stock() {
		return "stock/stock";
	}
	/**
	 * 入库管理页(仓库管理员专用路由)
	 * @return
	 */
	@RequestMapping(value="intostock", method=RequestMethod.GET)
	public String intoStock() {
		return "stock/intostock";
	}
	/**
	 * 出库管理页(仓库管理员专用路由)
	 * @return
	 */
	@RequestMapping(value="outostock", method=RequestMethod.GET)
	public String outoStock() {
		return "stock/outostock";
	}
	/*-------------------------------------------统计分析页----------------------------------------------*/
	/**
	 * 采购统计
	 * @return
	 */
	@RequestMapping(value="purchasestatistics", method=RequestMethod.GET)
	public String purchaseStatistics() {
		return "statistics/purchasestatistics";
	}
	/**
	 * 销售统计
	 * @return
	 */
	@RequestMapping(value="salestatistics", method=RequestMethod.GET)
	public String saleStatistics() {
		return "statistics/salestatistics";
	}
	/*-------------------------------------------用户管理页----------------------------------------------*/
	/**
	 * 用户管理页(管理员专用路由)
	 * @return
	 */
	@RequestMapping(value="user", method=RequestMethod.GET)
	public String user() {
		return "admin/user";
	}
	/**
	 * 用户修改密码页(所有用户共有功能)
	 * @return
	 */
	@RequestMapping(value="modifypassword", method=RequestMethod.GET)
	public String modifyPassword() {
		return "comment/modifypassword";
	}
}
