package gti310.tp2;

import java.io.FileNotFoundException;
import java.util.Arrays;

import gti310.tp2.audio.SNRFilter;

public class Application {

	/**
	 * Launch the application
	 * @param args This parameter is ignored
	 * @throws FileNotFoundException 
	 */
	public static void main(String args[]) throws FileNotFoundException {
		//System.out.println("Audio Resample project!");
		//System.out.println("With args of length "+args.length+":");
		SNRFilter filter = new SNRFilter(args[0],Arrays.copyOfRange(args,1,args.length));
	}
}
