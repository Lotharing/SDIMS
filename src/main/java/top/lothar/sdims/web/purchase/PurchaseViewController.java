package top.lothar.sdims.web.purchase;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/purchase", method=RequestMethod.GET)
public class PurchaseViewController {
	/**
	 * 采购管理页(采购员专用路由)
	 * @return
	 */
	@RequestMapping(value="index", method=RequestMethod.GET)
	public String index() {
		return "purchase/index";
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
	 * 商品展示页
	 * @return
	 */
	@RequestMapping(value="goods", method=RequestMethod.GET)
	public String goods() {
		return "admin/goods";
	}
	/*-------------------------------------------采购管理功能----------------------------------------------*/
	/**
	 * 供应商管理页(采购员专用路由)
	 * @return
	 */
	@RequestMapping(value="purchasesupplier", method=RequestMethod.GET)
	public String purchaseSupplier() {
		return "purchase/purchasesupplier";
	}
	/**
	 * 采购订单管理页(采购员专用路由)
	 * @return
	 */
	@RequestMapping(value="purchaseorder", method=RequestMethod.GET)
	public String purchaseOrder() {
		return "purchase/purchaseorder";
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
