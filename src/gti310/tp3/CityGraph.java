package gti310.tp3;

import java.util.Set;

public class CityGraph {
	private Point[] points;
	@SuppressWarnings("unused")
	private int infinityWeight;
	private int startID;
	private int pointsSetted=0;
	private int numberOfRoutes=0;
	public CityGraph(int size, int infinityWeight, int startID){
		this.points = new Point[size];
		this.infinityWeight=infinityWeight;
		this.startID=startID;
	}
	
	/**
	 * Return the first Point of the 
	 * @return
	 */
	public int getStart(){
		return this.startID;
	}
	
	/**
	 * Check if we reach the maximum
	 * @param depth, la profondeur de la route actuel
	 * @return, la
	 */
	public boolean checkDepth(int depth){
		boolean valid=false;
		if(this.numberOfRoutes+1==depth){
			valid=false;
		}else{
			valid=true;
		}
		return valid;
	}
	
	public int getDepth(){
		return this.numberOfRoutes;
	}
	
	/**
	 * Getter des ids de tous les descendants d'un point
	 * @param ID, Id du point de base
	 * @return, return le tableau d'id
	 */
	public int[] getDescendants(int ID){
		Set<Point> descendants=getPoint(ID).getDescendants();
		int[] descendants_ID = new int[descendants.size()];
		int i=0;
		for(Point descendant: descendants){
			descendants_ID[i]=descendant.getID();
			i++;
		}
		return descendants_ID;
	}
	
	/**
	 * Retourne un point demandé
	 * @param ID, le id du point demandé
	 * @return, le point demandé ou null en cas d'erreur.
	 */
	private Point getPoint(int ID){
		Point actual=null;
		if((actual=pointExist(ID))==null){
			actual=new Point(ID);
			points[pointsSetted]=actual;
			pointsSetted++;
		}
		return actual;
	}
	
	/**
	 * Compare la grandeur du tableau de point
	 * @param size, valeur à qui est demandé.
	 * @return boolean true si la grandeur est la même
	 */
	public boolean checkSize(int size){
		return size==pointsSetted;
	}
	
	/**
	 * Ajoute une route (arrête) au graph de la ville.
	 * @param sid, id du point de départ
	 * @param eid, id du point de sortie
	 * @param weight, poids de la route
	 */
	public void addRoute(int sid, int eid, int weight){
		Point _s=getPoint(sid);
		Point _e=getPoint(eid);
		if(_s==null || _e==null){
			System.out.println("La création d'une route est impossible si un des point est null.");
		}
		if(_s.addDescendant(_e, weight))
		{
			this.numberOfRoutes++;
		}
	}
	
	/**
	 * Vérifie l'existence d'un point
	 * @param ID, le id du point à vérifier
	 * @return, point trouvé ou null
	 */
	private Point pointExist(int ID){
		for(int i=pointsSetted-1;i>-1;i--){
			if(points[i].getID()==ID){
				return points[i];
			}
		}
		return null;
	}
}