package src.tp4;

public class Convert {

  private static int[][] Qy = {
    {16,11,10,16,24,40,51,61},
    {12,12,14,19,26,58,60,55},
    {14,13,16,24,40,57,69,56},
    {14,17,22,29,51,87,80,62},
    {18,22,37,56,68,109,103,77},
    {24,35,55,64,81,104,113,92},
    {49,64,78,87,103,121,120,101},
    {72,92,95,98,112,100,103,99}
  };

  private static int[][] Qc={
    {17,18,24,47,99,99,99,99},
    {18,21,26,66,99,99,99,99},
    {24,26,56,99,99,99,99,99},
    {47,66,99,99,99,99,99,99},
    {99,99,99,99,99,99,99,99},
    {99,99,99,99,99,99,99,99},
    {99,99,99,99,99,99,99,99},
    {99,99,99,99,99,99,99,99},
  };

	/**
	 * Convertir RGB a YCBCR
	 *
	 * La table de conversion a ete prise dans les notes de cours.
	 *
	 * @param matriceRVB
	 * @return
	 *
	 * Complexite O(n^2)
	 */
	public static int[][][] convertRgbYcbcr (int[][][] matriceRVB) {

		int Height = matriceRVB[0].length;
		int Weight = matriceRVB[0][0].length;

		int [][][] matriceYcbcr = new int [Main.COLOR_SPACE_SIZE][Height][Weight];

		for (int i = 0; i < Height; i++) {
			for (int j = 0; j < Weight; j++) {

				matriceYcbcr[Main.Y][i][j] =(int)Math.round(0.299*matriceRVB [Main.R][i][j] + 0.587*matriceRVB [Main.G][i][j] + 0.114*matriceRVB [Main.B][i][j]);
				matriceYcbcr[Main.U][i][j] =(int)Math.round(-0.168736*matriceRVB [Main.R][i][j] + -0.331264*matriceRVB [Main.G][i][j] +0.5*matriceRVB [Main.B][i][j]);
				matriceYcbcr[Main.V][i][j] =(int)Math.round(0.5*matriceRVB [Main.R][i][j] +-0.418688*matriceRVB [Main.G][i][j] +-0.081312*matriceRVB [Main.B][j][j]);

			}
		}

		return matriceYcbcr;

	}


	/**
	 * Convertir Ycbcr en format Rgb
	 *
	 * La table de conversion de ycbcr a rgb a ete prise sur le site
	 * http://www.mir.com/DMG/ycbcr.html
	 *
	 * @param matriceYcbcr
	 * @return
	 *x
	 * Complexite O(n^2)
	 */


	public static int[][][] convertYcbcrRgb (int[][][] matriceYcbcr) {

		int Height = matriceYcbcr[0].length;
		int Weight = matriceYcbcr[0][0].length;

		int [][][] matriceRGB = new int [Main.COLOR_SPACE_SIZE][Height][Weight];

		for (int i = 0; i < Height; i++) {
			for (int j = 0; j < Weight; j++) {

				matriceRGB[Main.R][i][j] =(int)Math.round(1*matriceYcbcr [Main.Y][i][j] +0*matriceYcbcr [Main.U][i][j] +1.402*matriceYcbcr [Main.V][i][j]);
				matriceRGB[Main.G][i][j] =(int)Math.round(1*matriceYcbcr [Main.Y][i][j] +-0.344136*matriceYcbcr [Main.U][i][j] +0.714136*matriceYcbcr [Main.V][i][j]);
				matriceRGB[Main.B][i][j] =(int)Math.round(1*matriceYcbcr [Main.Y][i][j] +1.772*matriceYcbcr [Main.U][i][j] +0*matriceYcbcr [Main.V][j][j]);

			}
		}
		return matriceRGB;

	}

  /**
   * Will do the step needed to convert but first seperate into block.
   * O(((image_height/8)+1)*((image_width/8)+1)*4096)
   */
  public static void compress_to_zigzag(
    int[][][] matrix,
    int block_size,
    int fq
  ){
    int number_variable_image = matrix.length;
    int[][] r_matrix=new int[matrix.length][];
    int last_dc = 0;
    for(
      int x = 0;
      x < number_variable_image;
      x++
    ){
      int[][] pixels = matrix[x];
      int image_height = pixels.length;
      int image_width = pixels[0].length;
      int number_vertical_blocks = (int) Math.ceil(image_height / block_size);
      int number_horizontal_blocks = (int) Math.ceil(image_width / block_size);
      r_matrix[x]=new int[
        number_vertical_blocks*number_horizontal_blocks*block_size*block_size
      ];
      for(
        int vertical_blocks_count = 0;
        vertical_blocks_count < number_vertical_blocks;
        vertical_blocks_count++
      ){
        for(
          int horizontal_blocks_count = 0;
          horizontal_blocks_count < number_horizontal_blocks;
          horizontal_blocks_count++
        ){
          int vertical_offset = block_size;
          if((block_size * vertical_blocks_count)>image_height){
            vertical_offset = image_height % block_size;
          }
          int horizontal_offset = block_size;
          if((block_size * horizontal_blocks_count)>image_width){
            horizontal_offset = image_width % block_size;
          }
          int[][] block_matrix= new int[vertical_offset][horizontal_offset];
          for(
            int y = 0;
            y < block_matrix.length;
            y++
          ){
            System.arraycopy(
              matrix[x][y],
              block_size*horizontal_blocks_count,
              block_matrix[y],
              0,
              horizontal_offset
            );
          }

          block_matrix = DCT(block_matrix);
          if(fq<100){
            if(x==0){
              block_matrix = Quantization(block_matrix, fq, true);
            }else{
              block_matrix = Quantization(block_matrix, fq, false);
            }
          }

          int[] z_block = zigzag(block_matrix);


          if( horizontal_blocks_count + vertical_blocks_count > 0){
            int new_dc = z_block[0] - last_dc;
            last_dc = z_block[0];
            z_block[0] = new_dc;
          }else{
            last_dc = z_block[0];
          }
          Entropy.writeDC(z_block[0]);

          int zCnt = 0;
          for(
            int i=1;
            i < z_block.length;
            i++
          ){
            if(z_block[i]==0){
              zCnt++;
            }else{
              Entropy.writeAC(zCnt, z_block[i]);
            }
          }
          Entropy.writeAC(0,0);

        }
      }
    }
  }

  /**
   * transforme une matrix to dct.
   * @params matrix sans dct
   * @return matrix avec dct
   * O(4096) http://stackoverflow.com/questions/4240490/problems-with-dct-and-idct-algorithm-in-java,
   Dernière visite 23 mars 2016.
   */
  public static int[][] DCT(
    int[][] matrix
  ){
    double[] c = new double[matrix.length];
    for (int y=1;y<matrix.length;y++) {
      c[y]=1;
    }
    c[0]=1/Math.sqrt(2.0);
    int[][] temp_matrix = new int[matrix.length][matrix[0].length];
    for(
      int u=0;
      u < matrix.length;
      u++
    ){
      for(
        int v=0;
        v < matrix[0].length;
        v++
      ){
        double sum = 0.0;
        for(
          int i = 0;
          i < matrix.length;
          i++
        ){
          for(
            int j = 0;
            j < matrix[0].length;
            j++
          ){
            sum +=
              Math.cos(((2*i+1)/(2.0*matrix.length))*u*Math.PI)
              * Math.cos(((2*j+1)/(2.0*matrix[0].length))*v*Math.PI)
              * matrix
              [i]
              [j];
          }
        }
        sum *= ((c[u]*c[v])/4.0);
        temp_matrix[u][v] = (int)sum;
      }
    }
    return temp_matrix;
  }
  /**
   * transforme une matrix sans dct.
   * @params matrix avec dct
   * @return matrix sans dct
   * O(4096) http://stackoverflow.com/questions/4240490/problems-with-dct-and-idct-algorithm-in-java,
   Dernière visite 23 mars 2016.
   */
  public static int[][] IDCT(
    int[][] matrix
  ){
    double[] c = new double[matrix.length];
    for (int y=1;y<matrix.length;y++) {
      c[y]=1;
    }
    c[0]=1/Math.sqrt(2.0);
    int[][] temp_matrix = new int[matrix.length][matrix[0].length];
    for(
      int i=0;
      i < matrix.length;
      i++
    ){
      for(
        int j=0;
        j < matrix[0].length;
        j++
      ){
        double sum = 0.0;
        for(
          int u = 0;
          u < matrix.length;
          u++
        ){
          for(
            int v = 0;
            v < matrix[0].length;
            v++
          ){
            sum += (c[u]*c[v])/4.0*Math.cos(((2*i+1)/(2.0*matrix.length))*u*Math.PI)*Math.cos(((2*j+1)/(2.0*matrix[0].length))*v*Math.PI)*matrix[u][v];
          }
        }
        temp_matrix[i][j] = (int)Math.round(sum);
      }
    }
    return temp_matrix;
  }

  /**
   * Fonction qui transforme un bloc en un bloc compresser selon le facteur de compression.
   * @params matrix, la matrice
   * @params fq, facteur de compression
   * @params y or cbcr, y si vrai cb, cr si faux
   * @return, matrix confirmation
   * O(64)
   */
  public static int[][] Quantization(
    int[][] matrix,
    int fq,
    boolean y
  ){
    double a=0.0;
    if(fq < 50){
      a = 50.0/(double)fq;
    }else{
      a=((200.0-(2.0*(double)fq))/100.0);
    }
    for(
      int i = 0;
      i < matrix.length;
      i++
    ){
      for(
        int j = 0;
        j < matrix[i].length;
        j++
      ){
        int valeur = matrix[i][j];
        int q = (y ? Qy[i][j] : Qc[i][j]);
        matrix[i][j] = (int)Math.round(valeur/(a*q));
      }
    }
    return matrix;
  }

  /**
   * Fonction qui transforme un bloc en un bloc decompresser selon le facteur de compression.
   * @params matrix, la matrice
   * @params fq, facteur de compression
   * @params y or cbcr, y si vrai cb, cr si faux
   * @return, matrix confirmation
   * O(64)
   */
  public static int[][] Dequantization(
    int[][] matrix,
    int fq,
    boolean y
  ){
    double a=0.0;
    if(fq < 50){
      a = 50.0/(double)fq;
    }else{
      a=((200.0-(2.0*(double)fq))/100.0);
    }
    for(
      int i = 0;
      i < matrix.length;
      i++
    ){
      for(
        int j = 0;
        j < matrix[i].length;
        j++
      ){
        int valeur = matrix[i][j];
        int q = (y ? Qy[i][j] : Qc[i][j]);
        matrix[i][j] = (int)Math.round(valeur*(a*q));
      }
    }
    return matrix;
  }

  /**
   * Transforme une matrice en un tableau de façon zigzag.
   * @params matrix, matrice à convetir
   * @return, tableau converti
   * O(m*n)
   * code inspiré de la page https://rosettacode.org/wiki/Zig-zag_matrix
   * dernière visite 23 mars 2016.
   */
  public static int[] zigzag(
    int[][] matrix
  ){
    int x_pos = 1;
    int y_pos = 1;
    int h_size = matrix[0].length;
    int v_size = matrix.length;
    int[] aZ = new int[h_size*v_size];
    for(
      int i=0;
      i < (h_size*v_size);
      i++
    ){
      aZ[i] = matrix[(y_pos-1)][(x_pos-1)];
      if((y_pos + x_pos) % 2 == 0){
        if(x_pos < h_size){
          x_pos++;
        }else{
          y_pos += 2;
        }
        if(y_pos > 1){
          y_pos--;
        }
      }else{
        if(y_pos < v_size){
          y_pos++;
        }else{
          x_pos += 2;
        }
        if(x_pos > 1){
          x_pos--;
        }
      }
    }
    return aZ;
  }

  /**
  * Transforme un tableau en une matrice de façon zigzag.
  * @params tableau, tableau à convetir
  * @params h_size, la largeur de la matrice
  * @params v_size, la hauteur de la matrice
  * @return, matrice converti
  * O(m*n)
  * code inspiré de la page https://rosettacode.org/wiki/Zig-zag_matrix
  * dernière visite 23 mars 2016.
  */
  public static int[][] izigzag(
    int[] aZ,
    int h_size,
    int v_size
  ){
    int x_pos = 1;
    int y_pos = 1;
    int[][] matrix = new int[v_size][h_size];
    for(
      int i=0;
      i < (h_size*v_size);
      i++
    ){
      matrix[(y_pos-1)][(x_pos-1)]=aZ[i];
      if((y_pos + x_pos) % 2 == 0){
        if(x_pos < h_size){
          x_pos++;
        }else{
          y_pos += 2;
        }
        if(y_pos > 1){
          y_pos--;
        }
      }else{
        if(y_pos < v_size){
          y_pos++;
        }else{
          x_pos += 2;
        }
        if(x_pos > 1){
          x_pos--;
        }
      }
    }
    return matrix;
  }
}
