package com.nanny.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SysReturnPoints entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sys_return_points", catalog = "nanny")
public class SysReturnPoints implements java.io.Serializable {

	// Fields

	private Long id;
	private Integer integral;
	private Double cash;
	private Timestamp createTime;
	private String memo;

	// Constructors

	/** default constructor */
	public SysReturnPoints() {
	}

	/** full constructor */
	public SysReturnPoints(Integer integral, Double cash, Timestamp createTime,
			String memo) {
		this.integral = integral;
		this.cash = cash;
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

	@Column(name = "integral")
	public Integer getIntegral() {
		return this.integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	@Column(name = "cash", precision = 22, scale = 0)
	public Double getCash() {
		return this.cash;
	}

	public void setCash(Double cash) {
		this.cash = cash;
	}

	@Column(name = "createTime", length = 0)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "memo", length = 200)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}