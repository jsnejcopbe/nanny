package com.nanny.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SysScoreShop entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sys_score_shop", catalog = "nanny")
public class SysScoreShop implements java.io.Serializable {

	// Fields

	private Long id;
	private Long proId;
	private Double score;
	private Integer maxLimit;
	private Timestamp createTime;
	private String memo;

	// Constructors

	/** default constructor */
	public SysScoreShop() {
	}

	/** full constructor */
	public SysScoreShop(Long proId, Double score, Integer maxLimit,
			Timestamp createTime, String memo) {
		this.proId = proId;
		this.score = score;
		this.maxLimit = maxLimit;
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

	@Column(name = "score", precision = 18)
	public Double getScore() {
		return this.score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	@Column(name = "maxLimit")
	public Integer getMaxLimit() {
		return this.maxLimit;
	}

	public void setMaxLimit(Integer maxLimit) {
		this.maxLimit = maxLimit;
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