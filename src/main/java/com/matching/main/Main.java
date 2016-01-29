package com.matching.main;



import java.util.List;
import java.util.Map;

import com.matching.data.Listing;
import com.matching.data.Product;
import com.matching.logic.CurrencyProcessor;
import com.matching.logic.ProcessFiles;

public class Main {

	public static void main(String[] args) throws Exception {
		/*
		 * if(args[0].equals("learn_unique")){
		 
			List<Uniqueness> nonUnique = SupervisedLearner.irrelaventWords(args[1]);
			//Not needed since all Listing in learning data give imp strings
		}
		*/
		CurrencyProcessor.connectAndProcess();
		if(args[0].equals("solve")){
			Map<Product, List<Listing>> answer = ProcessFiles.solve(args[1],args[2]);
			ProcessFiles.printToFile(answer);
		}

	}

}
