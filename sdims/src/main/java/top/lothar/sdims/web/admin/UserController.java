package top.lothar.sdims.web.admin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import top.lothar.sdims.dto.TExecution;
import top.lothar.sdims.entity.User;
import top.lothar.sdims.service.UserService;
import top.lothar.sdims.util.HttpServletRequestUtil;
import top.lothar.sdims.util.MD5;
import top.lothar.sdims.util.PageBean;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	private PageBean<User> pageBean;
	/**
	 * 得到用户列表信息
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getuserlist",method=RequestMethod.GET)
	private Map<String, Object> getUserList(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		//获取页码
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		//获取每页展示的数据量
		int pageSize = 5;
		//获取查询条件（仓库名）
		String account = HttpServletRequestUtil.getString(request, "account");
		String roleName = HttpServletRequestUtil.getString(request, "roleName");
		User userCondition = compactUserCondition(account, roleName);
		//非空判断
		if (pageIndex > -1) {
			TExecution<User> userExecution = userService.getUserList(userCondition, pageIndex, pageSize);
			pageBean = new PageBean<User>();
			//总记录数
			pageBean.setAllRowCounts(userExecution.getCount());
			//仓库列表
			pageBean.setDatas(userExecution.getData());
			//每页记录数
			pageBean.setPageSize(5);
			//当前页
			pageBean.setCurPage(pageIndex);
			int sumPages = PageBean.getSumPages(userExecution.getCount(),pageSize);
			//总页数
			pageBean.setSumPages(sumPages);
			//定义数组(用于页码展示组件)
			int[] tempNum = new int[sumPages];
			for (int i = 0; i<sumPages; i++) {
				tempNum[i]=i+1;
			}
			//页码数字
			pageBean.setNavigatepageNums(tempNum);
			modelMap.put("success", true);
			modelMap.put("pageBean", pageBean);
			modelMap.put("stateInfo", userExecution.getStateInfo());
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "pageIndex or pageSize is Null");
		}
		return modelMap;
	}
	/**
	 * 添加用户信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/adduser",method=RequestMethod.POST)
	private Map<String, Object> addUser(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		String userStr = HttpServletRequestUtil.getString(request, "userStr");
		//JSON转化后的用户存储对象
		User user = null;
		//存放json的对象
		ObjectMapper objectMapper = new ObjectMapper();
		//接受json形式的User信息
		try {
			//使用ObjectMapper类把请求中的json信息存放在User实体类中
			user = objectMapper.readValue(userStr, User.class);
			System.out.println(user.getAccount());
		} catch (Exception e) {
			// TODO: handle exception
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		try {
			//非空判断
			if (user!=null) {
				//账户不能重复判断
				String account = user.getAccount();
				int tempCount = userService.checkRegisterUserByAccount(account);
				if (tempCount > 0) {
					modelMap.put("success", false);
					modelMap.put("errMsg", "-已有此用户名");
					return modelMap;
				}
				int effectNum = userService.addUser(user);
				if (effectNum < 1) {
					modelMap.put("success", false);
					modelMap.put("errMsg", "插入失败");
				}else {
					modelMap.put("success", true);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("err2");
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		return modelMap;
	}
	/**
	 * 根据前台传的ID移除对应用户
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/removeuser",method=RequestMethod.GET)
	private Map<String, Object> removeUser(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String,Object>();
		long userId = HttpServletRequestUtil.getLong(request, "userId");
		try {
			int effectNum = userService.removeUser(userId);
			if (effectNum < 0) {
				modelMap.put("success", false);
				modelMap.put("errMsg", "删除失败");
			}else {
				modelMap.put("success", true);
				modelMap.put("successMsg", "删除成功");
			}
		} catch (Exception e) {
			// TODO: handle exception
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		return modelMap;
	}
	/**
	 * 根据ID,account,password去修改密码
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/modifypassword",method=RequestMethod.GET)
	private Map<String, Object> modifyPassword(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String,Object>();
		//id从登陆后的session中获取
		User user = (User) request.getSession().getAttribute("loginUser");
		long userId = user.getUserId();
		String account = HttpServletRequestUtil.getString(request, "account");
		String password = HttpServletRequestUtil.getString(request, "password");
		String MDPassword = MD5.getMd5(password);
		String newPassword = HttpServletRequestUtil.getString(request, "newPassword");
		String MDNewPassword = MD5.getMd5(newPassword);
		try {
			int effectNum = userService.modifyPasswordById(userId, account, MDPassword, MDNewPassword);
			if (effectNum < 1) {
				modelMap.put("success", false);
				modelMap.put("errMsg", "账户名/旧密码输入错误");
			}else {
				modelMap.put("success", true);
				//注销当前session
				request.getSession().removeAttribute("loginUser");
				request.getSession().invalidate();
				modelMap.put("successMsg", "密码更新成功");
			}
		} catch (Exception e) {
			// TODO: handle exception
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		return modelMap;
	}
	/**
	 * 登录验证
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/checkLoginInfo",method=RequestMethod.GET)
	private Map<String, Object> checkLoginInfo(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String,Object>();
		String account = HttpServletRequestUtil.getString(request, "account");
		String password = HttpServletRequestUtil.getString(request, "password");
		User loginUser = null;
		try {
			loginUser = userService.checkLoginInfo(account, password);
		} catch (Exception e) {
			// TODO: handle exception
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证错误"+e.getMessage());
		}
		if (loginUser!=null) {
			//当前用户展示，修改密码时候账户的填充--某些添加信息的名称获得
			request.getSession().setAttribute("loginUser", loginUser);
			//角色类型-跳转不同主页依据
			int state = (int) loginUser.getRole().getRoleId();
			modelMap.put("success", true);
			modelMap.put("state",state);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg","没有此用户名和密码或输入错误");
		}
		return modelMap;
	}
	/**
	 * 登录验证
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getsessionofaccount",method=RequestMethod.GET)
	private Map<String, Object> getSessionOfAccount(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String,Object>();
		//因为是通过拦截器（拦截器中对session中的user进行判断）后到主页，所以session必有用户
		User user = (User) request.getSession().getAttribute("loginUser");
		if (user!=null) {
			modelMap.put("success",true);
			modelMap.put("account", user.getAccount());
		}else {
			modelMap.put("success",false);
			modelMap.put("errMsg", "获取用户名错误");
		}
		return modelMap;
	}
	/**
	 * 注销登录
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/loginout",method=RequestMethod.GET)
	private Map<String, Object> loginOut(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> modelMap = new HashMap<String,Object>();		
		HttpSession session = request.getSession();//防止创建session
		session.removeAttribute("loginUser");
		session.invalidate();
		modelMap.put("success", true);
		modelMap.put("successMsg", "注销成功");
		return modelMap;
	}
	/**
	 * 判断对于查询用户时候的筛选条件
	 * @param orderNumber
	 * @param checkState
	 * @return
	 */
	private User compactUserCondition(String account,String roleName) {
		User userCondition = new User();
		// 若有指定订单号的要求则添加进去
		if (account != null) {
			userCondition.setAccount(account);
		}
		// 若有指定订单经理审核通过
		if (roleName != null) {
			userCondition.setRoleName(roleName);
		}
		return userCondition;
	}
}
