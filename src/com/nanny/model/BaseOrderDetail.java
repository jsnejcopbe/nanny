package com.nanny.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BaseOrderDetail entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base_order_detail", catalog = "nanny")
public class BaseOrderDetail implements java.io.Serializable {

	// Fields

	private Long id;
	private Long orderId;
	private Long proId;
	private String proCode;
	private Double price;
	private Long shotId;
	private Integer count;
	private Timestamp createTime;
	private String memo;

	// Constructors

	/** default constructor */
	public BaseOrderDetail() {
	}

	/** full constructor */
	public BaseOrderDetail(Long orderId, Long proId, String proCode,
			Double price, Long shotId, Integer count, Timestamp createTime,
			String memo) {
		this.orderId = orderId;
		this.proId = proId;
		this.proCode = proCode;
		this.price = price;
		this.shotId = shotId;
		this.count = count;
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

	@Column(name = "proCode", length = 50)
	public String getProCode() {
		return this.proCode;
	}

	public void setProCode(String proCode) {
		this.proCode = proCode;
	}

	@Column(name = "price", precision = 18)
	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Column(name = "shotID")
	public Long getShotId() {
		return this.shotId;
	}

	public void setShotId(Long shotId) {
		this.shotId = shotId;
	}

	@Column(name = "count")
	public Integer getCount() {
		return this.count;
	}

	public void setCount(Integer count) {
		this.count = count;
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