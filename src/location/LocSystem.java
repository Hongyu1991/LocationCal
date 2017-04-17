package location;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class LocSystem {
	private List<Device> list;

	private double x1 = 100;
	private double y1 = 200.1;
	private double x2 = 100.1;
	private double y2 = 0.4;
	private double x3 = 0.3;
	private double y3 = 1.2;
	private double x4 = 0.31;
	private double y4 = 0.1;
//	private double x5 = 0;
//	private double y5 = 0;

	public LocSystem() {
		list = new ArrayList<>();
		list.add(new Device(x1, y1));
		list.add(new Device(x2, y2));
		list.add(new Device(x3, y3));
		//list.add(new Device(x4, y4));
	}
	
	//calculate the signal location
	public Point calculate() {
		Point coor = new Point(0, 0);
		Device[] nearest = select();
		if (nearest.length == 3) {
			calculate(nearest, coor);
			return coor;
		} else {
			return null;
		}
	}
	private void calculate(Device[] ds, Point coor) {
		double A1 = 2.0 * (ds[1].x - ds[0].x);
		double B1 = 2.0 * (ds[1].y - ds[0].y);
		double C1 = (ds[0].x * ds[0].x - ds[1].x * ds[1].x) + 
				(ds[0].y * ds[0].y - ds[1].y * ds[1].y) -
				(ds[0].dis * ds[0].dis - ds[1].dis * ds[1].dis);
		double A2 = 2.0 * (ds[2].x - ds[0].x);
		double B2 = 2.0 * (ds[2].y - ds[0].y);
		double C2 = (ds[0].x * ds[0].x - ds[2].x * ds[2].x) + 
				(ds[0].y * ds[0].y - ds[2].y * ds[2].y) -
				(ds[0].dis * ds[0].dis - ds[2].dis * ds[2].dis);
		
		coor.x = - (C1 - B1 * C2 / B2) / (A1 - B1 * A2 / B2);
		coor.y = - (C1 - A1 * C2 / A2) / (B1 - B2 * A1 / A2 );
	}
	
	
	
	//select the nearest three device to process the location calculation
	private Device[] select() {
		Device[] selected = new Device[3];
		//create a min heap to get the nearest device
		PriorityQueue<Device> pq = new PriorityQueue<>(new MyComparator());
		for (int i = 0; i < list.size(); i++) {
			list.get(i).distance();  //before put device into the pq, renew its current distance to the signal
			pq.offer(list.get(i)); //pq's order is based on the distance.
		}
		for (int i = 0; i < 3; i++) {
			selected[i] = pq.poll();
			System.out.println(selected[i]);
		}
		return selected;
	}
	
	class MyComparator implements Comparator<Device> {
		@Override 
		public int compare(Device d1, Device d2) {
			if (d1.dis == d2.dis) {
				return 0;
			} else {
				return d1.dis < d2.dis ? -1 : 1;
			}
		}
	}
	
}
