package gti310.tp3;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Point {
	
	private int ID;
	
	private Map<Point,Integer> descendants=new HashMap<Point,Integer>();
	
	/**
	 * Constructeur minimum d'un point.
	 * @param ID, id du point
	 */
	public Point(int ID){
		this.ID=ID;
	}
	
	/**
	 * getter du point
	 * @return, ID du point
	 */
	public int getID(){
		return this.ID;
	}
	
	/**
	 * Ajoute un décendant au point.
	 * @param point. le décendant
	 * @param weight, le point de la route
	 */
	public void addDescendant(Point point, int weight){
		if(descendants.containsKey(point)){
			System.out.format("Point %d already contains point %d.",this.ID,point.getID());
		}
		descendants.put(point, weight);
	}
	
	/**
	 * Retourne le poids de la route vers un décendant.
	 * @param point
	 * @return
	 */
	public int getWeight(Point point){
		return descendants.get(point);
	}
	
	/**
	 * Getter des descendants
	 * @return, un Set des décendants.
	 */
	public Set<Point> getDecendents(){
		return descendants.keySet();
	}
	
	public boolean hasDescendant(Point point){
		if(descendants.containsKey(point)){
			return true;
		}
		return false;
	}
}
