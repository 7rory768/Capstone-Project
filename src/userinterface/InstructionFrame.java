package userinterface;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Created by Rory on 6/11/2017.
 */
public class InstructionFrame extends JFrame {

    private final UserInterface userInterface;
    private final InterfaceActions interfaceActions;
    private final int WIDTH = 600;
    private final int HEIGHT = 550;

    private JButton moveButton, rotateButton, resizeButton, ctrlMoveButton, ctrlRotateButton, ctrlScrollButton, moveScrollButton, continueButton;
    private JTextArea title, moveLabel, rotateLabel, resizeLabel, ctrlMoveLabel, ctrlRotateLabel, ctrlScrollLabel, moveScrollLabel;

    public InstructionFrame(UserInterface userInterface, InterfaceActions interfaceActions) {
        this.userInterface = userInterface;
        this.interfaceActions = interfaceActions;
    }

    public void setup() {
        this.setLayout(new GridBagLayout());
        this.setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.createComponents();;
        this.addComponents();

        this.pack();
        this.setFocusable(false);
    }

    public void createComponents() {
        this.title = new JTextArea("Instructions");
        this.title.setEditable(false);

        Dimension dimensions = new Dimension(35, 35);
        
        this.moveButton = new JButton();
        this.moveButton.setPreferredSize(dimensions);
        this.moveButton.setIcon(this.userInterface.getMoveIcon());

        this.moveLabel = new JTextArea("Use the move button and drag to move the selected prism");
        this.moveLabel.setEditable(false);

        this.rotateButton = new JButton();
        this.rotateButton.setPreferredSize(dimensions);
        this.rotateButton.setIcon(this.userInterface.getRotateIcon());

        this.rotateLabel = new JTextArea("Use the rotate button and drag to rotate the selected prism");
        this.rotateLabel.setEditable(false);

        this.resizeButton = new JButton();
        this.resizeButton.setPreferredSize(dimensions);
        this.resizeButton.setIcon(this.userInterface.getResizeIcon());

        this.resizeLabel = new JTextArea("Use the resize button and drag to resize the selected prism");
        this.resizeLabel.setEditable(false);

        dimensions = new Dimension(90, 35);
        
        this.ctrlMoveButton = new JButton();
        this.ctrlMoveButton.setPreferredSize(dimensions);

        this.ctrlMoveLabel = new JTextArea("Hold down control and drag to move your view of the prisms");
        this.ctrlMoveLabel.setEditable(false);

        try {
            //File ctrlMoveIconFile = new File("images" + File.separator + "ctrlmovebutton.png");
            File ctrlMoveIconFile = new File("Capstone-Project" + File.separator + "images" + File.separator + "ctrlmovebutton.png");
            Image img = ImageIO.read(ctrlMoveIconFile);
            this.ctrlMoveButton.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }

        this.ctrlRotateButton = new JButton();
        this.ctrlRotateButton.setPreferredSize(dimensions);

        this.ctrlRotateLabel = new JTextArea("Hold down control and drag to rotate your view of the prisms");
        this.ctrlRotateLabel.setEditable(false);

        try {
            //File ctrlRotateIconFile = new File("images" + File.separator + "ctrlrotatebutton.png");
            File ctrlRotateIconFile = new File("Capstone-Project" + File.separator + "images" + File.separator + "ctrlrotatebutton.png");
            Image img = ImageIO.read(ctrlRotateIconFile);
            this.ctrlRotateButton.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }

        this.ctrlScrollButton = new JButton();
        this.ctrlScrollButton.setPreferredSize(dimensions);

        this.ctrlScrollLabel = new JTextArea("Hold down control and scroll to zoom in or out");
        this.ctrlScrollLabel.setEditable(false);

        try {
            //File ctrlScrollIconFile = new File("images" + File.separator + "ctrlscrollbutton.png");
            File ctrlScrollIconFile = new File("Capstone-Project" + File.separator + "images" + File.separator + "ctrlscrollbutton.png");
            Image img = ImageIO.read(ctrlScrollIconFile);
            this.ctrlScrollButton.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }

        this.moveScrollButton = new JButton();
        this.moveScrollButton.setPreferredSize(dimensions);

        this.moveScrollLabel = new JTextArea("Hold down control and scroll to zoom in or out");
        this.moveScrollLabel.setEditable(false);

        try {
            //File moveScrollIconFile = new File("images" + File.separator + "movescrollbutton.png");
            File moveScrollIconFile = new File("Capstone-Project" + File.separator + "images" + File.separator + "movescrollbutton.png");
            Image img = ImageIO.read(moveScrollIconFile);
            this.moveScrollButton.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        
        this.continueButton = new JButton("Continue");
    }

    public void addComponents() {

        // INSETS: TOP, LEFT, BOTTOM, RIGHT
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.insets = new Insets((int) (-4 * this.HEIGHT/5.0), 0, 0, 0);
        this.add(this.title, constraints);

        int left = (int) (-8 * this.WIDTH/10.0) + 83;

        constraints.gridy = 1;
        
        constraints.insets = new Insets((int) (-3 * this.HEIGHT/5.0), left, 0, 0);
        this.add(this.moveButton, constraints);

        constraints.gridy = 2;

        constraints.insets = new Insets((int) (-3 * this.HEIGHT/5.0), 0, 0,-100);
        this.add(this.moveLabel, constraints);

        constraints.gridy = 3;

        constraints.insets = new Insets((int) (-2 * this.HEIGHT/5.0), left, 0, 0);
        this.add(this.rotateButton, constraints);

        constraints.gridy = 4;

        constraints.insets = new Insets((int) (-2 * this.HEIGHT/5.0), 0, 0,-100);
        this.add(this.rotateLabel, constraints);

        constraints.gridy = 5;

        constraints.insets = new Insets((int) (-1 * this.HEIGHT/5.0), left, 0, 0);
        this.add(this.resizeButton, constraints);

        constraints.gridy = 6;

        constraints.insets = new Insets((int) (-1 * this.HEIGHT/5.0), 0, 0,-100);
        this.add(this.resizeLabel, constraints);

        constraints.gridy = 7;
        
        left = (int) (-6.5 * this.WIDTH/10.0) - 5;

        constraints.insets = new Insets(0, left, 0, 0);
        this.add(this.ctrlMoveButton, constraints);

        constraints.gridy = 8;

        constraints.insets = new Insets(-38, 0, 0,-100);
        this.add(this.ctrlMoveLabel, constraints);

        constraints.gridy = 9;

        constraints.insets = new Insets(0, left ,(int) (-0.75 * this.HEIGHT/5.0), 0);
        this.add(this.ctrlRotateButton, constraints);

        constraints.gridy = 10;

        constraints.insets = new Insets(0, 0, (int) (-0.75 * this.HEIGHT/5.0),-100);
        this.add(this.ctrlRotateLabel, constraints);

        constraints.gridy = 11;

        constraints.insets = new Insets(0, left, (int) (-1.80 * this.HEIGHT/5.0), 0);
        this.add(this.ctrlScrollButton, constraints);

        constraints.gridy = 12;

        constraints.insets = new Insets(0, 0, (int) (-1.80 * this.HEIGHT/5.0),-100);
        this.add(this.ctrlScrollLabel, constraints);

        constraints.gridy = 13;

        constraints.insets = new Insets(0, left, (int) (-2.85 * this.HEIGHT/5.0), 0);
        this.add(this.moveScrollButton, constraints);

        constraints.gridy = 14;

        constraints.insets = new Insets(0, 0, (int) (-2.85 * this.HEIGHT/5.0),-100);
        this.add(this.moveScrollLabel, constraints);

        constraints.gridy = 15;

        constraints.insets = new Insets(0, 0, (int) (-3.8 * this.HEIGHT/5.0), 0);
        this.add(this.continueButton, constraints);
    }

    public JButton getContinueButton() {
        return this.continueButton;
    }

}
