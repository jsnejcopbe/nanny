package com.nanny.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SysRedbagRec entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sys_redbag_rec", catalog = "nanny")
public class SysRedbagRec implements java.io.Serializable {

	// Fields

	private Long id;
	private Long userId;
	private Double money;
	private String result;
	private String type;
	private Integer num;
	private String actName;
	private Timestamp createTime;
	private String memo;

	// Constructors

	/** default constructor */
	public SysRedbagRec() {
	}

	/** full constructor */
	public SysRedbagRec(Long userId, Double money, String result, String type,
			Integer num, String actName, Timestamp createTime, String memo) {
		this.userId = userId;
		this.money = money;
		this.result = result;
		this.type = type;
		this.num = num;
		this.actName = actName;
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

	@Column(name = "money", precision = 18)
	public Double getMoney() {
		return this.money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	@Column(name = "result", length = 50)
	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Column(name = "type", length = 50)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "num")
	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	@Column(name = "actName", length = 50)
	public String getActName() {
		return this.actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
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