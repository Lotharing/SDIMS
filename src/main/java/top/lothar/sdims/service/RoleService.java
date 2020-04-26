package top.lothar.sdims.service;

import java.util.List;

import top.lothar.sdims.entity.Role;

public interface RoleService {
	/**
	 * 得到角色信息
	 * @return
	 */
	List<Role> getRoleList();
	
}
