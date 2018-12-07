/**
 * 
 */
package io.citymap;

import java.util.HashMap;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import processing.core.PConstants;
import processing.core.PGraphics;

 
/**
 * This is abstract class which abstracts the properties of the city map
 * @author MD AL MAMUNUR RASHID
 *
 */
abstract public class CityMarker extends SimplePointMarker
{
	public static int TRI_SIZE = 10;
	public float markerRatio = 0; //ratio to determine the marker size
	
	//constructor
	public CityMarker(Feature feature)
	{
		super(((PointFeature) feature).getLocation(), feature.getProperties());
		
	}
	public CityMarker(Location location, java.util.HashMap<java.lang.String,java.lang.Object> properties)
	{
		super(location, properties);
		
		//determine the size of cityMarker size according to the city's population
		
		markerRatio = getPopulation()/5;
		
		if(markerRatio < 1.0) //if city's population is less 1 million, set its ration 0.5
		{
			markerRatio = 0.50f;
		}
		else if(markerRatio > 3) //if city's population is larger than 15 million, set its ration 2.0
		{
			markerRatio = 2.0f;
		}
	}

	//abstract methods which will be implemented in subclasses 
	 abstract public void drawMarker(PGraphics pg, float x, float y);
	 
	//drawing method
	public void draw(PGraphics pg, float x, float y)
	{ 
		if (!hidden) drawMarker(pg, x, y);  
		
		if (selected) showCityInformation(pg, x, y);//when hover over the marker show city information
	}//end of draw method
 
	 public void showCityInformation(PGraphics pg, float x, float y)
		{
			String name = this.getCity() + " " + this.getCountry() + " ";
			String pop = "Pop: " + this.getPopulation() + " Million";
			
			pg.pushStyle();
			
			pg.fill(255, 255, 255);
			pg.textSize(12);
			pg.rectMode(PConstants.CORNER);
			pg.rect(x, y-TRI_SIZE-39, Math.max(pg.textWidth(name), pg.textWidth(pop)) + 6, 39);
			pg.fill(0, 0, 0);
			pg.textAlign(PConstants.LEFT, PConstants.TOP);
			pg.text(name, x+3, y-TRI_SIZE-33);
			pg.text(pop, x+3, y - TRI_SIZE -18);
			
			pg.popStyle();
		}//end of the method showCityInformation
		
	//show city title if
	/** Show the title of the earthquake if this marker is selected */
	
	public void showTitle(PGraphics pg, float x, float y)
	{
		String name = getCity(); 
		pg.pushStyle();

		pg.fill(100,100, 100);
		pg.text(name, x, y);
		pg.popStyle();
		
	}//end of method showTitle
	 
	//getter methods
	 
	public String getTitle() {
		
		return "City";//(getProperty("name").toString());
	}
	private String getCity()
	{
		return getStringProperty("name");
	}
	
	private String getCountry()
	{
		return getStringProperty("country");
	}
	
	private float getPopulation()
	{
		return Float.parseFloat(getStringProperty("population"));
	}
	//if the given city is coastal city
		
}//end of the class
