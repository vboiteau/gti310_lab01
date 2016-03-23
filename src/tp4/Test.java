package src.tp4;

import src.tp4.Convert;

/**
 *	Cette classe est la classe de test.
 */
public class Test {

	/**
	 * Cette fonction est la fonction d'entrée des tests.
	 *
	 * @param args, devrais être null.
	 */
	public static void main(String[] args) {
    long start=System.nanoTime();
    System.out.println("Le programme est le testeur pour lab04.");
    Test();
    long end = System.nanoTime();
    long duration = end - start;
    System.out.println("Le temps d'éxecution du programme est de "+duration+" us ou "+(duration/1000000)+" ms.");
    Runtime runtime = Runtime.getRuntime();
    runtime.gc();
    long memory = runtime.totalMemory() - runtime.freeMemory();
    System.out.println("L'usage de mémoire par le programme est de "+memory+" octets ou "+(memory/1024L)+" ko ou "+(memory/(1024L*1024L))+" mo.");
	}

  /**
   * Cette classe est le test de DCT.
   */
  private static void Test(){
    System.out.println("Start the test of DCT and IDCT: ");

    int[][] mYUV = {
      {70,70,100,70,87,87,150,187},
      {85,100,96,79,87,150,87,113},
      {100,85,116,79,70,87,86,196},
      {136,69,87,200,79,71,117,96},
      {161,70,87,200,103,71,96,113},
      {161,123,147,133,113,113,85,161},
      {146,147,175,100,103,103,163,187},
      {156,146,189,70,113,161,163,197}
    };

    int[][] mDCT = {
      {944,-40,89,-73,44,32,53,-3},
      {-135,-59,-26,6,14,-3,-13,-28},
      {47,-76,66,-3,-108,-78,33,59},
      {-2,10,-18,0,33,11,-21,1},
      {-1,-9,-22,8,32,65,-36,-1},
      {5,-20,28,-46,3,24,-30,24},
      {6,-20,37,-28,12,-35,33,17},
      {-5,-23,33,-30,17,-5,-4,20}
    };

    int[][] mQ ={
      {59,-4,9,-5,2,1,1,0},
      {-11,-5,-2,0,1,0,0,-1},
      {3,-6,4,0,-3,-1,0,1},
      {0,1,-1,0,1,0,0,0},
      {0,0,-1,0,0,1,0,0},
      {0,-1,1,-1,0,0,0,0},
      {0,0,0,0,0,0,0,0},
      {0,0,0,0,0,0,0,0}
    };

    int[] aZ = { 944, -44, -132, 42, -60, 90, -80, -28, -78, 0, 0, 17, 64, 0, 48, 40, 26, 0, -22, 0, 0, 0, -35, -37, 0, -120, 0, 51, 0, 0, -57, 51, 0, 55, 0, 0, 0, 0, -64, 0, 0, 0, -55, 56, 0, 109, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

    int[][] mCDCT = Convert.DCT(mYUV);

    for(int i=0; i<mCDCT.length;i++){
      System.out.println(" ");
      for (int j=0;j < mCDCT[i].length; j++) {
        System.out.print(mCDCT[i][j]+" ");
      }
    }

    System.out.print("\n\n");

    int[][] mCIDCT = Convert.IDCT(mDCT);

    for(int i=0; i<mCIDCT.length;i++){
      System.out.println(" ");
      for (int j=0;j < mCIDCT[i].length; j++) {
        System.out.print(mCIDCT[i][j]+" ");
      }
    }

    System.out.print("\n\n");

    System.out.println("Start the test about quantization:");

    int[][] mCQ = Convert.Quantization(mDCT,50,true);

    for(int i=0; i<mCQ.length;i++){
      System.out.println(" ");
      for (int j=0;j < mCQ[i].length; j++) {
        System.out.print(mCQ[i][j]+" ");
      }
    }

    System.out.print("\n\n");

    int[][] mCD = Convert.Dequantization(mQ,50,true);

    for(int i=0; i<mCD.length;i++){
      for (int j=0;j < mCD[i].length; j++) {
        System.out.print(mCD[i][j]+" ");
      }
      System.out.println(" ");
    }

    System.out.print("\n");

    System.out.println("Start the test for zigzaging like no tomorrow: ");

    int[] aCZ = Convert.zigzag(mQ);

    System.out.print("\n{ ");
    for(
      int i = 0;
      i < aCZ.length;
      i++
    ){
      System.out.print(aCZ[i]);
      if(i<(aCZ.length-1)){
        System.out.print(", ");
      }
    }
    System.out.print(" }\n\n");
  }
}
