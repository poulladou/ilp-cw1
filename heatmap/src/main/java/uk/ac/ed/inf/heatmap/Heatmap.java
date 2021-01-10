package uk.ac.ed.inf.heatmap;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Geometry;
import com.mapbox.geojson.Point;
import com.mapbox.geojson.Polygon;


/*
 *This class contains all methods used to create the heatmap.geojson output file. It combines methods from it's inherited classes to do so.
 */

public class Heatmap extends ColourCoding{
	
	//returns a Polygon without holes, given its coordinates
	public static Polygon createPolygon(List<Point> polygonCoords) {
		var polygon = Polygon.fromLngLats(List.of(polygonCoords));
		return polygon;
	}
	
	
	//given the Polygon coordinates and rgb string/fill colour of the Polygon, 
	//returns a Feature consisting of the Polygon geometry with properties "fill-opacity","rgb-string" and "fill"
	public static Feature createFeature(List<Point> polygonCoords, String colour) {
		var polygon = (Geometry) createPolygon(polygonCoords); //Polygon casted as Geometry object, in order to be able to make a Feature from it
		var feature = Feature.fromGeometry(polygon);
		feature.addNumberProperty("fill-opacity", 0.75);
		feature.addStringProperty("rgb-string", colour);
		feature.addStringProperty("fill", colour);
		return feature;
	}
	
	//reads the name of the predictions.txt file and
	//returns a FeatureCollection 
	public static FeatureCollection createFeatureCollection(String predictionsFile) throws IOException {
		var featureList = new ArrayList<Feature>();
		var gridCoords = getGridCoords();
		var gridColours = getGridColours(predictionsFile);
		for(var i= 0 ; i<d*d; i++) {
			var feature = createFeature(gridCoords.get(i), gridColours.get(i));
			featureList.add(feature);
		}
		
		var featureCollection = FeatureCollection.fromFeatures(featureList);
		return featureCollection;
	}
	
	//reads the name of the predictions.txt file and
	//creates a heatmap.geojson file
	public static void writeGeojsonFile(String predictionsFile) throws IOException {
		var myWriter = new FileWriter("heatmap.geojson");
		myWriter.write(createFeatureCollection(predictionsFile).toJson());
		myWriter.close();
	}
}
