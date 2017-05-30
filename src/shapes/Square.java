package shapes;

import util.ShapeType;
import util.Vertex;

public class Square extends Shape {

	private int length;

	public Square(Vertex v1, Vertex v2, Vertex v3, Vertex v4, int length) {
		super(ShapeType.SQUARE);
		this.addVertex(v1);
		this.addVertex(v2);
		this.addVertex(v3);
		this.addVertex(v4);
		this.length = length;
	}

	public int getLength() {
		return this.length;
	}
}
