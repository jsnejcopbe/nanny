package com.nanny.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ShopProType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "shop_pro_type", catalog = "nanny")
public class ShopProType implements java.io.Serializable {

	// Fields

	private Long id;
	private String name;
	private Long parId;
	private Long shopId;
	private Integer type;
	private Timestamp createTime;
	private String memo;

	// Constructors

	/** default constructor */
	public ShopProType() {
	}

	/** full constructor */
	public ShopProType(String name, Long parId, Long shopId, Integer type,
			Timestamp createTime, String memo) {
		this.name = name;
		this.parId = parId;
		this.shopId = shopId;
		this.type = type;
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

	@Column(name = "name", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "parID")
	public Long getParId() {
		return this.parId;
	}

	public void setParId(Long parId) {
		this.parId = parId;
	}

	@Column(name = "shopID")
	public Long getShopId() {
		return this.shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	@Column(name = "type")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
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