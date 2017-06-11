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
    private int zDisplacement;

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

    public int getZDisplacement() {
        return this.zDisplacement;
    }

    public void addZDisplacement(int add) {
        this.zDisplacement += add;
    }

    public Prism clone() {
        if (this.getType() == PrismType.CUBE) {
            int length = ((Cube) this).getRealLength();
            Cube newCube = new Cube(origin, length, color);;
            newCube.setLength(length + this.getZDisplacement());
            return newCube;
        }

        if (this.getType() == PrismType.EQUILATERAL) {
            int length = ((Equilateral) this).getRealLength();
            Equilateral newEquilateral = new Equilateral(origin, length, color);
            newEquilateral.setLength(length + this.getZDisplacement());
            return newEquilateral;
        }

        if (this.getType() == PrismType.PENTAGONAL) {
            Pentagonal pentagonal = (Pentagonal) this;
            int radius = pentagonal.getRealRadius();
            int height = pentagonal.getRealHeight();
            Pentagonal newPentagonal = new Pentagonal(origin, radius, height, color);
            newPentagonal.setRadius(radius + this.getZDisplacement());
            newPentagonal.setHeight(height + this.getZDisplacement());
        }

        return null;
    }

    public abstract void createShapes();

}
