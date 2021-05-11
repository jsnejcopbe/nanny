package com.nanny.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * BaseSupplier entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="base_supplier"
    ,catalog="nanny"
)

public class BaseSupplier  implements java.io.Serializable {


    // Fields    

     private Long id;
     private String supplierName;
     private String password;
     private String tel;
     private String qq;
     private String supplierIcon;
     private String supplierDes;
     private Long provinceId;
     private Long cityId;
     private Long districtId;
     private Timestamp createTime;
     private Integer status;
     private String memo;


    // Constructors

    /** default constructor */
    public BaseSupplier() {
    }

    
    /** full constructor */
    public BaseSupplier(String supplierName, String password, String tel, String qq, String supplierIcon, String supplierDes, Long provinceId, Long cityId, Long districtId, Timestamp createTime, Integer status, String memo) {
        this.supplierName = supplierName;
        this.password = password;
        this.tel = tel;
        this.qq = qq;
        this.supplierIcon = supplierIcon;
        this.supplierDes = supplierDes;
        this.provinceId = provinceId;
        this.cityId = cityId;
        this.districtId = districtId;
        this.createTime = createTime;
        this.status = status;
        this.memo = memo;
    }

   
    // Property accessors
    @Id @GeneratedValue
    
    @Column(name="id", unique=true, nullable=false)

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    @Column(name="supplier_name", length=50)

    public String getSupplierName() {
        return this.supplierName;
    }
    
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
    
    @Column(name="password", length=50)

    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    @Column(name="tel", length=50)

    public String getTel() {
        return this.tel;
    }
    
    public void setTel(String tel) {
        this.tel = tel;
    }
    
    @Column(name="qq", length=50)

    public String getQq() {
        return this.qq;
    }
    
    public void setQq(String qq) {
        this.qq = qq;
    }
    
    @Column(name="supplier_icon", length=200)

    public String getSupplierIcon() {
        return this.supplierIcon;
    }
    
    public void setSupplierIcon(String supplierIcon) {
        this.supplierIcon = supplierIcon;
    }
    
    @Column(name="supplier_des", length=500)

    public String getSupplierDes() {
        return this.supplierDes;
    }
    
    public void setSupplierDes(String supplierDes) {
        this.supplierDes = supplierDes;
    }
    
    @Column(name="provinceID")

    public Long getProvinceId() {
        return this.provinceId;
    }
    
    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }
    
    @Column(name="cityID")

    public Long getCityId() {
        return this.cityId;
    }
    
    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }
    
    @Column(name="districtID")

    public Long getDistrictId() {
        return this.districtId;
    }
    
    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }
    
    @Column(name="createTime", length=0)

    public Timestamp getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
    
    @Column(name="status")

    public Integer getStatus() {
        return this.status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    @Column(name="memo", length=500)

    public String getMemo() {
        return this.memo;
    }
    
    public void setMemo(String memo) {
        this.memo = memo;
    }
   








}