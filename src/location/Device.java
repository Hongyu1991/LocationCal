package location;

public class Device {
	private int index;
	private double x;
	private double y;
	public double distance = 0;
	
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
	
	public double distance() {
		//return the distance between signal and the device. 
		distance = 1.0;
		return distance;
	}
	
}
