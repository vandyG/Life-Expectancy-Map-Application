package practice;

import processing.core.*;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.utils.MapUtils;

public class Adding_Buttons_Week5 extends PApplet {

	private UnfoldingMap map;
	private int value;
	
	public void setup() {
		size(800, 600);
		background(50,50,50);
		map = new UnfoldingMap(this, 50, 50, 700, 500, new Microsoft.HybridProvider());
		MapUtils.createDefaultEventDispatcher(this, map);
	}
	public void draw() {
		map.draw();
		drawButtons();
	}
	
	private void drawButtons() {
		fill(255,255,255);
		rect(100,100,25,25);
		
		fill(100,100,100);
		rect(100, 150,25,25);
	}
	
	public void mouseReleased() {
		if(mouseX>100 && mouseY<125 && mouseY>100 && mouseX<125) {
			background(255,255,255);
		}
		
		if(mouseX>100 && mouseY<175 && mouseX<125 && mouseY>150) {
			background(100,100,100);
		}
	}
}
