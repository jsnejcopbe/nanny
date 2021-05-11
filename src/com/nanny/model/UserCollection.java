package com.nanny.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * UserCollection entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_collection", catalog = "nanny")
public class UserCollection implements java.io.Serializable {

	// Fields

	private Long id;
	private Long userId;
	private Long proId;
	private Long shopId;
	private Timestamp createTime;
	private String memo;

	// Constructors

	/** default constructor */
	public UserCollection() {
	}

	/** full constructor */
	public UserCollection(Long userId, Long proId, Long shopId,
			Timestamp createTime, String memo) {
		this.userId = userId;
		this.proId = proId;
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

	@Column(name = "userID")
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "proID")
	public Long getProId() {
		return this.proId;
	}

	public void setProId(Long proId) {
		this.proId = proId;
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