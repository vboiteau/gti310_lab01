package gti310.tp3.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import gti310.tp3.CityGraph;

public class ConcreteParser implements Parser<CityGraph> {
	private BufferedReader reader=null;
	private CityGraph cityGraph;
	public CityGraph parse(String inputFile){
		File file=new File(inputFile);
		try{
			reader=new BufferedReader(new FileReader(file));
			int points_count=stringToInt(reader.readLine());
			int infinity_weight=stringToInt(reader.readLine());
			int start_id=stringToInt(reader.readLine());
			cityGraph = new CityGraph(points_count,infinity_weight,start_id);
			readRoute();
		}catch(Exception e){
			e.printStackTrace();
			System.exit(1);
		}
		return cityGraph;
	}
	public int stringToInt(String data){
		int i_data=-1;
		try{
			i_data=Integer.parseInt(data);
		}catch(NumberFormatException e){
			System.out.println("Une des données dans le fichier d'entrée n'est pas un nombre, comme exiger.");
			System.exit(1);
		}
		return i_data;
	}
	private void readRoute(){
		try{			
			String actual_route=reader.readLine();
			if(!actual_route.equals("$")){
				String[] array_actual_route=actual_route.split("\\t");
				if(array_actual_route.length!=3){
					System.out.println("Une des route dans le fichier d'entrée ne respecte pas le modèle <départ><tabulation><destination><tabulation><source>");
					System.exit(1);
				}
				int[] array_route = new int[3];
				for(byte i=0;i<3;i++){
					array_route[i]=stringToInt(array_actual_route[i]);
				}
				cityGraph.addRoute(array_route[0], array_route[1], array_route[2]);
				readRoute();
			}
		}catch(Exception e){
			System.out.println("Une route dans le fichier d'entrée n'est pas lisible.");
			System.exit(1);
		}
	}
}

