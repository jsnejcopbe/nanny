package com.nanny.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SysVoucher entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sys_voucher", catalog = "nanny")
public class SysVoucher implements java.io.Serializable {

	// Fields

	private Long id;
	private String name;
	private Long userId;
	private String vouCode;
	private Double money;
	private Timestamp startTime;
	private Timestamp endTime;
	private Integer maxLimit;
	private Timestamp createTime;
	private Timestamp usageTime;
	private Integer status;
	private String memo;

	// Constructors

	/** default constructor */
	public SysVoucher() {
	}

	/** full constructor */
	public SysVoucher(String name, Long userId, String vouCode, Double money,
			Timestamp startTime, Timestamp endTime, Integer maxLimit,
			Timestamp createTime, Timestamp usageTime, Integer status,
			String memo) {
		this.name = name;
		this.userId = userId;
		this.vouCode = vouCode;
		this.money = money;
		this.startTime = startTime;
		this.endTime = endTime;
		this.maxLimit = maxLimit;
		this.createTime = createTime;
		this.usageTime = usageTime;
		this.status = status;
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

	@Column(name = "name", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "userID")
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "vouCode", length = 50)
	public String getVouCode() {
		return this.vouCode;
	}

	public void setVouCode(String vouCode) {
		this.vouCode = vouCode;
	}

	@Column(name = "money", precision = 50, scale = 0)
	public Double getMoney() {
		return this.money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	@Column(name = "startTime", length = 0)
	public Timestamp getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	@Column(name = "endTime", length = 0)
	public Timestamp getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	@Column(name = "maxLimit")
	public Integer getMaxLimit() {
		return this.maxLimit;
	}

	public void setMaxLimit(Integer maxLimit) {
		this.maxLimit = maxLimit;
	}

	@Column(name = "createTime", length = 0)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "usageTime", length = 0)
	public Timestamp getUsageTime() {
		return this.usageTime;
	}

	public void setUsageTime(Timestamp usageTime) {
		this.usageTime = usageTime;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "memo", length = 50)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}