package location;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class LocSystem {
	private List<Device> list;
	private int num;

	private double x1 = 0;
	private double y1 = 0;
	private double x2 = 0;
	private double y2 = 0;
	private double x3 = 0;
	private double y3 = 0;
	private double x4 = 0;
	private double y4 = 0;
	private double x5 = 0;
	private double y5 = 0;
	
	public LocSystem() {
		list = new ArrayList<>();
		num = 0;
		list.add(new Device(x1, y1));
		list.add(new Device(x2, y2));
		list.add(new Device(x3, y3));
	}
	
	//calculate the signal location
	public Point calculate() {
		Point coor = new Point(0, 0);
		Device[] nearest = select();
		
		
		return coor;
	}
	
	
	
	//select the nearest three device to process the location calculation
	private Device[] select() {
		Device[] selected = new Device[3];
		//create a min heap to get the nearest device
		PriorityQueue<Device> pq = new PriorityQueue<>(new MyComparator());
		for (int i = 0; i < list.size(); i++) {
			pq.offer(list.get(i));
		}
		for (int i = 0; i < 3; i++) {
			selected[i] = pq.poll();
		}
		return selected;
	}
	
	class MyComparator implements Comparator<Device> {
		@Override 
		public int compare(Device d1, Device d2) {
			if (d1.distance == d2.distance) {
				return 0;
			} else {
				return d1.distance < d2.distance ? -1 : 1;
			}
		}
	}
	
}
