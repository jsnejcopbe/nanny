package com.nanny.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SalemanCapitalRec entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "saleman_capital_rec", catalog = "nanny")
public class SalemanCapitalRec implements java.io.Serializable {

	// Fields

	private Long id;
	private Long salId;
	private Double money;
	private String type;
	private String memo;
	private Timestamp createTime;

	// Constructors

	/** default constructor */
	public SalemanCapitalRec() {
	}

	/** full constructor */
	public SalemanCapitalRec(Long salId, Double money, String type,
			String memo, Timestamp createTime) {
		this.salId = salId;
		this.money = money;
		this.type = type;
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

	@Column(name = "salID")
	public Long getSalId() {
		return this.salId;
	}

	public void setSalId(Long salId) {
		this.salId = salId;
	}

	@Column(name = "money", precision = 18)
	public Double getMoney() {
		return this.money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	@Column(name = "type", length = 50)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
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

}