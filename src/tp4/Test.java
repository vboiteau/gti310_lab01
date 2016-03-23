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
    TestDCT();
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
  private static void TestDCT(){
    int[][] mYUV = {
      {200,202,189,188,189,175,175,175},
      {200,203,198,188,189,182,178,175},
      {203,200,200,195,200,197,185,175},
      {200,200,200,200,197,187,187,187},
      {200,205,200,200,195,188,187,175},
      {200,200,200,200,200,190,187,175},
      {205,200,199,200,191,187,187,175},
      {210,200,200,200,188,185,187,186}
    };

    int[][] mDCT = {
      {1539,65,12,4,1,2,-8,5},
      {-16,3,2,0,0,-11,-2,3},
      {-12,6,11,-1,3,0,1,-2},
      {-8,3,-4,2,-2,-3,-5,-2},
      {0,-2,7,-5,4,0,-1,-4},
      {0,-3,-1,0,4,1,-1,0},
      {3,-2,-3,3,3,-1,-1,3},
      {-2,5,-2,4,-2,2,-3,0}
    };

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
  }
}
