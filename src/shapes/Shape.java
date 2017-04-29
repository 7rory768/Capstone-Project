package shapes;

import util.ShapeType;

import java.awt.*;

public class Shape {

    private final ShapeType type;

    private Color color;

    public Shape(ShapeType type) {
        this(type, Color.GREEN);
    }

    public Shape(ShapeType type, Color color) {
        this.type = type;
        this.color = color;
    }

    public ShapeType getType() {
        return this.type;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

}
