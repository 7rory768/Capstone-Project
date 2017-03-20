package shapes;

import java.awt.Color;
import java.util.ArrayList;

import util.Vertex;

public class Cube extends Prism {

	private ArrayList<Square> squares = new ArrayList<Square>();
	private int length;

	public Cube(int xOrigin, int yOrigin, int length, Color color) {
		super (xOrigin, yOrigin);
		this.length = length;
		this.createSquares(color);

	}
	
	public int getLength() {
		return this.length;
	}

	public ArrayList<Square> getSquares() {
		return this.squares;
	}
	
	public void createSquares(Color color) {
		this.squares.clear();
		int biggerHalf = (int) Math.ceil((double) length / 2.0);
		int smallerHalf = (int) Math.ceil((double) length / 2.0);
		int middleX = 0;
		int middleY = 0;
		int middleZ = smallerHalf;
		int largerXCord = middleX + biggerHalf;
		int largerYCord = middleY + biggerHalf;
		int largerZCord = middleZ + biggerHalf;
		int smallerXCord = middleX - smallerHalf;
		int smallerYCord = middleY - smallerHalf;
		int smallerZCord = middleZ - smallerHalf;

		Vertex v1 = new Vertex(largerXCord, largerYCord, largerZCord);
		Vertex v2 = new Vertex(smallerXCord, largerYCord, largerZCord);
		Vertex v3 = new Vertex(smallerXCord, smallerYCord, largerZCord);
		Vertex v4 = new Vertex(largerXCord, smallerYCord, largerZCord);
		this.squares.add(new Square(v1, v2, v3, v4, length, Color.RED));

		v3 = new Vertex(smallerXCord, largerYCord, smallerZCord);
		v4 = new Vertex(largerXCord, largerYCord, smallerZCord);
		this.squares.add(new Square(v1, v2, v3, v4, length, Color.GREEN));

		v1 = new Vertex(largerXCord, smallerYCord, smallerZCord);
		v2 = new Vertex(smallerXCord, smallerYCord, smallerZCord);
		this.squares.add(new Square(v1, v2, v3, v4, length, Color.ORANGE));

		v3 = new Vertex(smallerXCord, smallerYCord, largerZCord);
		v4 = new Vertex(largerXCord, smallerYCord, largerZCord);
		this.squares.add(new Square(v1, v2, v3, v4, length, Color.YELLOW));

		v1 = new Vertex(smallerXCord, smallerYCord, smallerZCord);
		v2 = v3;
		v3 = new Vertex(smallerXCord, largerYCord, largerZCord);
		v4 = new Vertex(smallerXCord, largerYCord, smallerZCord);
		this.squares.add(new Square(v1, v2, v3, v4, length, Color.BLUE));

		// LAST SQUARE WITH ALL LARGER X
		v1 = new Vertex(largerXCord, smallerYCord, smallerZCord);
		v2 = new Vertex(largerXCord, smallerYCord, largerZCord);
		v3 = new Vertex(largerXCord, largerYCord, largerZCord);
		v4 = new Vertex(largerXCord, largerYCord, smallerZCord);
		this.squares.add(new Square(v1, v2, v3, v4, length, Color.MAGENTA));
	}

}
