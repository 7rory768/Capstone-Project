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

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;

public class UserInterface {

	private final InterfaceActions interfaceActions;
	private final PrismManager prismManager;

	private static JPanel paintPanel;
	private JPanel buttonPanel, pitchPanel, headingPanel;
	private final static int WIDTH = 1000;
	private final static int HEIGHT = 600;
	private final static int HEADING_DIVIDER = 35;
	private final static int PITCH_DIVIDER = HEIGHT - HEADING_DIVIDER;

	private JFrame frame;
	private JSlider pitchSlider;
	private JSlider headingSlider;
	private Matrix3 transform;
	private JSplitPane paintSplit, buttonSplit;
    private JButton button; 

	public UserInterface(InterfaceActions interfaceActions, PrismManager prismManager) {
		this.interfaceActions = interfaceActions;
		this.prismManager = prismManager;
	}

	public void setupInterface() {
		this.setupButtons();
		this.setupPanels();
		this.addButtons();
		this.frame.pack();
		this.frame.setVisible(true);
		//repaint();
	}

	private void setupButtons() {
		this.headingSlider = new JSlider(0, 360, 180);
		this.pitchSlider = new JSlider(SwingConstants.VERTICAL, -90, 90, 0);
		this.pitchPanel = new JPanel();
		this.headingPanel = new JPanel();
		this.buttonSplit = new JSplitPane();
		this.paintSplit = new JSplitPane();
		this.button = new JButton("button");

		this.headingSlider.setIgnoreRepaint(true);
		this.pitchSlider.setIgnoreRepaint(true);
		this.button.setIgnoreRepaint(true);
	}
	
	private void addButtons() {
		this.paintSplit.setOrientation(JSplitPane.VERTICAL_SPLIT);  
		this.paintSplit.setDividerLocation(PITCH_DIVIDER);
		this.paintSplit.setTopComponent(paintPanel);
		this.paintSplit.setBottomComponent(headingPanel);
		
		this.buttonSplit.setOrientation(JSplitPane.HORIZONTAL_SPLIT);  
		this.buttonSplit.setDividerLocation(HEADING_DIVIDER);
		this.buttonSplit.setLeftComponent(this.pitchPanel);
		this.buttonSplit.setRightComponent(buttonPanel);

        // our topPanel doesn't need anymore for this example. Whatever you want it to contain, you can add it here
        paintPanel.setLayout(new BoxLayout(paintPanel, BoxLayout.Y_AXIS)); // BoxLayout.Y_AXIS will arrange the content vertically
        paintPanel.setMinimumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setMinimumSize(new Dimension(Integer.MAX_VALUE, 0));
      
        // let's set the maximum size of the inputPanel, so it doesn't get too big when the user resizes the window
        pitchPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));     // we set the max height to 75 and the max width to (almost) unlimited

        pitchPanel.add(pitchSlider);
        
        headingPanel.setMaximumSize(new Dimension(20, Integer.MAX_VALUE));
        headingPanel.add(headingSlider);
        
        buttonPanel.add(button);
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
				g2.fillRect(0, 0, UserInterface.WIDTH/2, UserInterface.HEIGHT);
				g2.setColor(Color.BLACK);

				double heading = Math.toRadians(headingSlider.getValue());
				Matrix3 headingTransform = new Matrix3(new double[] { Math.cos(heading),
						0, Math.sin(heading), 0, 1, 0, -Math.sin(heading), 0,
						Math.cos(heading) });

				double pitch = Math.toRadians(pitchSlider.getValue());
				Matrix3 pitchTransform = new Matrix3(new double[] { 1, 0, 0, 0,
						Math.cos(pitch), Math.sin(pitch), 0, -Math.sin(pitch),
						Math.cos(pitch) });

				transform = headingTransform.multiply(pitchTransform);

				this.createPrisms(g2);
				Path2D path = new Path2D.Double();
				path.moveTo(UserInterface.WIDTH, 0);
				path.lineTo(UserInterface.WIDTH, UserInterface.HEIGHT);
				path.closePath();
				g2.draw(path);

				// g2.translate(getWidth() / 2, getHeight() / 2);

				headingSlider.addChangeListener(interfaceActions
						.getHeadingChangeListener());
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
							Square sq = (Square) shape;
							num++;

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
									// * (v4.getX() - 
									//this.pane.add(UserInterface.jPanel2, BorderLayout.CENTER);x));

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
						BufferedImage img = new BufferedImage(getWidth(), getHeight(),
								BufferedImage.TYPE_INT_ARGB);

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

							/*v1.setX(v1.getX() + getWidth() / 2);
							v1.setY(v1.getY() + getHeight() / 2);
							v2.setX(v2.getX() + getWidth() / 2);
							v2.setY(v2.getY() + getHeight() / 2);
							v3.setX(v3.getX() + getWidth() / 2);
							v3.setY(v3.getY() + getHeight() / 2);*/

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
/*							for (int y = minY; y <= maxY; y++) {
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
							}*/

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
