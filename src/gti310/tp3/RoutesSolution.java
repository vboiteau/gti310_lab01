package gti310.tp3;

import java.util.ArrayList;
import java.util.ListIterator;

public class RoutesSolution {
	private ArrayList<int[]> routes= new ArrayList<int[]>(); 
	private int route_size;
	private ListIterator<int[]> routesIterator;
	public RoutesSolution(int route_size){
		this.route_size=route_size;
	}
	public boolean createRoute(int[] data){
		if(data.length!=this.route_size){
			return false;
		}
		int[] route=new int[this.route_size];
		for(int i=0;i<this.route_size;i++){
			route[i]=data[i];
		}
		routes.add(route);
		return true;
	}
	public String printableRoute(){
		if(routesIterator==null){
			routesIterator=routes.listIterator();
		}
		String prRoute="";
		if(routesIterator.hasNext()){
			int[] route=routesIterator.next();
			prRoute+=route[0];
			for(int i=1;i<this.route_size;i++){
				prRoute+=" "+route[i];
			}
			prRoute+="\n";
		}
		return prRoute;
	}
	public boolean checkRoutes(){
		return (this.routes.size()>0?true:false);
	}
}
