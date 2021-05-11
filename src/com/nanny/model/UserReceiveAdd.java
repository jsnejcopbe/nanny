package com.nanny.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * UserReceiveAdd entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_receive_add", catalog = "nanny")
public class UserReceiveAdd implements java.io.Serializable {

	// Fields

	private Long id;
	private String address;
	private String community;
	private String doorplate;
	private String tel;
	private String recName;
	private Long userId;
	private String code;
	private Timestamp createTime;
	private String memo;

	// Constructors

	/** default constructor */
	public UserReceiveAdd() {
	}

	/** full constructor */
	public UserReceiveAdd(String address, String community, String doorplate,
			String tel, String recName, Long userId, String code,
			Timestamp createTime, String memo) {
		this.address = address;
		this.community = community;
		this.doorplate = doorplate;
		this.tel = tel;
		this.recName = recName;
		this.userId = userId;
		this.code = code;
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

	@Column(name = "address", length = 100)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "community", length = 100)
	public String getCommunity() {
		return this.community;
	}

	public void setCommunity(String community) {
		this.community = community;
	}

	@Column(name = "doorplate", length = 100)
	public String getDoorplate() {
		return this.doorplate;
	}

	public void setDoorplate(String doorplate) {
		this.doorplate = doorplate;
	}

	@Column(name = "tel", length = 50)
	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Column(name = "recName", length = 50)
	public String getRecName() {
		return this.recName;
	}

	public void setRecName(String recName) {
		this.recName = recName;
	}

	@Column(name = "userID")
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "code", length = 50)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "createTime", length = 0)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "memo", length = 50)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}