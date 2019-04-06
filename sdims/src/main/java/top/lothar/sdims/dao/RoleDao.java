package top.lothar.sdims.dao;

import java.util.List;

import top.lothar.sdims.entity.Role;

public interface RoleDao {
	
	/*-------------------------------------------------角色管理----------------------------------------------------*/
	/**
	 * 查询所有角色信息
	 * @return
	 */
	List<Role> queryRoleList();
	
	/**
	 * 由于对于需求来说只有四个角色用例，所以这是固定数据，只需要查看展示就可以，此数据提供给添加User时候指定用户角色
	 * 从而在不同的用户角色中，负责与自身相对应的功能，而在User中的state字段，就是标识用户的角色信息，前台的角色响应
	 * 页面也根据state去跳转
	 * 
	 * 我了解了Ext.js 和 shiro 的目录树去控制角色以及权限，但实现过程难度较高，经验不足
	 */
}
