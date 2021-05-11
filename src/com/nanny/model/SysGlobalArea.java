package com.nanny.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SysGlobalArea entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sys_global_area", catalog = "nanny")
public class SysGlobalArea implements java.io.Serializable {

	// Fields

	private Long id;
	private String province;
	private String city;
	private String area;
	private String detAdd;
	private String addName;
	private String lon;
	private String lat;
	private Timestamp createTime;
	private String memo;

	// Constructors

	/** default constructor */
	public SysGlobalArea() {
	}

	/** full constructor */
	public SysGlobalArea(String province, String city, String area,
			String detAdd, String addName, String lon, String lat,
			Timestamp createTime, String memo) {
		this.province = province;
		this.city = city;
		this.area = area;
		this.detAdd = detAdd;
		this.addName = addName;
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

	@Column(name = "province", length = 50)
	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Column(name = "city", length = 50)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "area", length = 50)
	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@Column(name = "detAdd", length = 200)
	public String getDetAdd() {
		return this.detAdd;
	}

	public void setDetAdd(String detAdd) {
		this.detAdd = detAdd;
	}

	@Column(name = "addName", length = 50)
	public String getAddName() {
		return this.addName;
	}

	public void setAddName(String addName) {
		this.addName = addName;
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

	@Override
	public String toString() {
		return "SysGlobalArea [id=" + id + ", province=" + province + ", city=" + city + ", area=" + area + ", detAdd="
				+ detAdd + ", addName=" + addName + ", lon=" + lon + ", lat=" + lat + ", createTime=" + createTime
				+ ", memo=" + memo + "]";
	}

}