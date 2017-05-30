package shapes;

import util.ShapeType;
import util.Vertex;

public class Rectangle extends Shape {

	private int length;
	private int width;

	public Rectangle(Vertex v1, Vertex v2, Vertex v3, Vertex v4, int length, int width) {
		super(ShapeType.RECTANGLE);
		this.addVertex(v1);
		this.addVertex(v2);
		this.addVertex(v3);
		this.addVertex(v4);
		this.length = length;
		this.width = width;
	}
	
	public int getLength() {
		return this.length;
	}
	
	public int getWidth() {
		return this.width;
	}
}
