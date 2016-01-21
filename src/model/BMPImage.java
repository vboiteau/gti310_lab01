
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
	public BMPImage(File file){
		super._file=file;
		try{
			System.out.print("in image creation try.\n");
			DataInputStream inputStream = new DataInputStream(new FileInputStream(file));
			inputStream.skipBytes(18);
			System.out.print("read buffer width.\n");
			width = streamToSize(inputStream);
			height = streamToSize(inputStream);
			System.out.print("image height is:"+height+" and width is: "+width+".\n");
			inputStream.close();
			inputStream.skipBytes(28);
			BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		}
		catch(Exception e){
			System.out.print("File reading error.\n");
		}
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
		return null;
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
