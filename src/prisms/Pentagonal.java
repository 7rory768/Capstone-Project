package prisms;

import java.awt.Color;

import util.PrismType;

public class Pentagonal extends Prism {

	private int sideLength, height;
	
	public Pentagonal(PrismType type, int xOrigin, int yOrigin, Color color, int sideLength, int height) {
		super(type, xOrigin, yOrigin, color);
		this.sideLength = sideLength;
		this.height = height;
		this.createShapes();
	}
	
	public int getSideLength() {
		return this.sideLength;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	// 108 angle between each vertex
	public void createShapes() {
		
	}

}
