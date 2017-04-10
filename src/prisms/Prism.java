package prisms;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import shapes.Shape;
import util.PrismType;

public abstract class Prism {
	
	private int xOrigin, yOrigin;
	private List<Shape> shapes = new ArrayList<Shape>();
	
	private final PrismType type;
	private final Color color;
	
	public Prism(PrismType type, int xOrigin, int yOrigin, Color color) {
		this.type = type;
		this.xOrigin = xOrigin;
		this.yOrigin = yOrigin;
		this.color = color;
	}
	
	public PrismType getType() {
		return this.type;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public int getxOrigin() {
		return this.xOrigin;
	}
	
	public int getyOrigin() {
		return this.yOrigin;
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
	
	public abstract void createShapes();
}
