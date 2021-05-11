package com.nanny.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ShopProduct entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "shop_product", catalog = "nanny")
public class ShopProduct implements java.io.Serializable {

	// Fields

	private Long id;
	private String name;
	private String proCode;
	private Double price;
	private Double disPrice;
	private Double costPrice;
	private Integer inventory;
	private String cover;
	private Integer buyCount;
	private Integer viewCount;
	private String proDes;
	private Long shopId;
	private Long brandId;
	private Long typeId;
	private String proTag;
	private Integer isUse;
	private Integer isCommission;
	private Integer isRecommond;
	private Timestamp createTime;
	private String memo;
	private Integer isExchange;

	// Constructors

	/** default constructor */
	public ShopProduct() {
	}

	/** full constructor */
	public ShopProduct(String name, String proCode, Double price,
			Double disPrice, Double costPrice, Integer inventory, String cover,
			Integer buyCount, Integer viewCount, String proDes, Long shopId,
			Long brandId, Long typeId, String proTag, Integer isUse,
			Integer isCommission, Integer isRecommond, Timestamp createTime,
			String memo, Integer isExchange) {
		this.name = name;
		this.proCode = proCode;
		this.price = price;
		this.disPrice = disPrice;
		this.costPrice = costPrice;
		this.inventory = inventory;
		this.cover = cover;
		this.buyCount = buyCount;
		this.viewCount = viewCount;
		this.proDes = proDes;
		this.shopId = shopId;
		this.brandId = brandId;
		this.typeId = typeId;
		this.proTag = proTag;
		this.isUse = isUse;
		this.isCommission = isCommission;
		this.isRecommond = isRecommond;
		this.createTime = createTime;
		this.memo = memo;
		this.isExchange = isExchange;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "name", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "proCode", length = 50)
	public String getProCode() {
		return this.proCode;
	}

	public void setProCode(String proCode) {
		this.proCode = proCode;
	}

	@Column(name = "price", precision = 18)
	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Column(name = "disPrice", precision = 18)
	public Double getDisPrice() {
		return this.disPrice;
	}

	public void setDisPrice(Double disPrice) {
		this.disPrice = disPrice;
	}

	@Column(name = "costPrice", precision = 18)
	public Double getCostPrice() {
		return this.costPrice;
	}

	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}

	@Column(name = "inventory")
	public Integer getInventory() {
		return this.inventory;
	}

	public void setInventory(Integer inventory) {
		this.inventory = inventory;
	}

	@Column(name = "cover", length = 200)
	public String getCover() {
		return this.cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	@Column(name = "buyCount")
	public Integer getBuyCount() {
		return this.buyCount;
	}

	public void setBuyCount(Integer buyCount) {
		this.buyCount = buyCount;
	}

	@Column(name = "viewCount")
	public Integer getViewCount() {
		return this.viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	@Column(name = "proDes", length = 16000)
	public String getProDes() {
		return this.proDes;
	}

	public void setProDes(String proDes) {
		this.proDes = proDes;
	}

	@Column(name = "shopID")
	public Long getShopId() {
		return this.shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	@Column(name = "brandID")
	public Long getBrandId() {
		return this.brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	@Column(name = "typeID")
	public Long getTypeId() {
		return this.typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	@Column(name = "proTag", length = 200)
	public String getProTag() {
		return this.proTag;
	}

	public void setProTag(String proTag) {
		this.proTag = proTag;
	}

	@Column(name = "isUse")
	public Integer getIsUse() {
		return this.isUse;
	}

	public void setIsUse(Integer isUse) {
		this.isUse = isUse;
	}

	@Column(name = "isCommission")
	public Integer getIsCommission() {
		return this.isCommission;
	}

	public void setIsCommission(Integer isCommission) {
		this.isCommission = isCommission;
	}

	@Column(name = "isRecommond")
	public Integer getIsRecommond() {
		return this.isRecommond;
	}

	public void setIsRecommond(Integer isRecommond) {
		this.isRecommond = isRecommond;
	}

	@Column(name = "createTime", length = 0)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "memo", length = 50)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name = "isExchange")
	public Integer getIsExchange() {
		return this.isExchange;
	}

	public void setIsExchange(Integer isExchange) {
		this.isExchange = isExchange;
	}

}