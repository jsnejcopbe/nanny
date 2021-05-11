package com.nanny.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * UserViewShopRec entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_view_shop_rec", catalog = "nanny")
public class UserViewShopRec implements java.io.Serializable {

	// Fields

	private Long id;
	private String openId;
	private Long shopId;
	private String url;
	private Timestamp createTime;
	private String memo;

	// Constructors

	/** default constructor */
	public UserViewShopRec() {
	}

	/** full constructor */
	public UserViewShopRec(String openId, Long shopId, String url,
			Timestamp createTime, String memo) {
		this.openId = openId;
		this.shopId = shopId;
		this.url = url;
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

	@Column(name = "openID", length = 50)
	public String getOpenId() {
		return this.openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	@Column(name = "shopID")
	public Long getShopId() {
		return this.shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	@Column(name = "url", length = 50)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
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