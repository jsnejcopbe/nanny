package com.nanny.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SupplierShopTouch entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "supplier_shop_touch", catalog = "nanny")
public class SupplierShopTouch implements java.io.Serializable {

	// Fields

	private Long id;
	private Long supplierId;
	private Long shopId;
	private Timestamp createTime;
	private String memo;

	// Constructors

	/** default constructor */
	public SupplierShopTouch() {
	}

	/** full constructor */
	public SupplierShopTouch(Long supplierId, Long shopId,
			Timestamp createTime, String memo) {
		this.supplierId = supplierId;
		this.shopId = shopId;
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

	@Column(name = "supplierID")
	public Long getSupplierId() {
		return this.supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	@Column(name = "shopID")
	public Long getShopId() {
		return this.shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	@Column(name = "createTime", length = 0)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "memo")
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}