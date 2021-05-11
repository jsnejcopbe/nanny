package com.nanny.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * UserAccountsRec entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_accounts_rec", catalog = "nanny")
public class UserAccountsRec implements java.io.Serializable {

	// Fields

	private Long id;
	private Long userId;
	private Long otherSide;
	private String orderCode;
	private Integer type;
	private Double money;
	private String des;
	private Double balance;
	private Integer payType;
	private Timestamp createTime;
	private String memo;

	// Constructors

	/** default constructor */
	public UserAccountsRec() {
	}

	/** full constructor */
	public UserAccountsRec(Long userId, Long otherSide, String orderCode,
			Integer type, Double money, String des, Double balance,
			Integer payType, Timestamp createTime, String memo) {
		this.userId = userId;
		this.otherSide = otherSide;
		this.orderCode = orderCode;
		this.type = type;
		this.money = money;
		this.des = des;
		this.balance = balance;
		this.payType = payType;
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

	@Column(name = "otherSide")
	public Long getOtherSide() {
		return this.otherSide;
	}

	public void setOtherSide(Long otherSide) {
		this.otherSide = otherSide;
	}

	@Column(name = "orderCode", length = 50)
	public String getOrderCode() {
		return this.orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	@Column(name = "type")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "money", precision = 18)
	public Double getMoney() {
		return this.money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	@Column(name = "des", length = 500)
	public String getDes() {
		return this.des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	@Column(name = "balance", precision = 18)
	public Double getBalance() {
		return this.balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	@Column(name = "payType")
	public Integer getPayType() {
		return this.payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
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