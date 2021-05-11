package com.nanny.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * SupplierOrder entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="supplier_order"
    ,catalog="nanny"
)

public class SupplierOrder  implements java.io.Serializable {


    // Fields    

     private Long id;
     private String orderCode;
     private Long payShopId;
     private Long recSupplierId;
     private Double totalPrice;
     private Double fee;
     private Integer status;
     private Timestamp createTime;
     private String shopmsg;
     private String memo;


    // Constructors

    /** default constructor */
    public SupplierOrder() {
    }

    
    /** full constructor */
    public SupplierOrder(String orderCode, Long payShopId, Long recSupplierId, Double totalPrice, Double fee, Integer status, Timestamp createTime, String shopmsg, String memo) {
        this.orderCode = orderCode;
        this.payShopId = payShopId;
        this.recSupplierId = recSupplierId;
        this.totalPrice = totalPrice;
        this.fee = fee;
        this.status = status;
        this.createTime = createTime;
        this.shopmsg = shopmsg;
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
    
    @Column(name="orderCode", length=50)

    public String getOrderCode() {
        return this.orderCode;
    }
    
    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }
    
    @Column(name="payShopID")

    public Long getPayShopId() {
        return this.payShopId;
    }
    
    public void setPayShopId(Long payShopId) {
        this.payShopId = payShopId;
    }
    
    @Column(name="recSupplierID")

    public Long getRecSupplierId() {
        return this.recSupplierId;
    }
    
    public void setRecSupplierId(Long recSupplierId) {
        this.recSupplierId = recSupplierId;
    }
    
    @Column(name="totalPrice", precision=18)

    public Double getTotalPrice() {
        return this.totalPrice;
    }
    
    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    @Column(name="fee", precision=18)

    public Double getFee() {
        return this.fee;
    }
    
    public void setFee(Double fee) {
        this.fee = fee;
    }
    
    @Column(name="status")

    public Integer getStatus() {
        return this.status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    @Column(name="createTime", length=0)

    public Timestamp getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
    
    @Column(name="shopmsg", length=200)

    public String getShopmsg() {
        return this.shopmsg;
    }
    
    public void setShopmsg(String shopmsg) {
        this.shopmsg = shopmsg;
    }
    
    @Column(name="memo", length=200)

    public String getMemo() {
        return this.memo;
    }
    
    public void setMemo(String memo) {
        this.memo = memo;
    }
   








}