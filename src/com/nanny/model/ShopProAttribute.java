package com.nanny.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ShopProAttribute entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "shop_pro_attribute", catalog = "nanny")
public class ShopProAttribute implements java.io.Serializable {

	// Fields

	private Long id;
	private String attrName;
	private String attrDes;
	private String proCode;
	private Long shopId;
	private Timestamp createTime;
	private String memo;

	// Constructors

	/** default constructor */
	public ShopProAttribute() {
	}

	/** full constructor */
	public ShopProAttribute(String attrName, String attrDes, String proCode,
			Long shopId, Timestamp createTime, String memo) {
		this.attrName = attrName;
		this.attrDes = attrDes;
		this.proCode = proCode;
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

	@Column(name = "attrName", length = 50)
	public String getAttrName() {
		return this.attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	@Column(name = "attrDes", length = 50)
	public String getAttrDes() {
		return this.attrDes;
	}

	public void setAttrDes(String attrDes) {
		this.attrDes = attrDes;
	}

	@Column(name = "proCode", length = 50)
	public String getProCode() {
		return this.proCode;
	}

	public void setProCode(String proCode) {
		this.proCode = proCode;
	}

	@Column(name = "shopID")
	public Long getShopId() {
		return this.shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
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