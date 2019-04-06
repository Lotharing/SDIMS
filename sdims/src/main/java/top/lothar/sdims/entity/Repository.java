package top.lothar.sdims.entity;

import java.util.Date;
/**
 * 仓库
 * @author Lothar
 *
 */
public class Repository {
	//仓库ID
	private Long repoId;
    //仓库名称
    private String name;
    //仓库编号
    private String code;
    //仓库地址
    private String address;
    //描述
    private String repoDesc;
    //更新人
    private String updater;
    //修改时间
    private Date updateTime;
    //仓库管理员ID 与Employee ID 关联
    private Employee employee;
    
	public Long getRepoId() {
		return repoId;
	}
	public void setRepoId(Long repoId) {
		this.repoId = repoId;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRepoDesc() {
		return repoDesc;
	}
	public void setRepoDesc(String repoDesc) {
		this.repoDesc = repoDesc;
	}
	public String getUpdater() {
		return updater;
	}
	public void setUpdater(String updater) {
		this.updater = updater;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
    
}
