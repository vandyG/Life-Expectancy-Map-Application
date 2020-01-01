package practice;

import processing.core.*;

public class Using_PApplet_Week3 extends PApplet {
	private String URL = "http://static.asiawebdirect.com/m/phuket/portals/kosamui-com/homepage/beaches/pagePropertiesImage/samui-beaches.jpg.jpg";
	private PImage backgroundImage;
	
	public void setup() {
		size(200,200);								//set canvas size
		backgroundImage = loadImage(URL, "jpg");	//load the image
		backgroundImage.resize(0, height);			//resize the loaded image and let java decide its width according to the height of the canvas
		image(backgroundImage,0,0);					//diplay the image
	}
	
	public void draw() {
		int[] color = sunSecondColor(second());
		fill(color[0], color[1], color[2]);
		ellipse(width/4, height/5, width/5, height/5);
		}
	
	private int[] sunSecondColor(int seconds) {
		int[] rgb = new int[3];
		float diffFrom30 = Math.abs(30-seconds);
		float ratio = diffFrom30/30;
		rgb[0] = (int)(255*ratio);
		rgb[1] = (int)(255*ratio);
		rgb[2] = 0;
		
		return rgb;
	}
}
