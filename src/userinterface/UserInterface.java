package userinterface;

import managers.PrismManager;
import prisms.Cube;
import prisms.Equilateral;
import prisms.Pentagonal;
import prisms.Prism;
import shapes.Pentagon;
import shapes.Rectangle;
import shapes.Shape;
import shapes.Triangle;
import util.Matrix3;
import util.PrismType;
import util.ShapeType;
import util.Vertex;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;

public class UserInterface {

    private final InterfaceActions interfaceActions;
    private final PrismManager prismManager;

    private static JPanel jPanel;

    private JFrame frame;
    private Container pane;
    private JSlider pitchSlider;
    private JSlider headingSlider;
    private Matrix3 transform;

    public UserInterface(InterfaceActions interfaceActions, PrismManager prismManager) {
        this.interfaceActions = interfaceActions;
        this.prismManager = prismManager;
    }

    public void setupInterface() {
        this.setupButtons();
        this.setupPane();
    }

    private void setupButtons() {
        this.headingSlider = new JSlider(0, 360, 180);
        this.pitchSlider = new JSlider(SwingConstants.VERTICAL, -90, 90, 0);
    }

    public static void repaint() {
        UserInterface.jPanel.repaint();
    }

    private void setupPane() {
        this.frame = new JFrame();
        this.frame.setSize(750, 750);
        this.pane = this.frame.getContentPane();
        this.pane.setLayout(new BorderLayout());

        this.pane.add(this.headingSlider, BorderLayout.SOUTH);
        this.pane.add(this.pitchSlider, BorderLayout.EAST);

        UserInterface.jPanel = new JPanel() {

            public void paintComponent(Graphics g) {

                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.BLACK);
                g2.fillRect(0, 0, getWidth(), getHeight());

                g2.setColor(Color.WHITE);

                double heading = Math.toRadians(headingSlider.getValue());
                Matrix3 headingTransform = new Matrix3(new double[]{Math.cos(heading),
                        0, Math.sin(heading), 0, 1, 0, -Math.sin(heading), 0,
                        Math.cos(heading)});

                double pitch = Math.toRadians(pitchSlider.getValue());
                Matrix3 pitchTransform = new Matrix3(new double[]{1, 0, 0, 0,
                        Math.cos(pitch), Math.sin(pitch), 0, -Math.sin(pitch),
                        Math.cos(pitch)});

                transform = headingTransform.multiply(pitchTransform);

                this.createPrisms(g2);

                // g2.translate(getWidth() / 2, getHeight() / 2);

                headingSlider.addChangeListener(interfaceActions.getHeadingChangeListener());
                pitchSlider.addChangeListener(interfaceActions.getPitchChangeListener());

            }

            public void createPrisms(Graphics2D g2) {
                for (Prism prism : prismManager.getPrisms()) {

                    double xOrigin = prism.getOrigin().getX();
                    double yOrigin = prism.getOrigin().getY();
                    if (prism.getType() == PrismType.CUBE) {
                        Cube cube = (Cube) prism;

                        // double[] zBuffer = new double[img.getWidth() *
                        // img.getHeight()];
                        //
                        // for (int q = 0; q < zBuffer.length; q++) {
                        // zBuffer[q] = Double.NEGATIVE_INFINITY;
                        // }

                        BufferedImage img = new BufferedImage(getWidth(), getHeight(),
                                BufferedImage.TYPE_INT_ARGB);

                        int num = 0;
                        for (Shape shape : cube.getShapes()) {
                            Rectangle sq = (Rectangle) shape;
                            num++;

                            if (num == 1) {
                                System.out.println("BEFORE VERTEX MODIFICATION:");
                                System.out.println("V1: (" + sq.getVertex1().getX()
                                        + ", " + sq.getVertex1().getY() + ")");
                                System.out.println("V2: (" + sq.getVertex2().getX()
                                        + ", " + sq.getVertex2().getY() + ")");
                                System.out.println("V3: (" + sq.getVertex3().getX()
                                        + ", " + sq.getVertex3().getY() + ")");
                                System.out
                                        .println("-------------------------------------------");
                            }

                            Vertex v1 = transform.transform(sq.getVertex1());
                            Vertex v2 = transform.transform(sq.getVertex2());
                            Vertex v3 = transform.transform(sq.getVertex3());
                            Vertex v4 = transform.transform(sq.getVertex4());

                            if (num == 1) {
                                System.out.println("AFTER VERTEX TRANSFORMATION:");
                                System.out.println("V1: (" + v1.getX() + ", " + v1.getY()
                                        + ")");
                                System.out.println("V2: (" + v2.getX() + ", " + v2.getY()
                                        + ")");
                                System.out.println("V3: (" + v3.getX() + ", " + v3.getY()
                                        + ")");
                                System.out
                                        .println("-------------------------------------------");
                            }

                            v1.setX(v1.getX() + xOrigin);
                            v1.setY(v1.getY() + yOrigin);
                            v2.setX(v2.getX() + xOrigin);
                            v2.setY(v2.getY() + yOrigin);
                            v3.setX(v3.getX() + xOrigin);
                            v3.setY(v3.getY() + yOrigin);
                            v4.setX(v4.getX() + xOrigin);
                            v4.setY(v4.getY() + yOrigin);

                            Path2D path = new Path2D.Double();
                            path.moveTo(v1.getX(), v1.getY());
                            path.lineTo(v2.getX(), v2.getY());
                            path.lineTo(v3.getX(), v3.getY());
                            path.lineTo(v4.getX(), v4.getY());
                            path.closePath();
                            g2.draw(path);

                            // compute rectangular bounds for square
                            int minX = (int) Math.max(
                                    0,
                                    Math.ceil(Math.min(
                                            v1.getX(),
                                            Math.min(v2.getX(),
                                                    Math.min(v3.getX(), v4.getX())))));
                            int maxX = (int) Math.min(
                                    img.getWidth() - 1,
                                    Math.floor(Math.max(
                                            v1.getX(),
                                            Math.max(v2.getX(),
                                                    Math.max(v3.getX(), v4.getX())))));
                            int minY = (int) Math.max(
                                    0,
                                    Math.ceil(Math.min(
                                            v1.getY(),
                                            Math.min(v2.getY(),
                                                    Math.min(v3.getY(), v4.getY())))));
                            int maxY = (int) Math.min(
                                    img.getHeight() - 1,
                                    Math.floor(Math.max(
                                            v1.getY(),
                                            Math.max(v2.getY(),
                                                    Math.max(v3.getY(), v4.getY())))));

                            double squareArea = (maxX - minX) * (maxY - minY);
                            // System.out.println("actual area: " + squareArea);
                            // squareArea = ((v1.getY() - v3.getY()) *
                            // (v2.getX() - v3.getX()) +
                            // (v2.getY() - v3.getY())
                            // * (v3.getX() - v1.getX()));
                            // System.out.println("area calc: " + squareArea);

                            if (num == 1) {
                                System.out.println("AFTER ALL VERTEX MODIFICATION:");
                                System.out.println("V1: (" + v1.getX() + ", " + v1.getY()
                                        + ")");
                                System.out.println("V2: (" + v2.getX() + ", " + v2.getY()
                                        + ")");
                                System.out.println("V3: (" + v3.getX() + ", " + v3.getY()
                                        + ")");
                                System.out
                                        .println("-------------------------------------------");
                            }

                            if (num == 1) {
                                System.out.println("Min X: " + minX);
                                System.out.println("Max X: " + maxX);
                                System.out.println("Min Y: " + minY);
                                System.out.println("Max Y: " + maxY);
                            }

                            for (int y = minY; y <= maxY; y++) {
                                for (int x = minX; x <= maxX; x++) {
                                    double b1 = ((y - v3.getY())
                                            * (v2.getX() - v3.getX()) + (v2.getY() - v3
                                            .getY()) * (v3.getX() - x))
                                            / squareArea;
                                    double b2 = ((y - v1.getY())
                                            * (v3.getX() - v1.getX()) + (v3.getY() - v1
                                            .getY()) * (v1.getX() - x))
                                            / squareArea;
                                    // double b1 = (maxX - x) * (maxY - y)
                                    // / squareArea;
                                    // double b2 = (x - minX) * (y - minY)
                                    // / squareArea;
                                    // double b3 = ((y - v2.getY()) * (v1.getX()
                                    // - v2.getX()) +
                                    // (v1.getY() - v2.getY())
                                    // * (v2.getX() - x))
                                    // / squareArea;
                                    // double b4 = ((y - v4.getY()) * (v3.getX()
                                    // - v4.getX()) +
                                    // (v3.getY() - v4.getY())
                                    // * (v4.getX() - x));

                                    // double b5 = ((y -
                                    // v3.getY()) * (v4.getX() -
                                    // v3.getX()) +
                                    // (v4.getY() - v3.getY())
                                    // * (v3.getX() - x));
                                    // double b6 = ((y - v2.getY()) * (v4.getX()
                                    // - v2.getX()) +
                                    // (v4.getY() - v2.getY())
                                    // * (v2.getX() - x));

                                    if (x < minX + 1 && y < minY + 1 && num == 1) {
                                        System.out.println("B1: " + b1);
                                        System.out.println("B2: " + b2);
                                        // System.out.println("B3: " + b3);
                                    }

                                    if (b1 >= 0 && b1 <= 1 && b2 >= 0 && b2 <= 1) { // &&
                                        // b3
                                        // >=
                                        // 0
                                        // && b3 <= 1 && b4 <= 1 && b4 >= 0 &&
                                        // b5 <= 1 && b5 >= 0
                                        // && b6 <= 1 && b6 >= 0) {
                                        // img.setRGB(x, y,
                                        // sq.getColor().getRGB());
                                    }
                                }
                            }
                        }

                        g2.drawImage(img, 0, 0, null);

                    } else if (prism.getType() == PrismType.EQUILATERAL) {
                        Equilateral equilateral = (Equilateral) prism;

                        // g2.translate(getWidth() / 2, getHeight() / 2);
                        BufferedImage img = new BufferedImage(getWidth(), getHeight(),
                                BufferedImage.TYPE_INT_ARGB);

                        double[] zBuffer = new double[img.getWidth() * img.getHeight()];

                        for (int q = 0; q < zBuffer.length; q++) {
                            zBuffer[q] = Double.NEGATIVE_INFINITY;
                        }

                        int num = 0;
                        for (Shape shape : equilateral.getShapes()) {
                            Triangle t = (Triangle) shape;
                            num++;

                            if (num == 1) {
                                System.out.println("BEFORE VERTEX MODIFICATION:");
                                System.out.println("V1: (" + t.getVertex1().getX() + ", "
                                        + t.getVertex1().getY() + ")");
                                System.out.println("V2: (" + t.getVertex2().getX() + ", "
                                        + t.getVertex2().getY() + ")");
                                System.out.println("V3: (" + t.getVertex3().getX() + ", "
                                        + t.getVertex3().getY() + ")");
                                System.out
                                        .println("-------------------------------------------");
                            }

                            Vertex v1 = transform.transform(t.getVertex1());
                            Vertex v2 = transform.transform(t.getVertex2());
                            Vertex v3 = transform.transform(t.getVertex3());

                            if (num == 1) {
                                System.out.println("AFTER VERTEX TRANSFORMATION:");
                                System.out.println("V1: (" + v1.getX() + ", " + v1.getY()
                                        + ")");
                                System.out.println("V2: (" + v2.getX() + ", " + v2.getY()
                                        + ")");
                                System.out.println("V3: (" + v3.getX() + ", " + v3.getY()
                                        + ")");
                                System.out
                                        .println("-------------------------------------------");
                            }

                            v1.setX(v1.getX() + getWidth() / 2);
                            v1.setY(v1.getY() + getHeight() / 2);
                            v2.setX(v2.getX() + getWidth() / 2);
                            v2.setY(v2.getY() + getHeight() / 2);
                            v3.setX(v3.getX() + getWidth() / 2);
                            v3.setY(v3.getY() + getHeight() / 2);

                            Vertex ab = new Vertex(v2.getX() - v1.getX(), v2.getY()
                                    - v1.getY(), v2.getZ() - v1.getZ());
                            Vertex ac = new Vertex(v3.getX() - v1.getX(), v3.getY()
                                    - v1.getY(), v3.getZ() - v1.getZ());
                            Vertex norm = new Vertex(ab.getY() * ac.getZ() - ab.getZ()
                                    * ac.getY(), ab.getZ() * ac.getX() - ab.getX()
                                    * ac.getZ(), ab.getX() * ac.getY() - ab.getY()
                                    * ac.getX());

                            double normalLength = Math.sqrt(norm.getX() * norm.getX()
                                    + norm.getY() + norm.getZ() * norm.getZ());
                            norm.setX(norm.getX() / normalLength);
                            norm.setY(norm.getY() / normalLength);
                            norm.setZ(norm.getZ() / normalLength);

                            double angleCos = Math.abs(norm.getZ());

                            Path2D path = new Path2D.Double();
                            path.moveTo(v1.getX(), v1.getY());
                            path.lineTo(v2.getX(), v2.getY());
                            path.lineTo(v3.getX(), v3.getY());
                            path.closePath();
                            g2.draw(path);

                            // since we are not using Graphics2D anymore,
                            // we have to do translation manually

                            // compute rectangular bounds for triangle
                            int minX = (int) Math.max(
                                    0,
                                    Math.ceil(Math.min(v1.getX(),
                                            Math.min(v2.getX(), v3.getX()))));
                            int maxX = (int) Math.min(
                                    img.getWidth() - 1,
                                    Math.floor(Math.max(v1.getX(),
                                            Math.max(v2.getX(), v3.getX()))));
                            int minY = (int) Math.max(
                                    0,
                                    Math.ceil(Math.min(v1.getY(),
                                            Math.min(v2.getY(), v3.getY()))));
                            int maxY = (int) Math.min(
                                    img.getHeight() - 1,
                                    Math.floor(Math.max(v1.getY(),
                                            Math.max(v2.getY(), v3.getY()))));

                            double triangleArea = (v1.getY() - v3.getY())
                                    * (v2.getX() - v3.getX()) + (v2.getY() - v3.getY())
                                    * (v3.getX() - v1.getX());

                            if (num == 1) {
                                System.out.println("AFTER ALL VERTEX MODIFICATION:");
                                System.out.println("V1: (" + v1.getX() + ", " + v1.getY()
                                        + ")");
                                System.out.println("V2: (" + v2.getX() + ", " + v2.getY()
                                        + ")");
                                System.out.println("V3: (" + v3.getX() + ", " + v3.getY()
                                        + ")");
                                System.out
                                        .println("-------------------------------------------");
                            }

                            if (num == 1) {
                                System.out.println("Min X: " + minX);
                                System.out.println("Max X: " + maxX);
                                System.out.println("Min Y: " + minY);
                                System.out.println("Max Y: " + maxY);
                            }

                            for (int y = minY; y <= maxY; y++) {
                                for (int x = minX; x <= maxX; x++) {
                                    double b1 = ((y - v3.getY())
                                            * (v2.getX() - v3.getX()) + (v2.getY() - v3
                                            .getY()) * (v3.getX() - x))
                                            / triangleArea;
                                    double b2 = ((y - v1.getY())
                                            * (v3.getX() - v1.getX()) + (v3.getY() - v1
                                            .getY()) * (v1.getX() - x))
                                            / triangleArea;
                                    double b3 = ((y - v2.getY())
                                            * (v1.getX() - v2.getX()) + (v1.getY() - v2
                                            .getY()) * (v2.getX() - x))
                                            / triangleArea;
                                    if (x == minX && y == minY && num == 1) {
                                        System.out.println("B1: " + b1);
                                        System.out.println("B2: " + b2);
                                        System.out.println("B3: " + b3);
                                    }
                                    if (b1 >= 0 && b1 <= 1 && b2 >= 0 && b2 <= 1
                                            && b3 >= 0 && b3 <= 1) {
                                        double depth = b1 + v1.getZ() + b2 * v2.getZ()
                                                + b3 * v3.getZ();
                                        int zIndex = y * img.getWidth() + x;
                                        if (zBuffer[zIndex] < depth) {
                                            img.setRGB(x, y,
                                                    getShade(t.getColor(), angleCos)
                                                            .getRGB());
                                            zBuffer[zIndex] = depth;
                                        }
                                    }
                                }
                            }

                            g2.drawImage(img, 0, 0, null);
                        }
                    } else if (prism.getType() == PrismType.PENTAGONAL) {
                        Pentagonal pentagonal = (Pentagonal) prism;

                        // double[] zBuffer = new double[img.getWidth() *
                        // img.getHeight()];
                        //
                        // for (int q = 0; q < zBuffer.length; q++) {
                        // zBuffer[q] = Double.NEGATIVE_INFINITY;
                        // }

                        BufferedImage img = new BufferedImage(getWidth(), getHeight(),
                                BufferedImage.TYPE_INT_ARGB);

                        int num = 0;
                        for (Shape shape : pentagonal.getShapes()) {
                            if (shape.getType() == ShapeType.RECTANGLE) {
                                Rectangle sq = (Rectangle) shape;
                                num++;

                                if (num == 1) {
                                    System.out.println("BEFORE VERTEX MODIFICATION:");
                                    System.out.println("V1: (" + sq.getVertex1().getX()
                                            + ", " + sq.getVertex1().getY() + ")");
                                    System.out.println("V2: (" + sq.getVertex2().getX()
                                            + ", " + sq.getVertex2().getY() + ")");
                                    System.out.println("V3: (" + sq.getVertex3().getX()
                                            + ", " + sq.getVertex3().getY() + ")");
                                    System.out
                                            .println("-------------------------------------------");
                                }

                                Vertex v1 = transform.transform(sq.getVertex1());
                                Vertex v2 = transform.transform(sq.getVertex2());
                                Vertex v3 = transform.transform(sq.getVertex3());
                                Vertex v4 = transform.transform(sq.getVertex4());

                                if (num == 1) {
                                    System.out.println("AFTER VERTEX TRANSFORMATION:");
                                    System.out.println("V1: (" + v1.getX() + ", "
                                            + v1.getY() + ")");
                                    System.out.println("V2: (" + v2.getX() + ", "
                                            + v2.getY() + ")");
                                    System.out.println("V3: (" + v3.getX() + ", "
                                            + v3.getY() + ")");
                                    System.out
                                            .println("-------------------------------------------");
                                }

                                v1.setX(v1.getX() + xOrigin);
                                v1.setY(v1.getY() + yOrigin);
                                v2.setX(v2.getX() + xOrigin);
                                v2.setY(v2.getY() + yOrigin);
                                v3.setX(v3.getX() + xOrigin);
                                v3.setY(v3.getY() + yOrigin);
                                v4.setX(v4.getX() + xOrigin);
                                v4.setY(v4.getY() + yOrigin);

                                Path2D path = new Path2D.Double();
                                path.moveTo(v1.getX(), v1.getY());
                                path.lineTo(v2.getX(), v2.getY());
                                path.lineTo(v3.getX(), v3.getY());
                                path.lineTo(v4.getX(), v4.getY());
                                path.closePath();
                                g2.draw(path);

                                // compute rectangular bounds for square
                                int minX = (int) Math
                                        .max(0,
                                                Math.ceil(Math.min(
                                                        v1.getX(),
                                                        Math.min(
                                                                v2.getX(),
                                                                Math.min(v3.getX(),
                                                                        v4.getX())))));
                                int maxX = (int) Math
                                        .min(img.getWidth() - 1,
                                                Math.floor(Math.max(
                                                        v1.getX(),
                                                        Math.max(
                                                                v2.getX(),
                                                                Math.max(v3.getX(),
                                                                        v4.getX())))));
                                int minY = (int) Math
                                        .max(0,
                                                Math.ceil(Math.min(
                                                        v1.getY(),
                                                        Math.min(
                                                                v2.getY(),
                                                                Math.min(v3.getY(),
                                                                        v4.getY())))));
                                int maxY = (int) Math
                                        .min(img.getHeight() - 1,
                                                Math.floor(Math.max(
                                                        v1.getY(),
                                                        Math.max(
                                                                v2.getY(),
                                                                Math.max(v3.getY(),
                                                                        v4.getY())))));

                                double squareArea = (maxX - minX) * (maxY - minY);
                                // System.out.println("actual area: " +
                                // squareArea);
                                // squareArea = ((v1.getY() - v3.getY()) *
                                // (v2.getX() - v3.getX()) +
                                // (v2.getY() - v3.getY())
                                // * (v3.getX() - v1.getX()));
                                // System.out.println("area calc: " +
                                // squareArea);

                                if (num == 1) {
                                    System.out.println("AFTER ALL VERTEX MODIFICATION:");
                                    System.out.println("V1: (" + v1.getX() + ", "
                                            + v1.getY() + ")");
                                    System.out.println("V2: (" + v2.getX() + ", "
                                            + v2.getY() + ")");
                                    System.out.println("V3: (" + v3.getX() + ", "
                                            + v3.getY() + ")");
                                    System.out
                                            .println("-------------------------------------------");
                                }

                                if (num == 1) {
                                    System.out.println("Min X: " + minX);
                                    System.out.println("Max X: " + maxX);
                                    System.out.println("Min Y: " + minY);
                                    System.out.println("Max Y: " + maxY);
                                }

                                for (int y = minY; y <= maxY; y++) {
                                    for (int x = minX; x <= maxX; x++) {
                                        double b1 = ((y - v3.getY())
                                                * (v2.getX() - v3.getX()) + (v2.getY() - v3
                                                .getY()) * (v3.getX() - x))
                                                / squareArea;
                                        double b2 = ((y - v1.getY())
                                                * (v3.getX() - v1.getX()) + (v3.getY() - v1
                                                .getY()) * (v1.getX() - x))
                                                / squareArea;
                                        // double b1 = (maxX - x) * (maxY - y)
                                        // / squareArea;
                                        // double b2 = (x - minX) * (y - minY)
                                        // / squareArea;
                                        // double b3 = ((y - v2.getY()) *
                                        // (v1.getX() - v2.getX()) +
                                        // (v1.getY() - v2.getY())
                                        // * (v2.getX() - x))
                                        // / squareArea;
                                        // double b4 = ((y - v4.getY()) *
                                        // (v3.getX() - v4.getX()) +
                                        // (v3.getY() - v4.getY())
                                        // * (v4.getX() - x));

                                        // double b5 = ((y -
                                        // v3.getY()) * (v4.getX() -
                                        // v3.getX()) +
                                        // (v4.getY() - v3.getY())
                                        // * (v3.getX() - x));
                                        // double b6 = ((y - v2.getY()) *
                                        // (v4.getX() - v2.getX()) +
                                        // (v4.getY() - v2.getY())
                                        // * (v2.getX() - x));

                                        if (x < minX + 1 && y < minY + 1 && num == 1) {
                                            System.out.println("B1: " + b1);
                                            System.out.println("B2: " + b2);
                                            // System.out.println("B3: " + b3);
                                        }

                                        if (b1 >= 0 && b1 <= 1 && b2 >= 0 && b2 <= 1) { // &&
                                            // b3
                                            // >=
                                            // 0
                                            // && b3 <= 1 && b4 <= 1 && b4 >= 0
                                            // &&
                                            // b5 <= 1 && b5 >= 0
                                            // && b6 <= 1 && b6 >= 0) {
                                            // img.setRGB(x, y,
                                            // sq.getColor().getRGB());
                                        }
                                    }
                                }
                            }

                            if (shape.getType() == ShapeType.PENTAGON) {
                                Pentagon pentagon = (Pentagon) shape;

                                Vertex v1 = transform.transform(pentagon.getVertex1());
                                Vertex v2 = transform.transform(pentagon.getVertex2());
                                Vertex v3 = transform.transform(pentagon.getVertex3());
                                Vertex v4 = transform.transform(pentagon.getVertex4());

                                v1.setX(v1.getX() + xOrigin);
                                v1.setY(v1.getY() + yOrigin);
                                v2.setX(v2.getX() + xOrigin);
                                v2.setY(v2.getY() + yOrigin);
                                v3.setX(v3.getX() + xOrigin);
                                v3.setY(v3.getY() + yOrigin);
                                v4.setX(v4.getX() + xOrigin);
                                v4.setY(v4.getY() + yOrigin);

                                Path2D path = new Path2D.Double();
                                path.moveTo(v1.getX(), v1.getY());
                                path.lineTo(v2.getX(), v2.getY());
                                path.lineTo(v3.getX(), v3.getY());
                                path.lineTo(v4.getX(), v4.getY());
                                path.closePath();
                                g2.draw(path);

                            }

                            g2.drawImage(img, 0, 0, null);
                        }
                    }

                }
            }
        };

        pane.add(UserInterface.jPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private static Color getShade(Color color, double shade) {
        double redLinear = Math.pow(color.getRed(), 2.4) * shade;
        double greenLinear = Math.pow(color.getGreen(), 2.4) * shade;
        double blueLinear = Math.pow(color.getBlue(), 2.4) * shade;

        int red = (int) Math.pow(redLinear, 1 / 2.4);
        int green = (int) Math.pow(greenLinear, 1 / 2.4);
        if (green > 255) {
            green = 255;
        }
        int blue = (int) Math.pow(blueLinear, 1 / 2.4);
        return new Color(red, green, blue);
    }

}
