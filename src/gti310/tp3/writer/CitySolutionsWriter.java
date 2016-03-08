package gti310.tp3.writer;

import java.io.FileWriter;

import gti310.tp3.RoutesSolution;

public class CitySolutionsWriter implements Writer<RoutesSolution> {
	public void write(String filename,RoutesSolution solutions){
		try{			
			FileWriter fw = new FileWriter(filename);
			String route="";
			if(solutions.checkRoutes()){				
				while((route=solutions.printableRoute())!=""){
					fw.write(route);
				}
			}else{
				System.out.println("Le fichier de sortie sera vide puisque nous n'avons pas de solution à votre problème");
			}
			fw.close();
		}catch(Exception e){
			System.out.println("Sorry output file is couldn't be set!");
		}
	}
	
}
