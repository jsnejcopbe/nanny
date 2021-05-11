package com.nanny.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * UserSign entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_sign", catalog = "nanny")
public class UserSign implements java.io.Serializable {

	// Fields

	private Long id;
	private Integer score;
	private Timestamp signTime;
	private Long userId;
	private Integer signCount;
	private String memo;

	// Constructors

	/** default constructor */
	public UserSign() {
	}

	/** full constructor */
	public UserSign(Integer score, Timestamp signTime, Long userId,
			Integer signCount, String memo) {
		this.score = score;
		this.signTime = signTime;
		this.userId = userId;
		this.signCount = signCount;
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

	@Column(name = "score")
	public Integer getScore() {
		return this.score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	@Column(name = "signTime", length = 19)
	public Timestamp getSignTime() {
		return this.signTime;
	}

	public void setSignTime(Timestamp signTime) {
		this.signTime = signTime;
	}

	@Column(name = "userID")
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "signCount")
	public Integer getSignCount() {
		return this.signCount;
	}

	public void setSignCount(Integer signCount) {
		this.signCount = signCount;
	}

	@Column(name = "memo", length = 50)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}