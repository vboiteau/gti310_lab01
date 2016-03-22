package src.tp4;

public class ConvertImgColor {


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
	public int[][][] convertRgbYcbcr (int[][][] matriceRVB) {

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


	public int[][][] convertYcbcrRgb (int[][][] matriceYcbcr) {

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
}
