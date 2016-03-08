package gti310.tp3.solver;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import gti310.tp3.CityGraph;
import gti310.tp3.RoutesSolution;

public class CityGraphSolver implements Solver<CityGraph, RoutesSolution> {
	private int start;
	private HashMap<Integer,HashSet<Integer>> blacklist= new HashMap<Integer,HashSet<Integer>>();
	private int solutionsCounter=0;
	public RoutesSolution solve(CityGraph graph){
		start=graph.getStart();
		RoutesSolution solutions=new RoutesSolution();
		int[] route_template=new int[graph.getDepth()+1];
		get_options(start,route_template,blacklist, graph,solutions, 0);
		System.out.println(solutionsCounter);
		return solutions;
	}
	
	private void get_options(int _s, int[] currentRoute, HashMap<Integer, HashSet<Integer>> _blacklist, CityGraph graph, RoutesSolution solutions, int depth){
		currentRoute[(depth)]=_s;
		depth++;
		if(graph.checkDepth(depth)==false){
			if(_s==this.start){
				solutionsCounter++;
				printRoutes(currentRoute);
			}else{
				printRoutes(currentRoute);
			}
		}else{			
			int[] descendants = graph.getDescendants(_s);
			if(_blacklist.get(_s)==null){
				_blacklist.put(_s,new HashSet<Integer>());
			}
			for(int i=0;i<descendants.length;i++){
				if(!_blacklist.get(_s).contains(descendants[i])){
					_blacklist.get(_s).add(descendants[i]);
					get_options(descendants[i],currentRoute.clone(),_blacklist,graph,solutions,depth);
					_blacklist.get(_s).remove(descendants[i]);
				}else{
					//printRoutes(currentRoute);
				}
			}
		}
		depth--;
	}
	
	private void printRoutes(int[] route){
		System.out.print("Printing new route: ");
		for(int i=0;i<route.length;i++){
			System.out.print(route[i]);
			if(i+1<route.length){
				System.out.print(", ");
			}
		}
		System.out.println(".\n");
	}
}
