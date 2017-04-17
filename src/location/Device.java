package location;

public class Device {
	private int index;
	public double x;
	public double y;
	public double dis = 0;
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public Device(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	@Override 
	public String toString() {
		String result = "The device location is " + "(" + x + ", " + y + ") ";
		return result;
	}
	
	
	
	public double distance() {
		//return the distance between signal and the device. 
		double x1 = 2.6;
		double y1 = 3;
		dis = Math.sqrt((x - x1) * (x - x1) + (y - y1) * (y - y1));
		return dis;
	}
	
}
