package shapes;

import java.awt.Color;

import util.Vertex;

public class Square {
	public Vertex v1;
	public Vertex v2;
	public Vertex v3;
	public Vertex v4;
	int length;
	public Color color;

	public Square(Vertex v1, Vertex v2, Vertex v3, Vertex v4, int length, Color color) {
		this.v1 = v1;
		this.v2 = v2;
		this.v3 = v3;
		this.v4 = v4;
		this.length = length;
		this.color = color;
	}
}
