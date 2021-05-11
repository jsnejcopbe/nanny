package com.nanny.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SysPayType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sys_pay_type", catalog = "nanny")
public class SysPayType implements java.io.Serializable {

	// Fields

	private Long id;
	private String typeName;
	private String icon;
	private String parseList;
	private Integer status;
	private Timestamp createTime;
	private String memo;

	// Constructors

	/** default constructor */
	public SysPayType() {
	}

	/** full constructor */
	public SysPayType(String typeName, String icon, String parseList,
			Integer status, Timestamp createTime, String memo) {
		this.typeName = typeName;
		this.icon = icon;
		this.parseList = parseList;
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

	@Column(name = "typeName", length = 50)
	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Column(name = "icon", length = 200)
	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Column(name = "parseList", length = 500)
	public String getParseList() {
		return this.parseList;
	}

	public void setParseList(String parseList) {
		this.parseList = parseList;
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