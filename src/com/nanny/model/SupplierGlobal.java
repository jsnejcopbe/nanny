package com.nanny.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SupplierGlobal entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "supplier_global", catalog = "nanny")
public class SupplierGlobal implements java.io.Serializable {

	// Fields

	private Long id;
	private Long supplierId;
	private String province;
	private String city;
	private String area;
	private String lon;
	private String lat;
	private Timestamp createTime;
	private String memo;

	// Constructors

	/** default constructor */
	public SupplierGlobal() {
	}

	/** full constructor */
	public SupplierGlobal(Long supplierId, String province, String city,
			String area, String lon, String lat, Timestamp createTime,
			String memo) {
		this.supplierId = supplierId;
		this.province = province;
		this.city = city;
		this.area = area;
		this.lon = lon;
		this.lat = lat;
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

	@Column(name = "supplierID")
	public Long getSupplierId() {
		return this.supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	@Column(name = "province", length = 50)
	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Column(name = "city", length = 100)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "area", length = 100)
	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@Column(name = "lon", length = 50)
	public String getLon() {
		return this.lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	@Column(name = "lat", length = 50)
	public String getLat() {
		return this.lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	@Column(name = "createTime", length = 0)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "memo", length = 200)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}