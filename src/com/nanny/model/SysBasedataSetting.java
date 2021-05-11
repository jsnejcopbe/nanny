package com.nanny.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SysBasedataSetting entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sys_basedata_setting", catalog = "nanny")
public class SysBasedataSetting implements java.io.Serializable {

	// Fields

	private Long id;
	private Integer signScore;
	private Integer maxScore;
	private String scrollerNotice;
	private String icp;
	private Double commission;

	// Constructors

	/** default constructor */
	public SysBasedataSetting() {
	}

	/** full constructor */
	public SysBasedataSetting(Integer signScore, Integer maxScore,
			String scrollerNotice, String icp, Double commission) {
		this.signScore = signScore;
		this.maxScore = maxScore;
		this.scrollerNotice = scrollerNotice;
		this.icp = icp;
		this.commission = commission;
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

	@Column(name = "signScore")
	public Integer getSignScore() {
		return this.signScore;
	}

	public void setSignScore(Integer signScore) {
		this.signScore = signScore;
	}

	@Column(name = "maxScore")
	public Integer getMaxScore() {
		return this.maxScore;
	}

	public void setMaxScore(Integer maxScore) {
		this.maxScore = maxScore;
	}

	@Column(name = "scrollerNotice", length = 500)
	public String getScrollerNotice() {
		return this.scrollerNotice;
	}

	public void setScrollerNotice(String scrollerNotice) {
		this.scrollerNotice = scrollerNotice;
	}

	@Column(name = "icp", length = 50)
	public String getIcp() {
		return this.icp;
	}

	public void setIcp(String icp) {
		this.icp = icp;
	}

	@Column(name = "commission", precision = 18)
	public Double getCommission() {
		return this.commission;
	}

	public void setCommission(Double commission) {
		this.commission = commission;
	}

}