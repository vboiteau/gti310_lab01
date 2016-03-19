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
	
	public float[][][] convertRgbYcbcr (int[][][] matriceRVB) {
		
		int Height = matriceRVB[0].length;
		int Weight = matriceRVB[0][0].length;
		
		float [][][] matriceYcbcr = new float [Main.COLOR_SPACE_SIZE][Height][Weight]; 
		
		for (int i = 0; i < Height; i++) {
			for (int j = 0; j < Weight; j++) {
				
				matriceYcbcr[Main.Y][i][j] = (float) 0.299*matriceRVB [Main.R][i][j] + (float) 0.587*matriceRVB [Main.G][i][j] + (float) 0.114*matriceRVB [Main.B][i][j];
				matriceYcbcr[Main.U][i][j] = (float) -0.168736*matriceRVB [Main.R][i][j] + (float) -0.331264*matriceRVB [Main.G][i][j] + (float) 0.5*matriceRVB [Main.B][i][j];
				matriceYcbcr[Main.V][i][j] = (float) 0.5*matriceRVB [Main.R][i][j] + (float) -0.418688*matriceRVB [Main.G][i][j] + (float) -0.081312*matriceRVB [Main.B][j][j];
				
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
	 * 
	 * Complexite O(n^2)
	 */
	
	
	public float[][][] convertYcbcrRgb (int[][][] matriceYcbcr) {
	
		int Height = matriceYcbcr[0].length;
		int Weight = matriceYcbcr[0][0].length;
		
		float [][][] matriceRGB = new float [Main.COLOR_SPACE_SIZE][Height][Weight]; 
		
		for (int i = 0; i < Height; i++) {
			for (int j = 0; j < Weight; j++) {
				
				matriceRGB[Main.R][i][j] = (float) 1*matriceYcbcr [Main.Y][i][j] + (float) 0*matriceYcbcr [Main.U][i][j] + (float) 1.402*matriceYcbcr [Main.V][i][j];
				matriceRGB[Main.G][i][j] = (float) 1*matriceYcbcr [Main.Y][i][j] + (float) -0.344136*matriceYcbcr [Main.U][i][j] + (float) 0.714136*matriceYcbcr [Main.V][i][j];
				matriceRGB[Main.B][i][j] = (float) 1*matriceYcbcr [Main.Y][i][j] + (float) 1.772*matriceYcbcr [Main.U][i][j] + (float) 0*matriceYcbcr [Main.V][j][j];
				
			}
		}
			
		
		return matriceRGB;
		
	}
}
	


