package top.lothar.sdims.dao;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import top.lothar.sdims.BaseTest;
import top.lothar.sdims.entity.Role;

public class RoleDaoTest extends BaseTest{
	
	@Autowired
	private RoleDao roleDao;
	
	private static Logger log = LoggerFactory.getLogger(RoleDaoTest.class);
	
	@Ignore
	public void testQueryRoleLilst() {
		
		log.debug("测试开始");
		
		List<Role> roleList = roleDao.queryRoleList();
		
		System.out.println("角色数量:"+roleList.size());
		
		for (Role role : roleList) {
			System.out.println("角色名称:"+role.getRoleName());
		}
		
		log.debug("测试结束");
	}
	
	@Test
	public void test() {
		long roleId = 3;
		Role queryRoleById = roleDao.queryRoleById(roleId);
		System.out.println(queryRoleById.getRoleName());
	}
}
