package top.lothar.sdims.entity;

import java.util.Date;
/**
 * 系统用户
 * @author Lothar
 *
 */
public class User {
	//用户ID
	private Long userId;
	//用户账号
	private String account;
	//用户密码
	private String password;
	//角色
	private String roleName;
	//(雇员)个人信息
	private Employee employee;
	//更新时间
	private Date updateTime;
	//角色1.系统管理员 2.库存管理员 3.采购员 4.销售员
	private Role role;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
}
