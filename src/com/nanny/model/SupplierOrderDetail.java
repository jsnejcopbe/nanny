package com.nanny.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * SupplierOrderDetail entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="supplier_order_detail"
    ,catalog="nanny"
)

public class SupplierOrderDetail  implements java.io.Serializable {


    // Fields    

     private Long id;
     private Long supOrderId;
     private Long proId;
     private Double price;
     private Integer count;
     private Timestamp createTiem;
     private String memo;


    // Constructors

    /** default constructor */
    public SupplierOrderDetail() {
    }

    
    /** full constructor */
    public SupplierOrderDetail(Long supOrderId, Long proId, Double price, Integer count, Timestamp createTiem, String memo) {
        this.supOrderId = supOrderId;
        this.proId = proId;
        this.price = price;
        this.count = count;
        this.createTiem = createTiem;
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
    
    @Column(name="sup_orderID")

    public Long getSupOrderId() {
        return this.supOrderId;
    }
    
    public void setSupOrderId(Long supOrderId) {
        this.supOrderId = supOrderId;
    }
    
    @Column(name="proID")

    public Long getProId() {
        return this.proId;
    }
    
    public void setProId(Long proId) {
        this.proId = proId;
    }
    
    @Column(name="price", precision=18)

    public Double getPrice() {
        return this.price;
    }
    
    public void setPrice(Double price) {
        this.price = price;
    }
    
    @Column(name="count")

    public Integer getCount() {
        return this.count;
    }
    
    public void setCount(Integer count) {
        this.count = count;
    }
    
    @Column(name="createTiem", length=0)

    public Timestamp getCreateTiem() {
        return this.createTiem;
    }
    
    public void setCreateTiem(Timestamp createTiem) {
        this.createTiem = createTiem;
    }
    
    @Column(name="memo", length=500)

    public String getMemo() {
        return this.memo;
    }
    
    public void setMemo(String memo) {
        this.memo = memo;
    }
   








}