package top.lothar.sdims.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.lothar.sdims.dao.UserDao;
import top.lothar.sdims.dto.TExecution;
import top.lothar.sdims.entity.Stock;
import top.lothar.sdims.entity.User;
import top.lothar.sdims.service.UserService;
import top.lothar.sdims.util.PageCalculator;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public int addUser(User user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int removeUser(long userId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int modify(User user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TExecution<User> getUserList(User userCondition, int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
		List<User> userList = userDao.queryUserList(userCondition, rowIndex, pageSize);
		int count = userDao.queryUserCount(userCondition);
		TExecution<User> userExecution = new TExecution<>();
		if (userList!=null) {
			userExecution.setData(userList);
			userExecution.setCount(count);
			userExecution.setStateInfo("成功得到数据");
		}else {
			return new TExecution<User>("数据获取失败");
		}
		return userExecution;
	}

	@Override
	public int modifyPasswordById(long userId, String account, String password, String newPassword) {
		// TODO Auto-generated method stub
		return 0;
	}

}
