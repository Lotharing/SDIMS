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

}
