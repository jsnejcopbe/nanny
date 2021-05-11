package com.nanny.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * UserSiteMessage entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_site_message", catalog = "nanny")
public class UserSiteMessage implements java.io.Serializable {

	// Fields

	private Long id;
	private Long userId;
	private Long otherSide;
	private String mailCon;
	private Integer isRead;
	private Timestamp createTime;
	private String memo;

	// Constructors

	/** default constructor */
	public UserSiteMessage() {
	}

	/** full constructor */
	public UserSiteMessage(Long userId, Long otherSide, String mailCon,
			Integer isRead, Timestamp createTime, String memo) {
		this.userId = userId;
		this.otherSide = otherSide;
		this.mailCon = mailCon;
		this.isRead = isRead;
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

	@Column(name = "userID")
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "otherSide")
	public Long getOtherSide() {
		return this.otherSide;
	}

	public void setOtherSide(Long otherSide) {
		this.otherSide = otherSide;
	}

	@Column(name = "mailCon", length = 5000)
	public String getMailCon() {
		return this.mailCon;
	}

	public void setMailCon(String mailCon) {
		this.mailCon = mailCon;
	}

	@Column(name = "isRead")
	public Integer getIsRead() {
		return this.isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
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