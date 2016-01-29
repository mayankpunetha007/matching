package com.sortable.challenge.main;



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
			ProcessFiles.solveAndPrintToFile(args[1],args[2]);
		}

	}

}