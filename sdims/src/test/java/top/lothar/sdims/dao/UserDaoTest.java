package top.lothar.sdims.dao;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import top.lothar.sdims.BaseTest;
import top.lothar.sdims.entity.Employee;
import top.lothar.sdims.entity.Role;
import top.lothar.sdims.entity.User;
import top.lothar.sdims.util.MD5;

public class UserDaoTest extends BaseTest{
	
	@Autowired
	private UserDao userDao;
	
	@Ignore
	public void testAInsertUser() {
		Employee employee = new Employee();
		Role role = new Role();
		role.setRoleId(5L);
		employee.setEmployeeId(5L);
		User user = new User();
		user.setAccount("xiaodan");
		user.setPassword("111111");
		user.setRoleName("订单审核员");
		user.setEmployee(employee);
		user.setUpdateTime(new Date());
		user.setRole(role);
		int insertUser = userDao.insertUser(user);
		System.out.println(insertUser);
	}
	
	@Ignore
	public void testBDeleteUserByID() {
		long userId = 8L;
		int deleteUserById = userDao.deleteUserById(userId);
		System.out.println(deleteUserById);
	}
	
	@Ignore
	public void testCUpdateUser() {
		User user = new User();
		Role role = new Role();
		role.setRoleId(4L);
		Employee employee = new Employee();
		employee.setEmployeeId(5L);
		user.setUserId(7L);
		user.setPassword("111111");
		user.setEmployee(employee);
		user.setRole(role);
		int updateUser = userDao.updateUser(user);
		System.out.println(updateUser);
	}
	
	@Ignore
	public void testDQueryUserList() {
		User userCondition = new User();
//		userCondition.setAccount("admin");
//		userCondition.setRoleName("系统管理员");
		List<User> queryUserList = userDao.queryUserList(null, 0, 10);
		for (User user : queryUserList) {
			//System.out.println(user.getEmployee().getCode());
			//System.out.println(user.getRole().getRoleDesc());
			System.out.println(user.getRole().getRoleId());
		}
	}
	
	@Ignore
	public void testEQueryUserCount() {
		User userCondition = new User();
//		userCondition.setAccount("admin");
		userCondition.setRoleName("订单审核员");
		int queryUserCount = userDao.queryUserCount(userCondition);
		System.out.println(queryUserCount);
	}
	
	@Ignore
	public void FUpdatePasswordById() {
		int updatePasswordById = userDao.updatePasswordById(7, "xiaodan", "111111", "222222", new Date());
		System.out.println(updatePasswordById);
	}
	
	@Ignore
	public void test() {
		String account = "admin";
		String password = "111111";
		String MDPassword = MD5.getMd5(password);
		User checkLoginInfo = userDao.checkLoginInfo(account, MDPassword);
		System.out.println(checkLoginInfo.getRole().getRoleId());
		System.out.println(checkLoginInfo.getEmployee().getName());
	}
	
	@Ignore
	public void tests() {
		String account = "xiaokuddd";
		int registerCheckUserAccount = userDao.registerCheckUserAccount(account);
		System.out.println(registerCheckUserAccount);
	}
	
	
}
