package top.lothar.sdims.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import top.lothar.sdims.entity.User;

public interface UserDao {

	/*--------------------------------------------------用户管理----------------------------------------------------*/
	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
	int insertUser(User user);
	/**
	 * 根据ID查询用户
	 * @param userId
	 * @return
	 */
	int deleteUserById(long userId);
	/**
	 * 更新用户信息（user.Employee）
	 * @param user
	 * @return
	 */
	int updateUser(User user);
	/**
	 * 分页条件查询所有用户（用户账号，用户角色）实体为空查询所有
	 * @param rowIndex
	 * @param pageSize
	 * @return
	 */
	List<User> queryUserList(@Param("user")User user,@Param("rowIndex")int rowIndex, @Param("pageSize")int pageSize);
	/**
	 * 查询所有用户数量
	 * @return
	 */
	int queryUserCount(@Param("user")User user);
	/**
	 * 修改用户密码
	 * @param userId
	 * @param username
	 * @param password
	 * @param newPassword
	 * @param updateTime
	 * @return
	 */
	int updatePasswordById(@Param("userId")long userId,@Param("account")String account,@Param("password")String password,@Param("newPassword")String newPassword,@Param("updateTime")Date updateTime);
}
