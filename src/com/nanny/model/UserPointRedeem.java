package com.nanny.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * UserPointRedeem entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_point_redeem", catalog = "nanny")
public class UserPointRedeem implements java.io.Serializable {

	// Fields

	private Long id;
	private Long userId;
	private Long shopId;
	private Long orderId;
	private Long proId;
	private Integer number;
	private Integer point;
	private String des;
	private Timestamp createTime;
	private Integer status;
	private String memo;

	// Constructors

	/** default constructor */
	public UserPointRedeem() {
	}

	/** full constructor */
	public UserPointRedeem(Long userId, Long shopId, Long orderId, Long proId,
			Integer number, Integer point, String des, Timestamp createTime,
			Integer status, String memo) {
		this.userId = userId;
		this.shopId = shopId;
		this.orderId = orderId;
		this.proId = proId;
		this.number = number;
		this.point = point;
		this.des = des;
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

	@Column(name = "orderID")
	public Long getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	@Column(name = "proID")
	public Long getProId() {
		return this.proId;
	}

	public void setProId(Long proId) {
		this.proId = proId;
	}

	@Column(name = "number")
	public Integer getNumber() {
		return this.number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	@Column(name = "point")
	public Integer getPoint() {
		return this.point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	@Column(name = "des", length = 100)
	public String getDes() {
		return this.des;
	}

	public void setDes(String des) {
		this.des = des;
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

	@Column(name = "memo", length = 200)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}