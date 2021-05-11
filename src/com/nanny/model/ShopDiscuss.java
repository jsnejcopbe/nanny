package com.nanny.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ShopDiscuss entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "shop_discuss", catalog = "nanny")
public class ShopDiscuss implements java.io.Serializable {

	// Fields

	private Long id;
	private String con;
	private String img;
	private Integer score;
	private Long userId;
	private String reply;
	private Long shopId;
	private Timestamp createTime;
	private String memo;

	// Constructors

	/** default constructor */
	public ShopDiscuss() {
	}

	/** full constructor */
	public ShopDiscuss(String con, String img, Integer score, Long userId,
			String reply, Long shopId, Timestamp createTime, String memo) {
		this.con = con;
		this.img = img;
		this.score = score;
		this.userId = userId;
		this.reply = reply;
		this.shopId = shopId;
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

	@Column(name = "con", length = 500)
	public String getCon() {
		return this.con;
	}

	public void setCon(String con) {
		this.con = con;
	}

	@Column(name = "img", length = 5000)
	public String getImg() {
		return this.img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	@Column(name = "score")
	public Integer getScore() {
		return this.score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	@Column(name = "userID")
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "reply", length = 500)
	public String getReply() {
		return this.reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	@Column(name = "shopID")
	public Long getShopId() {
		return this.shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
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