package com.nanny.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * UserVoucher entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_voucher", catalog = "nanny")
public class UserVoucher implements java.io.Serializable {

	// Fields

	private Long id;
	private String vouCode;
	private Integer count;
	private Long userId;
	private Timestamp createTime;
	private String memo;

	// Constructors

	/** default constructor */
	public UserVoucher() {
	}

	/** full constructor */
	public UserVoucher(String vouCode, Integer count, Long userId,
			Timestamp createTime, String memo) {
		this.vouCode = vouCode;
		this.count = count;
		this.userId = userId;
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

	@Column(name = "vouCode", length = 50)
	public String getVouCode() {
		return this.vouCode;
	}

	public void setVouCode(String vouCode) {
		this.vouCode = vouCode;
	}

	@Column(name = "count")
	public Integer getCount() {
		return this.count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Column(name = "userID")
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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