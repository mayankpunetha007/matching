package com.matching.logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class CurrencyProcessor {

	public static Map<String, Double> connectAndProcess() throws SAXException, IOException,
			ParserConfigurationException, URISyntaxException {
		Map<String,Double> currentMap = new HashMap<String, Double>();
		Document doc = null;
		InputStream currencyFile = CurrencyProcessor.class.getClassLoader().getResourceAsStream("currency.xml");
		BufferedReader br = new BufferedReader(new InputStreamReader(currencyFile));
		String xmlContent ="",temp="";
		while((temp=br.readLine())!=null){
			xmlContent+=temp;
			xmlContent+="\n";
		}
		doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(xmlContent)));
		NodeList answers = doc.getElementsByTagName("Cube");
		for(int i=0;i<answers.getLength();i++){
			try
			{
			Node currentNode =  answers.item(i);
			String currency = currentNode.getAttributes().getNamedItem("currency").toString().split("=")[1].replace('\"', ' ').trim();
			Double rate = Double.parseDouble(currentNode.getAttributes().getNamedItem("rate").toString().split("=")[1].replace('\"', ' ').trim());
			currentMap.put(currency, rate);
			}catch(Exception e){
				
			}
		}
		return currentMap;
		
	}

}
