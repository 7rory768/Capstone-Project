package shapes;

import util.ShapeType;
import util.Vertex;

import java.util.ArrayList;
import java.util.List;

public class Shape {

    private List<Vertex> vertices = new ArrayList();
    private final ShapeType type;

    public Shape(ShapeType type) {
        this.type = type;
    }

    public ShapeType getType() {
        return this.type;
    }

    public void addVertex(Vertex vertex) {
        this.vertices.add(vertex);
    }

    public List<Vertex> getVertices() {
        return this.vertices;
    };

}
