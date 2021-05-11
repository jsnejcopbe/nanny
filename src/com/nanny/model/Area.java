package com.nanny.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Area entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="area"
    ,catalog="nanny"
)

public class Area  implements java.io.Serializable {


    // Fields    

     private Long id;
     private Long cityId;
     private String area;
     private String postcode;


    // Constructors

    /** default constructor */
    public Area() {
    }

    
    /** full constructor */
    public Area(Long cityId, String area, String postcode) {
        this.cityId = cityId;
        this.area = area;
        this.postcode = postcode;
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
    
    @Column(name="cityID")

    public Long getCityId() {
        return this.cityId;
    }
    
    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }
    
    @Column(name="area", length=60)

    public String getArea() {
        return this.area;
    }
    
    public void setArea(String area) {
        this.area = area;
    }
    
    @Column(name="postcode", length=50)

    public String getPostcode() {
        return this.postcode;
    }
    
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
   








}