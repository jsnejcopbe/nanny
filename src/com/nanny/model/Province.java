package com.nanny.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Province entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="province"
    ,catalog="nanny"
)

public class Province  implements java.io.Serializable {


    // Fields    

     private Long id;
     private String pname;
     private Long orderid;


    // Constructors

    /** default constructor */
    public Province() {
    }

    
    /** full constructor */
    public Province(String pname, Long orderid) {
        this.pname = pname;
        this.orderid = orderid;
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
    
    @Column(name="pname", length=20)

    public String getPname() {
        return this.pname;
    }
    
    public void setPname(String pname) {
        this.pname = pname;
    }
    
    @Column(name="orderid")

    public Long getOrderid() {
        return this.orderid;
    }
    
    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }
   








}