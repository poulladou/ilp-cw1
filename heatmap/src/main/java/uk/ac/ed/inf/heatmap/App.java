package uk.ac.ed.inf.heatmap;

import java.io.IOException;

/*
 * This is the main class. By running this with the argument "predictions.txt", the heatmap.geojson file is created. It inherits the class
 * Heatmap and therefore it inherits all classes.
 */
public class App extends Heatmap{
	

	public static void main(String[] args) throws IOException {
		
		var predictionsFile = args[0]; 
		
		writeGeojsonFile(predictionsFile);

	}

}
