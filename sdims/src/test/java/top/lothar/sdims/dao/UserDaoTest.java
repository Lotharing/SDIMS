package top.lothar.sdims.dao;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import top.lothar.sdims.BaseTest;
import top.lothar.sdims.entity.Employee;
import top.lothar.sdims.entity.User;

public class UserDaoTest extends BaseTest{
	
	@Autowired
	private UserDao userDao;
	
	@Ignore
	public void testAInsertUser() {
		Employee employee = new Employee();
		employee.setEmployeeId(5L);
		User user = new User();
		user.setAccount("xiaodan");
		user.setPassword("111111");
		user.setRoleName("订单审核员");
		user.setEmployee(employee);
		user.setUpdateTime(new Date());
		user.setState(5);
		int insertUser = userDao.insertUser(user);
		System.out.println(insertUser);
	}
	
	@Ignore
	public void testBDeleteUserByID() {
		long userId = 6L;
		int deleteUserById = userDao.deleteUserById(userId);
		System.out.println(deleteUserById);
	}
	
	@Ignore
	public void testCUpdateUser() {
		User user = new User();
		Employee employee = new Employee();
		employee.setEmployeeId(3L);
		user.setUserId(7L);
		user.setPassword("111111");
		user.setEmployee(employee);
		int updateUser = userDao.updateUser(user);
		System.out.println(updateUser);
	}
	
	@Ignore
	public void testDQueryUserList() {
		User userCondition = new User();
//		userCondition.setAccount("admin");
		userCondition.setRoleName("系统管理员");
		List<User> queryUserList = userDao.queryUserList(userCondition, 0, 10);
		for (User user : queryUserList) {
			System.out.println(user.getEmployee().getName());
		}
	}
	
	@Ignore
	public void testEQueryUserCount() {
		User userCondition = new User();
		userCondition.setAccount("admin");
//		userCondition.setRoleName("系统管理员");
		int queryUserCount = userDao.queryUserCount(userCondition);
		System.out.println(queryUserCount);
	}
	
	@Ignore
	public void FUpdatePasswordById() {
		int updatePasswordById = userDao.updatePasswordById(7, "xiaodan", "222222", "111111", new Date());
		System.out.println(updatePasswordById);
	}
}
