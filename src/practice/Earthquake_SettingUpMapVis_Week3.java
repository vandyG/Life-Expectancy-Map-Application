package practice;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

public class Earthquake_SettingUpMapVis_Week3 extends PApplet {
	private UnfoldingMap map;
	
	public void setup() {
		size(400,400,OPENGL);
		map = new UnfoldingMap(this, 200, 50, 700, 500, new Microsoft.HybridProvider());
		map.zoomToLevel(2);
		MapUtils.createDefaultEventDispatcher(this, map);
		Location valLoc = new Location(-38.14f, -73.03f);
		SimplePointMarker val = new SimplePointMarker(valLoc);
		map.addMarker(val);
		
	}
	
	public void draw() {
		background(10);
		map.draw();
		//addKey();
	}
}
