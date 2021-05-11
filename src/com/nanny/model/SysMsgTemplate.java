package com.nanny.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SysMsgTemplate entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sys_msg_template", catalog = "nanny")
public class SysMsgTemplate implements java.io.Serializable {

	// Fields

	private Long id;
	private String template;
	private String name;
	private Timestamp createTime;
	private String memo;

	// Constructors

	/** default constructor */
	public SysMsgTemplate() {
	}

	/** full constructor */
	public SysMsgTemplate(String template, String name, Timestamp createTime,
			String memo) {
		this.template = template;
		this.name = name;
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

	@Column(name = "template", length = 1000)
	public String getTemplate() {
		return this.template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	@Column(name = "name", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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