package com.nanny.model;

import java.util.List;

public class AttributesBean {
	private List<String> buteName;//属性名
	private List<String> buteDes;//属性描述
	
	public AttributesBean(){
		super();
	}
	
	public List<String> getButeName() {
		return buteName;
	}
	public void setButeName(List<String> buteName) {
		this.buteName = buteName;
	}
	public List<String> getButeDes() {
		return buteDes;
	}
	public void setButeDes(List<String> buteDes) {
		this.buteDes = buteDes;
	}
	
	@Override
	public String toString() {
		return "attributesBean [buteName=" + buteName + ", buteDes="
				+ buteDes + "]";
	}
}
