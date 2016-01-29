package com.sortable.challenge.data;

import java.util.List;

public class ProductName{
	private String product_name;
	private List<Listing> listings;
	public ProductName(String man, List<Listing> temp) {
		this.setProduct_name(man);
		this.setListings(temp);
	}
	public List<Listing> getListings() {
		return listings;
	}
	public void setListings(List<Listing> listings) {
		this.listings = listings;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	
}