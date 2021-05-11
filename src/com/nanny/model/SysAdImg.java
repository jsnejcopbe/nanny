package com.nanny.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SysAdImg entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sys_ad_img", catalog = "nanny")
public class SysAdImg implements java.io.Serializable {

	// Fields

	private Long id;
	private String img;
	private String link;
	private Long colId;
	private Timestamp createTime;
	private String memo;

	// Constructors

	/** default constructor */
	public SysAdImg() {
	}

	/** full constructor */
	public SysAdImg(String img, String link, Long colId, Timestamp createTime,
			String memo) {
		this.img = img;
		this.link = link;
		this.colId = colId;
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

	@Column(name = "img", length = 200)
	public String getImg() {
		return this.img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	@Column(name = "link", length = 200)
	public String getLink() {
		return this.link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@Column(name = "colID")
	public Long getColId() {
		return this.colId;
	}

	public void setColId(Long colId) {
		this.colId = colId;
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