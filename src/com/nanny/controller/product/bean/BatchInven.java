package com.nanny.controller.product.bean;

import java.util.List;

public class BatchInven {
	private List<String> ids;
	private List<String> names;
	private List<String> proCodes;
	private List<String> inventorys;
	private List<String> proPrice;
	
	
	
	public BatchInven(List<String> ids, List<String> names,
			List<String> proCodes, List<String> inventorys,List<String> proPrice) {
		super();
		this.ids = ids;
		this.names = names;
		this.proCodes = proCodes;
		this.inventorys = inventorys;
		this.proPrice = proPrice;
	}
	public List<String> getIds() {
		return ids;
	}
	public void setIds(List<String> ids) {
		this.ids = ids;
	}
	public List<String> getNames() {
		return names;
	}
	public void setNames(List<String> names) {
		this.names = names;
	}
	public List<String> getInventorys() {
		return inventorys;
	}
	public void setInventorys(List<String> inventorys) {
		this.inventorys = inventorys;
	}
	public List<String> getProCodes() {
		return proCodes;
	}
	public void setProCodes(List<String> proCodes) {
		this.proCodes = proCodes;
	}

	@Override
	public String toString() {
		return "BatchInven [ids=" + ids + ", names=" + names + ", proCodes="
				+ proCodes + ", inventorys=" + inventorys +", proPrice=" + proPrice + "]";
	}
	public List<String> getProPrice() {
		return proPrice;
	}
	public void setProPrice(List<String> proPrice) {
		this.proPrice = proPrice;
	}
}
