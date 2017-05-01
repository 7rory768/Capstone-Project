package prisms;

import shapes.Square;
import util.PrismType;
import util.Vertex;

import java.awt.*;

public class Cube extends Prism {
	
	private int length;

	public Cube(Vertex origin, int length, Color color) {
		super (PrismType.CUBE, origin, color);
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

		Vertex v1 = new Vertex(largeXCord, largeYCord, largeZCord);
		Vertex v2 = new Vertex(smallXCord, largeYCord, largeZCord);
		Vertex v3 = new Vertex(smallXCord, smallYCord, largeZCord);
		Vertex v4 = new Vertex(largeXCord, smallYCord, largeZCord);
		super.addShape(new Square(v1, v2, v3, v4, this.length, Color.RED));

		v3 = new Vertex(smallXCord, largeYCord, smallZCord);
		v4 = new Vertex(largeXCord, largeYCord, smallZCord);
		super.addShape(new Square(v1, v2, v3, v4, this.length, Color.GREEN));

		v1 = new Vertex(largeXCord, smallYCord, smallZCord);
		v2 = new Vertex(smallXCord, smallYCord, smallZCord);
		super.addShape(new Square(v1, v2, v3, v4, this.length, Color.ORANGE));

		v3 = new Vertex(smallXCord, smallYCord, largeZCord);
		v4 = new Vertex(largeXCord, smallYCord, largeZCord);
		super.addShape(new Square(v1, v2, v3, v4, this.length, Color.YELLOW));

		v1 = new Vertex(smallXCord, smallYCord, smallZCord);
		v2 = v3;
		v3 = new Vertex(smallXCord, largeYCord, largeZCord);
		v4 = new Vertex(smallXCord, largeYCord, smallZCord);
		super.addShape(new Square(v1, v2, v3, v4, this.length, Color.BLUE));

		// LAST SQUARE WITH ALL large X
		v1 = new Vertex(largeXCord, smallYCord, smallZCord);
		v2 = new Vertex(largeXCord, smallYCord, largeZCord);
		v3 = new Vertex(largeXCord, largeYCord, largeZCord);
		v4 = new Vertex(largeXCord, largeYCord, smallZCord);
		super.addShape(new Square(v1, v2, v3, v4, this.length, Color.MAGENTA));
	}

}
