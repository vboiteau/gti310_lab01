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
		System.out.println("Squeeze Light Media Codec !");
    System.out.format("The program was launch with %d.\n",args.length);
    if(args.length>=2){
      String in_file_path=args[1];
      System.out.println("hey");
      int[][][] ppm_matrix = PPMReaderWriter.readPPMFile(in_file_path);
      if(ppm_matrix!=null){
        System.out.println("Able to open \""+args[1]+"\", so continue.");
        if(args[0].equals("-c")){
          System.out.println("we want compress");
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
	}
}
