package prisms;

import shapes.Square;
import util.PrismType;
import util.Vertex;

import java.awt.*;

public class Cube extends Prism {
	
	private int length, realLength;

	public Cube(Vertex origin, int length, Color color) {
		super (PrismType.CUBE, origin, color);
		this.length = length;
		this.realLength = length;
		this.createShapes();

	}
	
	public int getLength() {
		return this.length;
	}
	
	public void setLength(int length) {
		this.length = length;
		this.createShapes();
	}

    public int getRealLength() {
        return this.realLength;
    }

	public void setRealLength(int realLength) {
        this.realLength = realLength;
        this.setLength(realLength + this.getZDisplacement());
        this.createShapes();
	}
	
	public void createShapes() {
		super.clearShapes();
		int biggerHalf = (int) Math.ceil((double) this.length / 2.0);
		int smallHalf = (int) Math.ceil((double) this.length / 2.0);
		int largeXCord = biggerHalf;
		int largeYCord = biggerHalf;
		int largeZCord = biggerHalf;
		int smallXCord = smallHalf;
		int smallYCord = smallHalf;
		int smallZCord = smallHalf;

		Vertex v1 = new Vertex(largeXCord, largeYCord, largeZCord);
		Vertex v2 = new Vertex(smallXCord, largeYCord, largeZCord);
		Vertex v3 = new Vertex(smallXCord, smallYCord, largeZCord);
		Vertex v4 = new Vertex(largeXCord, smallYCord, largeZCord);
		super.addShape(new Square(v1, v2, v3, v4, this.length));

		v3 = new Vertex(smallXCord, largeYCord, smallZCord);
		v4 = new Vertex(largeXCord, largeYCord, smallZCord);
		super.addShape(new Square(v1, v2, v3, v4, this.length));

		v1 = new Vertex(largeXCord, smallYCord, smallZCord);
		v2 = new Vertex(smallXCord, smallYCord, smallZCord);
		super.addShape(new Square(v1, v2, v3, v4, this.length));

		v3 = new Vertex(smallXCord, smallYCord, largeZCord);
		v4 = new Vertex(largeXCord, smallYCord, largeZCord);
		super.addShape(new Square(v1, v2, v3, v4, this.length));

		v1 = new Vertex(smallXCord, smallYCord, smallZCord);
		v2 = v3;
		v3 = new Vertex(smallXCord, largeYCord, largeZCord);
		v4 = new Vertex(smallXCord, largeYCord, smallZCord);
		super.addShape(new Square(v1, v2, v3, v4, this.length));

		// LAST SQUARE WITH ALL large X
		v1 = new Vertex(largeXCord, smallYCord, smallZCord);
		v2 = new Vertex(largeXCord, smallYCord, largeZCord);
		v3 = new Vertex(largeXCord, largeYCord, largeZCord);
		v4 = new Vertex(largeXCord, largeYCord, smallZCord);
		super.addShape(new Square(v1, v2, v3, v4, this.length));
	}

}
