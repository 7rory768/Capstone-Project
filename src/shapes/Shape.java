package shapes;

import util.ShapeType;

import java.awt.*;

public class Shape {

    private final ShapeType type;

    public Shape(ShapeType type) {
        this.type = type;
    }

    public ShapeType getType() {
        return this.type;
    }

}
