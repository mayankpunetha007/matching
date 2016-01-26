package com.sortable.challenge.learn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.parser.ParseException;

import com.sortable.challenge.data.Listing;
import com.sortable.challenge.data.Product;
import com.sortable.challenge.data.Uniqueness;
import com.sortable.challenge.index.Index;
import com.sortable.challenge.main.IOUtils;

public class SupervisedLearner {

	/**
	 * To check uniqueness of a word in the current index For given data all
	 * words are unique so not needed
	 * 
	 * @param listings
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 */
	public static List<Uniqueness> irrelaventWords(String listings)
			throws FileNotFoundException, IOException, ParseException {
		List<Listing> allListings = IOUtils
				.readListingsData(new File(listings));
		int i = 0;
		for (Listing listing : allListings) {
			String cleanData = listing.getManufacturer();
			Index.addDataToIndex(cleanData, i);
			cleanData = Index.cleanPrimaryData(listing.getTitle());
			Index.addDataToIndex(cleanData, i);
			i++;
		}
		List<Uniqueness> temp = Index.getSortedUniquenesses();
		int start = 0, end = temp.size(), mid = 0;
		while (start < end) {
			mid = (start + end) / 2;
			System.out.println("Is the below word relavent? Y else anything");
			System.out.println(temp.get(mid).getValue());
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			String ans = br.readLine();
			if (ans.trim().equalsIgnoreCase("Y")) {
				start = mid + 1;
			} else {
				end = mid - 1;
			}
		}
		if (mid >= temp.size()) {
			return new ArrayList<Uniqueness>();
		}
		return temp.subList(mid, temp.size() - 1);
	}

	public static void solveAndPrintToFile(String listings, String products)
			throws Exception {
		List<Listing> allListings = IOUtils
				.readListingsData(new File(listings));
		List<Product> allProducts = IOUtils
				.readProductsData(new File(products));
		int i = 0;
		for (Listing listing : allListings) {
			String cleanData = Index
					.cleanPrimaryData(listing.getManufacturer());
			Index.addDataToIndex(cleanData, i);
			cleanData = Index.cleanPrimaryData(listing.getTitle());
			Index.addDataToIndex(cleanData, i);
			i++;
		}
		FileOutputStream fileOutputStream = new FileOutputStream(new File(
				"ans.txt"));
		// List<String> irrelaventWords = IOUtils.readIrreLaventWords();
		Map<Product, List<Listing>> answer = new HashMap<Product, List<Listing>>();
		for (Product product : allProducts) {

			Index.resetIndex();
			String cleanData = Index
					.cleanPrimaryData(product.getManufacturer());
			Map<Integer, Double> finalMatches = Index
					.getIndexingDataWithScores(cleanData, 1.0);
			cleanData = Index.cleanPrimaryData(product.getProductName());
			Map<Integer, Double> firstMatches = Index
					.getIndexingDataWithScores(cleanData, 50.0);
			join(finalMatches, firstMatches, 2.0);
			cleanData = Index.cleanPrimaryData(product.getModel());
			firstMatches = Index.getIndexingDataWithScores(cleanData, 50.0);
			join(finalMatches, firstMatches, 5.0);
			cleanData = Index.cleanPrimaryData(product.getFamily());
			firstMatches = Index.getIndexingDataWithScores(cleanData, 1.0);
			join(finalMatches, firstMatches, 1.0);
			double max = 100.0;
			int index = -1;
			List<Listing> possibleMatches = new ArrayList<Listing>();
			for (Integer key : finalMatches.keySet()) {
				if (finalMatches.get(key) > 100.0) {
					possibleMatches.add(allListings.get(key));
					if (finalMatches.get(key) > max) {
						max = finalMatches.get(key);
						index = finalMatches.size() - 1;
					}
				}
			}
			answer.put(product, filterMatches(possibleMatches,index));
		}
	}

	private static List<Listing> filterMatches(
			List<Listing> possibleMatches, int index) {
		return possibleMatches;
	}

	private static void join(Map<Integer, Double> finalMatches,
			Map<Integer, Double> firstMatches, double threshold) {
		for (Integer key : firstMatches.keySet()) {
			// System.out.println(key + " "+firstMatches.get(key));
			if (finalMatches.containsKey(key)) {
				finalMatches.put(key,
						finalMatches.get(key) + firstMatches.get(key));
			}
		}

	}

}
