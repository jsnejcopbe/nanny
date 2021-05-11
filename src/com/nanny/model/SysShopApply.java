package com.nanny.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SysShopApply entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sys_shop_apply", catalog = "nanny")
public class SysShopApply implements java.io.Serializable {

	// Fields
	private Long id;
	private Long userId;
	private String name;
	private String shopName;
	private int state;
	private String tel;
	private String weixinAcc;
	private Timestamp createTime;
	private String memo;

	// Constructors

	/** default constructor */
	public SysShopApply() {
	}
 
	public SysShopApply(Long id, Long userId, String name, String shopName, int state, String tel, String weixinAcc,
			Timestamp createTime, String memo) {
		super();
		this.id = id;
		this.userId = userId;
		this.name = name;
		this.shopName = shopName;
		this.state = state;
		this.tel = tel;
		this.weixinAcc = weixinAcc;
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

	@Column(name = "name", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "tel", length = 50)
	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Column(name = "weixinAcc", length = 50)
	public String getWeixinAcc() {
		return this.weixinAcc;
	}

	public void setWeixinAcc(String weixinAcc) {
		this.weixinAcc = weixinAcc;
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

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "SysShopApply [id=" + id + ", userId=" + userId + ", name=" + name + ", shopName=" + shopName
				+ ", state=" + state + ", tel=" + tel + ", weixinAcc=" + weixinAcc + ", createTime=" + createTime
				+ ", memo=" + memo + "]";
	}

	
}