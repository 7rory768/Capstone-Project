package userinterface;

import prisms.Cube;
import prisms.Equilateral;
import prisms.Pentagonal;
import prisms.Prism;
import shapes.Pentagon;
import shapes.Rectangle;
import shapes.Shape;
import shapes.Square;
import shapes.Triangle;
import util.Matrix3;
import util.PrismType;
import util.ShapeType;
import util.Vertex;

import javax.swing.*;

import managers.PrismManager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.List;
import java.awt.TextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;

public class UserInterface {

	private final InterfaceActions interfaceActions;
	private final PrismManager prismManager;

	private static JPanel paintPanel;
	private JPanel buttonPanel, pitchPanel, headingPanel;
	private final static int WIDTH = 1500;
	private final static int HEIGHT = 900;
	private final static int HEADING_DIVIDER = 35;
	private final static int PITCH_DIVIDER = HEIGHT - HEADING_DIVIDER;

	private JFrame frame;
	private JSlider pitchSlider;
	private JSlider headingSlider;
	private Matrix3 transform;
	private JSplitPane paintSplit, buttonSplit;
	private JButton addButton, removeButton, cubeButton, equilateralButton, pentagonalButton, createButton;
	private JTextArea selectPrismLabel, colorLabel, originLabel, xLabel, yLabel, lengthLabel, radiusLabel, heightLabel;
	private JTextArea noColorLabel, noOriginLabel, noLengthLabel, noRadiusLabel, noHeightLabel;
	private JTextArea invalidOriginLabel, invalidLengthLabel, invalidRadiusLabel, invalidHeightLabel;
	private TextField lengthField, xOriginField, yOriginField, radiusField, heightField;
	private List colorList;

	public UserInterface(PrismManager prismManager) {
		this.prismManager = prismManager;
		this.interfaceActions = new InterfaceActions(this, this.prismManager);
	}

	public void setupInterface() {
		this.setupButtons();
		this.setupPanels();
		this.addButtons();
		this.interfaceActions.registerEvents();
		this.frame.pack();
		this.frame.setVisible(true);
	}

	private void setupButtons() {
		this.headingSlider = new JSlider(0, 360, 180);
		this.pitchSlider = new JSlider(SwingConstants.VERTICAL, -90, 90, 0);
		this.pitchPanel = new JPanel();
		this.headingPanel = new JPanel();
		this.buttonSplit = new JSplitPane();
		this.paintSplit = new JSplitPane();
		this.addButton = new JButton("Add Prism");
		this.removeButton = new JButton("Remove Prism");
		this.selectPrismLabel = new JTextArea("Select a prism type below");
		this.cubeButton = new JButton("Cube");
		this.equilateralButton = new JButton("Equilateral");
		this.pentagonalButton = new JButton("Pentagonal");
		this.colorLabel = new JTextArea("Color: ");
		this.colorList = new List();
		this.colorList.add("BLACK");
		this.colorList.add("BLUE");
		this.colorList.add("CYAN");
		this.colorList.add("GRAY");
		this.colorList.add("DARK_GRAY");
		this.colorList.add("LIGHT_GRAY");
		this.colorList.add("GREEN");
		this.colorList.add("MAGENTA");
		this.colorList.add("ORANGE");
		this.colorList.add("PINK");
		this.colorList.add("RED");
		this.colorList.add("YELLOW");
		this.originLabel = new JTextArea("Origin: ");
		this.xLabel = new JTextArea("X = ");
		this.yLabel = new JTextArea("Y = ");
		this.xOriginField = new TextField(" " + UserInterface.WIDTH / 4);
		this.yOriginField = new TextField(" " + UserInterface.HEIGHT / 2);
		this.lengthLabel = new JTextArea("Length: ");
		this.lengthField = new TextField();
		this.radiusLabel = new JTextArea("Radius: ");
		this.radiusField = new TextField();
		this.heightLabel = new JTextArea("Height: ");
		this.heightField = new TextField();

		this.noColorLabel = new JTextArea("Error: Missing parameter, please provide a color");
		this.noOriginLabel = new JTextArea("Error: Missing parameter, please provide both coordinates");
		this.noLengthLabel = new JTextArea("Error: Missing parameter, please provide a length");
		this.noRadiusLabel = new JTextArea("Error: Missing parameter, please provide a radius");
		this.noHeightLabel = new JTextArea("Error: Missing parameter, please provide a height");

		this.invalidOriginLabel = new JTextArea("Error: Invalid parameter, please provide integers");
		this.invalidLengthLabel = new JTextArea("Error: Invalid parameter, please provide an integer");
		this.invalidRadiusLabel = new JTextArea("Error: Invalid parameter, please provide an integer");
		this.invalidHeightLabel = new JTextArea("Error: Invalid parameter, please provide an integer");

		this.createButton = new JButton("Create");

		this.selectPrismLabel.setEditable(false);
		this.lengthLabel.setEditable(false);
		this.originLabel.setEditable(false);
		this.radiusLabel.setEditable(false);
		this.heightLabel.setEditable(false);
		this.colorLabel.setEditable(false);

		this.noColorLabel.setEditable(false);
		this.noOriginLabel.setEditable(false);
		this.noLengthLabel.setEditable(false);
		this.noRadiusLabel.setEditable(false);
		this.noHeightLabel.setEditable(false);
		
		this.invalidOriginLabel.setEditable(false);
		this.invalidLengthLabel.setEditable(false);
		this.invalidRadiusLabel.setEditable(false);
		this.invalidHeightLabel.setEditable(false);

		this.headingSlider.setIgnoreRepaint(true);
		this.pitchSlider.setIgnoreRepaint(true);
		this.addButton.setIgnoreRepaint(true);
		this.removeButton.setIgnoreRepaint(true);
		this.colorLabel.setIgnoreRepaint(true);
		this.originLabel.setIgnoreRepaint(true);
		this.xLabel.setIgnoreRepaint(true);
		this.yLabel.setIgnoreRepaint(true);
		this.lengthLabel.setIgnoreRepaint(true);
		this.radiusLabel.setIgnoreRepaint(true);
		this.heightLabel.setIgnoreRepaint(true);

		this.createButton.setIgnoreRepaint(true);
	}

	private void addButtons() {
		this.paintSplit.setOrientation(JSplitPane.VERTICAL_SPLIT);
		this.paintSplit.setDividerLocation(PITCH_DIVIDER);
		this.paintSplit.setTopComponent(paintPanel);
		this.paintSplit.setBottomComponent(this.headingPanel);

		this.buttonSplit.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		this.buttonSplit.setDividerLocation(HEADING_DIVIDER);
		this.buttonSplit.setLeftComponent(this.pitchPanel);
		this.buttonSplit.setRightComponent(this.buttonPanel);

		paintPanel.setLayout(new BoxLayout(paintPanel, BoxLayout.Y_AXIS));

		paintPanel.setMinimumSize(new Dimension((int) (3 * WIDTH / 5.0), Integer.MAX_VALUE));

		this.buttonPanel.setLayout(new GridBagLayout());

		// stop pitch panel from becoming bigger than 25 pixels high
		this.pitchPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));

		this.pitchPanel.add(this.pitchSlider);

		this.headingPanel.setMaximumSize(new Dimension(20, Integer.MAX_VALUE));
		this.headingPanel.add(this.headingSlider);

		// INSETS: TOP, LEFT, BOTTOM, RIGHT
		GridBagConstraints constraints = new GridBagConstraints();

		// add button
		constraints.insets = new Insets((int) (-7 * HEIGHT / 8.0), 0, 0, 125);
		this.buttonPanel.add(this.addButton, constraints);

		// remove button
		constraints.insets = new Insets((int) (-7 * HEIGHT / 8.0), 125, 0, 0);
		constraints.gridx = 3;
		this.buttonPanel.add(this.removeButton, constraints);

		constraints.gridwidth = 3;

		// select prism label
		constraints.insets = new Insets((int) (-6 * HEIGHT / 8.0), -250, 0, 0);
		constraints.gridx = 1;
		constraints.gridy = 1;
		this.buttonPanel.add(this.selectPrismLabel, constraints);
		this.selectPrismLabel.setVisible(false);

		// cube button
		constraints.insets = new Insets((int) (-5 * HEIGHT / 8.0), -600, 0, 0);
		constraints.gridy = 2;
		this.buttonPanel.add(this.cubeButton, constraints);
		this.cubeButton.setVisible(false);

		// equilateral button
		constraints.insets = new Insets((int) (-5 * HEIGHT / 8.0), -250, 0, 0);
		constraints.gridx = 2;
		this.buttonPanel.add(this.equilateralButton, constraints);
		this.equilateralButton.setVisible(false);

		// pentagonal button
		constraints.insets = new Insets((int) (-5 * HEIGHT / 8.0), 0, 0, -150);
		constraints.gridx = 3;
		this.buttonPanel.add(this.pentagonalButton, constraints);
		this.pentagonalButton.setVisible(false);

		constraints.gridx = 1;
		constraints.gridy = 3;

		int spacing = 50; // 50

		// color label
		constraints.insets = new Insets((int) (-3 * HEIGHT / 8.0) - 110 + spacing, -500, 0, 0);
		this.buttonPanel.add(this.colorLabel, constraints);
		this.colorLabel.setVisible(false);

		// no color warning
		constraints.insets = new Insets((int) (-4 * HEIGHT / 8.0) - 50, -250, 0, 0);
		this.buttonPanel.add(this.noColorLabel, constraints);
		this.noColorLabel.setVisible(false);

		// color list
		constraints.insets = new Insets((int) (-4 * HEIGHT / 8.0) + spacing, -250, 0, 0);
		this.buttonPanel.add(this.colorList, constraints);
		this.colorList.setVisible(false);

		constraints.gridy = 4;
		spacing = 100; // 100

		// origin label
		constraints.insets = new Insets((int) (-3 * HEIGHT / 8.0) + spacing, -500, 0, 0);
		this.buttonPanel.add(this.originLabel, constraints);
		this.originLabel.setVisible(false);

		// x label
		constraints.insets = new Insets((int) (-3 * HEIGHT / 8.0) + spacing, -375, 0, 0);
		this.buttonPanel.add(this.xLabel, constraints);
		this.xLabel.setVisible(false);

		constraints.gridx = 2;
		
		// no origin label
		constraints.insets = new Insets((int) (-3 * HEIGHT / 8.0) + spacing - 60, -245, 0, 0);
		this.buttonPanel.add(this.noOriginLabel, constraints);
		this.noOriginLabel.setVisible(false);
		
		// invalid origin label
		this.buttonPanel.add(this.invalidOriginLabel, constraints);
		this.invalidOriginLabel.setVisible(false);
		
		// x origin field
		constraints.insets = new Insets((int) (-3 * HEIGHT / 8.0) + spacing, -300, 0, 0);
		this.buttonPanel.add(this.xOriginField, constraints);
		this.xOriginField.setVisible(false);

		// y label
		constraints.insets = new Insets((int) (-3 * HEIGHT / 8.0) + spacing, -220, 0, 0);
		constraints.gridy = 4;
		this.buttonPanel.add(this.yLabel, constraints);
		this.yLabel.setVisible(false);

		constraints.gridx = 3;
		
		// y origin field
		constraints.insets = new Insets((int) (-3 * HEIGHT / 8.0) + spacing, -145, 0, 0);
		this.buttonPanel.add(this.yOriginField, constraints);
		this.yOriginField.setVisible(false);

		constraints.gridy = 5;

		// length label
		constraints.insets = new Insets((int) (-2 * HEIGHT / 8.0) + spacing, -800, 0, 0);
		constraints.fill = GridBagConstraints.NONE;
		this.buttonPanel.add(this.lengthLabel, constraints);
		this.lengthLabel.setVisible(false);

		constraints.gridx = 3;
		
		// no length label
		constraints.insets = new Insets((int) (-2 * HEIGHT / 8.0) + spacing - 50, -200, 0, 0);
		this.buttonPanel.add(this.noLengthLabel, constraints);
		this.noLengthLabel.setVisible(false);
		
		// invalid length label
		this.buttonPanel.add(this.invalidLengthLabel, constraints);
		this.invalidLengthLabel.setVisible(false);

		// length field
		constraints.insets = new Insets((int) (-2 * HEIGHT / 8.0) + spacing, -200, 0, 0);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		this.buttonPanel.add(this.lengthField, constraints);
		this.lengthField.setVisible(false);

		// radius label
		constraints.insets = new Insets((int) (-2 * HEIGHT / 8.0) + spacing, -800, 0, 0);
		constraints.fill = GridBagConstraints.NONE;
		this.buttonPanel.add(this.radiusLabel, constraints);
		this.radiusLabel.setVisible(false);

		constraints.gridx = 3;
		
		// no radius label
		constraints.insets = new Insets((int) (-2 * HEIGHT / 8.0) + spacing - 50, -200, 0, 0);
		this.buttonPanel.add(this.noRadiusLabel, constraints);
		this.noRadiusLabel.setVisible(false);
		
		// invalid radius label
		this.buttonPanel.add(this.invalidRadiusLabel, constraints);
		this.invalidRadiusLabel.setVisible(false);

		// radius field
		constraints.insets = new Insets((int) (-2 * HEIGHT / 8.0) + spacing, -200, 0, 0);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		this.buttonPanel.add(this.radiusField, constraints);
		this.radiusField.setVisible(false);

		// height label
		constraints.insets = new Insets(-spacing + 80, -800, 0, 0);
		constraints.fill = GridBagConstraints.NONE;
		this.buttonPanel.add(this.heightLabel, constraints);
		this.heightLabel.setVisible(false);

		constraints.gridx = 3;
		
		// no height label
		constraints.insets = new Insets(-spacing + 30, -200, 0, 0);
		this.buttonPanel.add(this.noHeightLabel, constraints);
		this.noHeightLabel.setVisible(false);
		
		// invalid height label
		this.buttonPanel.add(this.invalidHeightLabel, constraints);
		this.invalidHeightLabel.setVisible(false);

		// height field
		constraints.insets = new Insets(-spacing + 80, -200, 0, 0);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		this.buttonPanel.add(this.heightField, constraints);
		this.heightField.setVisible(false);

		// create button
		constraints.insets = new Insets(0, -250, (int) (-7 * HEIGHT / 8.0), 0);
		constraints.gridy = 7;
		constraints.gridx = 1;
		this.buttonPanel.add(this.createButton, constraints);
		this.createButton.setVisible(false);

	}

	public static void repaint() {
		UserInterface.paintPanel.repaint();
	}

	private void setupPanels() {
		this.frame = new JFrame();
		this.frame.setPreferredSize(new Dimension(UserInterface.WIDTH, UserInterface.HEIGHT));
		this.frame.setResizable(false);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.getContentPane().setLayout(new GridLayout());
		this.frame.getContentPane().add(this.paintSplit);
		this.frame.getContentPane().add(this.buttonSplit);
		this.buttonPanel = new JPanel();
		UserInterface.paintPanel = new JPanel() {

			public void paintComponent(Graphics g) {

				Graphics2D g2 = (Graphics2D) g;
				g2.setColor(Color.WHITE);
				g2.fillRect(0, 0, UserInterface.WIDTH / 2, UserInterface.HEIGHT);
				g2.setColor(Color.BLACK);

				double heading = Math.toRadians(headingSlider.getValue());
				Matrix3 headingTransform = new Matrix3(new double[] { Math.cos(heading), 0, Math.sin(heading), 0, 1, 0, -Math.sin(heading), 0, Math.cos(heading) });

				double pitch = Math.toRadians(pitchSlider.getValue());
				Matrix3 pitchTransform = new Matrix3(new double[] { 1, 0, 0, 0, Math.cos(pitch), Math.sin(pitch), 0, -Math.sin(pitch), Math.cos(pitch) });

				transform = headingTransform.multiply(pitchTransform);

				this.createPrisms(g2);
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

						BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

						for (Shape shape : cube.getShapes()) {
							Square sq = (Square) shape;

							Vertex v1 = transform.transform(sq.getVertex1());
							Vertex v2 = transform.transform(sq.getVertex2());
							Vertex v3 = transform.transform(sq.getVertex3());
							Vertex v4 = transform.transform(sq.getVertex4());

							v1.setX(v1.getX() + xOrigin);
							v1.setY(v1.getY() + yOrigin);
							v2.setX(v2.getX() + xOrigin);
							v2.setY(v2.getY() + yOrigin);
							v3.setX(v3.getX() + xOrigin);
							v3.setY(v3.getY() + yOrigin);
							v4.setX(v4.getX() + xOrigin);
							v4.setY(v4.getY() + yOrigin);

							g2.setColor(prism.getColor());
							Path2D path = new Path2D.Double();
							path.moveTo(v1.getX(), v1.getY());
							path.lineTo(v2.getX(), v2.getY());
							path.lineTo(v3.getX(), v3.getY());
							path.lineTo(v4.getX(), v4.getY());
							path.closePath();
							g2.draw(path);

							// compute rectangular bounds for square
							int minX = (int) Math.max(0, Math.ceil(Math.min(v1.getX(), Math.min(v2.getX(), Math.min(v3.getX(), v4.getX())))));
							int maxX = (int) Math.min(img.getWidth() - 1, Math.floor(Math.max(v1.getX(), Math.max(v2.getX(), Math.max(v3.getX(), v4.getX())))));
							int minY = (int) Math.max(0, Math.ceil(Math.min(v1.getY(), Math.min(v2.getY(), Math.min(v3.getY(), v4.getY())))));
							int maxY = (int) Math.min(img.getHeight() - 1, Math.floor(Math.max(v1.getY(), Math.max(v2.getY(), Math.max(v3.getY(), v4.getY())))));

							double squareArea = (maxX - minX) * (maxY - minY);

							for (int y = minY; y <= maxY; y++) {
								for (int x = minX; x <= maxX; x++) {
									double b1 = ((y - v3.getY()) * (v2.getX() - v3.getX()) + (v2.getY() - v3.getY()) * (v3.getX() - x)) / squareArea;
									double b2 = ((y - v1.getY()) * (v3.getX() - v1.getX()) + (v3.getY() - v1.getY()) * (v1.getX() - x)) / squareArea;
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
									// * (v4.getX() -
									// this.pane.add(UserInterface.jPanel2,
									// BorderLayout.CENTER);x));

									// double b5 = ((y -
									// v3.getY()) * (v4.getX() -
									// v3.getX()) +
									// (v4.getY() - v3.getY())
									// * (v3.getX() - x));
									// double b6 = ((y - v2.getY()) * (v4.getX()
									// - v2.getX()) +
									// (v4.getY() - v2.getY())
									// * (v2.getX() - x));

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
						BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

						double[] zBuffer = new double[img.getWidth() * img.getHeight()];

						for (int q = 0; q < zBuffer.length; q++) {
							zBuffer[q] = Double.NEGATIVE_INFINITY;
						}

						for (Shape shape : equilateral.getShapes()) {
							Triangle t = (Triangle) shape;

							Vertex v1 = transform.transform(t.getVertex1());
							Vertex v2 = transform.transform(t.getVertex2());
							Vertex v3 = transform.transform(t.getVertex3());

							v1.setX(v1.getX() + xOrigin);
							v1.setY(v1.getY() + yOrigin);
							v2.setX(v2.getX() + xOrigin);
							v2.setY(v2.getY() + yOrigin);
							v3.setX(v3.getX() + xOrigin);
							v3.setY(v3.getY() + yOrigin);

							/*
							 * v1.setX(v1.getX() + getWidth() / 2);
							 * v1.setY(v1.getY() + getHeight() / 2);
							 * v2.setX(v2.getX() + getWidth() / 2);
							 * v2.setY(v2.getY() + getHeight() / 2);
							 * v3.setX(v3.getX() + getWidth() / 2);
							 * v3.setY(v3.getY() + getHeight() / 2);
							 */

							Vertex ab = new Vertex(v2.getX() - v1.getX(), v2.getY() - v1.getY(), v2.getZ() - v1.getZ());
							Vertex ac = new Vertex(v3.getX() - v1.getX(), v3.getY() - v1.getY(), v3.getZ() - v1.getZ());
							Vertex norm = new Vertex(ab.getY() * ac.getZ() - ab.getZ() * ac.getY(), ab.getZ() * ac.getX() - ab.getX() * ac.getZ(), ab.getX() * ac.getY()
									- ab.getY() * ac.getX());

							double normalLength = Math.sqrt(norm.getX() * norm.getX() + norm.getY() + norm.getZ() * norm.getZ());
							norm.setX(norm.getX() / normalLength);
							norm.setY(norm.getY() / normalLength);
							norm.setZ(norm.getZ() / normalLength);

							double angleCos = Math.abs(norm.getZ());

							g2.setColor(prism.getColor());
							Path2D path = new Path2D.Double();
							path.moveTo(v1.getX(), v1.getY());
							path.lineTo(v2.getX(), v2.getY());
							path.lineTo(v3.getX(), v3.getY());
							path.closePath();
							g2.draw(path);

							// since we are not using Graphics2D anymore,
							// we have to do translation manually

							// compute rectangular bounds for triangle
							int minX = (int) Math.max(0, Math.ceil(Math.min(v1.getX(), Math.min(v2.getX(), v3.getX()))));
							int maxX = (int) Math.min(img.getWidth() - 1, Math.floor(Math.max(v1.getX(), Math.max(v2.getX(), v3.getX()))));
							int minY = (int) Math.max(0, Math.ceil(Math.min(v1.getY(), Math.min(v2.getY(), v3.getY()))));
							int maxY = (int) Math.min(img.getHeight() - 1, Math.floor(Math.max(v1.getY(), Math.max(v2.getY(), v3.getY()))));

							double triangleArea = (v1.getY() - v3.getY()) * (v2.getX() - v3.getX()) + (v2.getY() - v3.getY()) * (v3.getX() - v1.getX());
							for (int y = minY; y <= maxY; y++) {
								for (int x = minX; x <= maxX; x++) {
									double b1 = ((y - v3.getY()) * (v2.getX() - v3.getX()) + (v2.getY() - v3.getY()) * (v3.getX() - x)) / triangleArea;
									double b2 = ((y - v1.getY()) * (v3.getX() - v1.getX()) + (v3.getY() - v1.getY()) * (v1.getX() - x)) / triangleArea;
									double b3 = ((y - v2.getY()) * (v1.getX() - v2.getX()) + (v1.getY() - v2.getY()) * (v2.getX() - x)) / triangleArea;

									if (b1 >= 0 && b1 <= 1 && b2 >= 0 && b2 <= 1 && b3 >= 0 && b3 <= 1) {
										double depth = b1 + v1.getZ() + b2 * v2.getZ() + b3 * v3.getZ();
										int zIndex = y * img.getWidth() + x;
										if (zBuffer[zIndex] < depth) {
											//img.setRGB(x, y, getShade(t.getColor(), angleCos).getRGB());
											zBuffer[zIndex] = depth;
										}
									}
								}
							}
							/*
							 * for (int y = minY; y <= maxY; y++) { for (int x =
							 * minX; x <= maxX; x++) { double b1 = ((y -
							 * v3.getY()) (v2.getX() - v3.getX()) + (v2.getY() -
							 * v3 .getY()) * (v3.getX() - x)) / triangleArea;
							 * double b2 = ((y - v1.getY()) (v3.getX() -
							 * v1.getX()) + (v3.getY() - v1 .getY()) *
							 * (v1.getX() - x)) / triangleArea; double b3 = ((y
							 * - v2.getY()) (v1.getX() - v2.getX()) + (v1.getY()
							 * - v2 .getY()) * (v2.getX() - x)) / triangleArea;
							 * 
							 * if (b1 >= 0 && b1 <= 1 && b2 >= 0 && b2 <= 1 &&
							 * b3 >= 0 && b3 <= 1) { double depth = b1 +
							 * v1.getZ() + b2 * v2.getZ() + b3 * v3.getZ(); int
							 * zIndex = y * img.getWidth() + x; if
							 * (zBuffer[zIndex] < depth) { img.setRGB(x, y,
							 * getShade(t.getColor(), angleCos) .getRGB());
							 * zBuffer[zIndex] = depth; } } } }
							 */

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

						BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

						for (Shape shape : pentagonal.getShapes()) {
							if (shape.getType() == ShapeType.RECTANGLE) {
								Rectangle sq = (Rectangle) shape;

								Vertex v1 = transform.transform(sq.getVertex1());
								Vertex v2 = transform.transform(sq.getVertex2());
								Vertex v3 = transform.transform(sq.getVertex3());
								Vertex v4 = transform.transform(sq.getVertex4());

								v1.setX(v1.getX() + xOrigin);
								v1.setY(v1.getY() + yOrigin);
								v2.setX(v2.getX() + xOrigin);
								v2.setY(v2.getY() + yOrigin);
								v3.setX(v3.getX() + xOrigin);
								v3.setY(v3.getY() + yOrigin);
								v4.setX(v4.getX() + xOrigin);
								v4.setY(v4.getY() + yOrigin);

								g2.setColor(prism.getColor());
								Path2D path = new Path2D.Double();
								path.moveTo(v1.getX(), v1.getY());
								path.lineTo(v2.getX(), v2.getY());
								path.lineTo(v3.getX(), v3.getY());
								path.lineTo(v4.getX(), v4.getY());
								path.closePath();
								g2.draw(path);

								// compute rectangular bounds for square
								int minX = (int) Math.max(0, Math.ceil(Math.min(v1.getX(), Math.min(v2.getX(), Math.min(v3.getX(), v4.getX())))));
								int maxX = (int) Math.min(img.getWidth() - 1, Math.floor(Math.max(v1.getX(), Math.max(v2.getX(), Math.max(v3.getX(), v4.getX())))));
								int minY = (int) Math.max(0, Math.ceil(Math.min(v1.getY(), Math.min(v2.getY(), Math.min(v3.getY(), v4.getY())))));
								int maxY = (int) Math.min(img.getHeight() - 1, Math.floor(Math.max(v1.getY(), Math.max(v2.getY(), Math.max(v3.getY(), v4.getY())))));

								double squareArea = (maxX - minX) * (maxY - minY);
								// System.out.println("actual area: " +
								// squareArea);
								// squareArea = ((v1.getY() - v3.getY()) *
								// (v2.getX() - v3.getX()) +
								// (v2.getY() - v3.getY())
								// * (v3.getX() - v1.getX()));
								// System.out.println("area calc: " +
								// squareArea);

								for (int y = minY; y <= maxY; y++) {
									for (int x = minX; x <= maxX; x++) {
										double b1 = ((y - v3.getY()) * (v2.getX() - v3.getX()) + (v2.getY() - v3.getY()) * (v3.getX() - x)) / squareArea;
										double b2 = ((y - v1.getY()) * (v3.getX() - v1.getX()) + (v3.getY() - v1.getY()) * (v1.getX() - x)) / squareArea;
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
								Vertex v5 = transform.transform(pentagon.getVertex5());

								v1.setX(v1.getX() + xOrigin);
								v1.setY(v1.getY() + yOrigin);
								v2.setX(v2.getX() + xOrigin);
								v2.setY(v2.getY() + yOrigin);
								v3.setX(v3.getX() + xOrigin);
								v3.setY(v3.getY() + yOrigin);
								v4.setX(v4.getX() + xOrigin);
								v4.setY(v4.getY() + yOrigin);
								v5.setX(v5.getX() + xOrigin);
								v5.setY(v5.getY() + yOrigin);

								Path2D path = new Path2D.Double();
								path.moveTo(v1.getX(), v1.getY());
								path.lineTo(v2.getX(), v2.getY());
								path.lineTo(v3.getX(), v3.getY());
								path.lineTo(v4.getX(), v4.getY());
								path.lineTo(v5.getX(), v5.getY());
								path.lineTo(v1.getX(), v1.getY());
								path.closePath();
								g2.draw(path);

							}

							g2.drawImage(img, 0, 0, null);
						}
					}

				}
			}
		};
		
		MouseListener[] mouseListeners = UserInterface.paintPanel.getMouseListeners();
	
		for (int i = 0; i < mouseListeners.length; i++) {
			this.paintPanel.removeMouseListener(mouseListeners[i]);
		}
		
		UserInterface.paintPanel.addMouseListener(new PrismMouseListener(this, this.prismManager));

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

	public JSlider getHeadingSlider() {
		return this.headingSlider;
	}

	public JSlider getPitchSlider() {
		return this.pitchSlider;
	}

	public JButton getAddButton() {
		return this.addButton;
	}

	public JButton getRemoveButton() {
		return this.removeButton;
	}

	public JTextArea getSelectPrismLabel() {
		return this.selectPrismLabel;
	}

	public JButton getCubeButton() {
		return this.cubeButton;
	}

	public JButton getEquilateralButton() {
		return this.equilateralButton;
	}

	public JButton getPentagonalButton() {
		return this.pentagonalButton;
	}

	public JTextArea getNoColorLabel() {
		return this.noColorLabel;
	}

	public JTextArea getColorLabel() {
		return this.colorLabel;
	}

	public List getColorList() {
		return this.colorList;
	}

	public JTextArea getOriginLabel() {
		return this.originLabel;
	}

	public JTextArea getXLabel() {
		return this.xLabel;
	}

	public JTextArea getYLabel() {
		return this.yLabel;
	}

	public JTextArea getNoOriginLabel() {
		return this.noOriginLabel;
	}

	public JTextArea getInvalidOriginLabel() {
		return this.invalidOriginLabel;
	}

	public TextField getXOriginField() {
		return this.xOriginField;
	}

	public TextField getYOriginField() {
		return this.yOriginField;
	}

	public JTextArea getLengthLabel() {
		return this.lengthLabel;
	}

	public JTextArea getNoLengthLabel() {
		return this.noLengthLabel;
	}

	public JTextArea getInvalidLengthLabel() {
		return this.invalidLengthLabel;
	}

	public TextField getLengthField() {
		return this.lengthField;
	}

	public JTextArea getRadiusLabel() {
		return this.radiusLabel;
	}

	public JTextArea getNoRadiusLabel() {
		return this.noRadiusLabel;
	}

	public JTextArea getInvalidRadiusLabel() {
		return this.invalidRadiusLabel;
	}

	public TextField getRadiusField() {
		return this.radiusField;
	}

	public JTextArea getHeightLabel() {
		return this.heightLabel;
	}

	public JTextArea getNoHeightLabel() {
		return this.noHeightLabel;
	}

	public JTextArea getInvalidHeightLabel() {
		return this.invalidHeightLabel;
	}

	public TextField getHeightField() {
		return this.heightField;
	}

	public JButton getCreateButton() {
		return this.createButton;
	}
	
	public static int getWidth() {
		return UserInterface.WIDTH;
	}
	
	public static int getHeight() {
		return UserInterface.HEIGHT;
	}
	
	public Matrix3 getTransform() {
		return this.transform;
	}

}
