package utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import charrecognition.CharacterRecognition;
import preprocessing.ImageCleaner;

public class Initializer {
	
	static String path = "assets/step1/";
	
	public static void main(String[] args) {
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	    
	    Mat out = ImageCleaner.CleanImage(path + "m.bmp");
	    System.out.println("Text Found: " + CharacterRecognition.getCharacter(out));
	    ImageDisplayer.displayImage(ImageDisplayer.Mat2BufferedImage(out), "test");
	}
}
