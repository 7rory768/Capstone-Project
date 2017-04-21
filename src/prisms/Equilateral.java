package prisms;

import java.awt.Color;

import shapes.Triangle;
import util.PrismType;
import util.Vertex;

public class Equilateral extends Prism {

	private int length;
	
	public Equilateral(int xOrigin, int yOrigin, int length, Color color) {
		super(PrismType.EQUILATERAL, xOrigin, yOrigin, color);
		this.length = length;
		this.createShapes();
	}
	
	public int getLength() {
		return this.length;
	}
	
	public void setLength(int length) {
		this.length = length;
		this.createShapes();
	}
	
	public void createShapes() {
		super.clearShapes();
		int biggerHalf = (int) Math.ceil((double) this.length / 2.0);
		int smallHalf = (int) Math.ceil((double) this.length / 2.0);
		int middleX = 0;
		int middleY = 0;
		int middleZ = 0;
		int largeXCord = middleX + biggerHalf;
		int largeYCord = middleY + biggerHalf;
		int largeZCord = middleZ + biggerHalf;
		int smallXCord = middleX - smallHalf;
		int smallYCord = middleY - smallHalf;
		int smallZCord = middleZ - smallHalf;
		
		Vertex v1, v2, v3;
		
		//TRAINGLE 1
		v1 = new Vertex(largeXCord, largeYCord, largeZCord);
		v2 = new Vertex(smallXCord, smallYCord, largeZCord);
		v3 = new Vertex(smallXCord, largeYCord, smallZCord);
		super.addShape(new Triangle(v1, v2, v3, super.getColor()));
		
		// TRAINGLE 2
		v3 = new Vertex(largeXCord, smallYCord, smallZCord);
		super.addShape(new Triangle(v1, v2, v3, super.getColor()));
		
		//TRIANGLE 3
		v2 = v3;
		v3 = v1;
		v1 = new Vertex(smallXCord, largeYCord, smallZCord);
		super.addShape(new Triangle(v1, v2, v3, super.getColor()));
		
		//TRIANGLE 4
		v2 = new Vertex(smallXCord, smallYCord, largeZCord);
		v3 = new Vertex(largeXCord, smallYCord, smallZCord);
		super.addShape(new Triangle(v1, v2, v3, super.getColor()));
	}

}
