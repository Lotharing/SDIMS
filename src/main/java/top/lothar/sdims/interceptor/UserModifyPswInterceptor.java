package top.lothar.sdims.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import top.lothar.sdims.entity.User;

public class UserModifyPswInterceptor extends HandlerInterceptorAdapter{
	/*
	 * 主要做事前拦截，即用户操作发生前，改写preHandle里的逻辑，进行拦截 个人理解 ：在调用指定的controller前进行身份拦截
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 先从session中取出用户信息
		Object objectUser = request.getSession().getAttribute("loginUser");
		// 对用户信息进行判断
		if (objectUser != null) {
			// 用户信息不为空，则将其转换为PersonInfo
			User user = (User) objectUser;
			// 对user进行判断userId和角色类型
			if (user != null && user.getUserId()> 0) {
				if (user.getRole().getRoleId() == 1 || user.getRole().getRoleId() == 2
						|| user.getRole().getRoleId() == 3 || user.getRole().getRoleId() == 4
						|| user.getRole().getRoleId() == 5) {
					// 如果通过上述的验证步骤，则返回true，可以进行接下来操作
					return true;
				}else {
					return false;
				}
			}
		}
		// 不满足验证，则直接跳转到登录页面
		response.sendRedirect(request.getContextPath());  
		return false;
	}
}
