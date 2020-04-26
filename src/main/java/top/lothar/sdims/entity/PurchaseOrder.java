package top.lothar.sdims.entity;

import java.util.Date;
/**
 * 采购单
 * @author Lothar
 *
 */
public class PurchaseOrder {
	//采购订单ID
	private Long porderId;
	//商品ID
	private Goods goods;
	//供应商ID
	private Supplier supplier;
	//仓库ID
	private Repository repository;
	//供应商名称
	private String supplierName;
	//订单编号
	private String orderNumber;
	//订单数量
	private Integer count;
	//单价
	private Double unitPrice;
	//总价
	private Double totalPrice;
	//订单描述
	private String orderDesc;
	//创建时间
	private Date createTime;
	//创建者
	private String creater;
	//订单经理审核状态
	private Integer checkState;
	//审核结果
	private String checkResult;
	//审核人
	private String checkMan;
	//审核时间
	private Date checkTime;
	//库存审核状态
	private Integer stockState;
	//库存审核人
	private String stockMan;
	//库存审核时间
	private Date stockTime;
	
	public Long getPorderId() {
		return porderId;
	}

	public void setPorderId(Long porderId) {
		this.porderId = porderId;
	}

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public Repository getRepository() {
		return repository;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public String getOrderDesc() {
		return orderDesc;
	}

	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public Integer getCheckState() {
		return checkState;
	}

	public void setCheckState(Integer checkState) {
		this.checkState = checkState;
	}

	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}

	public String getCheckMan() {
		return checkMan;
	}

	public void setCheckMan(String checkMan) {
		this.checkMan = checkMan;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public Integer getStockState() {
		return stockState;
	}

	public void setStockState(Integer stockState) {
		this.stockState = stockState;
	}

	public String getStockMan() {
		return stockMan;
	}

	public void setStockMan(String stockMan) {
		this.stockMan = stockMan;
	}

	public Date getStockTime() {
		return stockTime;
	}

	public void setStockTime(Date stockTime) {
		this.stockTime = stockTime;
	}
	
}
