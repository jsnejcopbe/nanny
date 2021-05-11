package com.nanny.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SystemSetup entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "system_setup", catalog = "nanny")
public class SystemSetup implements java.io.Serializable {

	// Fields

	private Long id;
	private Integer integral;
	private Double cash;

	// Constructors

	/** default constructor */
	public SystemSetup() {
	}

	/** full constructor */
	public SystemSetup(Integer integral, Double cash) {
		this.integral = integral;
		this.cash = cash;
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

	@Column(name = "integral")
	public Integer getIntegral() {
		return this.integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	@Column(name = "cash", precision = 22, scale = 0)
	public Double getCash() {
		return this.cash;
	}

	public void setCash(Double cash) {
		this.cash = cash;
	}

}