package com.nanny.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * UserSearchRec entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_search_rec", catalog = "nanny")
public class UserSearchRec implements java.io.Serializable {

	// Fields

	private Long id;
	private Long userId;
	private String keyWord;
	private Timestamp createTime;
	private String memo;

	// Constructors

	/** default constructor */
	public UserSearchRec() {
	}

	/** full constructor */
	public UserSearchRec(Long userId, String keyWord, Timestamp createTime,
			String memo) {
		this.userId = userId;
		this.keyWord = keyWord;
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

	@Column(name = "keyWord", length = 50)
	public String getKeyWord() {
		return this.keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	@Column(name = "createTime", length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "memo", length = 50)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}