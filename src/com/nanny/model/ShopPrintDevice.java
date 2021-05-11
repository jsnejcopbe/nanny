package com.nanny.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ShopPrintDevice entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "shop_print_device", catalog = "nanny")
public class ShopPrintDevice implements java.io.Serializable {

	// Fields

	private Long id;
	private Long shopId;
	private String deNo;
	private String deKey;
	private Timestamp createTime;
	private String memo;

	// Constructors

	/** default constructor */
	public ShopPrintDevice() {
	}

	/** full constructor */
	public ShopPrintDevice(Long shopId, String deNo, String deKey,
			Timestamp createTime, String memo) {
		this.shopId = shopId;
		this.deNo = deNo;
		this.deKey = deKey;
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

	@Column(name = "deNo", length = 50)
	public String getDeNo() {
		return this.deNo;
	}

	public void setDeNo(String deNo) {
		this.deNo = deNo;
	}

	@Column(name = "deKey", length = 50)
	public String getDeKey() {
		return this.deKey;
	}

	public void setDeKey(String deKey) {
		this.deKey = deKey;
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