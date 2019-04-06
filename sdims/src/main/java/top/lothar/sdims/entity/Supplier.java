package top.lothar.sdims.entity;

import java.util.Date;
/**
 * 供应商
 * @author Lothar
 *
 */
public class Supplier {
	//供应商Id
	private Long supplierId;
    //供应商名称
    private String name;
    //联系人
    private String linkName;
    //联系电话
    private String mobile;
    //联系地址
    private String address;
    //描述
    private String supplierDesc;
    //修改人账号
    private String  updater;
    //修改时间
    private Date updateTime;
    
	public Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSupplierDesc() {
		return supplierDesc;
	}
	public void setSupplierDesc(String supplierDesc) {
		this.supplierDesc = supplierDesc;
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
    
}
