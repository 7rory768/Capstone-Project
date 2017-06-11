package prisms;

import shapes.Triangle;
import util.PrismType;
import util.Vertex;

import java.awt.*;

public class Equilateral extends Prism {

    private int length, realLength;

    public Equilateral(Vertex origin, int length, Color color) {
        super(PrismType.EQUILATERAL, origin, color);
        this.length = length;
        this.realLength = length;
        this.createShapes();
    }

    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
        this.createShapes();
    }

    public int getRealLength() {
        return this.realLength;
    }

    public void setRealLength(int realLength) {
        this.realLength = realLength;
        this.setLength(realLength + this.getZDisplacement());
        this.createShapes();
    }

    public void createShapes() {
        super.clearShapes();

        if (this.length > 1) {
            int biggerHalf = (int) Math.ceil((double) this.length / 2.0);
            int smallHalf = (int) Math.floor((double) this.length / 2.0);
            int largeXCord = biggerHalf;
            int largeYCord = biggerHalf;
            int largeZCord = biggerHalf;
            int smallXCord = -smallHalf;
            int smallYCord = -smallHalf;
            int smallZCord = -smallHalf;

            Vertex v1, v2, v3;

            //TRAINGLE 1
            v1 = new Vertex(largeXCord, largeYCord, largeZCord);
            v2 = new Vertex(smallXCord, smallYCord, largeZCord);
            v3 = new Vertex(smallXCord, largeYCord, smallZCord);
            super.addShape(new Triangle(v1, v2, v3));

            // TRAINGLE 2
            v3 = new Vertex(largeXCord, smallYCord, smallZCord);
            super.addShape(new Triangle(v1, v2, v3));

            //TRIANGLE 3
            v2 = v3;
            v3 = v1;
            v1 = new Vertex(smallXCord, largeYCord, smallZCord);
            super.addShape(new Triangle(v1, v2, v3));

            //TRIANGLE 4
            v2 = new Vertex(smallXCord, smallYCord, largeZCord);
            v3 = new Vertex(largeXCord, smallYCord, smallZCord);
            super.addShape(new Triangle(v1, v2, v3));
        }
    }

}
