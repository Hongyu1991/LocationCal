package location;

public class Point {
	public double x;
	public double y;
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	@Override 
	public String toString() {
		String result = "The Point is " + "(" + x + ", " + y + ") ";
		return result;
	}
}
