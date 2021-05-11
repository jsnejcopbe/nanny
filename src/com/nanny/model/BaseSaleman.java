package com.nanny.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BaseSaleman entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base_saleman", catalog = "nanny")
public class BaseSaleman implements java.io.Serializable {

	// Fields

	private Long id;
	private String name;
	private String mail;
	private String qq;
	private String tel;
	private String password;
	private Integer extension;
	private String guid;
	private String memo;
	private Timestamp createTime;

	// Constructors

	/** default constructor */
	public BaseSaleman() {
	}

	/** full constructor */
	public BaseSaleman(String name, String mail, String qq, String tel,
			String password, Integer extension, String guid, String memo,
			Timestamp createTime) {
		this.name = name;
		this.mail = mail;
		this.qq = qq;
		this.tel = tel;
		this.password = password;
		this.extension = extension;
		this.guid = guid;
		this.memo = memo;
		this.createTime = createTime;
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

	@Column(name = "name", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "mail", length = 50)
	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	@Column(name = "qq", length = 50)
	public String getQq() {
		return this.qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	@Column(name = "tel", length = 50)
	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Column(name = "password", length = 50)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "extension")
	public Integer getExtension() {
		return this.extension;
	}

	public void setExtension(Integer extension) {
		this.extension = extension;
	}

	@Column(name = "guid", length = 50)
	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	@Column(name = "memo", length = 500)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name = "createTime", length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "BaseSaleman [id=" + id + ", name=" + name + ", mail=" + mail + ", qq=" + qq + ", tel=" + tel
				+ ", password=" + password + ", extension=" + extension + ", guid=" + guid + ", memo=" + memo
				+ ", createTime=" + createTime + "]";
	}

	
}