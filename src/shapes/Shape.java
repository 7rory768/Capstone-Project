package shapes;

import java.awt.Color;

public class Shape {
	
	private Color color;
	
	public Shape() {
		this.color = Color.GREEN;
	}
	
	public Shape(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}

}
