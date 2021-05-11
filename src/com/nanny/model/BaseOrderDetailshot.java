package com.nanny.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BaseOrderDetailshot entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base_order_detailshot", catalog = "nanny")
public class BaseOrderDetailshot implements java.io.Serializable {

	// Fields

	private Long id;
	private String proName;
	private String proCover;
	private Timestamp createTime;
	private String memo;

	// Constructors

	/** default constructor */
	public BaseOrderDetailshot() {
	}

	/** full constructor */
	public BaseOrderDetailshot(String proName, String proCover,
			Timestamp createTime, String memo) {
		this.proName = proName;
		this.proCover = proCover;
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

	@Column(name = "proName", length = 50)
	public String getProName() {
		return this.proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	@Column(name = "proCover", length = 50)
	public String getProCover() {
		return this.proCover;
	}

	public void setProCover(String proCover) {
		this.proCover = proCover;
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