package top.lothar.sdims.service;

import top.lothar.sdims.dto.TExecution;
import top.lothar.sdims.entity.User;

public interface UserService {
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
	 * 更新用户
	 * @param user
	 * @return
	 */
	int modify(User user);
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
}
