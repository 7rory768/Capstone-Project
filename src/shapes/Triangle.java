package shapes;

import util.ShapeType;
import util.Vertex;

public class Triangle extends Shape {
	private Vertex v1;
	private Vertex v2;
	private Vertex v3;

	public Triangle (Vertex v1, Vertex v2, Vertex v3) {
		super(ShapeType.TRIANGLE);
		this.addVertex(v1);
		this.addVertex(v2);
		this.addVertex(v3);
	}
}
