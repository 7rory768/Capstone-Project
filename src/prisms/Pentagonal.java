package prisms;

import java.awt.Color;

import shapes.Pentagon;
import shapes.Rectangle;
import util.PrismType;
import util.Vertex;

public class Pentagonal extends Prism {

	private int radius, height;
	private double sideLength;

	public Pentagonal(int xOrigin, int yOrigin, int radius, int height, Color color) {
		super(PrismType.PENTAGONAL, xOrigin, yOrigin, color);
		this.radius = radius;
		this.height = height;
		this.createShapes();
	}

	public int getRadius() {
		return this.radius;
	}

	public int getHeight() {
		return this.height;
	}

	// 108 angle between each vertex
	public void createShapes() {
		this.sideLength = Math.sin(Math.toRadians(36.0)) * this.radius;                          
		Vertex v1 = this.calculateLeftHorizontalVertex(), v2, v3, v4, v5;
		System.out.println(v1);

		v2 = v1.clone();
		v2.setX(v2.getX() + this.sideLength);
		System.out.println(v2);

		v3 = v2.clone();
		v3.setX(v3.getX() - this.sideLength);
		v3.setY(v3.getY() + Math.tan(18.0) * this.sideLength);
		System.out.println(v3);

		v4 = v3.clone();
		v4.setX(v4.getX() - this.sideLength);
		v4.setY(v4.getY() - Math.tan(36.0) * this.sideLength);

		v5 = v4.clone();
		v5.setX(v5.getX() - this.sideLength);
		v5.setY(v5.getY() - Math.tan(36) * this.sideLength);

		super.addShape(new Pentagon(v1, v2, v3, v4, v5, this.radius, super.getColor()));

		v1 = v1.clone();
		v1.setZ(v1.getZ() - this.height);

		v2 = v2.clone();
		v2.setZ(v1.getZ());

		Rectangle rect = this.getSquare(v1, v2);
		// super.addShape(this.getSquare(v1, v2));
		// System.out.println(rect.getVertex1());
		// System.out.println(rect.getVertex2());
		// System.out.println(rect.getVertex3());
		// System.out.println(rect.getVertex4());

		v3 = v3.clone();
		v3.setZ(v1.getZ());

		// super.addShape(this.getSquare(v2, v3));

		v4 = v4.clone();
		v4.setZ(v1.getZ());

		// super.addShape(this.getSquare(v3, v4));

		v5 = v5.clone();
		v5.setZ(v1.getZ());

		// super.addShape(this.getSquare(v4, v5));
		// super.addShape(this.getSquare(v5, v1));

		// super.addShape(new Pentagon(v1, v2, v3, v4, v5, this.sideLength,
		// super.getColor()));
	}

	public Rectangle getSquare(Vertex v1, Vertex v2) {
		Vertex v3 = v2.clone();
		v3.setZ(v3.getZ() + this.height);
		Vertex v4 = v1.clone();
		v4.setZ(v4.getZ() + this.height);
		return new Rectangle(v1, v2, v3, v4, (int) this.sideLength, this.height,
				super.getColor());
	}

	public Vertex calculateLeftHorizontalVertex() {
		double yDistance = Math.abs((Math.cos(Math.toRadians(36.0)) * Math
				.ceil(this.sideLength / 2.0)));
		System.out.println("R: " + this.radius);
		System.out.println("H: " + yDistance);
		System.out.println("C: " + Math.cos(Math.toRadians(72.0)) * this.radius);
		return new Vertex(0 - Math.floor(this.sideLength / 2.0), yDistance,
				Math.ceil(this.height / 2.0));	
	}

}
