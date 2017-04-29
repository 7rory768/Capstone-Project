package util;

public class Vertex {
	private double x, y, z;

	public Vertex(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public double getZ() {
		return this.z;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public Vertex clone() {
		return new Vertex(this.x, this.y, this.z);
	}
	
	public String toString() {
		return "{X = " + this.x + ", Y = " + this.y + ", Z = " + this.z + "}";
	}
}
