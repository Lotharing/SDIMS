package top.lothar.sdims.web.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import top.lothar.sdims.entity.Role;
import top.lothar.sdims.service.RoleService;

@Controller
@RequestMapping("/admin")
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	/**
	 * 获取角色信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/listrole", method=RequestMethod.GET)
	private Map<String, Object> listRole(){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<Role> roleList = new ArrayList<Role>();
		try {
			roleList = roleService.getRoleList();
			modelMap.put("roleList", roleList);
			modelMap.put("count", roleList.size());
			modelMap.put("success", true);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
		}
		return modelMap;
	}
	
}
