package com.nanny.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BaseOrders entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base_orders", catalog = "nanny")
public class BaseOrders implements java.io.Serializable {

	// Fields

	private Long id;
	private String orderCode;
	private Long payUserId;
	private Long recShopId;
	private Double totalPrice;
	private Double fee;
	private Integer status;
	private String address;
	private String recName;
	private String recTel;
	private Integer payType;
	private String sendTime;
	private Timestamp createTime;
	private String shopmsg;
	private String message;
	private String memo;
	private Long vcId;

	// Constructors

	/** default constructor */
	public BaseOrders() {
	}

	/** full constructor */
	public BaseOrders(String orderCode, Long payUserId, Long recShopId,
			Double totalPrice, Double fee, Integer status, String address,
			String recName, String recTel, Integer payType, String sendTime,
			Timestamp createTime, String shopmsg, String message, String memo,
			Long vcId) {
		this.orderCode = orderCode;
		this.payUserId = payUserId;
		this.recShopId = recShopId;
		this.totalPrice = totalPrice;
		this.fee = fee;
		this.status = status;
		this.address = address;
		this.recName = recName;
		this.recTel = recTel;
		this.payType = payType;
		this.sendTime = sendTime;
		this.createTime = createTime;
		this.shopmsg = shopmsg;
		this.message = message;
		this.memo = memo;
		this.vcId = vcId;
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

	@Column(name = "orderCode", length = 50)
	public String getOrderCode() {
		return this.orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	@Column(name = "payUserID")
	public Long getPayUserId() {
		return this.payUserId;
	}

	public void setPayUserId(Long payUserId) {
		this.payUserId = payUserId;
	}

	@Column(name = "recShopID")
	public Long getRecShopId() {
		return this.recShopId;
	}

	public void setRecShopId(Long recShopId) {
		this.recShopId = recShopId;
	}

	@Column(name = "totalPrice", precision = 18)
	public Double getTotalPrice() {
		return this.totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Column(name = "fee", precision = 18)
	public Double getFee() {
		return this.fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "address", length = 500)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "recName", length = 50)
	public String getRecName() {
		return this.recName;
	}

	public void setRecName(String recName) {
		this.recName = recName;
	}

	@Column(name = "recTel", length = 50)
	public String getRecTel() {
		return this.recTel;
	}

	public void setRecTel(String recTel) {
		this.recTel = recTel;
	}

	@Column(name = "payType")
	public Integer getPayType() {
		return this.payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	@Column(name = "sendTime", length = 100)
	public String getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	@Column(name = "createTime", length = 0)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "shopmsg", length = 200)
	public String getShopmsg() {
		return this.shopmsg;
	}

	public void setShopmsg(String shopmsg) {
		this.shopmsg = shopmsg;
	}

	@Column(name = "message", length = 200)
	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Column(name = "memo", length = 500)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name = "vcID")
	public Long getVcId() {
		return this.vcId;
	}

	public void setVcId(Long vcId) {
		this.vcId = vcId;
	}

}