package top.lothar.sdims.entity;

import java.util.Date;
/**
 * 库存
 * @author Lothar
 *
 */
public class Stock {
	//库存ID
	private Long stockId;
    //商品ID
    private Goods goods;
    //仓库ID
    private Repository repository;
    //库存量
    private Integer totalCount;
    //进价
    private Double buyPrice;
    //售价
    private Double salePrice;
    //销售量
    private Integer saleCount;
    //库存总值
    private Double totalBuyPrice;
    //售价总值
    private Double totalSalePrice;
    //修改时间
    private Date updateTime;
    
	public Long getStockId() {
		return stockId;
	}
	public void setStockId(Long stockId) {
		this.stockId = stockId;
	}
	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	public Repository getRepository() {
		return repository;
	}
	public void setRepository(Repository repository) {
		this.repository = repository;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public Double getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(Double buyPrice) {
		this.buyPrice = buyPrice;
	}
	public Double getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}
	public Integer getSaleCount() {
		return saleCount;
	}
	public void setSaleCount(Integer saleCount) {
		this.saleCount = saleCount;
	}
	public Double getTotalBuyPrice() {
		return totalBuyPrice;
	}
	public void setTotalBuyPrice(Double totalBuyPrice) {
		this.totalBuyPrice = totalBuyPrice;
	}
	public Double getTotalSalePrice() {
		return totalSalePrice;
	}
	public void setTotalSalePrice(Double totalSalePrice) {
		this.totalSalePrice = totalSalePrice;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
    
}
