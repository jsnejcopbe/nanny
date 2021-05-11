package com.nanny.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SysBanks entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sys_banks", catalog = "nanny")
public class SysBanks implements java.io.Serializable {

	// Fields

	private Long id;
	private String bankName;
	private String icon;
	private Integer isUse;
	private Timestamp createTime;
	private String memo;

	// Constructors

	/** default constructor */
	public SysBanks() {
	}

	/** full constructor */
	public SysBanks(String bankName, String icon, Integer isUse,
			Timestamp createTime, String memo) {
		this.bankName = bankName;
		this.icon = icon;
		this.isUse = isUse;
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

	@Column(name = "bankName", length = 50)
	public String getBankName() {
		return this.bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Column(name = "icon", length = 200)
	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Column(name = "isUse")
	public Integer getIsUse() {
		return this.isUse;
	}

	public void setIsUse(Integer isUse) {
		this.isUse = isUse;
	}

	@Column(name = "createTime", length = 0)
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