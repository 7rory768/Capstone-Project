package prisms;

import shapes.Pentagon;
import shapes.Rectangle;
import util.PrismType;
import util.Vertex;

import java.awt.*;

public class Pentagonal extends Prism {

	private int radius, height;
	private double sideLength;

	public Pentagonal(Vertex origin, int radius, int height, Color color) {
		super(PrismType.PENTAGONAL, origin, color);
		this.radius = radius;
		this.height = height;
		this.createShapes();
	}

	public int getRadius() {
		return this.radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
		createShapes();
	}

	public int getHeight() {
		return this.height;
	}

	public void setHeight(int height) {
		this.height = height;
		createShapes();
	}

	// 108 angle between each vertex
	public void createShapes() {
		super.clearShapes();

		System.out.println("Radius: " + this.radius);

		System.out.println(Math.sin(36.0));
		System.out.println(Math.sin(Math.toRadians(36.0)));
		this.sideLength = Math.sin(Math.toRadians(36.0)) * this.radius * 2;
		System.out.println("L: " + this.sideLength);

		double h = Math.cos(Math.toRadians(36.0)) * this.radius;
		System.out.println("H: " + h);
		double c = Math.cos(Math.toRadians(72.0)) * this.radius;
		System.out.println("C: " + c);

		Vertex v1 = new Vertex(-this.sideLength / 2.0, h, Math.ceil(this.height / 2.0)), v2, v3, v4, v5;
		System.out.println(v1);

		v2 = v1.clone();
		v2.setX(v2.getX() + this.sideLength);
		System.out.println(v2);

		v3 = v2.clone();
		v3.setX(0 + Math.sin(Math.toRadians(72.0)) * this.radius);
		v3.setY(0 - c);
		System.out.println(v3);

		v4 = v1.clone();
		v4.setX(0);
		v4.setY(-h);
		System.out.println(v4);

		v5 = v1.clone();
		v5.setX(0 - Math.sin(Math.toRadians(72.0)) * this.radius);
		v5.setY(0 - c);
		System.out.println(v5);

		super.addShape(new Pentagon(v1, v2, v3, v4, v5, this.radius, super.getColor()));

		v1 = v1.clone();
		v1.setZ(v1.getZ() - this.height);

		v2 = v2.clone();
		v2.setZ(v1.getZ());

		Rectangle rect = this.getSquare(v1, v2);
		super.addShape(this.getSquare(v1, v2));
		System.out.println(rect.getVertex1());
		System.out.println(rect.getVertex2());
		System.out.println(rect.getVertex3());
		System.out.println(rect.getVertex4());

		v3 = v3.clone();
		v3.setZ(v1.getZ());

		super.addShape(this.getSquare(v2, v3));

		v4 = v4.clone();
		v4.setZ(v1.getZ());

		super.addShape(this.getSquare(v3, v4));

		v5 = v5.clone();
		v5.setZ(v1.getZ());

		super.addShape(this.getSquare(v4, v5));
		super.addShape(this.getSquare(v5, v1));

		super.addShape(new Pentagon(v1, v2, v3, v4, v5, this.radius, super.getColor()));
	}

	public Rectangle getSquare(Vertex v1, Vertex v2) {
		Vertex v3 = v2.clone();
		v3.setZ(v3.getZ() + this.height);
		Vertex v4 = v1.clone();
		v4.setZ(v4.getZ() + this.height);
		return new Rectangle(v1, v2, v3, v4, (int) this.sideLength, this.height,
				super.getColor());
	}

}
