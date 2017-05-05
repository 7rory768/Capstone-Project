package tester;

import managers.LightManager;
import prisms.Cube;
import prisms.Equilateral;
import prisms.Pentagonal;
import shapes.Triangle;
import userinterface.InterfaceActions;
import managers.PrismManager;
import userinterface.UserInterface;
import util.Vertex;

import java.awt.*;
import java.util.ArrayList;

public class Tester {

	private static final InterfaceActions interfaceActions = new InterfaceActions();
	private static final PrismManager prismManager = new PrismManager();
	private static final LightManager lightManager = new LightManager();
	private static final UserInterface userInterface = new UserInterface(Tester.interfaceActions, Tester.prismManager);

	public static void setupInterface() {
		Tester.userInterface.setupInterface();
	}
	
	public static void main(String[] args) {
		Tester.setupInterface();

		Tester.prismManager.addPrism(new Cube(new Vertex(70, 300, 0), 100, Color.GREEN));
		Tester.prismManager.addPrism(new Equilateral(new Vertex(245, 300, 0), 100, Color.GREEN));
		Tester.prismManager.addPrism(new Pentagonal(new Vertex(420, 300, 0), 75, 50, Color.GREEN));
	}

	public static ArrayList<Triangle> inflate(ArrayList<Triangle> tris) {
		ArrayList<Triangle> result = new ArrayList<Triangle>();
		for (Triangle t : tris) {
			Vertex m1 = new Vertex((t.getVertex1().getX() + t.getVertex2().getX()) / 2,
					(t.getVertex1().getY() + t.getVertex2().getY()) / 2,
					(t.getVertex1().getZ() + t.getVertex2().getZ()) / 2);
			Vertex m2 = new Vertex((t.getVertex2().getX() + t.getVertex3().getX()) / 2,
					(t.getVertex2().getY() + t.getVertex3().getY()) / 2,
					(t.getVertex2().getZ() + t.getVertex3().getZ()) / 2);
			Vertex m3 = new Vertex((t.getVertex1().getX() + t.getVertex3().getX()) / 2,
					(t.getVertex1().getY() + t.getVertex3().getY()) / 2,
					(t.getVertex1().getZ() + t.getVertex3().getZ()) / 2);
			Color color = t.getColor();
			result.add(new Triangle(t.getVertex1(), m1, m3, color));
			result.add(new Triangle(t.getVertex2(), m1, m2, color));
			result.add(new Triangle(t.getVertex3(), m2, m3, color));
			result.add(new Triangle(m1, m2, m3, color));
		}
		for (Triangle t : result) {
			for (Vertex v : new Vertex[] { t.getVertex1(), t.getVertex2(), t.getVertex3() }) {
				double l = Math.sqrt(v.getX() * v.getX() + v.getY() * v.getY() + v.getZ() * v.getZ())
						/ Math.sqrt(30000);
				v.setX(v.getX() / l);
				v.setY(v.getY() / l);
				v.setZ(v.getZ() / l);
			}
		}
		return result;
	}

}