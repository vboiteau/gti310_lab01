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
        if(args.length>2){
          String in_file_path=args[1];
          int[][][] matrix = PPMReaderWriter.readPPMFile(in_file_path);
          if(matrix!=null){
            int fq=Integer.parseInt(args[0]);
            if(
              1>fq &&
              fq<100
            ){
              System.out.println("Le facteur de qualité n'est pas entre 1 et 100 inclus comme demandé.");
              System.exit(1);
            }
            int height = matrix[0].length;
            int width = matrix[0][0].length;
            matrix = Convert.convertRgbYcbcr(matrix);
            Convert.compress(matrix, BLOCK_SIZE, fq);

            SZLReaderWriter.writeSZLFile(args[2], height, width, fq);
        }else{
          System.out.println("Input file can't be open.");
        }
      }else{
        String in_file_path=args[0];
        int[] header=SZLReaderWriter.readSZLFile(in_file_path);
        if(header == null){
          System.out.println("Le fichier SZL est invalide.");
          System.exit(1);
        }
        if(header.length!=4){
          System.out.println("L'entête du fichier SZL est invalid.");
          System.exit(1);
        }
        int height = header[0];
        int width = header[1];
        if(header[2] != COLOR_SPACE_SIZE){
          System.out.println("La taille du color space du fichier SZL n'est pas comme celui attendu.");
          System.exit(1);
        }
        int fq=header[3];
        int[][][] matrix = Convert.decompress(height, width, COLOR_SPACE_SIZE, BLOCK_SIZE, fq);
        matrix = Convert.convertYcbcrRgb(matrix);
        PPMReaderWriter.writePPMFile(args[1],matrix);
      }
    }else{
      System.out.println("La commande ne respecte aucun format attendu.\ncomression: 'java src.tp4.Main <facteur de qualite> <chemin image entree> <chemin fichier compressé>'\ndécompression: 'java src.tp4.Main <chemin fichier compressé> <chemin image sortie>'");
      System.exit(1);
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
