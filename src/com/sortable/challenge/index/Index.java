package com.sortable.challenge.index;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Index {
	
	public static Map<String, List<Integer> > index;
	
	public static void addDataToIndex(String data, int index){
		String cleanString = cleanPrimaryData(data);
		indexData(cleanString, index);
	}

	private static String cleanPrimaryData(String data) {
		String ans="";
		for(int i=0;i<data.length();i++){
			if(data.charAt(i)=='/' || data.charAt(i)=='\\'){
				data+=" ";
				continue;
			}
			if((data.charAt(i)>='a' && data.charAt(i)<='z') || (data.charAt(i)>='A' && data.charAt(i)<='Z')||  data.charAt(i)==' '){
				ans+=data.charAt(i);
			}
		}
		return ans;
		
	}
	private static void indexData(String dataToIndex, int currentData){
		String[] tokens = dataToIndex.split(" ");
		for(String token:tokens){
			if(index.containsKey(token)){
				index.get(token).add(currentData);
			}else{
				index.put(token, new ArrayList<Integer>(Arrays.asList(currentData)));
			}
		}
	}
	

}
