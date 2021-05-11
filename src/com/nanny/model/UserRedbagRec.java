package com.nanny.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * UserRedbagRec entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_redbag_rec", catalog = "nanny")
public class UserRedbagRec implements java.io.Serializable {

	// Fields

	private Long id;
	private Double money;
	private Long recUserId;
	private Long shopId;
	private Timestamp recTime;
	private String memo;

	// Constructors

	/** default constructor */
	public UserRedbagRec() {
	}

	/** full constructor */
	public UserRedbagRec(Double money, Long recUserId, Long shopId,
			Timestamp recTime, String memo) {
		this.money = money;
		this.recUserId = recUserId;
		this.shopId = shopId;
		this.recTime = recTime;
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

	@Column(name = "recUserID")
	public Long getRecUserId() {
		return this.recUserId;
	}

	public void setRecUserId(Long recUserId) {
		this.recUserId = recUserId;
	}

	@Column(name = "shopID")
	public Long getShopId() {
		return this.shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	@Column(name = "recTime", length = 19)
	public Timestamp getRecTime() {
		return this.recTime;
	}

	public void setRecTime(Timestamp recTime) {
		this.recTime = recTime;
	}

	@Column(name = "memo", length = 500)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}