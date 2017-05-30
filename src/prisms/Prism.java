package prisms;

import shapes.Shape;
import util.PrismType;
import util.Vertex;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Prism {

	private final PrismType type;

	private List<Shape> shapes = new ArrayList<Shape>();
	private Vertex origin;
	private Color color;
	private int headingValue = 180;
	private int pitchValue = 0;

	public Prism(PrismType type, Vertex origin, Color color) {
		this.type = type;
		this.origin = origin;
		this.color = color;
	}

	public PrismType getType() {
		return this.type;
	}

	public Color getColor() {
		return this.color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Vertex getOrigin() {
		return this.origin;
	}

	public void setOrigin(Vertex origin) {
		this.origin = origin;
	}

	public List<Shape> getShapes() {
		return this.shapes;
	}

	public void clearShapes() {
		this.shapes.clear();
	}

	public void addShape(Shape shape) {
		this.shapes.add(shape);
	}

	public void removeShape(Shape shape) {
		this.shapes.remove(shape);
	}

	public void setHeadingValue(int value) {
		this.headingValue = value;
	}

	public int getHeadingValue() {
		return this.headingValue;
	}

	public void setPitchValue(int value) {
		this.pitchValue = value;
	}

	public int getPitchValue() {
		return this.pitchValue;
	}

	public abstract void createShapes();

}
