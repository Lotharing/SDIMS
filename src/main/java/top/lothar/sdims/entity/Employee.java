package top.lothar.sdims.entity;

import java.util.Date;
/**
  * 员工
 * @author Lothar
 *
 */
public class Employee {
	//员工ID
	private Long employeeId;
    //员工姓名
    private String name;
    //员工编号
    private String code;
    //身份证
    private String  idCard;
    //手机号码
    private String  mobile;
    //性别1.男2.女
    private Integer sex;
    //用户地址
    private String  address;
    //用户邮箱
    private String  email;
    // 员工类别名称
    private String type;
    //修改时间
    private Date updateTime;
    
	public Long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}
