package gti310.tp3;

import gti310.tp3.parser.ConcreteParser;
import gti310.tp3.parser.Parser;
import gti310.tp3.solver.CityGraphSolver;
import gti310.tp3.solver.Solver;

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
		System.out.println("Unreal Networks Solver !");
		if(args.length!=2){			
			System.out.format("Le nombre d'argument du programme doit être 2, le premier étant le fichier d'entrée and le second le fichier de sortie.");
			System.exit(1);
		}
		long before_parsing=System.nanoTime();
		Parser<CityGraph> parser=new ConcreteParser();
		CityGraph cityGraph=parser.parse(args[0]);
		long after_parsing=System.nanoTime();
		long duration_parsing=(after_parsing-before_parsing)/1000000;
		System.out.println("Duration of parsing is "+duration_parsing+" ms");
		if(cityGraph==null){
			System.out.println("Impossibilité de lire les données dans le fichier.");
			System.exit(1);
		}
		Solver<CityGraph,RoutesSolution> solver=new CityGraphSolver();
		RoutesSolution solution=solver.solve(cityGraph);
		if(solution==null){
			System.out.println("Impossibilité de trouver des routes.");
		}
		System.out.println("Everything went fine.");
	}
}
