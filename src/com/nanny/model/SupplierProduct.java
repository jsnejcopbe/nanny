package com.nanny.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * SupplierProduct entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="supplier_product"
    ,catalog="nanny"
)

public class SupplierProduct  implements java.io.Serializable {


    // Fields    

     private Long id;
     private String name;
     private String proCode;
     private Double price;
     private Integer inventory;
     private String cover;
     private Long supplierId;
     private Long brandId;
     private Long typeId;
     private Integer isUse;
     private Integer isCommission;
     private Integer isRecommond;
     private Timestamp createTime;
     private String memo;


    // Constructors

    /** default constructor */
    public SupplierProduct() {
    }

    
    /** full constructor */
    public SupplierProduct(String name, String proCode, Double price, Integer inventory, String cover, Long supplierId, Long brandId, Long typeId, Integer isUse, Integer isCommission, Integer isRecommond, Timestamp createTime, String memo) {
        this.name = name;
        this.proCode = proCode;
        this.price = price;
        this.inventory = inventory;
        this.cover = cover;
        this.supplierId = supplierId;
        this.brandId = brandId;
        this.typeId = typeId;
        this.isUse = isUse;
        this.isCommission = isCommission;
        this.isRecommond = isRecommond;
        this.createTime = createTime;
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
    
    @Column(name="name", length=50)

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="proCode", length=50)

    public String getProCode() {
        return this.proCode;
    }
    
    public void setProCode(String proCode) {
        this.proCode = proCode;
    }
    
    @Column(name="price", precision=18)

    public Double getPrice() {
        return this.price;
    }
    
    public void setPrice(Double price) {
        this.price = price;
    }
    
    @Column(name="inventory")

    public Integer getInventory() {
        return this.inventory;
    }
    
    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }
    
    @Column(name="cover", length=200)

    public String getCover() {
        return this.cover;
    }
    
    public void setCover(String cover) {
        this.cover = cover;
    }
    
    @Column(name="supplierID")

    public Long getSupplierId() {
        return this.supplierId;
    }
    
    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }
    
    @Column(name="brandID")

    public Long getBrandId() {
        return this.brandId;
    }
    
    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }
    
    @Column(name="typeID")

    public Long getTypeId() {
        return this.typeId;
    }
    
    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }
    
    @Column(name="isUse")

    public Integer getIsUse() {
        return this.isUse;
    }
    
    public void setIsUse(Integer isUse) {
        this.isUse = isUse;
    }
    
    @Column(name="isCommission")

    public Integer getIsCommission() {
        return this.isCommission;
    }
    
    public void setIsCommission(Integer isCommission) {
        this.isCommission = isCommission;
    }
    
    @Column(name="isRecommond")

    public Integer getIsRecommond() {
        return this.isRecommond;
    }
    
    public void setIsRecommond(Integer isRecommond) {
        this.isRecommond = isRecommond;
    }
    
    @Column(name="createTime", length=0)

    public Timestamp getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
    
    @Column(name="memo", length=50)

    public String getMemo() {
        return this.memo;
    }
    
    public void setMemo(String memo) {
        this.memo = memo;
    }
   








}