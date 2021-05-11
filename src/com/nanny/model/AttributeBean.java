package com.nanny.model;

public class AttributeBean {
	private String buteName;
	private String buteDes;
	
	public AttributeBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getButeName() {
		return buteName;
	}
	public void setButeName(String buteName) {
		this.buteName = buteName;
	}
	public String getButeDes() {
		return buteDes;
	}
	public void setButeDes(String buteDes) {
		this.buteDes = buteDes;
	}
	
	@Override
	public String toString() {
		return "attributeBean [buteName=" + buteName + ", buteDes="
				+ buteDes +  "]";
	}
}
