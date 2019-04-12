package top.lothar.sdims.web.stock;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/stock", method=RequestMethod.GET)
public class StockViewController {
	/**
	 * 仓库管理页(仓库管理员专用路由)
	 * @return
	 */
	@RequestMapping(value="index", method=RequestMethod.GET)
	public String index() {
		return "stock/index";
	}
	/**
	 * 系统欢迎展示页
	 * @return
	 */
	@RequestMapping(value="welcome", method=RequestMethod.GET)
	public String welcome() {
		return "comment/welcome";
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
	/*-------------------------------------------基础信息页----------------------------------------------*/
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
