package top.lothar.sdims.service;

import top.lothar.sdims.dto.TExecution;
import top.lothar.sdims.entity.User;

public interface UserService {
	/**
	 * 验证添加的用户是否已近存在
	 * @param account
	 * @return
	 */
	int checkRegisterUserByAccount(String account);
	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
	int addUser(User user);
	/**
	 * 删除用户
	 * @param userId
	 * @return
	 */
	int removeUser(long userId);
	/**
	 * 得到用户列表
	 * @param userCondition
	 * @param rowIndex
	 * @param pageSize
	 * @return
	 */
	TExecution<User> getUserList(User userCondition,int pageIndex,int pageSize);
	/**
	 * 修改密码
	 * @param userId
	 * @param account
	 * @param password
	 * @param newPassword
	 * @return
	 */
	int modifyPasswordById(long userId,String account,String password,String newPassword);
	/**
	 * 登录验证
	 * @param account
	 * @param password
	 * @return
	 */
	User checkLoginInfo(String account, String password);
}
