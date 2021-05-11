package com.nanny.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * UserCashRefund entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_cash_refund", catalog = "nanny")
public class UserCashRefund implements java.io.Serializable {

	// Fields

	private Long id;
	private Long userId;
	private Long shopId;
	private String orderCode;
	private Timestamp createTime;
	private Integer status;
	private String memo;

	// Constructors

	/** default constructor */
	public UserCashRefund() {
	}

	/** full constructor */
	public UserCashRefund(Long userId, Long shopId, String orderCode,
			Timestamp createTime, Integer status, String memo) {
		this.userId = userId;
		this.shopId = shopId;
		this.orderCode = orderCode;
		this.createTime = createTime;
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

	@Column(name = "userID")
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "shopID")
	public Long getShopId() {
		return this.shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	@Column(name = "orderCode", length = 50)
	public String getOrderCode() {
		return this.orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	@Column(name = "createTime", length = 0)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "memo", length = 500)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}