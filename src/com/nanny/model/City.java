package com.nanny.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * City entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="city"
    ,catalog="nanny"
)

public class City  implements java.io.Serializable {


    // Fields    

     private Long id;
     private Long provinceId;
     private String city;
     private String areaCode;


    // Constructors

    /** default constructor */
    public City() {
    }

    
    /** full constructor */
    public City(Long provinceId, String city, String areaCode) {
        this.provinceId = provinceId;
        this.city = city;
        this.areaCode = areaCode;
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
    
    @Column(name="provinceId")

    public Long getProvinceId() {
        return this.provinceId;
    }
    
    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }
    
    @Column(name="city", length=50)

    public String getCity() {
        return this.city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    @Column(name="AreaCode", length=60)

    public String getAreaCode() {
        return this.areaCode;
    }
    
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
   








}