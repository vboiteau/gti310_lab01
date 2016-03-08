package gti310.tp3;

import gti310.tp3.parser.ConcreteParser;
import gti310.tp3.parser.Parser;
import gti310.tp3.solver.CityGraphSolver;
import gti310.tp3.solver.Solver;
import gti310.tp3.writer.CitySolutionsWriter;
import gti310.tp3.writer.Writer;

/**
 * The Application class defines a template method to call the elements to
 * solve the problem Unreal-Networks is fa�ing.
 * 
 * @author Fran�ois Caron <francois.caron.7@ens.etsmtl.ca>
 */
public class Application {

	/**
	 * The Application's entry point.
	 * 
	 * The main method makes a series of calls to find a solution to the
	 * problem. The program awaits two arguments, the complete path to the
	 * input file, and the complete path to the output file.
	 * 
	 * @param args The array containing the arguments to the files.
	 */
	public static void main(String args[]) {
		System.out.println("Unreal Networks Solver!");
		if(args.length!=2){			
			System.out.format("Le nombre d'argument du programme doit être 2, le premier étant le fichier d'entrée and le second le fichier de sortie.");
			System.exit(1);
		}
		long before_parsing=System.nanoTime();
		Parser<CityGraph> parser=new ConcreteParser();
		CityGraph cityGraph=parser.parse(args[0]);
		long after_parsing=System.nanoTime();
		long duration_parsing=(after_parsing-before_parsing)/1000000;
		if(cityGraph==null){
			System.out.println("Impossibilité de lire les données dans le fichier.");
			System.exit(1);
		}
		Solver<CityGraph,RoutesSolution> solver=new CityGraphSolver();
		RoutesSolution solutions=solver.solve(cityGraph);
		long after_solving=System.nanoTime();
		long duration_solving=(after_solving-after_parsing)/1000000;
		if(solutions==null){
			System.out.println("Impossibilité de trouver des routes.");
		}
		Writer<RoutesSolution> writer=new CitySolutionsWriter();
		writer.write(args[1],solutions);
		long after_writing=System.nanoTime();
		long duration_writing=(after_writing-after_solving)/1000000;
		long duration=(after_writing-before_parsing)/1000000;
		System.out.println("Times / total: "+duration+" ms, parsing: "+duration_parsing+" ms, solving: "+duration_solving+" ms, writing: "+duration_writing+" ms.");
	}
}
