package com.nanny.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * UserCashApply entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_cash_apply", catalog = "nanny")
public class UserCashApply implements java.io.Serializable {

	// Fields

	private Long id;
	private Long userId;
	private Double money;
	private Long accId;
	private String accName;
	private String tel;
	private Integer status;
	private Timestamp createTime;
	private String memo;

	// Constructors

	/** default constructor */
	public UserCashApply() {
	}

	/** full constructor */
	public UserCashApply(Long userId, Double money, Long accId, String accName,
			String tel, Integer status, Timestamp createTime, String memo) {
		this.userId = userId;
		this.money = money;
		this.accId = accId;
		this.accName = accName;
		this.tel = tel;
		this.status = status;
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

	@Column(name = "money", precision = 18)
	public Double getMoney() {
		return this.money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	@Column(name = "accID")
	public Long getAccId() {
		return this.accId;
	}

	public void setAccId(Long accId) {
		this.accId = accId;
	}

	@Column(name = "accName", length = 50)
	public String getAccName() {
		return this.accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	@Column(name = "tel", length = 50)
	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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