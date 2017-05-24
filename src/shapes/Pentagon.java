package shapes;

import util.ShapeType;
import util.Vertex;

import java.awt.*;

public class Pentagon extends Shape {

	private Vertex v1;
	private Vertex v2;
	private Vertex v3;
	private Vertex v4;
	private Vertex v5;
	private int radius;

	public Pentagon(Vertex v1, Vertex v2, Vertex v3, Vertex v4, Vertex v5, int radius) {
		super(ShapeType.PENTAGON);
		this.v1 = v1;
		this.v2 = v2;
		this.v3 = v3;
		this.v4 = v4;
		this.v5 = v5;
		this.radius = radius;
	}

	public void setVertex1(Vertex v1) {
		this.v1 = v1;
	}

	public void setVertex2(Vertex v2) {
		this.v2 = v2;
	}

	public void setVertex3(Vertex v3) {
		this.v3 = v3;
	}

	public void setVertex4(Vertex v4) {
		this.v4 = v4;
	}

	public void setVertex5(Vertex v5) {
		this.v5 = v5;
	}

	public Vertex getVertex1() {
		return this.v1;
	}

	public Vertex getVertex2() {
		return this.v2;
	}

	public Vertex getVertex3() {
		return this.v3;
	}

	public Vertex getVertex4() {
		return this.v4;
	}

	public Vertex getVertex5() {
		return this.v5;
	}
	
	public int getRadius() {
		return this.radius;
	}

}
