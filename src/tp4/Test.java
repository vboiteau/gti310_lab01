package src.tp4;

import src.tp4.PPMReaderWriter;

/**
 * The Main class is where the different functions are called to either encode
 * a PPM file to the Squeeze-Light format or to decode a Squeeze-Ligth image
 * into PPM format. It is the implementation of the simplified JPEG block
 * diagrams.
 *
 * @author Fran�ois Caron
 */
public class Test {

	/**
	 * The application's entry point.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
    long start=System.nanoTime();
    System.out.println("Le programme est le testeur pour lab04.");
    long end = System.nanoTime();
    long duration = end - start;
    System.out.println("Le temps d'éxecution du programme est de "+duration+" us ou "+(duration/1000000)+" ms.");
    Runtime runtime = Runtime.getRuntime();
    runtime.gc();
    long memory = runtime.totalMemory() - runtime.freeMemory();
    System.out.println("L'usage de mémoire par le programme est de "+memory+" octets ou "+(memory/1024L)+" ko ou "+(memory/(1024L*1024L))+" mo.");
	}
}
