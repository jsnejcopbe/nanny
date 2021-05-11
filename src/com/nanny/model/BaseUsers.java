package com.nanny.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BaseUsers entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base_users", catalog = "nanny")
public class BaseUsers implements java.io.Serializable {

	// Fields

	private Long id;
	private String nickName;
	private String tel;
	private String mail;
	private Timestamp birthdate;
	private String qq;
	private String password;
	private Double balance;
	private Integer point;
	private String headImg;
	private String sex;
	private Integer isAdmin;
	private Long recShopId;
	private String origin;
	private String originId;
	private Timestamp createTime;
	private String token;

	// Constructors

	/** default constructor */
	public BaseUsers() {
	}

	/** full constructor */
	public BaseUsers(String nickName, String tel, String mail,
			Timestamp birthdate, String qq, String password, Double balance,
			Integer point, String headImg, String sex, Integer isAdmin,
			Long recShopId, String origin, String originId,
			Timestamp createTime, String token) {
		this.nickName = nickName;
		this.tel = tel;
		this.mail = mail;
		this.birthdate = birthdate;
		this.qq = qq;
		this.password = password;
		this.balance = balance;
		this.point = point;
		this.headImg = headImg;
		this.sex = sex;
		this.isAdmin = isAdmin;
		this.recShopId = recShopId;
		this.origin = origin;
		this.originId = originId;
		this.createTime = createTime;
		this.token = token;
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

	@Column(name = "nickName", length = 50)
	public String getNickName() {
		return this.nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	@Column(name = "tel", length = 50)
	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Column(name = "mail", length = 50)
	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	@Column(name = "birthdate", length = 0)
	public Timestamp getBirthdate() {
		return this.birthdate;
	}

	public void setBirthdate(Timestamp birthdate) {
		this.birthdate = birthdate;
	}

	@Column(name = "qq", length = 50)
	public String getQq() {
		return this.qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	@Column(name = "password", length = 50)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "balance", precision = 18)
	public Double getBalance() {
		return this.balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	@Column(name = "point")
	public Integer getPoint() {
		return this.point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	@Column(name = "headImg", length = 200)
	public String getHeadImg() {
		return this.headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	@Column(name = "sex", length = 50)
	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "isAdmin")
	public Integer getIsAdmin() {
		return this.isAdmin;
	}

	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}

	@Column(name = "recShopID")
	public Long getRecShopId() {
		return this.recShopId;
	}

	public void setRecShopId(Long recShopId) {
		this.recShopId = recShopId;
	}

	@Column(name = "origin", length = 50)
	public String getOrigin() {
		return this.origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	@Column(name = "originID", length = 50)
	public String getOriginId() {
		return this.originId;
	}

	public void setOriginId(String originId) {
		this.originId = originId;
	}

	@Column(name = "createTime", length = 0)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "token", length = 50)
	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}