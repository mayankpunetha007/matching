package com.matching.logic;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Test;

import com.google.gson.GsonBuilder;
import com.matching.data.Listing;
import com.matching.data.Product;
import com.matching.logic.ProcessFiles;


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
		assertEquals(true, ProcessFiles.currencyMatches(new Listing("", "", "USD", "55"), new Listing("", "", "CAD", "75")));
		assertEquals(false, ProcessFiles.currencyMatches(new Listing("", "", "USD", "5"), new Listing("", "", "CAD", "59")));
	}
}
