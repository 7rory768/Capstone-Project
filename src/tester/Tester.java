package tester;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import prisms.Cube;
import prisms.Equilateral;
import prisms.Prism;

import shapes.Rectangle;
import shapes.Triangle;
import userinterface.InterfaceActions;
import userinterface.PrismManager;
import userinterface.UserInterface;
import util.PrismType;
import util.Vertex;
import util.Matrix3;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Tester {

	private static final InterfaceActions interfaceActions = new InterfaceActions();
	private static final PrismManager prismManager = new PrismManager();
	private static final UserInterface userInterface = new UserInterface(Tester.interfaceActions, Tester.prismManager);

	public static void setupInterface() {
		Tester.userInterface.setupInterface();
	}
	
	public static void main(String[] args) {
		Tester.setupInterface();
		
		Tester.prismManager.addPrism(new Equilateral(150, 150, 200, Color.GREEN));
		Tester.prismManager.addPrism(new Cube(100, 100, 150, Color.GREEN));
	}

	public static ArrayList<Triangle> inflate(ArrayList<Triangle> tris) {
		ArrayList<Triangle> result = new ArrayList<Triangle>();
		for (Triangle t : tris) {
			Vertex m1 = new Vertex((t.getVertex1().x + t.getVertex2().x) / 2,
					(t.getVertex1().y + t.getVertex2().y) / 2,
					(t.getVertex1().z + t.getVertex2().z) / 2);
			Vertex m2 = new Vertex((t.getVertex2().x + t.getVertex3().x) / 2,
					(t.getVertex2().y + t.getVertex3().y) / 2,
					(t.getVertex2().z + t.getVertex3().z) / 2);
			Vertex m3 = new Vertex((t.getVertex1().x + t.getVertex3().x) / 2,
					(t.getVertex1().y + t.getVertex3().y) / 2,
					(t.getVertex1().z + t.getVertex3().z) / 2);
			Color color = t.getColor();
			result.add(new Triangle(t.getVertex1(), m1, m3, color));
			result.add(new Triangle(t.getVertex2(), m1, m2, color));
			result.add(new Triangle(t.getVertex3(), m2, m3, color));
			result.add(new Triangle(m1, m2, m3, color));
		}
		for (Triangle t : result) {
			for (Vertex v : new Vertex[] { t.getVertex1(), t.getVertex2(), t.getVertex3() }) {
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