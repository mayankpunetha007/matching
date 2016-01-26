package com.sortable.challenge.main;



import com.sortable.challenge.learn.SupervisedLearner;

public class Main {

	public static void main(String[] args) throws Exception {
		/*
		 * if(args[0].equals("learn_unique")){
		 
			List<Uniqueness> nonUnique = SupervisedLearner.irrelaventWords(args[1]);
			//Not needed since all Listing in learning data give imp strings
		}
		*/
		if(args[0].equals("learn_constants")){
			SupervisedLearner.solveAndPrintToFile(args[1],args[2]);
		}

	}

}
