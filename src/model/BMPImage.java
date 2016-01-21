package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

public class BMPImage extends Image {
	private int height=0;
	private int width=0;
	public BMPImage(File file){
		super._file=file;
		System.out.print("nope");
		byte[] intsize = new byte[4]; 
		try{
			FileInputStream inputStream = new FileInputStream(file);
			this.width = inputStream.read(intsize,17,4);
			this.height = inputStream.read(intsize,21,4);
			inputStream.close();
		}
		catch(Exception e){
		}
	}

	@Override
	public BufferedImage draw() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return this.height;
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return this.width;
	}

}
