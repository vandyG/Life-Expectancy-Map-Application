package practice;

import processing.core.PApplet;

public class PracticeCode extends PApplet {
	
	public void setup() {
		size(200,200,OPENGL);
		fill(100);
		
	}
	
	public void draw() {
		
		noStroke();
		colorMode(HSB, 100);
		for (int i = 0; i < 100; i++) {
		  for (int j = 0; j < 100; j++) {
		    stroke(j, j, 100);
		    point(i, j);
		  }
		}
	}
}
