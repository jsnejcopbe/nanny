package com.nanny.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ShopProApply entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "shop_pro_apply", catalog = "nanny")
public class ShopProApply implements java.io.Serializable {

	// Fields

	private Long id;
	private Long shopId;
	private Long brandId;
	private Long typeId;
	private String shopImg;
	private Timestamp craeteTime;
	private Integer status;
	private String memo;

	// Constructors

	/** default constructor */
	public ShopProApply() {
	}

	/** full constructor */
	public ShopProApply(Long shopId, Long brandId, Long typeId, String shopImg,
			Timestamp craeteTime, Integer status, String memo) {
		this.shopId = shopId;
		this.brandId = brandId;
		this.typeId = typeId;
		this.shopImg = shopImg;
		this.craeteTime = craeteTime;
		this.status = status;
		this.memo = memo;
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

	@Column(name = "shopImg", length = 200)
	public String getShopImg() {
		return this.shopImg;
	}

	public void setShopImg(String shopImg) {
		this.shopImg = shopImg;
	}

	@Column(name = "craeteTime", length = 0)
	public Timestamp getCraeteTime() {
		return this.craeteTime;
	}

	public void setCraeteTime(Timestamp craeteTime) {
		this.craeteTime = craeteTime;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "memo", length = 200)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}