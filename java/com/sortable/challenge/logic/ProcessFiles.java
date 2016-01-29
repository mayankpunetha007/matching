package com.sortable.challenge.logic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sortable.challenge.data.Listing;
import com.sortable.challenge.data.Product;
import com.sortable.challenge.data.ProductName;
import com.sortable.challenge.data.Uniqueness;
import com.sortable.challenge.index.Index;
import com.sortable.challenge.main.IOUtils;

public class ProcessFiles {
	public ProcessFiles() throws SAXException, IOException,
			ParserConfigurationException, URISyntaxException {
		currencyMap = CurrencyProcessor.connectAndProcess();
	}
	static Map<String, Double> currencyMap;

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
			Index.addDataToManufactruerIndex(cleanData, i);
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
		currencyMap = CurrencyProcessor.connectAndProcess();
		List<Listing> allListings = IOUtils
				.readListingsData(new File(listings));
		List<Product> allProducts = IOUtils
				.readProductsData(new File(products));
		int i = 0;
		for (Listing listing : allListings) {
			String cleanData = Index
					.cleanPrimaryData(listing.getManufacturer());
			Index.addDataToManufactruerIndex(cleanData, i);
			Index.addDataToIndex(cleanData, i);
			cleanData = Index.cleanPrimaryData(listing.getTitle());
			Index.addDataToIndex(cleanData, i);
			i++;
		}
		// List<String> irrelaventWords = IOUtils.readIrreLaventWords();
		Map<Product, List<Listing>> answer = new HashMap<Product, List<Listing>>();
		for (Product product : allProducts) {

			Index.resetIndex();
			String cleanData = Index
					.cleanPrimaryData(product.getManufacturer());
			Map<Integer, Double> finalMatches = Index
					.getManufactruerDataWithScores(cleanData, 50.0);
			Map<Integer, Double> firstMatches = Index
					.getIndexingDataWithScores(cleanData, 30.0);
			join(finalMatches, firstMatches, 50.0);
			cleanData = Index.cleanPrimaryData(product.getProductName());
			firstMatches = Index
					.getIndexingDataWithScores(cleanData, 50.0);
			join(finalMatches, firstMatches, 50.0);
			cleanData = Index.cleanPrimaryData(product.getModel());
			firstMatches = Index.getIndexingDataWithScores(cleanData, 50.0);
			join(finalMatches, firstMatches, 50.0);
			cleanData = Index.cleanPrimaryData(product.getFamily());
			firstMatches = Index.getIndexingDataWithScores(cleanData, 25.0);
			join(finalMatches, firstMatches, 1.0);
			double max = 100.0;
			int index = 0;
			List<Listing> possibleMatches = new ArrayList<Listing>();
			for (Integer key : finalMatches.keySet()) {
				if (finalMatches.get(key) > 100.0) {
					possibleMatches.add(allListings.get(key));
					if (finalMatches.get(key) > max) {
						max = finalMatches.get(key);
						index = possibleMatches.size() - 1;
					}
				}
			}
			if (index < possibleMatches.size()) {
				answer.put(product, filterMatches(possibleMatches, index));
			}
		}
		printToFile(answer);
	}

	private static void printToFile(Map<Product, List<Listing>> answer)
			throws IOException {
		FileWriter fileOutputStream = new FileWriter(new File("ans.txt"));
		for(Product p:answer.keySet()){
			List<Listing> temp = answer.get(p);
			String man = p.getProductName();
			ProductName prod = new ProductName(man,temp);
			fileOutputStream.write(new GsonBuilder().setPrettyPrinting().create().toJson(prod) +"\n");
		}
		fileOutputStream.flush();
		fileOutputStream.close();
	}

	private static List<Listing> filterMatches(List<Listing> possibleMatches,
			int index) throws Exception {
		Listing tempAns = possibleMatches.get(index);
		List<Listing> answer = new ArrayList<Listing>();
		answer.add(tempAns);
		for (int i = 0; i < possibleMatches.size(); i++) {
			if (i == index) {
				continue;
			} else if (currencyMatches(tempAns,
					possibleMatches.get(i)) && hasHighCorrelation(tempAns, possibleMatches.get(i))) {
				answer.add(possibleMatches.get(i));
			}
		}
		return answer;
	}

	private static boolean hasHighCorrelation(Listing tempAns, Listing listing) {
		// TODO Auto-generated method stub
		return true;
	}

	private static boolean currencyMatches(Listing tempAns, Listing listing) {
		try {
			String currency1 = tempAns.getCurrency().trim().toUpperCase();
			Double currencyValue1 = Double.parseDouble(tempAns.getPrice()
					.trim()) * currencyMap.get(currency1);
			String currency2 = listing.getCurrency().trim().toUpperCase();
			Double currencyValue2 = Double.parseDouble(listing.getPrice()
					.trim()) * currencyMap.get(currency2);
			if ((currencyValue1 / currencyValue2) > 0.99
					|| (currencyValue1 / currencyValue2) < 1.01) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
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
