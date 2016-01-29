package com.sortable.challenge.data;

public class Uniqueness{

	private String value;
	private Integer count;
	
	public Uniqueness(String value, Integer count){
		this.value = value;
		this.count = count;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
}