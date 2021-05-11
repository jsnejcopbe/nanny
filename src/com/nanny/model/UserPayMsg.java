package com.nanny.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * UserPayMsg entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_pay_msg", catalog = "nanny")
public class UserPayMsg implements java.io.Serializable {

	// Fields

	private Long id;
	private String data;
	private Long userId;
	private Double totalMoney;
	private Timestamp createTime;
	private String memo;

	// Constructors

	/** default constructor */
	public UserPayMsg() {
	}

	/** full constructor */
	public UserPayMsg(String data, Long userId, Double totalMoney,
			Timestamp createTime, String memo) {
		this.data = data;
		this.userId = userId;
		this.totalMoney = totalMoney;
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

	@Column(name = "data", length = 500)
	public String getData() {
		return this.data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Column(name = "userID")
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "totalMoney", precision = 18)
	public Double getTotalMoney() {
		return this.totalMoney;
	}

	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
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