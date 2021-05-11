package com.nanny.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * UserChargeRec entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_charge_rec", catalog = "nanny")
public class UserChargeRec implements java.io.Serializable {

	// Fields

	private Long id;
	private Double money;
	private Long userId;
	private Integer status;
	private String payType;
	private Timestamp createTime;
	private String chargeCode;
	private String memo;

	// Constructors

	/** default constructor */
	public UserChargeRec() {
	}

	/** full constructor */
	public UserChargeRec(Double money, Long userId, Integer status,
			String payType, Timestamp createTime, String chargeCode, String memo) {
		this.money = money;
		this.userId = userId;
		this.status = status;
		this.payType = payType;
		this.createTime = createTime;
		this.chargeCode = chargeCode;
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

	@Column(name = "money", precision = 18)
	public Double getMoney() {
		return this.money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	@Column(name = "userID")
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "payType", length = 50)
	public String getPayType() {
		return this.payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	@Column(name = "createTime", length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "chargeCode", length = 50)
	public String getChargeCode() {
		return this.chargeCode;
	}

	public void setChargeCode(String chargeCode) {
		this.chargeCode = chargeCode;
	}

	@Column(name = "memo", length = 50)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}