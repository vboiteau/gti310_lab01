package gti310.tp3;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Point {
	
	private int ID;
	
	private Map<Point,Integer> decendents=new HashMap<Point,Integer>();
	
	public Point(int ID){
		this.ID=ID;
	}
	
	public int getID(){
		return this.ID;
	}
	
	public void addDecendent(Point point, int weight){
		if(decendents.containsKey(point)){
			System.out.format("Point %d already contains point %d.",this.ID,point.getID());
		}
		decendents.put(point, weight);
	}
	
	public int getWeight(Point point){
		return decendents.get(point);
	}
	
	public Set<Point> getDecendents(){
		return decendents.keySet();
	}
	
}
