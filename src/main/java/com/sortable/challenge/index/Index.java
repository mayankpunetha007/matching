package com.sortable.challenge.index;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

import com.sortable.challenge.data.Uniqueness;

public class Index {

	public static Map<String, List<Integer>> completeIndex = new TreeMap<String, List<Integer>>();
	public static Map<String, List<Integer>> manufactruerIndex = new TreeMap<String, List<Integer>>();
	public static Map<String, Integer> unique = new TreeMap<String, Integer>();
	private static HashMap<String, Integer> reset;

	private static void addDataToIndex(String data, int index,
			Map<String, List<Integer>> indexTo) {
		String cleanString = cleanPrimaryData(data);
		indexData(cleanString, index, indexTo);
	}

	public static String cleanPrimaryData(String data) {
		if (data == null) {
			return "";
		}
		data = data.toLowerCase();
		String ans = "";
		for (int i = 0; i < data.length(); i++) {
			if (data.charAt(i) == '/' || data.charAt(i) == '\\'
					|| data.charAt(i) == '_') {
				data += " ";
				continue;
			}
			if ((data.charAt(i) >= 'a' && data.charAt(i) <= 'z')
					|| data.charAt(i) == '-' || data.charAt(i) == ' '
					|| data.charAt(i) == '.'
					|| (data.charAt(i) >= '0' && data.charAt(i) <= '9')) {
				ans += data.charAt(i);
			}
		}
		return ans;

	}

	private static void indexData(String dataToIndex, int currentData,
			Map<String, List<Integer>> index) {
		String[] tokens = dataToIndex.split(" ");
		for (String token : tokens) {
			token = token.trim().toLowerCase();
			if (token.length() == 0) {
				continue;
			}
			if (index.containsKey(token)) {
				index.get(token).add(currentData);
			} else {
				index.put(token,
						new ArrayList<Integer>(Arrays.asList(currentData)));
			}
			if (unique.containsKey(token)) {
				unique.put(token, unique.get(token) + 1);
			} else {
				unique.put(token, 1);
			}
		}
	}

	public static void resetIndex() {
		reset = new HashMap<String, Integer>();
	}

	private static Map<Integer, Double> getIndexingDataWithScores(String temp,
			double score, Map<String, List<Integer>> index, boolean isModel) {
		temp = temp.trim().toLowerCase();
		Map<Integer, Double> ans = new HashMap<Integer, Double>();
		String cleanData = Index.cleanPrimaryData(temp);
		String[] tokens = cleanData.split(" ");
		int length = tokens.length;
		for (String token : tokens) {
			token = token.trim().toLowerCase();
			if (reset.containsKey(token)) {
				continue;
			}
			reset.put(token, 1);
			double givenScore = 0.1 * (score * 1.0)
					/ ((length * 1.0) / (temp.length() * 1.0));
			if (isModel) {
				if (Pattern.matches("^[a-z]+-[a-z0-9]+$", token)) {
					givenScore = score * 2;
				} else if (Pattern.matches("^[a-z0-9]+-[a-z0-9]$", token)
						&& token.length() >= 2) {
					givenScore = score * 2;
				} else if (Pattern.matches("^[a-z]+[0-9]+$", token)
						&& token.length() >= 2) {
					givenScore = score * 2;
				}
			}

			if (index.containsKey(token)) {
				List<Integer> tempList = index.get(token);
				for (Integer num : tempList) {
					if (ans.containsKey(num)) {
						ans.put(num, ans.get(num) + givenScore);
					} else {
						ans.put(num, givenScore

						);
					}
				}
			}
		}
		return ans;
	}

	public static List<Uniqueness> getSortedUniquenesses() {
		ArrayList<Uniqueness> ans = new ArrayList<Uniqueness>();
		for (String temp : unique.keySet()) {
			ans.add(new Uniqueness(temp, unique.get(temp)));
		}
		Collections.sort(ans, new Comparator<Uniqueness>() {

			@Override
			public int compare(Uniqueness o1, Uniqueness o2) {
				return o1.getCount() - o2.getCount();
			}
		});
		return ans;

	}

	public static void addDataToManufactruerIndex(String data, int index) {
		addDataToIndex(data, index, manufactruerIndex);
	}

	public static void addDataToIndex(String data, int index) {
		addDataToIndex(data, index, completeIndex);
	}

	public static Map<Integer, Double> getManufactruerDataWithScores(
			String temp, double score) {
		return getIndexingDataWithScores(temp, score, manufactruerIndex, false);
	}

	public static Map<Integer, Double> getIndexingDataWithScores(String temp,
			double score, boolean isModel) {
		return getIndexingDataWithScores(temp, score, completeIndex, isModel);
	}

	public static Map<Integer, Double> getIndexingDataWithScores(String temp,
			double score) {
		return getIndexingDataWithScores(temp, score, completeIndex, false);
	}

}
