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

	List<Point> records;
	private double DIFFERENCE_RATE;
	
	public LocSystem() {
		list = new ArrayList<>();
		list.add(new Device(x1, y1));
		list.add(new Device(x2, y2));
		list.add(new Device(x3, y3));
		//list.add(new Device(x4, y4))；
		
		records = new ArrayList<>();
	}

	//every time we get a new list of distances, we first calculate its location 
	//then try to compare with the last records. 
	//if the distance between the new calculated point and the last valid point is within a logical range,
	//we keep the record.
	//distance(newPoint, lastPoint) / (newPoint.index - lastPoint.index) < DIFFERENCE_RATE;
	public Point getCurrentValidPoint() {
		//first we get the new point
		Point coor = calculate();
		//if this is the first point
		if (records.size() < 1) {
			records.add(coor);
		} else {
			double dis = Point.distance(coor, records.get(0)) / (coor.index - records.get(0).index);
			if (dis < DIFFERENCE_RATE) { //we renew the records list
				records.add(0, coor); //insert it into the records
				if (records.size() > 10) {
					records.remove(10); //remove the 11-th element
				}
				filter();
			} else if (records.size() == 1) {
				//if we have only one record, but the new one is not consistent with the old one, 
				//we replace the old one. 
				records.remove(0); 
				records.add(coor);
			}
		}
		
		return records.get(0);
	}
	
	private void filter() {
		double[] ratio = new double[] {1.0, 1.0/2, 1.0/4, 1.0/8, 1.0/16, 
				1.0/32, 1.0/64, 1.0/128, 1.0/256, 1.0/512, 1.0/1024};
		double x = 0.0;
		double y = 0.0;
		for (int i = 0; i < records.size() - 1; i++) {
			x += records.get(i).x * ratio[i + 1];
			y += records.get(i).y * ratio[i + 1];
		}
		int size = records.size();
		x += records.get(size - 1).x * ratio[size - 1];
		y += records.get(size - 1).y * ratio[size - 1];
		
		records.get(0).x = x;
		records.get(0).y = y;
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
