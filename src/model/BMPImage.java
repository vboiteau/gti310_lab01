
/**
 * Début du code inspiré de:
 * Auteur: indiana_jules <http://codes-sources.commentcamarche.net/profile/user/indiana_jules>
 * Publieur: CodeS-SourceS <http://codes-sources.commentcamarche.net/>
 * Article: Lire et écrire un fichier bmp
 * Lien: http://codes-sources.commentcamarche.net/source/34684-lire-et-ecrire-un-fichier-bmp
 */

package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.DataInputStream;

public class BMPImage extends Image {
	private int height=0;
	private int width=0;
	private final int MASK = 0xFF;
	private BufferedImage image = null;
	public BMPImage(File file){
		super._file=file;
		try{
			DataInputStream inputStream = new DataInputStream(new FileInputStream(file));
			inputStream.skipBytes(18);
			width = streamToSize(inputStream);
			height = streamToSize(inputStream);
			inputStream.skipBytes(28);
			int padding = 4-((width * 3) % 4);
			image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			/**
			 * On sélectionne les lignes dans le sens inverse puique la hauteur est négatif dans les images bmp.
			 */
			
			for(int y = height-1;y>=0;y--){
				/**
				 * La longueur de l'image est positive nous pouvons donc sélectionné en sens positif.
				 */
				for(int x = 0; x < width; x++){
					/**
					 * On ajoute une la couleur du pixel à sa position en x et y.
					 */
					image.setRGB(x, y, readColors(inputStream));
					System.out.print("s1\n");
				}
				/**
				 * On saute le padding en fin de la ligne de l'image.
				 */
				inputStream.skipBytes(padding);
				System.out.print("s2\n");
			}
			inputStream.close();
			System.out.print("me ended\n");
		}
		catch(Exception e){
			System.out.print("File reading error.\n");
		}
	}

	private int readColors(DataInputStream stream) {
		// TODO Auto-generated method stub
		byte[] byteColors = new byte[3];
		int colors = 0;
		try{
			stream.read(byteColors);
			colors = byteColors[0]&MASK;
			colors = colors + ((byteColors[1]&MASK)<<8);
			colors = colors + ((byteColors[2]&MASK)<<16);
		}
		catch (Exception e){
			
		}
		return colors;
	}

	private int streamToSize(DataInputStream stream) {
		// TODO Auto-generated method stub
		int finalInt=0;
		byte[] byteBuffer= new byte[4];
		try{
			stream.read(byteBuffer);
			finalInt = byteBuffer[0]&MASK;
			finalInt = finalInt + ((byteBuffer[1]&MASK)<<8);
			finalInt = finalInt + ((byteBuffer[2]&MASK)<<16);
			finalInt = finalInt + ((byteBuffer[3]&MASK)<<24);
		}catch (Exception e){
			
		}
		return finalInt;
	}

	@Override
	public BufferedImage draw() {
		// TODO Auto-generated method stub
		System.out.print("concrete image draw.\n");
		return image;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		//System.out.print("image height is:"+height+".\n");
		return height;
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		//System.out.print("image width is:"+width+".\n");
		return width;
	}

}

/**
*	Fin du code inspiré de http://codes-sources.commentcamarche.net/source/34684-lire-et-ecrire-un-fichier-bmp
*/