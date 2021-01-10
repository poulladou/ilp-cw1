package uk.ac.ed.inf.heatmap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/*
 * This class contains all methods used to obtain the predictions of the maximum sensor readings from the input file predictions.txt. It knows how
 * many predictions to obtain through the dimensions of the grid, specified by the variable d in the class Grid. This is why this class extends Grid.
 */

public class Predictions extends Grid{
	
	//reads the name of the predictions.txt file and 
	//returns a list of d strings, where each string represents the predictions of the maximum sensor readings for each row in the dxd grid
	public static List<String> readPredictionsFile(String predictionsFile) throws IOException{
		var path = Paths.get(predictionsFile).normalize().toString(); //gets the path of the predictions.txt file and converts it to a string
		
		var file = new File(path); 
		
		var br = new BufferedReader(new FileReader(file)); 
		
		String st;
		var predictions = new ArrayList<String>();
		
		while ((st = br.readLine()) != null) { 
		     predictions.add(st);
		} 
		
		br.close();

		return predictions;
	}
	
	//reads the name of the predictions.txt file and
	//returns a list of d lists, where each list (corresponding to each row of the grid) contains d predictions of the maximum sensor readings, formatted as integers
	public static List<List<Integer>> getPredictions(String predictionsFile) throws IOException{
		var predictions = new ArrayList<List<Integer>>();
		for(var i=0; i<d ; i++) {
			var strOfPredictions  = readPredictionsFile(predictionsFile).get(i); //a string containing the predictions of one row in the grid
			var predictionRow = new ArrayList<Integer>();
			for(var j=0; j<d-1; j++) {
				var ind = strOfPredictions.indexOf(',');
				var substr = strOfPredictions.substring(0,ind+1); //creates a substring from the beginning of the string, up to and including the first comma 
													 			  //(i.e the substring contains one prediction and a comma, as well as whitespaces)
				
				strOfPredictions = strOfPredictions.replaceFirst(substr, "");//after this, the string contains one less prediction and comma
				
				substr = substr.replace("," , "");//removes the comma in the substring, leaving behind only the predicted value
				substr = substr.strip();//removes any whitespaces before or after the predicted value, allowing it to be parsed as an integer

				var predictedValue = -1; //prediction is initialised to be outside the range of sensor readings (0<=reading<=255) - if there is an error in try{} 
										//(i.e. if some data is missing) the value of predictedValue will reflect this error
				try {
					predictedValue = Integer.parseInt(substr);
				}
				catch(Exception e){
					System.out.println("A prediction is missing."); 
				}

				predictionRow.add(predictedValue);
				
			}
			strOfPredictions = strOfPredictions.strip();
			
			var predictedValue = -1;
			try {
				predictedValue = Integer.parseInt(strOfPredictions);
			}
			catch(Exception e) {
				System.out.println("A prediction is missing.");
			}
			predictionRow.add(predictedValue);
			
			predictions.add(predictionRow);
		}
		return predictions;
	}
}
