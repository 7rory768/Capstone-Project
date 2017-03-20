package tester;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import shapes.Cube;
import shapes.Square;
import shapes.Triangle;
import util.Vertex;
import util.Matrix3;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Tester {

	public static JFrame frame;
	public static Container pane;
	public static JSlider pitchSlider;
	public static JSlider headingSlider;
	public static Matrix3 transform;

	private static ArrayList<Triangle> tris = new ArrayList<Triangle>();
	private static ArrayList<Square> squares = new ArrayList<Square>();
	private static ArrayList<Cube> cubes = new ArrayList<Cube>();

	public static void main(String[] args) {
		frame = new JFrame();
		frame.setSize(750, 750);
		pane = frame.getContentPane();
		pane.setLayout(new BorderLayout());

		headingSlider = new JSlider(0, 360, 180);
		pane.add(headingSlider, BorderLayout.SOUTH);

		// slider to control vertical rotation
		pitchSlider = new JSlider(SwingConstants.VERTICAL, -90, 90, 0);
		pane.add(pitchSlider, BorderLayout.EAST);

		tris.add(new Triangle(new Vertex(100, 100, 100), new Vertex(-100, -100, 100),
				new Vertex(-100, 100, -100), Color.RED));
		tris.add(new Triangle(new Vertex(100, 100, 100), new Vertex(-100, -100, 100),
				new Vertex(100, -100, -100), Color.RED));
		tris.add(new Triangle(new Vertex(-100, 100, -100), new Vertex(100, -100, -100),
				new Vertex(100, 100, 100), Color.RED));
		tris.add(new Triangle(new Vertex(-100, 100, -100), new Vertex(-100, -100, 100),
				new Vertex(100, -100, -100), Color.RED));

		int squareSize = 150;
		cubes.add(new Cube(100, 100, squareSize, Color.GREEN));

		// slider to control horizontal rotation

		// panel to display render results
		final JPanel renderPanel = new JPanel() {

			public void paintComponent(Graphics g) {

				Graphics2D g2 = (Graphics2D) g;
				g2.setColor(Color.BLACK);
				g2.fillRect(0, 0, getWidth(), getHeight());

				g2.setColor(Color.WHITE);

				double heading = Math.toRadians(headingSlider.getValue());
				Matrix3 headingTransform = new Matrix3(new double[] { Math.cos(heading),
						0, Math.sin(heading), 0, 1, 0, -Math.sin(heading), 0,
						Math.cos(heading) });

				double pitch = Math.toRadians(pitchSlider.getValue());
				Matrix3 pitchTransform = new Matrix3(new double[] { 1, 0, 0, 0,
						Math.cos(pitch), Math.sin(pitch), 0, -Math.sin(pitch),
						Math.cos(pitch) });

				transform = headingTransform.multiply(pitchTransform);

				//this.createTrianglePrism(g2);
				this.createSquarePrism(g2);
				// for (Cube cube : cubes) {
				// int xOrigin = cube.getxOrigin();
				// int yOrigin = cube.getyOrigin();
				// for (Square s : cube.getSquares()) {
				// // g2.translate(getWidth() / 2, getHeight() / 2);
				//
				// Vertex v1 = transform.transform(s.v1);
				// v1.addToXandY(xOrigin, yOrigin);
				// Vertex v2 = transform.transform(s.v2);
				// v2.addToXandY(xOrigin, yOrigin);
				// Vertex v3 = transform.transform(s.v3);
				// v3.addToXandY(xOrigin, yOrigin);
				// Vertex v4 = transform.transform(s.v4);
				// v4.addToXandY(xOrigin, yOrigin);
				//
				// Path2D path = new Path2D.Double();
				// path.moveTo(v1.x, v1.y);
				// path.lineTo(v2.x, v2.y);
				// path.lineTo(v3.x, v3.y);
				// path.lineTo(v4.x, v4.y);
				// path.closePath();
				// g2.draw(path);
				//
				// }
				// }

				// g2.translate(getWidth() / 2, getHeight() / 2);

				headingSlider.addChangeListener(new ChangeListener() {

					public void stateChanged(ChangeEvent arg0) {
						repaint();
					}
				});
				pitchSlider.addChangeListener(new ChangeListener() {

					public void stateChanged(ChangeEvent arg0) {
						repaint();
					}
				});

			}

			public void createSquarePrism(Graphics2D g2) {

				// double[] zBuffer = new double[img.getWidth() *
				// img.getHeight()];
				//
				// for (int q = 0; q < zBuffer.length; q++) {
				// zBuffer[q] = Double.NEGATIVE_INFINITY;
				// }
				for (Cube cube : cubes) {

					BufferedImage img = new BufferedImage(getWidth(), getHeight(),
							BufferedImage.TYPE_INT_ARGB);

					int xOrigin = cube.getxOrigin();
					int yOrigin = cube.getyOrigin();
					int length = cube.getLength();

					int num = 0;
					for (Square sq : cube.getSquares()) {
						num++;
						
						if (num == 1) {
							System.out.println("BEFORE VERTEX MODIFICATION:");
							System.out.println("V1: (" + sq.v1.x + ", " + sq.v1.y + ")");
							System.out.println("V2: (" + sq.v2.x + ", " + sq.v2.y + ")");
							System.out.println("V3: (" + sq.v3.x + ", " + sq.v3.y + ")");
							System.out.println("-------------------------------------------");
						}

						Vertex v1 = transform.transform(sq.v1);
						Vertex v2 = transform.transform(sq.v2);
						Vertex v3 = transform.transform(sq.v3);
						Vertex v4 = transform.transform(sq.v4);

						if (num == 1) {
							System.out.println("AFTER VERTEX TRANSFORMATION:");
							System.out.println("V1: (" + v1.x + ", " + v1.y + ")");
							System.out.println("V2: (" + v2.x + ", " + v2.y + ")");
							System.out.println("V3: (" + v3.x + ", " + v3.y + ")");
							System.out.println("-------------------------------------------");
						}

						v1.x += xOrigin;
						v1.y += yOrigin;
						v2.x += xOrigin;
						v2.y += yOrigin;
						v3.x += xOrigin;
						v3.y += yOrigin;
						v4.x += xOrigin;
						v4.y += yOrigin;

						Path2D path = new Path2D.Double();
						path.moveTo(v1.x, v1.y);
						path.lineTo(v2.x, v2.y);
						path.lineTo(v3.x, v3.y);
						path.lineTo(v4.x, v4.y);
						path.closePath();
						g2.draw(path);

						// compute rectangular bounds for square
						int minX = (int) Math.max(
								0,
								Math.ceil(Math.min(v1.x,
										Math.min(v2.x, Math.min(v3.x, v4.x)))));
						int maxX = (int) Math.min(
								img.getWidth() - 1,
								Math.floor(Math.max(v1.x,
										Math.max(v2.x, Math.max(v3.x, v4.x)))));
						int minY = (int) Math.max(
								0,
								Math.ceil(Math.min(v1.y,
										Math.min(v2.y, Math.min(v3.y, v4.y)))));
						int maxY = (int) Math.min(
								img.getHeight() - 1,
								Math.floor(Math.max(v1.y,
										Math.max(v2.y, Math.max(v3.y, v4.y)))));

						double squareArea = (maxX - minX) * (maxY - minY);
						//System.out.println("actual area: " + squareArea);
//						squareArea = ((v1.y - v3.y) * (v2.x - v3.x) + (v2.y - v3.y)
//										* (v3.x - v1.x));
						//System.out.println("area calc: " + squareArea);

						if (num == 1) {
							System.out.println("AFTER ALL VERTEX MODIFICATION:");
							System.out.println("V1: (" + v1.x + ", " + v1.y + ")");
							System.out.println("V2: (" + v2.x + ", " + v2.y + ")");
							System.out.println("V3: (" + v3.x + ", " + v3.y + ")");
							System.out.println("-------------------------------------------");
						}
						
						if (num == 1) {
							System.out.println("Min X: " + minX);
							System.out.println("Max X: " + maxX);
							System.out.println("Min Y: " + minY);
							System.out.println("Max Y: " + maxY);
						}

						for (int y = minY; y <= maxY; y++) {
							for (int x = minX; x <= maxX; x++) {
								double b1 = ((y - v3.y) * (v2.x - v3.x) + (v2.y - v3.y)
										* (v3.x - x))
										/ squareArea;
								double b2 = ((y - v1.y) * (v3.x - v1.x) + (v3.y - v1.y)
										* (v1.x - x))
										/ squareArea;
//								double b1 = (maxX - x) * (maxY - y)
//										/ squareArea;
//								double b2 = (x - minX) * (y - minY)
//										/ squareArea;
								//double b3 = ((y - v2.y) * (v1.x - v2.x) + (v1.y - v2.y)
									//	* (v2.x - x))
									//	/ squareArea;
								//double b4 = ((y - v4.y) * (v3.x - v4.x) + (v3.y - v4.y)
												//* (v4.x - x));
								//double b5 = ((y - v3.y) * (v4.x - v3.x) + (v4.y - v3.y)
								//		* (v3.x - x));
								//double b6 = ((y - v2.y) * (v4.x - v2.x) + (v4.y - v2.y)
								//		* (v2.x - x));
								
								if (x < minX + 1 && y < minY + 1 && num == 1) {
									System.out.println("B1: " + b1);
									System.out.println("B2: " + b2);
									//System.out.println("B3: " + b3);
								}
								
								if (b1 >= 0 && b1 <= 1 && b2 >= 0 && b2 <= 1){ //&& b3 >= 0
										//&& b3 <= 1 && b4 <= 1 && b4 >= 0 && b5 <= 1 && b5 >= 0
										//&& b6 <= 1 && b6 >= 0) {
									img.setRGB(x, y, sq.color.getRGB());
								}
							}
						}
					}

					g2.drawImage(img, 0, 0, null);
				}

			}

			public void createTrianglePrism(Graphics2D g2) {

				// g2.translate(getWidth() / 2, getHeight() / 2);
				BufferedImage img = new BufferedImage(getWidth(), getHeight(),
						BufferedImage.TYPE_INT_ARGB);

				double[] zBuffer = new double[img.getWidth() * img.getHeight()];

				for (int q = 0; q < zBuffer.length; q++) {
					zBuffer[q] = Double.NEGATIVE_INFINITY;
				}

				int num = 0;
				for (Triangle t : tris) {
					num++;

					if (num == 1) {
						System.out.println("BEFORE VERTEX MODIFICATION:");
						System.out.println("V1: (" + t.v1.x + ", " + t.v1.y + ")");
						System.out.println("V2: (" + t.v2.x + ", " + t.v2.y + ")");
						System.out.println("V3: (" + t.v3.x + ", " + t.v3.y + ")");
						System.out.println("-------------------------------------------");
					}
					
					Vertex v1 = transform.transform(t.v1);
					Vertex v2 = transform.transform(t.v2);
					Vertex v3 = transform.transform(t.v3);

					if (num == 1) {
						System.out.println("AFTER VERTEX TRANSFORMATION:");
						System.out.println("V1: (" + v1.x + ", " + v1.y + ")");
						System.out.println("V2: (" + v2.x + ", " + v2.y + ")");
						System.out.println("V3: (" + v3.x + ", " + v3.y + ")");
						System.out.println("-------------------------------------------");
					}
					
					v1.x += getWidth() / 2;
					v1.y += getHeight() / 2;
					v2.x += getWidth() / 2;
					v2.y += getHeight() / 2;
					v3.x += getWidth() / 2;
					v3.y += getHeight() / 2;

					Vertex ab = new Vertex(v2.x - v1.x, v2.y - v1.y, v2.z - v1.z);
					Vertex ac = new Vertex(v3.x - v1.x, v3.y - v1.y, v3.z - v1.z);
					Vertex norm = new Vertex(ab.y * ac.z - ab.z * ac.y, ab.z * ac.x
							- ab.x * ac.z, ab.x * ac.y - ab.y * ac.x);

					double normalLength = Math.sqrt(norm.x * norm.x + norm.y + norm.z
							* norm.z);
					norm.x /= normalLength;
					norm.y /= normalLength;
					norm.z /= normalLength;

					double angleCos = Math.abs(norm.z);

					Path2D path = new Path2D.Double();
					path.moveTo(v1.x, v1.y);
					path.lineTo(v2.x, v2.y);
					path.lineTo(v3.x, v3.y);
					path.closePath();
					g2.draw(path);

					// since we are not using Graphics2D anymore,
					// we have to do translation manually

					// compute rectangular bounds for triangle
					int minX = (int) Math.max(0,
							Math.ceil(Math.min(v1.x, Math.min(v2.x, v3.x))));
					int maxX = (int) Math.min(img.getWidth() - 1,
							Math.floor(Math.max(v1.x, Math.max(v2.x, v3.x))));
					int minY = (int) Math.max(0,
							Math.ceil(Math.min(v1.y, Math.min(v2.y, v3.y))));
					int maxY = (int) Math.min(img.getHeight() - 1,
							Math.floor(Math.max(v1.y, Math.max(v2.y, v3.y))));

					double triangleArea = (v1.y - v3.y) * (v2.x - v3.x) + (v2.y - v3.y)
							* (v3.x - v1.x);

					if (num == 1) {
						System.out.println("AFTER ALL VERTEX MODIFICATION:");
						System.out.println("V1: (" + v1.x + ", " + v1.y + ")");
						System.out.println("V2: (" + v2.x + ", " + v2.y + ")");
						System.out.println("V3: (" + v3.x + ", " + v3.y + ")");
						System.out.println("-------------------------------------------");
					}
					
					if (num == 1) {
						System.out.println("Min X: " + minX);
						System.out.println("Max X: " + maxX);
						System.out.println("Min Y: " + minY);
						System.out.println("Max Y: " + maxY);
					}

					for (int y = minY; y <= maxY; y++) {
						for (int x = minX; x <= maxX; x++) {
							double b1 = ((y - v3.y) * (v2.x - v3.x) + (v2.y - v3.y)
									* (v3.x - x))
									/ triangleArea;
							double b2 = ((y - v1.y) * (v3.x - v1.x) + (v3.y - v1.y)
									* (v1.x - x))
									/ triangleArea;
							double b3 = ((y - v2.y) * (v1.x - v2.x) + (v1.y - v2.y)
									* (v2.x - x))
									/ triangleArea;
							if (x == minX && y == minY && num == 1) {
								System.out.println("B1: " + b1);
								System.out.println("B2: " + b2);
								System.out.println("B3: " + b3);
							}
							if (b1 >= 0 && b1 <= 1 && b2 >= 0 && b2 <= 1 && b3 >= 0
									&& b3 <= 1) {
								double depth = b1 + v1.z + b2 * v2.z + b3 * v3.z;
								int zIndex = y * img.getWidth() + x;
								if (zBuffer[zIndex] < depth) {
									img.setRGB(x, y, getShade(t.color, angleCos).getRGB());
									zBuffer[zIndex] = depth;
								}
							}
						}
					}

					g2.drawImage(img, 0, 0, null);
				}
			}

		};

		pane.add(renderPanel, BorderLayout.CENTER);

		frame.setVisible(true);
	}

	public static Color getShade(Color color, double shade) {
		double redLinear = Math.pow(color.getRed(), 2.4) * shade;
		double greenLinear = Math.pow(color.getGreen(), 2.4) * shade;
		double blueLinear = Math.pow(color.getBlue(), 2.4) * shade;

		int red = (int) Math.pow(redLinear, 1 / 2.4);
		int green = (int) Math.pow(greenLinear, 1 / 2.4);
		if (green > 255) {
			green = 255;
		}
		int blue = (int) Math.pow(blueLinear, 1 / 2.4);
		// System.out.println("Red: " + red + ", Green: " + green + ", Blue: " +
		// blue);
		return new Color(red, green, blue);
	}

	public static ArrayList<Triangle> inflate(ArrayList<Triangle> tris) {
		ArrayList<Triangle> result = new ArrayList<Triangle>();
		for (Triangle t : tris) {
			Vertex m1 = new Vertex((t.v1.x + t.v2.x) / 2, (t.v1.y + t.v2.y) / 2,
					(t.v1.z + t.v2.z) / 2);
			Vertex m2 = new Vertex((t.v2.x + t.v3.x) / 2, (t.v2.y + t.v3.y) / 2,
					(t.v2.z + t.v3.z) / 2);
			Vertex m3 = new Vertex((t.v1.x + t.v3.x) / 2, (t.v1.y + t.v3.y) / 2,
					(t.v1.z + t.v3.z) / 2);
			result.add(new Triangle(t.v1, m1, m3, t.color));
			result.add(new Triangle(t.v2, m1, m2, t.color));
			result.add(new Triangle(t.v3, m2, m3, t.color));
			result.add(new Triangle(m1, m2, m3, t.color));
		}
		for (Triangle t : result) {
			for (Vertex v : new Vertex[] { t.v1, t.v2, t.v3 }) {
				double l = Math.sqrt(v.x * v.x + v.y * v.y + v.z * v.z)
						/ Math.sqrt(30000);
				v.x /= l;
				v.y /= l;
				v.z /= l;
			}
		}
		return result;
	}

}