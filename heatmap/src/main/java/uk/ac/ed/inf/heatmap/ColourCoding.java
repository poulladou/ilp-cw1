package uk.ac.ed.inf.heatmap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * This class contains all methods used to colour code the heatmap. It uses the predictions from the predictions.txt input file to do so, 
 * hence why this class extends Predictions.
 */

public class ColourCoding extends Predictions {
		
	//returns an RGB string corresponding to inputed sensor reading
	public static String getColour(int sensorReading) {
		var colour = "#aaaaaa"; //default return value is RGB string corresponding to 'not visited' sensor to show missing/incorrect data
		if(0<=sensorReading && sensorReading<32 ) {
			colour= "#00ff00";
		}
		else if(32<=sensorReading && sensorReading<64 ) {
			colour= "#40ff00";
		}
		else if(64<=sensorReading && sensorReading<96 ) {
			colour= "#80ff00";
		}
		else if(96<=sensorReading && sensorReading<128 ) {
			colour= "#c0ff00";
		}
		else if(128<=sensorReading && sensorReading<160 ) {
			colour= "#ffc000";
		}
		else if(160<=sensorReading && sensorReading<192 ) {
			colour= "#ff8000";
		}		
		else if(192<=sensorReading && sensorReading<224 ) {
			colour= "#ff4000";
		}
		else if(224<=sensorReading && sensorReading<256 ) {
			colour= "#ff0000";
		}		
		return colour;
	}
	
	//given the name of the predictions file, 
	//returns a list of the colours corresponding to the predictions
	public static List<String> getGridColours(String predictionsFile) throws IOException{
		var predictions =getPredictions(predictionsFile);
		var gridColours = new ArrayList<String>();
		for(var i=0; i<d; i++) {
			for(var j=0; j<d; j++) {
				var prediction = predictions.get(i).get(j); //gets the prediction corresponding to the i-th row and j-th column of the grid
				var colour = getColour(prediction);
				gridColours.add(colour);
			}
		}
		return gridColours;
	}
}
