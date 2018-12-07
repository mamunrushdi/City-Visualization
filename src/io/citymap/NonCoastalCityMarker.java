package io.citymap;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import processing.core.PConstants;
import processing.core.PGraphics;
/**
 * This class draws marker non-coastal city
 * @author MD AL MAMUNUR RASHID
 *
 */
public class NonCoastalCityMarker extends CityMarker
{
	public NonCoastalCityMarker(Feature feature) 
	{
		super(((PointFeature) feature).getLocation(), feature.getProperties());
	}
 
	public void drawMarker(PGraphics pg, float x, float y)
	{		
		//save the current drawing style
		pg.pushStyle();
		
		// IMPLEMENT: drawing triangle for each city
		pg.fill(45, 200, 75);
		pg.triangle(x, y-TRI_SIZE*markerRatio, x-TRI_SIZE*markerRatio, y+TRI_SIZE*markerRatio, x+TRI_SIZE*markerRatio, y+TRI_SIZE*markerRatio);
		// Restore previous drawing style
		pg.popStyle(); 
	}

}
