
/**
 * Code de la classe inspiré par le tutoriel à cette addresse: http://codes-sources.commentcamarche.net/source/34684-lire-et-ecrire-un-fichier-bmp
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
			System.out.print("in image creation try.\n");
			DataInputStream inputStream = new DataInputStream(new FileInputStream(file));
			inputStream.skipBytes(18);
			System.out.print("read buffer width.\n");
			width = streamToSize(inputStream);
			if(width<0){
				width=width*-1;
			}
			height = streamToSize(inputStream);
			if(height<0){
				height=height*-1;
			}
			System.out.print("image height is:"+height+" and width is: "+width+".\n");
			inputStream.skipBytes(28);
			System.out.print("on saute les 28 dernier bytes de l'entête.\n");
			int padding = (width * 3) % 4;
			System.out.print("On a un padding de "+padding+".\n");
			if(padding > 0){
				System.out.print("La largeur de l'image n'est pas un multiple de 4. Donc la largeur est du padding est de "+padding+".\n");
			}
			System.out.print("we checked for the padding.\n");
			image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			System.out.print("Check the image.\n");			
			/**
			 * On sélectionne les lignes dans le sens inverse puique la hauteur est négatif dans les images bmp.
			 */
			for(int y = 0;y<height;y++){
				/**
				 * La longueur de l'image est positive nous pouvons donc sélectionné en sens positif.
				 */
				for(int x = 0; x < width; x++){
					/**
					 * On ajoute une la couleur du pixel à sa position en x et y.
					 */
					image.setRGB(x, y, readColors(inputStream));
				}
				/**
				 * On saute le padding en fin de la ligne de l'image.
				 */
				inputStream.skipBytes(padding);
			}
			inputStream.close();
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
			colors = byteColors[0];
			colors = colors + ((byteColors[1]&MASK)<<8);
			colors = colors + ((byteColors[2]&MASK)<<16);
		}
		catch (Exception e){
			
		}
		System.out.print(colors);
		return colors;
	}

	private int streamToSize(DataInputStream stream) {
		// TODO Auto-generated method stub
		int finalInt=0;
		byte[] byteBuffer= new byte[4];
		try{
			stream.read(byteBuffer);
			finalInt = byteBuffer[0];
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
		System.out.print("image height is:"+height+".\n");
		return height;
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		System.out.print("image width is:"+width+".\n");
		return width;
	}

}
