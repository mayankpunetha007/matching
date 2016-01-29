package com.sortable.challenge.logic;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Test;

import com.google.gson.GsonBuilder;
import com.sortable.challenge.data.Listing;
import com.sortable.challenge.data.Product;


public class CurrencyProcessorTest{
	
	@Test
	public void test() throws Exception{
		solve();
		currencyMatches();
	}
	
	void solve() throws Exception {
		Map<Product, List<Listing>> ans = ProcessFiles.solve("src\\test\\resources\\listings", "src\\test\\resources\\products");
		System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(ans));
	}
	
	void currencyMatches(){
		assertEquals(true, ProcessFiles.currencyMatches(new Listing("", "", "USD", "55"), new Listing("", "", "CAD", "59")));
		assertEquals(false, ProcessFiles.currencyMatches(new Listing("", "", "USD", "5"), new Listing("", "", "CAD", "59")));
	}
}
