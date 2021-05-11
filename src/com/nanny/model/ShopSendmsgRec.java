package com.nanny.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ShopSendmsgRec entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "shop_sendmsg_rec", catalog = "nanny")
public class ShopSendmsgRec implements java.io.Serializable {

	// Fields

	private Long id;
	private Long shopId;
	private String msgCon;
	private String sta;
	private Timestamp createTime;
	private String memo;

	// Constructors

	/** default constructor */
	public ShopSendmsgRec() {
	}

	/** full constructor */
	public ShopSendmsgRec(Long shopId, String msgCon, String sta,
			Timestamp createTime, String memo) {
		this.shopId = shopId;
		this.msgCon = msgCon;
		this.sta = sta;
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

	@Column(name = "shopID")
	public Long getShopId() {
		return this.shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	@Column(name = "msgCon", length = 500)
	public String getMsgCon() {
		return this.msgCon;
	}

	public void setMsgCon(String msgCon) {
		this.msgCon = msgCon;
	}

	@Column(name = "sta", length = 500)
	public String getSta() {
		return this.sta;
	}

	public void setSta(String sta) {
		this.sta = sta;
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