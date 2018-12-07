package io.citymap;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import processing.core.PConstants;
import processing.core.PGraphics;

/**
 * This class draws marker for costalCity
 * @author MD AL MAMUNUR RASHID
 *
 */
public class CoastalCityMarker extends CityMarker  
{
	public CoastalCityMarker(Feature feature) 
	{
		super(((PointFeature) feature).getLocation(), feature.getProperties());
	}

	@Override
	public void drawMarker(PGraphics pg, float x, float y) 
	{
		//save the current drawing style
		pg.pushStyle();
		
		pg.fill(115, 170, 255);
		
		pg.triangle(x, y-TRI_SIZE*markerRatio, x-TRI_SIZE*markerRatio, y+TRI_SIZE*markerRatio, x+TRI_SIZE*markerRatio, y+TRI_SIZE*markerRatio);
		 
		// Restore previous drawing style
		pg.popStyle();
	}

}
