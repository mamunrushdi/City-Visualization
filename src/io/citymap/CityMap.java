package io.citymap;

import java.util.ArrayList;
import java.util.List;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.providers.GeoMapApp;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

/**
 * This map shows the some cities of world with their information
 * draw markers according to their population size and color markers based its geographical location
 * if the city is coastal, then its color is blue
 * if the is non-coastal, then its color is green
 * 
 * @author MD Al Mamunur (Mamun)
 *
 */
public class CityMap extends PApplet
{
	//creating unfolding map
	UnfoldingMap map;
	
	//triangle size
	public static int TRI_SIZE = 20;
	
	//read city data
	private String cityFile = "city-data.json";
	
	// Markers for each city
	private List<Marker> cityMarkers;
	
	//for hover and click purpose
	private CityMarker lastSelected;
	private CityMarker lastClicked;
	//setup method
	public void setup()
	{
		/*
		 * set the canvas size of 850*600
	      *OPENGL argument indicates that this map use processing library's 3d drawing					
		 */
		size(1000, 800, OPENGL); 	
		
		//set the background of applet
		this.background(150, 150, 150);
		
		//map creation with provider
		map = new UnfoldingMap(this, 200, 50, 750, 600, new GeoMapApp.TopologicalGeoMapProvider());
		
		// Set a zoom level
		int zoomLevel = 3;
		// The next line zooms in and centers the map at my city
	    map.zoomAndPanTo(zoomLevel, new Location(59.92f,30.05f));

		//make the map interactive
		MapUtils.createDefaultEventDispatcher(this, map);
		
		//get cities and create markers
		List<Feature> cities = GeoJSONReader.loadData(this, cityFile);
		
		cityMarkers = new ArrayList<Marker>();
		
		for(Feature city : cities) 
		{		
			if(isCoastal(city))
				cityMarkers.add(new CoastalCityMarker(city));
			else
				cityMarkers.add(new NonCoastalCityMarker(city)); 
		}
		
		//add markers
		map.addMarkers(cityMarkers);
		
	}//end of draw method
	
	//draw method
	public void draw()
	{
		//draw the map
		map.draw();
		addKey();
	}//end of draw method

	@Override
	public void mouseMoved()
	{
		// clear the last selection
		if (lastSelected != null) {
			lastSelected.setSelected(false);
			lastSelected = null;
		
		}
		selectMarkerIfHover(cityMarkers);
		//loop();
	}
	
	// If there is a marker selected 
	private void selectMarkerIfHover(List<Marker> markers)
	{
		// Abort if there's already a marker selected
		if (lastSelected != null) {
			return;
		}
		
		for (Marker m : markers) 
		{
			CityMarker marker = (CityMarker)m;
			if (marker.isInside(map,  mouseX, mouseY)) {
				lastSelected = marker;
				marker.setSelected(true);
				return;
			}
		}//end for loop
	}
	
	/** The event handler for mouse clicks
	 * It will display a city with its detail information and hide other cities 
	 */
	@Override
	public void mouseClicked()
	{
		if(lastClicked != null)
		{
			unhideMarkers();
			lastClicked =null;
		}
		else checkCityMarkerForClick();
		
		//get the
	}
	// Helper method that will check if an an marker was clicked on
	// and respond appropriately
	private void checkCityMarkerForClick()
	{
		if (lastClicked != null) return;
		// Loop over the earthquake markers to see if one of them is selected
		for (Marker m : cityMarkers)
		{
			if (!m.isHidden() && m.isInside(map, mouseX, mouseY))
			{
				lastClicked = (CityMarker) m;
				// Hide all the other earthquakes and hide
				for (Marker mhide : cityMarkers) 
				{
					if (mhide != lastClicked) 
					{
						mhide.setHidden(true);
					}
				}
			return;
			}
		}
	}
	// loop over and unhide all markers
	private void unhideMarkers() {
		for(Marker marker : cityMarkers) {
			marker.setHidden(false);
		}
		
		}
	//helper method to check whether a city is coastal or not
	private boolean isCoastal(Feature feature)
	{
		//get country name of the given feature
		//get the coastal status of the given feature
		boolean coastal = Boolean.parseBoolean((String) feature.getProperty("coastal"));
		if(coastal)
		{
			return true;
		}
		return false;		
	}//end of isCoastal method
	
	// helper method to draw key in GUI 
	private void addKey() 
	{	
		//background(250, 250, 250);
		noFill();
		rect(15, 50, 170, 180); 
		
		//heading
		fill(255, 255, 255);
		text("Larger the triangle", 25, 75);
		text("larger population city has", 25, 90);
		
		//Coastal city 
		fill(115, 170, 255);
		triangle(50, 130-TRI_SIZE , 50-TRI_SIZE , 130 + TRI_SIZE , 50 + TRI_SIZE , 130 + TRI_SIZE);
		
		fill(115, 170, 255);
		text ("Coastal City", 88, 135);
		 
		//Non-Coastal city
		fill(45, 200, 75);
		triangle(50, 190-TRI_SIZE , 50-TRI_SIZE , 190 + TRI_SIZE , 50 + TRI_SIZE , 190 + TRI_SIZE);
		fill(45, 200, 75);
		text ("Non-Coastal City", 80, 193);
	}//end addKey method
	
}//end of class City
