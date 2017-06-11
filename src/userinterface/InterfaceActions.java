package userinterface;

import managers.PrismManager;
import prisms.Cube;
import prisms.Equilateral;
import prisms.Pentagonal;
import prisms.Prism;
import tester.Tester;
import util.PrismType;
import util.Vertex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfaceActions {

    private final UserInterface userInterface;
    private final PrismManager prismManager;

    private Component lastSelected;

    public InterfaceActions(UserInterface userInterface) {
        this.userInterface = userInterface;
        this.prismManager = Tester.getPrismManager();
    }

    private void registerButtonEvent(final JButton button) {
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (button.equals(Tester.getInstructionFrame().getContinueButton())) {
                    Tester.getInstructionFrame().setVisible(false);
                    userInterface.showFrame();
                }

                JButton component = button;

                Prism selectedPrism = prismManager.getSelectedPrism();
                if (selectedPrism != null) {
                    if (component.equals(userInterface.getConfirmChangesButton()) && !checkAndDisplayWarnings()) {
                        Color color = parseColor(userInterface.getColorList().getSelectedItem());
                        int xOrigin = Integer.parseInt(userInterface.getXOriginField().getText().trim());
                        int yOrigin = Integer.parseInt(userInterface.getYOriginField().getText().trim());

                        selectedPrism.setColor(color);
                        Vertex origin = selectedPrism.getOrigin();
                        origin.setX(xOrigin);
                        origin.setY(yOrigin);

                        if (selectedPrism.getType() == PrismType.CUBE) {
                            Cube cube = (Cube) selectedPrism;
                            int newLength = Integer.parseInt(userInterface.getLengthField().getText().trim());
                            if (newLength != cube.getRealLength()) {
                                cube.setRealLength(newLength);
                            }
                        } else if (selectedPrism.getType() == PrismType.EQUILATERAL) {
                            Equilateral equilateral = (Equilateral) selectedPrism;
                            int newLength = Integer.parseInt(userInterface.getLengthField().getText().trim());
                            if (newLength != equilateral.getRealLength()) {
                                equilateral.setRealLength(newLength);
                            }
                        } else if (selectedPrism.getType() == PrismType.PENTAGONAL) {
                            Pentagonal pentagonal = (Pentagonal) selectedPrism;
                            int radius = Integer.parseInt(userInterface.getRadiusField().getText().trim());
                            int height = Integer.parseInt(userInterface.getHeightField().getText().trim());
                            if (radius != pentagonal.getRealRadius()) {
                                pentagonal.setRealRadius(radius);
                            }
                            if (height != pentagonal.getRealHeight()) {
                                pentagonal.setRealHeight(height);
                            }
                        }
                        UserInterface.repaint();
                        return;
                    } else if (!(button.equals(userInterface.getMoveButton()) || button.equals(userInterface.getRotateButton()) || button.equals(userInterface.getResizeButton()))) {
                        switchToNoPrismSelected();
                        if (component.equals(userInterface.getRemoveButton())) {
                            prismManager.removePrism(selectedPrism);
                        }
                    }
                    UserInterface.repaint();
                }

                hideAllWarnings();

                boolean displayingWarnings = false;
                if (component.equals(userInterface.getCreateButton())) {
                    displayingWarnings = checkAndDisplayWarnings();
                    if (!displayingWarnings) {
                        createPrism();
                    }
                    component = null;
                }

                if (!displayingWarnings) {
                    setLastSelected(component);
                    if (!(button.equals(userInterface.getMoveButton()) || button.equals(userInterface.getRotateButton()) || button.equals(userInterface.getResizeButton()))) {
                        hideOldComponents();
                        showNewComponents();
                    }
                }

            }
        });
    }

    private void setLastSelected(JButton button) {
        boolean sameComponent = false;
        if (this.lastSelected != null) {
            sameComponent = this.lastSelected.equals(button);
            this.lastSelected.setBackground(null);

            if (sameComponent
                    && (this.lastSelected.equals(this.userInterface.getCubeButton()) || this.lastSelected.equals(this.userInterface.getEquilateralButton()) || this.lastSelected
                    .equals(this.userInterface.getPentagonalButton()))) {
                this.lastSelected = this.userInterface.getAddButton();
                this.lastSelected.setBackground(Color.CYAN);
            } else {
                if (button != null) {
                    if (button.equals(this.userInterface.getMoveButton())) {
                        this.userInterface.getMoveButton().setIcon(this.userInterface.getMoveIcon());
                    } else if (button.equals(this.userInterface.getRotateButton())) {
                        this.userInterface.getRotateButton().setIcon(this.userInterface.getRotateIcon());
                    } else if (button.equals(this.userInterface.getResizeButton())) {
                        this.userInterface.getResizeButton().setIcon(this.userInterface.getResizeIcon());
                    }
                }
                this.lastSelected = null;
            }
        }

        if (!sameComponent) {
            if (button != null) {
                if (button.equals(this.userInterface.getMoveButton())) {
                    this.userInterface.getMoveButton().setIcon(this.userInterface.getSelectedMoveIcon());
                    this.userInterface.getRotateButton().setIcon(this.userInterface.getRotateIcon());
                    this.userInterface.getResizeButton().setIcon(this.userInterface.getResizeIcon());
                } else if (button.equals(this.userInterface.getRotateButton())) {
                    this.userInterface.getRotateButton().setIcon(this.userInterface.getSelectedRotateIcon());
                    this.userInterface.getMoveButton().setIcon(this.userInterface.getMoveIcon());
                    this.userInterface.getResizeButton().setIcon(this.userInterface.getResizeIcon());
                } else if (button.equals(this.userInterface.getResizeButton())) {
                    this.userInterface.getResizeButton().setIcon(this.userInterface.getSelectedResizeIcon());
                    this.userInterface.getRotateButton().setIcon(this.userInterface.getRotateIcon());
                    this.userInterface.getMoveButton().setIcon(this.userInterface.getMoveIcon());
                } else {
                    this.userInterface.getRotateButton().setIcon(this.userInterface.getRotateIcon());
                    this.userInterface.getMoveButton().setIcon(this.userInterface.getMoveIcon());
                    this.userInterface.getResizeButton().setIcon(this.userInterface.getResizeIcon());
                    button.setBackground(Color.CYAN);
                }
            }
            this.lastSelected = button;
        }
    }

    public void switchToPrismSelected() {
        if (this.lastSelected != null && !(this.lastSelected.equals(this.userInterface.getMoveButton()) || this.lastSelected.equals(this.userInterface.getRotateButton()) || this.lastSelected.equals(this.userInterface.getResizeButton()))) {
            this.setLastSelected(null);
        }
        this.hideAllWarnings();
        this.hideOldComponents();
        this.resetFields();

        this.userInterface.getRemoveButton().setVisible(true);
        this.userInterface.getColorLabel().setVisible(true);
        this.userInterface.getColorList().setVisible(true);
        this.userInterface.getOriginLabel().setVisible(true);
        this.userInterface.getXLabel().setVisible(true);
        this.userInterface.getYLabel().setVisible(true);
        this.userInterface.getXOriginField().setVisible(true);
        this.userInterface.getYOriginField().setVisible(true);
        this.userInterface.getConfirmChangesButton().setVisible(true);

        Prism prism = this.prismManager.getSelectedPrism();
        Vertex origin = prism.getOrigin();
        String colorString = this.parseColorString(prism.getColor());

        this.userInterface.getColorList().select(-1);
        if (colorString != null) {
            int index = 0;
            for (String item : this.userInterface.getColorList().getItems()) {
                if (item.equals(colorString)) {
                    this.userInterface.getColorList().select(index);
                    break;
                }
                index++;
            }
        }

        this.userInterface.getXOriginField().setText(" " + origin.getX());
        this.userInterface.getYOriginField().setText(" " + origin.getY());

        if (prism.getType() == PrismType.PENTAGONAL) {
            this.userInterface.getRadiusLabel().setVisible(true);
            this.userInterface.getRadiusField().setVisible(true);
            this.userInterface.getHeightLabel().setVisible(true);
            this.userInterface.getHeightField().setVisible(true);

            Pentagonal pentagonal = (Pentagonal) prism;
            this.userInterface.getRadiusField().setText(" " + pentagonal.getRealRadius());
            this.userInterface.getHeightField().setText(" " + pentagonal.getRealHeight());
        } else {
            this.userInterface.getLengthLabel().setVisible(true);
            this.userInterface.getLengthField().setVisible(true);

            if (prism.getType() == PrismType.CUBE) {
                this.userInterface.getLengthField().setText(" " + ((Cube) prism).getRealLength());
            } else if (prism.getType() == PrismType.EQUILATERAL) {
                this.userInterface.getLengthField().setText(" " + ((Equilateral) prism).getRealLength());
            }
        }
        UserInterface.repaint();
    }

    public void switchToNoPrismSelected() {
        this.prismManager.setSelectedPrism(null);
        this.userInterface.getRemoveButton().setVisible(false);
        this.userInterface.getConfirmChangesButton().setVisible(false);
    }

    private void hideAllWarnings() {
        this.userInterface.getNoColorLabel().setVisible(false);
        this.userInterface.getNoOriginLabel().setVisible(false);
        this.userInterface.getNoLengthLabel().setVisible(false);
        this.userInterface.getNoRadiusLabel().setVisible(false);
        this.userInterface.getNoHeightLabel().setVisible(false);
        this.userInterface.getInvalidOriginLabel().setVisible(false);
        this.userInterface.getInvalidLengthLabel().setVisible(false);
        this.userInterface.getInvalidRadiusLabel().setVisible(false);
        this.userInterface.getInvalidHeightLabel().setVisible(false);
    }

    public void hideOldComponents() {
        this.userInterface.getRemoveButton().setVisible(false);
        this.userInterface.getSelectPrismLabel().setVisible(false);
        this.userInterface.getCubeButton().setVisible(false);
        this.userInterface.getEquilateralButton().setVisible(false);
        this.userInterface.getPentagonalButton().setVisible(false);
        this.userInterface.getColorLabel().setVisible(false);
        this.userInterface.getColorList().setVisible(false);
        this.userInterface.getOriginLabel().setVisible(false);
        this.userInterface.getXLabel().setVisible(false);
        this.userInterface.getYLabel().setVisible(false);
        this.userInterface.getXOriginField().setVisible(false);
        this.userInterface.getYOriginField().setVisible(false);
        this.userInterface.getLengthLabel().setVisible(false);
        this.userInterface.getLengthField().setVisible(false);
        this.userInterface.getRadiusLabel().setVisible(false);
        this.userInterface.getRadiusField().setVisible(false);
        this.userInterface.getHeightLabel().setVisible(false);
        this.userInterface.getHeightField().setVisible(false);
        this.userInterface.getCreateButton().setVisible(false);
        this.userInterface.getInvalidOriginLabel().setVisible(false);
        this.userInterface.getInvalidLengthLabel().setVisible(false);
        this.userInterface.getInvalidRadiusLabel().setVisible(false);
        this.userInterface.getInvalidHeightLabel().setVisible(false);
    }

    private void resetFields() {
        this.userInterface.getColorList().select(-1);
        this.userInterface.getXOriginField().setText(" " + UserInterface.getWidth() / 4);
        this.userInterface.getYOriginField().setText(" " + UserInterface.getHeight() / 2);
        this.userInterface.getLengthField().setText(" ");
        this.userInterface.getRadiusField().setText(" ");
        this.userInterface.getHeightField().setText(" ");
    }

    private void showPrismButtons() {
        this.userInterface.getCubeButton().setVisible(true);
        this.userInterface.getEquilateralButton().setVisible(true);
        this.userInterface.getPentagonalButton().setVisible(true);
    }

    private void showPrismCreationComponents() {
        this.userInterface.getColorLabel().setVisible(true);
        this.userInterface.getColorList().setVisible(true);
        this.userInterface.getOriginLabel().setVisible(true);
        this.userInterface.getXLabel().setVisible(true);
        this.userInterface.getYLabel().setVisible(true);
        this.userInterface.getXOriginField().setVisible(true);
        this.userInterface.getYOriginField().setVisible(true);
        this.userInterface.getCreateButton().setVisible(true);
        this.resetFields();
    }

    private void showNewComponents() {
        if (this.lastSelected != null) {
            Component newComponent = this.lastSelected;

            if (!(newComponent.equals(this.userInterface.getRemoveButton()) || newComponent.equals(this.userInterface.getMoveButton()) || newComponent.equals(this.userInterface
                    .getRotateButton()) || newComponent.equals(this.userInterface.getResizeButton()))) {
                this.userInterface.getSelectPrismLabel().setVisible(true);
            }

            if (newComponent.equals(this.userInterface.getAddButton())) {
                this.showPrismButtons();
            } else if (newComponent.equals(this.userInterface.getCubeButton())) {
                this.showPrismButtons();
                this.showPrismCreationComponents();

                this.userInterface.getLengthLabel().setVisible(true);
                this.userInterface.getLengthField().setVisible(true);
            } else if (newComponent.equals(this.userInterface.getEquilateralButton())) {
                this.showPrismButtons();
                this.showPrismCreationComponents();

                this.userInterface.getLengthLabel().setVisible(true);
                this.userInterface.getLengthField().setVisible(true);
            } else if (newComponent.equals(this.userInterface.getPentagonalButton())) {
                this.showPrismButtons();
                this.showPrismCreationComponents();

                this.userInterface.getRadiusLabel().setVisible(true);
                this.userInterface.getRadiusField().setVisible(true);
                this.userInterface.getHeightLabel().setVisible(true);
                this.userInterface.getHeightField().setVisible(true);
            }
        }
    }

    public boolean checkAndDisplayWarnings() {
        boolean displayingWarnings = false;

        Prism selectedPrism = this.prismManager.getSelectedPrism();

        String colorString = this.userInterface.getColorList().getSelectedItem();
        if (colorString == null) {
            displayingWarnings = true;
            this.userInterface.getNoColorLabel().setVisible(true);
        }

        String xOriginString = this.userInterface.getXOriginField().getText();
        String yOriginString = this.userInterface.getYOriginField().getText();
        if (xOriginString == null || xOriginString.trim().equals("") || yOriginString == null || yOriginString.trim().equals("")) {
            displayingWarnings = true;
            this.userInterface.getNoOriginLabel().setVisible(true);
        } else if (!(this.isInt(xOriginString.trim()) && this.isInt(xOriginString.trim()))) {
            displayingWarnings = true;
            this.userInterface.getInvalidOriginLabel().setVisible(true);
        }

        if ((selectedPrism != null && (selectedPrism.getType() == PrismType.CUBE || selectedPrism.getType() == PrismType.EQUILATERAL))
                || ((this.lastSelected != null && (this.lastSelected.equals(this.userInterface.getCubeButton()) || this.lastSelected.equals(this.userInterface
                .getEquilateralButton()))))) {
            String lengthString = this.userInterface.getLengthField().getText().trim();
            if (lengthString == null || lengthString.trim().equals("")) {
                displayingWarnings = true;
                this.userInterface.getNoLengthLabel().setVisible(true);
            } else if (!this.isInt(lengthString.trim())) {
                displayingWarnings = true;
                this.userInterface.getInvalidLengthLabel().setVisible(true);
            }
        }

        if ((selectedPrism != null && selectedPrism.getType() == PrismType.PENTAGONAL)
                || (this.lastSelected != null && this.lastSelected.equals(this.userInterface.getPentagonalButton()))) {
            String radiusString = this.userInterface.getRadiusField().getText().trim();
            if (radiusString == null || radiusString.trim().equals("")) {
                displayingWarnings = true;
                this.userInterface.getNoRadiusLabel().setVisible(true);
            } else if (!this.isInt(radiusString.trim())) {
                displayingWarnings = true;
                this.userInterface.getInvalidRadiusLabel().setVisible(true);
            }

            String heightString = this.userInterface.getHeightField().getText().trim();
            if (heightString == null || heightString.trim().equals("")) {
                displayingWarnings = true;
                this.userInterface.getNoHeightLabel().setVisible(true);
            } else if (!this.isInt(heightString.trim())) {
                displayingWarnings = true;
                this.userInterface.getInvalidHeightLabel().setVisible(true);
            }
        }

        return displayingWarnings;
    }

    public void createPrism() {
        int length = 0, radius = 0, height = 0;

        String colorString = this.userInterface.getColorList().getSelectedItem();
        Color color = this.parseColor(colorString.trim());

        String xOriginString = this.userInterface.getXOriginField().getText();
        String yOriginString = this.userInterface.getYOriginField().getText();

        int xOrigin = Integer.parseInt(xOriginString.trim());
        int yOrigin = Integer.parseInt(yOriginString.trim());

        String lengthText = this.userInterface.getLengthField().getText();
        if (this.lastSelected.equals(this.userInterface.getCubeButton()) || this.lastSelected.equals(this.userInterface.getEquilateralButton())) {
            length = Integer.parseInt(lengthText.trim());
        }

        if (this.lastSelected.equals(this.userInterface.getPentagonalButton())) {
            String radiusString = this.userInterface.getRadiusField().getText().trim();
            radius = Integer.parseInt(radiusString.trim());

            String heightString = this.userInterface.getHeightField().getText().trim();
            height = Integer.parseInt(heightString.trim());
        }

        Prism prism = null;
        if (this.lastSelected.equals(this.userInterface.getPentagonalButton())) {
            prism = new Pentagonal(new Vertex(xOrigin, yOrigin, 0), radius, height, color);
        } else if (this.lastSelected.equals(this.userInterface.getCubeButton())) {
            prism = new Cube(new Vertex(xOrigin, yOrigin, 0), length, color);
        } else if (this.lastSelected.equals(this.userInterface.getEquilateralButton())) {
            prism = new Equilateral(new Vertex(xOrigin, yOrigin, 0), length, color);
        }

        if (prism != null) {
            //TODO: prism.createShapes();
            this.prismManager.addPrism(prism);
            UserInterface.repaint();
        }
    }

    public void registerEvents() {
        this.registerButtonEvent(this.userInterface.getAddButton());
        this.registerButtonEvent(this.userInterface.getRemoveButton());
        this.registerButtonEvent(this.userInterface.getCubeButton());
        this.registerButtonEvent(this.userInterface.getEquilateralButton());
        this.registerButtonEvent(this.userInterface.getPentagonalButton());
        this.registerButtonEvent(this.userInterface.getConfirmChangesButton());
        this.registerButtonEvent(this.userInterface.getCreateButton());
        this.registerButtonEvent(this.userInterface.getMoveButton());
        this.registerButtonEvent(this.userInterface.getRotateButton());
        this.registerButtonEvent(this.userInterface.getResizeButton());
        this.registerButtonEvent(Tester.getInstructionFrame().getContinueButton());
    }

    public Component getSelectedComponent() {
        return this.lastSelected;
    }

    private Color parseColor(String arg) {
        switch (arg) {
            case "BLACK":
                return Color.BLACK;
            case "BLUE":
                return Color.BLUE;
            case "CYAN":
                return Color.CYAN;
            case "GRAY":
                return Color.GRAY;
            case "DARK_GRAY":
                return Color.DARK_GRAY;
            case "LIGHT_GRAY":
                return Color.LIGHT_GRAY;
            case "GREEN":
                return Color.GREEN;
            case "MAGENTA":
                return Color.MAGENTA;
            case "ORANGE":
                return Color.ORANGE;
            case "PINK":
                return Color.PINK;
            case "WHITE":
                return Color.WHITE;
            case "YELLOW":
                return Color.YELLOW;
            case "RED":
                return Color.RED;
            default:
                return null;
        }
    }

    private String parseColorString(Color color) {
        if (color == Color.BLACK) {
            return "BLACK";
        }
        if (color == Color.BLUE) {
            return "BLUE";
        }
        if (color == Color.CYAN) {
            return "CYAN";
        }
        if (color == Color.GRAY) {
            return "GRAY";
        }
        if (color == Color.DARK_GRAY) {
            return "DARK_GRAY";
        }
        if (color == Color.LIGHT_GRAY) {
            return "LIGHT_GRAY";
        }
        if (color == Color.GREEN) {
            return "GREEN";
        }
        if (color == Color.MAGENTA) {
            return "MAGENTA";
        }
        if (color == Color.ORANGE) {
            return "ORANGE";
        }
        if (color == Color.PINK) {
            return "PINK";
        }
        if (color == Color.WHITE) {
            return "WHITE";
        }
        if (color == Color.YELLOW) {
            return "YELLOW";
        }
        if (color == Color.RED) {
            return "RED";
        }

        return null;
    }

    private boolean isInt(String arg) {
        if (arg == null) {
            return false;
        }
        try {
            Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
