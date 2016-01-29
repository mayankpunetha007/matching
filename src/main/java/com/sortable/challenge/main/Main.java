package com.sortable.challenge.main;



import java.util.List;
import java.util.Map;

import com.sortable.challenge.data.Listing;
import com.sortable.challenge.data.Product;
import com.sortable.challenge.logic.CurrencyProcessor;
import com.sortable.challenge.logic.ProcessFiles;

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
