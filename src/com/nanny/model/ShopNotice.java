package com.nanny.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ShopNotice entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "shop_notice", catalog = "nanny")
public class ShopNotice implements java.io.Serializable {

	// Fields

	private Long id;
	private String title;
	private String con;
	private Long shopId;
	private Timestamp createTime;
	private String memo;

	// Constructors

	/** default constructor */
	public ShopNotice() {
	}

	/** full constructor */
	public ShopNotice(String title, String con, Long shopId,
			Timestamp createTime, String memo) {
		this.title = title;
		this.con = con;
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

	@Column(name = "title", length = 50)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "con", length = 5000)
	public String getCon() {
		return this.con;
	}

	public void setCon(String con) {
		this.con = con;
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

	@Column(name = "memo", length = 500)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}