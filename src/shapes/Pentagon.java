package shapes;

import util.ShapeType;
import util.Vertex;

public class Pentagon extends Shape {

	private Vertex v1;
	private Vertex v2;
	private Vertex v3;
	private Vertex v4;
	private Vertex v5;
	private int radius;

	public Pentagon(Vertex v1, Vertex v2, Vertex v3, Vertex v4, Vertex v5, int radius) {
		super(ShapeType.PENTAGON);
		this.addVertex(v1);
		this.addVertex(v2);
		this.addVertex(v3);
		this.addVertex(v4);
		this.addVertex(v5);
		this.radius = radius;
	}
	
	public int getRadius() {
		return this.radius;
	}

}
