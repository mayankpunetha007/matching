package com.matching.data;

public class Product{
	
	private String productName;
	
	private String manufacturer;
	
	private String family;	
	
	private String model;
	
	private String announcedDate;
	
	public Product(String productName, String manufacturer,String family,String model, String announcedDate){
		this.productName = productName;
		this.manufacturer = manufacturer;
		this.family = family;
		this.model = model;
		this.announcedDate = announcedDate;
	}
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getFamily() {
		return family;
	}
	public void setFamily(String family) {
		this.family = family;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getAnnouncedDate() {
		return announcedDate;
	}
	public void setAnnouncedDate(String announcedDate) {
		this.announcedDate = announcedDate;
	}
	public String toString(){
		return "productName:" + productName + ",manufacturer:" + manufacturer + ","
				+ "family:" + family + ",model:" + model + ";\n";
	}

}
