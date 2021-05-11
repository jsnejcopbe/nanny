package com.nanny.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * UserShoppingCar entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_shopping_car", catalog = "nanny")
public class UserShoppingCar implements java.io.Serializable {

	// Fields

	private Long id;
	private Long proId;
	private Long shopId;
	private Long userId;
	private Double price;
	private Integer count;
	private Timestamp createTime;
	private String memo;

	// Constructors

	/** default constructor */
	public UserShoppingCar() {
	}

	/** full constructor */
	public UserShoppingCar(Long proId, Long shopId, Long userId, Double price,
			Integer count, Timestamp createTime, String memo) {
		this.proId = proId;
		this.shopId = shopId;
		this.userId = userId;
		this.price = price;
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

	@Column(name = "userID")
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "price", precision = 18)
	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
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