package top.lothar.sdims.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.lothar.sdims.dao.RoleDao;
import top.lothar.sdims.entity.Role;
import top.lothar.sdims.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleDao roleDao;

	@Override
	public List<Role> getRoleList() {
		// TODO Auto-generated method stub
		return roleDao.queryRoleList();
	}

}
