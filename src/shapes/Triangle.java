package shapes;

import util.ShapeType;
import util.Vertex;

public class Triangle extends Shape {

	public Triangle (Vertex v1, Vertex v2, Vertex v3) {
		super(ShapeType.TRIANGLE);
		this.addVertex(v1);
		this.addVertex(v2);
		this.addVertex(v3);
	}
}
