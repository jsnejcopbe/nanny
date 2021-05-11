package com.nanny.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ShopOfficehours entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "shop_officehours", catalog = "nanny")
public class ShopOfficehours implements java.io.Serializable {

	// Fields

	private Long id;
	private Long shopId;
	private String days;
	private String startTime;
	private String endTime;
	private Timestamp createTime;
	private String memo;

	// Constructors

	/** default constructor */
	public ShopOfficehours() {
	}

	public ShopOfficehours(Long id, Long shopId, String days, String startTime, String endTime, Timestamp createTime,
			String memo) {
		super();
		this.id = id;
		this.shopId = shopId;
		this.days = days;
		this.startTime = startTime;
		this.endTime = endTime;
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

	@Column(name = "shopID")
	public Long getShopId() {
		return this.shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	@Column(name = "days", length = 50)
	public String getDays() {
		return this.days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	@Column(name = "startTime", length = 19)
	public String getStartTime() {
		return this.startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	@Column(name = "endTime", length = 19)
	public String getEndTime() {
		return this.endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Column(name = "createTime", length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "memo", length = 500)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}