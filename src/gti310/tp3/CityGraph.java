package gti310.tp3;

public class CityGraph {
	private Point[] points;
	@SuppressWarnings("unused")
	private int infinityWeight;
	@SuppressWarnings("unused")
	private int startID;
	private int pointsSetted=0;
	public CityGraph(int size, int infinityWeight, int startID){
		this.points = new Point[size];
		this.infinityWeight=infinityWeight;
		this.startID=startID;
	}
	
	public Point setPoint(int ID){
		Point actual=null;
		if((actual=pointExist(ID))==null){
			actual=new Point(ID);
			points[pointsSetted]=actual;
			pointsSetted++;
		}
		return actual;
	}
	
	public boolean checkSize(int size){
		return size==pointsSetted;
	}
	
	public void addRoute(int sid, int eid, int weight){
		Point _s=setPoint(sid);
		Point _e=setPoint(eid);
		if(_s==null || _e==null){
			System.out.println("La création d'une route est impossible si un des point est null.");
		}
		_s.addDecendent(_e, weight);
	}
	
	private Point pointExist(int ID){
		for(int i=pointsSetted-1;i>-1;i--){
			if(points[i].getID()==ID){
				return points[i];
			}
		}
		return null;
	}
}