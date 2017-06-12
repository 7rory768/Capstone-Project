package userinterface;

import managers.PrismManager;
import prisms.Cube;
import prisms.Equilateral;
import prisms.Pentagonal;
import prisms.Prism;
import util.PrismType;
import util.Vertex;

import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * Created by Rory on 6/10/2017.
 */
public class ScrollListener implements MouseWheelListener {

    private final UserInterface userInterface;
    private final InterfaceActions interfaceActions;
    private final KeyboardListener keyboardListener;
    private final PrismManager prismManager;

    public ScrollListener(UserInterface userInterface, InterfaceActions interfaceActions, KeyboardListener keyboardListener, PrismManager prismManager) {
        this.userInterface = userInterface;
        this.interfaceActions = interfaceActions;
        this.keyboardListener = keyboardListener;
        this.prismManager = prismManager;
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        int scrollAmount = e.getWheelRotation() * e.getScrollAmount();
        Prism selectedPrism = this.prismManager.getSelectedPrism();
        Component selectedComponent = this.interfaceActions.getSelectedComponent();

        if (scrollAmount != 0) {
            if (this.keyboardListener.holdingCtrl()) {
                scrollAmount *= -1;
                UserInterface.changeZoonFactor(scrollAmount);
                for (Prism prism : this.prismManager.getPrisms()) {
                    if (prism.getType() == PrismType.CUBE) {
                        Cube cube = (Cube) prism;
                        cube.setLength(cube.getLength() + scrollAmount);
                    } else if (prism.getType() == PrismType.EQUILATERAL) {
                        Equilateral equilateral = (Equilateral) prism;
                        equilateral.setLength(equilateral.getLength() + scrollAmount);
                    } else if (prism.getType() == PrismType.PENTAGONAL) {
                        Pentagonal pentagonal = (Pentagonal) prism;
                        pentagonal.setHeight(pentagonal.getHeight() + scrollAmount);
                        pentagonal.setRadius(pentagonal.getRadius() + scrollAmount);
                    }
                }
                UserInterface.repaint();
            } else if (selectedPrism != null && selectedComponent != null) {
                if (selectedComponent.equals(this.userInterface.getResizeButton())) {
                    if (selectedPrism.getType() == PrismType.CUBE) {
                        Cube cube = (Cube) selectedPrism;
                        if (cube.getRealLength() - scrollAmount >= 10) {
                            cube.setRealLength(cube.getRealLength() - scrollAmount);
                            this.userInterface.getLengthField().setText("" + (cube.getRealLength() - scrollAmount));
                        }
                    } else if (selectedPrism.getType() == PrismType.EQUILATERAL) {
                        Equilateral equilateral = (Equilateral) selectedPrism;
                        if (equilateral.getRealLength() - scrollAmount >= 10) {
                            equilateral.setRealLength(equilateral.getRealLength() - scrollAmount);
                            this.userInterface.getLengthField().setText("" + (equilateral.getRealLength() - scrollAmount));
                        }
                    } else if (selectedPrism.getType() == PrismType.PENTAGONAL) {
                        Pentagonal pentagonal = (Pentagonal) selectedPrism;
                        if (pentagonal.getRealRadius() - scrollAmount >= 10) {
                            this.userInterface.getRadiusField().setText("" + (pentagonal.getRealRadius() - scrollAmount));
                        }
                        if (pentagonal.getRealHeight() - scrollAmount >= 10) {
                            pentagonal.setRealHeight(pentagonal.getRealHeight() - scrollAmount);
                            this.userInterface.getHeightField().setText("" + (pentagonal.getRealHeight() - scrollAmount));
                        }
                    }

                } else if (selectedComponent.equals(this.userInterface.getMoveButton())) {
                    Vertex origin = selectedPrism.getOrigin();
                    origin.setZ(origin.getZ() + scrollAmount);
                    selectedPrism.addZDisplacement(scrollAmount);
                    if (selectedPrism.getType() == PrismType.CUBE) {
                        Cube cube = (Cube) selectedPrism;
                        cube.setRealLength(cube.getRealLength());
                    } else if (selectedPrism.getType() == PrismType.EQUILATERAL) {
                        Equilateral equilateral = (Equilateral) selectedPrism;
                        equilateral.setRealLength(equilateral.getRealLength());
                    } else if (selectedPrism.getType() == PrismType.PENTAGONAL) {
                        Pentagonal pentagonal = (Pentagonal) selectedPrism;
                        pentagonal.setRealRadius(pentagonal.getRealRadius());
                        pentagonal.setRealHeight(pentagonal.getRealHeight());
                    }
                }
                UserInterface.repaint();
            }
        }
    }

}
