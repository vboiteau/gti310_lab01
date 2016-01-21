package controller;

import java.io.File;

import model.BMPImage;
import model.Image;

public class BMPFactory implements ImageFactory {

	/** Singleton instance to the ProxyFactory */
	private static ImageFactory _instance = null;
	
	/** Hides the constructor from outside the class. */
	private BMPFactory() {};
	
	/*
	 *  (non-Javadoc)
	 * @see controller.ImageFactory#build(java.io.File)
	 */
	public Image build(File file) {
		return new BMPImage(file);
	}
	
	/**
	 * Creates a new instance of the ProxyFactory class if none exist.
	 * @return The well-known instance of the ProxyFactory class.
	 */
	public static ImageFactory getInstance() {
		if(_instance == null)
			_instance = new BMPFactory();
		return _instance;
	}
	
}
