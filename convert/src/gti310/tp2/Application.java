package gti310.tp2;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import gti310.tp2.audio.Convert16to8Filter;

public class Application {

	/**
	 * Launch the application
	 * @param args This parameter is ignored
	 * @throws FileNotFoundException 
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String args[]) throws FileNotFoundException, UnsupportedEncodingException {
		if(args.length==2){
			String original_file_path = args[0];
			String converted_file_path = args[1];
			Convert16to8Filter filter = new Convert16to8Filter(original_file_path, converted_file_path);
			long startTime=System.nanoTime();
			filter.process();
			long endTime=System.nanoTime();
			long duration = (endTime-startTime)/1000000;
			System.out.println("converting took "+duration+" milliseconds.");
		}
		else{
			System.out.println("Number of arguments is invalid, you need 2 arguments:");
			System.out.println("1. original file path;");
			System.out.println("2. converted file path.");
		}
	}
}
