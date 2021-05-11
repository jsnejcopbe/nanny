package com.nanny.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ShopDispatching entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "shop_dispatching", catalog = "nanny")
public class ShopDispatching implements java.io.Serializable {

	// Fields

	private Long id;
	private Integer minRange;
	private Integer maxRange;
	private Double fee;
	private Long shopId;
	private Long adressID;
	private Timestamp createTime;
	private String memo;

	// Constructors

	/** default constructor */
	public ShopDispatching() {
	}

	/** full constructor */
	public ShopDispatching(Long id, Integer minRange, Integer maxRange, Double fee, Long shopId, Long adressID,
			Timestamp createTime, String memo) {
		super();
		this.id = id;
		this.minRange = minRange;
		this.maxRange = maxRange;
		this.fee = fee;
		this.shopId = shopId;
		this.adressID = adressID;
		this.createTime = createTime;
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

	@Column(name = "minRange")
	public Integer getMinRange() {
		return this.minRange;
	}

	public void setMinRange(Integer minRange) {
		this.minRange = minRange;
	}

	@Column(name = "maxRange")
	public Integer getMaxRange() {
		return this.maxRange;
	}

	public void setMaxRange(Integer maxRange) {
		this.maxRange = maxRange;
	}

	@Column(name = "fee", precision = 18)
	public Double getFee() {
		return this.fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	@Column(name = "shopID")
	public Long getShopId() {
		return this.shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	
	@Column(name = "adressID")
	public Long getadressID() {
		return this.adressID;
	}
	
	public void setadressID(Long adressID) {
		this.adressID = adressID;
	}

	@Column(name = "createTime", length = 19)
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

}