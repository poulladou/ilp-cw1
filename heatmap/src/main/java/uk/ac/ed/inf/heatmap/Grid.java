package uk.ac.ed.inf.heatmap;

import java.util.ArrayList;
import java.util.List;

import com.mapbox.geojson.Point;

/*
 * This class contains all methods used to create the grid of the heatmap.
 */

public class Grid {
	private final static double latUB = 55.946233; //latitude upper bound
	private final static double latLB = 55.942617;
	private final static double lonUB = -3.184319;
	private final static double lonLB = -3.192473; //longitude lower bound
	public final static int d = 10; //to create a grid with dimensions dxd (in this case we consider a 10x10 grid)
	
	
	//returns a list of all the latitudes needed to make the dxd grid out of the drone's confined area
	public static List<Double> getLatsList(){
		double lat = latLB;
		var latsList = new ArrayList<Double>();
		latsList.add(lat);
		double latInterval = (latUB -latLB)/d; //calculates the latitude interval, which is used to increment the lattitude lower bound, in order to form a grid with d rows 
		for(var i=0; i<d; i++) {
			lat += latInterval;
			latsList.add(lat);
		}
		return latsList;
	}
	
	//returns a list of all the longitudes needed to make the dxd grid out of the drone's confined area
	public static List<Double> getLonsList(){
		double lon = lonLB;
		var lonsList = new ArrayList<Double>();
		lonsList.add(lon);
		double lonInterval = (lonUB -lonLB)/d;
		for(var i=0; i<d; i++) {
			lon += lonInterval;
			lonsList.add(lon);
		}
		return lonsList;
	}	
	
	//returns a list of d*d lists of five Points, representing the coordinates of each Polygon in the dxd grid 
	//(i.e. the boundaries of each of the rectangles in the grid)
	public static List<List<Point>> getGridCoords(){
		var gridCoords = new ArrayList<List<Point>>();
		var lonsList = getLonsList();
		var latsList = getLatsList();
		for(var i=d; i>0 ; i-- ) {
			for(var j=0; j<d ; j++) {
				var p1 = Point.fromLngLat(lonsList.get(j), latsList.get(i));
				var p2 = Point.fromLngLat(lonsList.get(j+1), latsList.get(i));
				var p3 = Point.fromLngLat(lonsList.get(j+1), latsList.get(i-1));
				var p4 = Point.fromLngLat(lonsList.get(j), latsList.get(i-1));
				var p5 = Point.fromLngLat(lonsList.get(j), latsList.get(i));

				var polygonCoords = List.of(p1,p2,p3,p4,p5);
				
				gridCoords.add(polygonCoords);
			}
		}
		
		return gridCoords;	
	}
	
	
}
