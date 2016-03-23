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
public class Main {

	/*
	 * The entire application assumes that the blocks are 8x8 squares.
	 */
	public static final int BLOCK_SIZE = 8;

	/*
	 * The number of dimensions in the color spaces.
	 */
	public static final int COLOR_SPACE_SIZE = 3;

	/*
	 * The RGB color space.
	 */
	public static final int R = 0;
	public static final int G = 1;
	public static final int B = 2;

	/*
	 * The YUV color space.
	 */
	public static final int Y = 0;
	public static final int U = 1;
	public static final int V = 2;

	/**
	 * The application's entry point.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
    long start=System.nanoTime();
    if(args.length>=2){
      String in_file_path=args[1];
      int[][][] matrix = PPMReaderWriter.readPPMFile(in_file_path);
      if(matrix!=null){
        if(args[0].equals("-c")){
          int fq=Integer.parseInt(args[3]);
          if(
            1>fq &&
            fq<100
          ){
            System.out.println("Le facteur de qualité n'est pas entre 1 et 100 inclus comme demandé.");
            System.exit(1);
          }
          matrix = Convert.convertRgbYcbcr(matrix);
          int[][] c_matrix = Convert.compress_to_zigzag(matrix, BLOCK_SIZE, fq);
          /*for(int i=0; i<matrix[0].length;i++){
            for (int j=0;j < matrix[0][i].length; j++) {
              System.out.print(matrix[0][i][j]+" ");
            }
            System.out.println(" ");
          }*/
        }else if(args[0].equals("-x")){

          System.out.print("we want decompress");
        }else{
          System.out.println("command type is unknown. Known commands are c:compress and x:decompress.");
        }
      }else{
        System.out.println("Input file can't be open.");
      }
    }else{
      System.out.println("Huston, we got a problem, you need to respect the command lines templates. java src.tp4.Application -c/x [c: compress/x: decompress] <input path> <output path> <compression factor if compress>.");
    }
    long end = System.nanoTime();
    long duration = end - start;
    System.out.println("Le temps d'éxecution du programme est de "+duration+" us ou "+(duration/1000000)+" ms.");
    Runtime runtime = Runtime.getRuntime();
    runtime.gc();
    long memory = runtime.totalMemory() - runtime.freeMemory();
    System.out.println("L'usage de mémoire par le programme est de "+memory+" octets ou "+(memory/1024L)+" ko ou "+(memory/(1024L*1024L))+" mo.");
	}
}
