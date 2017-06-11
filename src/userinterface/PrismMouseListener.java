package userinterface;

import managers.PrismManager;
import prisms.Cube;
import prisms.Equilateral;
import prisms.Pentagonal;
import prisms.Prism;
import shapes.Shape;
import util.Matrix3;
import util.PrismType;
import util.Vertex;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PrismMouseListener implements MouseListener {

    private final UserInterface userInterface;
    private final PrismManager prismManager;
    private final InterfaceActions interfaceActions;
    private final KeyboardListener keyboardListener;

    private final Timer timer = new Timer();

    private boolean mouseDown = false;
    private TimerTask dragTask = null;
    private int oldScreenX;
    private int oldScreenY;

    public PrismMouseListener(final UserInterface userInterface, final PrismManager prismManager, final InterfaceActions interfaceActions, final KeyboardListener keyboardListener) {
        this.prismManager = prismManager;
        this.userInterface = userInterface;
        this.interfaceActions = interfaceActions;
        this.keyboardListener = keyboardListener;
        this.dragTask = new TimerTask() {
            @Override
            public void run() {
                if (mouseDown) {
                    Prism selectedPrism = prismManager.getSelectedPrism();

                    Point mousePoint = MouseInfo.getPointerInfo().getLocation();
                    int xChange = mousePoint.x - oldScreenX;
                    int yChange = mousePoint.y - oldScreenY;

                    Component selectedComponent = interfaceActions.getSelectedComponent();
                    if (selectedComponent != null && (xChange != 0 || yChange != 0)) {
                        if (selectedComponent.equals(userInterface.getMoveButton())) {
                            if (keyboardListener.holdingCtrl()) {
                                System.out.println();
                                oldScreenX = mousePoint.x;
                                oldScreenY = mousePoint.y;

                                Vertex gridOrigin = userInterface.getGridOrigin();

                                // int oldXPlaces = String.valueOf((int)
                                // Math.abs(gridOrigin.getX())).length();
                                // int oldYPlaces = String.valueOf((int)
                                // Math.abs(gridOrigin.getY())).length();
                                gridOrigin.setX(gridOrigin.getX() + xChange);
                                gridOrigin.setY(gridOrigin.getY() + yChange);
                                // int newXPlaces = String.valueOf((int)
                                // Math.abs(gridOrigin.getX())).length();
                                // int newYPlaces = String.valueOf((int)
                                // Math.abs(gridOrigin.getY())).length();

                                userInterface.getXCordLabel().setText("X: " + (int) gridOrigin.getX());
                                userInterface.getYCordLabel().setText("Y: " + (int) gridOrigin.getY());

								/*
                                 * System.out.println("oldxPlaces: " +
								 * oldXPlaces);
								 * System.out.println("newXPlaces: " +
								 * newXPlaces); System
								 * .out.println("--------------------------------"
								 * );
								 * 
								 * Insets xInsets =
								 * userInterface.getXCordLabel().getInsets();
								 * Insets yInsets =
								 * userInterface.getYCordLabel().getInsets();
								 * userInterface
								 * .getPaintPanel().remove(userInterface
								 * .getXCordLabel());
								 * userInterface.getPaintPanel().remove
								 * (userInterface.getYCordLabel());
								 * 
								 * // INSETS: TOP, LEFT, BOTTOM, RIGHT
								 * GridBagConstraints constraints = new
								 * GridBagConstraints();
								 * 
								 * constraints.fill =
								 * GridBagConstraints.HORIZONTAL; if (oldXPlaces
								 * > newXPlaces) { xInsets.set(xInsets.top,
								 * xInsets.left + -50, xInsets.bottom,
								 * xInsets.right); } else if (oldXPlaces <
								 * newXPlaces) { yInsets.set(yInsets.top,
								 * yInsets.left, yInsets.bottom, yInsets.right +
								 * -50); }
								 * 
								 * constraints.insets = xInsets;
								 * userInterface.getPaintPanel
								 * ().add(userInterface.getXCordLabel(),
								 * constraints);
								 * 
								 * if (oldYPlaces > newYPlaces) {
								 * yInsets.set(yInsets.top, yInsets.left + -50,
								 * yInsets.bottom, yInsets.right); } else if
								 * (oldYPlaces < newYPlaces) {
								 * yInsets.set(yInsets.top, yInsets.left,
								 * yInsets.bottom, yInsets.right + -50); }
								 * 
								 * constraints.gridy = 1; constraints.insets =
								 * yInsets;
								 * userInterface.getPaintPanel().add(userInterface
								 * .getYCordLabel(), constraints);
								 */

                            } else if (selectedPrism != null) {
                                Vertex origin = selectedPrism.getOrigin();
                                if (xChange + origin.getX() < UserInterface.getWidth() / 2 && xChange + origin.getX() >= 0) {
                                    oldScreenX = mousePoint.x;
                                    origin.setX(origin.getX() + xChange);
                                    userInterface.getXOriginField().setText(" " + (int) origin.getX());
                                }
                                if (yChange + origin.getY() < UserInterface.getHeight() && yChange + origin.getY() >= 0) {
                                    oldScreenY = mousePoint.y;
                                    origin.setY(origin.getY() + yChange);
                                    userInterface.getYOriginField().setText(" " + (int) origin.getY());
                                }
                            } else {
                                return;
                            }
                            UserInterface.repaint();
                        } else if (selectedComponent.equals(userInterface.getRotateButton())) {
                            oldScreenX = mousePoint.x;
                            oldScreenY = mousePoint.y;

                            if (keyboardListener.holdingCtrl()) {
                                userInterface.setHeadingValue(userInterface.getHeadingValue() + xChange);
                                userInterface.setPitchValue(userInterface.getPitchValue() + yChange);
                            } else if (selectedPrism != null) {
                                selectedPrism.setHeadingValue(selectedPrism.getHeadingValue() + xChange);
                                selectedPrism.setPitchValue(selectedPrism.getPitchValue() + yChange);
                            } else {
                                return;
                            }

                            UserInterface.repaint();
                        } else if (selectedComponent.equals(userInterface.getResizeButton())) {
                            oldScreenX = mousePoint.x;
                            oldScreenY = mousePoint.y;

                            if (selectedPrism != null) {
                                int averageChange = (xChange - yChange) / 2;
                                if (selectedPrism.getType() == PrismType.CUBE) {
                                    Cube cube = (Cube) selectedPrism;
                                    if (cube.getRealLength() + averageChange >= 10) {
                                        cube.setRealLength(cube.getRealLength() + averageChange);
                                        userInterface.getLengthField().setText("" + (cube.getRealLength() + averageChange));
                                    }
                                } else if (selectedPrism.getType() == PrismType.EQUILATERAL) {
                                    Equilateral equilateral = (Equilateral) selectedPrism;
                                    if (equilateral.getRealLength() + averageChange >= 10) {
                                        equilateral.setRealLength(equilateral.getRealLength() + averageChange);
                                        userInterface.getLengthField().setText("" + (equilateral.getRealLength() + averageChange));
                                    }
                                } else if (selectedPrism.getType() == PrismType.PENTAGONAL) {
                                    Pentagonal pentagonal = (Pentagonal) selectedPrism;
                                    if (pentagonal.getRealRadius() + averageChange >= 10) {
                                        pentagonal.setRadius(pentagonal.getRealRadius() + averageChange);
                                        userInterface.getRadiusField().setText("" + (pentagonal.getRealRadius() + averageChange));
                                    }
                                    if (pentagonal.getRealHeight() + averageChange >= 10) {
                                        pentagonal.setRealHeight(pentagonal.getRealHeight() + averageChange);
                                        userInterface.getHeightField().setText("" + (pentagonal.getRealHeight() + averageChange));
                                    }
                                }

                                UserInterface.repaint();
                            }

                        }
                    }
                }
            }
        };
        this.timer.schedule(this.dragTask, 20, 20);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            this.mouseDown = true;
            this.oldScreenX = e.getXOnScreen();
            this.oldScreenY = e.getYOnScreen();
            Prism selectedPrism = this.prismManager.getSelectedPrism();

            double xGridOrigin = this.userInterface.getGridOrigin().getX();
            double yGridOrigin = this.userInterface.getGridOrigin().getY();

            List<Prism> prisms = this.prismManager.getPrisms();

            if (!prisms.isEmpty()) {
                prismloop:
                for (int prismIndex = prisms.size() - 1; prismIndex >= 0; prismIndex--)   {
                    Prism prism = prisms.get(prismIndex);
                    Matrix3 transform = this.userInterface.getTransform(prism);

                    Vertex origin = prism.getOrigin();
                    double xOrigin = origin.getX();
                    double yOrigin = origin.getY();

                    for (Shape shape : prism.getShapes()) {
                        List<Vertex> vertexList = new ArrayList<Vertex>();

                        for (Vertex vertex : shape.getVertices()) {
                            vertexList.add(transform.transform(vertex));
                        }

                        Path2D path = new Path2D.Double();
                        for (int i = 0; i < vertexList.size(); i++) {
                            Vertex vertex = vertexList.get(i);
                            vertex.setX(vertex.getX() + xOrigin + xGridOrigin);
                            vertex.setY(vertex.getY() + yOrigin + yGridOrigin);
                            if (i == 0) {
                                path.moveTo(vertex.getX(), vertex.getY());
                            } else {
                                path.lineTo(vertex.getX(), vertex.getY());
                            }
                        }
                        path.closePath();

                        if (path.contains(e.getX(), e.getY())) {
                            if (selectedPrism == null || !selectedPrism.equals(prism)) {
                                this.prismManager.setSelectedPrism(prism);
                                this.userInterface.getRemoveButton().setVisible(true);
                                this.interfaceActions.switchToPrismSelected();
                            }
                            break prismloop;
                        }
                    }
                }
            }

            if (selectedPrism != null) {
                UserInterface.repaint();
            }
//            if (!this.keyboardListener.holdingCtrl()) {
//                this.interfaceActions.switchToNoPrismSelected();
//            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            this.mouseDown = false;
        }
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {

    }

    @Override
    public void mouseEntered(MouseEvent arg0) {

    }

    @Override
    public void mouseExited(MouseEvent arg0) {

    }

}
