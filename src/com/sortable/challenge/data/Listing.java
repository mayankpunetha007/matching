package com.sortable.challenge.data;

public class Listing {

	private String title;

	private String manufacturer;

	private String currency;

	private String price;

	public Listing(String title, String manufacturer, String currency,
			String price) {
		this.title = title;
		this.manufacturer = manufacturer;
		this.currency = currency;
		this.price = price;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String toString() {
		return "title:" + title + ",manufacturer:" + manufacturer+";\n";
	}

}
