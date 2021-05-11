package com.nanny.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SysAdSetting entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sys_ad_setting", catalog = "nanny")
public class SysAdSetting implements java.io.Serializable {

	// Fields

	private Long id;
	private String columName;
	private Integer colType;
	private Timestamp createTime;
	private String memo;

	// Constructors

	/** default constructor */
	public SysAdSetting() {
	}

	/** full constructor */
	public SysAdSetting(String columName, Integer colType,
			Timestamp createTime, String memo) {
		this.columName = columName;
		this.colType = colType;
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

	@Column(name = "columName", length = 50)
	public String getColumName() {
		return this.columName;
	}

	public void setColumName(String columName) {
		this.columName = columName;
	}

	@Column(name = "colType")
	public Integer getColType() {
		return this.colType;
	}

	public void setColType(Integer colType) {
		this.colType = colType;
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