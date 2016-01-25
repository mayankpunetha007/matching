package com.sortable.challenge.learn;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.json.simple.parser.ParseException;

import com.sortable.challenge.data.Listing;
import com.sortable.challenge.data.Product;
import com.sortable.challenge.main.IOUtils;

public class SupervisedLearner {
	
	public void main(String listings,String products) throws FileNotFoundException, IOException, ParseException{
		List<Listing> allListings = IOUtils.readListingsData(new File(listings));
		List<Product> allProducts = IOUtils.readProductsData(new File(products));
	}

	

}
