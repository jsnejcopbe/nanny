package com.nanny.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BaseShop entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base_shop", catalog = "nanny")
public class BaseShop implements java.io.Serializable {

	// Fields

	private Long id;
	private String shopName;
	private Double balance;
	private String shopIcon;
	private String shopDes;
	private Long userId;
	private Long itroShopId;
	private Long salmanId;
	private Integer status;
	private Integer isHeadShop;
	private Long adressId;
	private Integer situation;
	private Double minSendPrice;
	private Double deliveryPrice;
	private Integer isSubsidy;
	private Integer printerType;
	private Timestamp createTime;
	private String memo;
	private Integer isTransfer;
	private Integer isVouchers;

	// Constructors

	/** default constructor */
	public BaseShop() {
	}

	/** full constructor */
	public BaseShop(String shopName, Double balance, String shopIcon,
			String shopDes, Long userId, Long itroShopId, Long salmanId,
			Integer status, Integer isHeadShop, Long adressId,
			Integer situation, Double minSendPrice, Double deliveryPrice,
			Integer isSubsidy, Integer printerType, Timestamp createTime,
			String memo, Integer isTransfer, Integer isVouchers) {
		this.shopName = shopName;
		this.balance = balance;
		this.shopIcon = shopIcon;
		this.shopDes = shopDes;
		this.userId = userId;
		this.itroShopId = itroShopId;
		this.salmanId = salmanId;
		this.status = status;
		this.isHeadShop = isHeadShop;
		this.adressId = adressId;
		this.situation = situation;
		this.minSendPrice = minSendPrice;
		this.deliveryPrice = deliveryPrice;
		this.isSubsidy = isSubsidy;
		this.printerType = printerType;
		this.createTime = createTime;
		this.memo = memo;
		this.isTransfer = isTransfer;
		this.isVouchers = isVouchers;
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

	@Column(name = "shop_name", length = 50)
	public String getShopName() {
		return this.shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	@Column(name = "balance", precision = 18)
	public Double getBalance() {
		return this.balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	@Column(name = "shop_icon", length = 200)
	public String getShopIcon() {
		return this.shopIcon;
	}

	public void setShopIcon(String shopIcon) {
		this.shopIcon = shopIcon;
	}

	@Column(name = "shop_des", length = 500)
	public String getShopDes() {
		return this.shopDes;
	}

	public void setShopDes(String shopDes) {
		this.shopDes = shopDes;
	}

	@Column(name = "userID")
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "itroShopID")
	public Long getItroShopId() {
		return this.itroShopId;
	}

	public void setItroShopId(Long itroShopId) {
		this.itroShopId = itroShopId;
	}

	@Column(name = "salmanID")
	public Long getSalmanId() {
		return this.salmanId;
	}

	public void setSalmanId(Long salmanId) {
		this.salmanId = salmanId;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "isHeadShop")
	public Integer getIsHeadShop() {
		return this.isHeadShop;
	}

	public void setIsHeadShop(Integer isHeadShop) {
		this.isHeadShop = isHeadShop;
	}

	@Column(name = "adressID")
	public Long getAdressId() {
		return this.adressId;
	}

	public void setAdressId(Long adressId) {
		this.adressId = adressId;
	}

	@Column(name = "situation")
	public Integer getSituation() {
		return this.situation;
	}

	public void setSituation(Integer situation) {
		this.situation = situation;
	}

	@Column(name = "min_send_price", precision = 18)
	public Double getMinSendPrice() {
		return this.minSendPrice;
	}

	public void setMinSendPrice(Double minSendPrice) {
		this.minSendPrice = minSendPrice;
	}

	@Column(name = "delivery_price", precision = 18)
	public Double getDeliveryPrice() {
		return this.deliveryPrice;
	}

	public void setDeliveryPrice(Double deliveryPrice) {
		this.deliveryPrice = deliveryPrice;
	}

	@Column(name = "isSubsidy")
	public Integer getIsSubsidy() {
		return this.isSubsidy;
	}

	public void setIsSubsidy(Integer isSubsidy) {
		this.isSubsidy = isSubsidy;
	}

	@Column(name = "printerType")
	public Integer getPrinterType() {
		return this.printerType;
	}

	public void setPrinterType(Integer printerType) {
		this.printerType = printerType;
	}

	@Column(name = "createTime", length = 0)
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

	@Column(name = "isTransfer")
	public Integer getIsTransfer() {
		return this.isTransfer;
	}

	public void setIsTransfer(Integer isTransfer) {
		this.isTransfer = isTransfer;
	}

	@Column(name = "isVouchers")
	public Integer getIsVouchers() {
		return this.isVouchers;
	}

	public void setIsVouchers(Integer isVouchers) {
		this.isVouchers = isVouchers;
	}

}