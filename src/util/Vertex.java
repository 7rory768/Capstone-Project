package util;

public class Vertex {
	public double x;
	public double y;
	public double z;

	public Vertex(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void addToXandY(int xAdd, int yAdd) {
		this.x += xAdd;
		this.y += yAdd;
	}
}
