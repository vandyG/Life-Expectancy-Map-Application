package module6;

import processing.core.PApplet;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.utils.MapUtils;
import parsing.ParseFeed;
import de.fhpotsdam.unfolding.providers.*;
import de.fhpotsdam.unfolding.providers.Google.*;

import java.util.List;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;

import java.util.HashMap;

import de.fhpotsdam.unfolding.marker.Marker;

/**
 * Visualizes life expectancy in different countries.
 * 
 * It loads the country shapes from a GeoJSON file via a data reader, and loads
 * the population density values from another CSV file (provided by the World
 * Bank). The data value is encoded to transparency via a simplistic linear
 * mapping.
 */
public class LifeExpectancy extends PApplet {

	UnfoldingMap map;
	HashMap<String, Float> lifeExpMap;
	List<Feature> countries;
	List<Marker> countryMarkers;
	private Marker lastSelected;
	private int lastClicked;
	private int xbase = 30;
	private int ybase = 370;
	
	public void setup() {
		size(800, 600);
		map = new UnfoldingMap(this, 0, 0, 1920, 1080, new Google.GoogleMapProvider());
		MapUtils.createDefaultEventDispatcher(this, map);

		// Load lifeExpectancy data
		lifeExpMap = ParseFeed.loadLifeExpectancyFromCSV(this, "LifeExpectancyWorldBank.csv");

		// Load country polygons and adds them as markers
		countries = GeoJSONReader.loadData(this, "countries.geo.json");
		countryMarkers = MapUtils.createSimpleMarkers(countries);
		map.addMarkers(countryMarkers);
		System.out.println(countryMarkers.get(0).getId());

		// Country markers are shaded according to life expectancy (only once)
		shadeCountries(true, countryMarkers);
	}

	public void draw() {
		// Draw map tiles and country markers
		map.draw();
		for (Marker m : countryMarkers) {
			if (m.isSelected()) {
				showInfo(m, mouseX, mouseY);
			}
		}

		filterButtons();
		addKey();
		selectButton();
	}

	// Helper method to color each country based on life expectancy
	// Red-orange indicates low (near 40)
	// Blue indicates high (near 100)
	private void shadeCountries(boolean x, List<Marker> m) {
		for (Marker marker : m) {
			// Find data for country of the current marker
			String countryId = marker.getId();
			System.out.println(lifeExpMap.containsKey(countryId));
			if (lifeExpMap.containsKey(countryId) && x) {
				float lifeExp = lifeExpMap.get(countryId);
				// Encode value as brightness (values range: 40-90)
				int colorLevel = (int) map(lifeExp, 40, 90, 10, 255);
				marker.setColor(color(255 - colorLevel, 100, colorLevel));
			} else {
				marker.setColor(color(150, 150, 150));
			}
		}
	}

	public void mouseMoved() {
		if (lastSelected != null) {
			lastSelected.setSelected(false);
			lastSelected = null;
		}
		selectMarkerIfHover(countryMarkers);
	}

	private void selectMarkerIfHover(List<Marker> markers) {
		if (lastSelected != null) {
			return;
		}
		for (Marker m : markers) {
			if (m.isInside(map, mouseX, mouseY)) {
				m.setSelected(true);
				lastSelected = m;
			}
		}
	}

	public void showInfo(Marker m, float x, float y) {

		String title = m.getId() + "\n" + lifeExpMap.get(m.getId());

		rectMode(CORNER);

		stroke(110);
		fill(255, 255, 255);
		rect(x, y + 15, textWidth(title) + 6, 36, 5);

		textAlign(LEFT, TOP);
		fill(0);
		text(title, x + 3, y + 18);

	}

	public void shadeSelected(float a, float b) {
		for (Marker m : countryMarkers) {
			String countryID = m.getId();
			if (lifeExpMap.containsKey(countryID) && lifeExpMap.get(countryID) > a && lifeExpMap.get(countryID) < b) {
				m.setColor(color(0, 0, 255));
			}
		}
	}

	public void mouseClicked() {
		if (lastClicked != 0) {
			lastClicked = 0;
			shadeCountries(true, countryMarkers);
		}
		if (mouseX > xbase+10 && mouseX < xbase+25 && mouseY > ybase+50 && mouseY < ybase+65) {
			shadeCountries(false, countryMarkers);
			shadeSelected(75, 150);
			lastClicked = 1;
		}
		if (mouseX > xbase+10 && mouseX < xbase+25 && mouseY > ybase+80 && mouseY < ybase+95) {
			shadeCountries(false, countryMarkers);
			shadeSelected(0, 50);
			lastClicked = 2;
		}
	}

	public void filterButtons() {

		
		fill(255, 250, 240);
		rect(xbase, ybase, 75, 120, 5);

		fill(0, 0, 0);
		textAlign(LEFT, TOP);
		text("Life", xbase + 25, ybase + 10);
		text("Expectancy", xbase + 5, ybase+20);

		fill(255, 255, 255);
		rect(xbase+10, ybase+50, 15, 15);
		fill(0, 0, 0);
		text(">75", xbase+35, ybase+50);
		fill(255, 255, 255);
		rect(xbase+10, ybase+80, 15, 15);
		fill(0, 0, 0);
		text("<50", xbase+35, ybase+80);
	}

	public void addKey() {

		int xbaseK = xbase;
		int ybaseK = ybase + 150;
		
		fill(255, 250, 240);
		rect(xbaseK, ybaseK, 75, 120, 5);
		
		for(int i = xbaseK+10; i < xbaseK+30; i++) {
			for(int j = ybaseK+10; j < ybaseK+110; j++) {
				int colourLevel = (int) map(j, ybaseK+10, ybaseK+110, 10, 255);
				stroke(colourLevel, 100, 255-colourLevel);
				point(i,j);
			}
		}
		
		stroke(0,0,0);
		fill(0,0,0);
		text("90 yrs", xbaseK+35, ybaseK+10);
		text("40 yrs", xbaseK+35, ybaseK+100);
	}
	
	private void selectButton() {
		if(lastClicked == 1) {
			fill(0, 0, 255);
			rect(xbase+10, ybase+50, 15, 15);
		}
		else if(lastClicked == 2) {
			fill(0, 0, 255);
			rect(xbase+10, ybase+80, 15, 15);
		}
		else {
			fill(255, 255, 255);
			rect(xbase+10, ybase+80, 15, 15);
			rect(xbase+10, ybase+50, 15, 15);
		}
		
	}

}
