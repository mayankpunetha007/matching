package com.sortable.challenge.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.sortable.challenge.data.Listing;
import com.sortable.challenge.data.Product;

public class IOUtils {
	public static List<Product> readProductsData(File file)
			throws FileNotFoundException, IOException, ParseException {
		List<Product> ans = new ArrayList<Product>();
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = "";
		while ((line = reader.readLine()) != null) {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(line);
			String productName = (String) jsonObject.get("product_name");
			String manufacturer = (String) jsonObject.get("manufacturer");
			String family = (String) jsonObject.get("family");
			String model = (String) jsonObject.get("model");
			String announcedDate = (String) jsonObject.get("announced-date");
			ans.add(new Product(productName, manufacturer, family, model,
					announcedDate));
		}
		reader.close();
		return ans;

	}

	public static List<Listing> readListingsData(File listings)
			throws FileNotFoundException, IOException, ParseException {
		List<Listing> ans = new ArrayList<Listing>();
		BufferedReader reader = new BufferedReader(new FileReader(listings)); 
			String line = "";
			while ((line = reader.readLine()) != null) {
				JSONParser jsonParser = new JSONParser();
				JSONObject jsonObject = (JSONObject) jsonParser.parse(line);
				String title = (String) jsonObject.get("title");
				String manufacturer = (String) jsonObject.get("manufacturer");
				String currency = (String) jsonObject.get("currency");
				String price = (String) jsonObject.get("price");
				ans.add(new Listing(title, manufacturer, currency, price));
			}
			reader.close();
			return ans;

	}

	public static List<String> readIrreLaventWords() {

		List<String> ans = new ArrayList<String>();
		return ans;
	}
}
