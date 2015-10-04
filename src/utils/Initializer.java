package utils;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;

import charrecognition.CharacterRecognition;
import preprocessing.ImageCleaner;
import utils.Character;

public class Initializer {
	
	static String path = "assets/step1/";
	
	public static void main(String[] args) {
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		System.out.println(Core.NATIVE_LIBRARY_NAME);
		List<Character> dataset;

		Mat out = ImageCleaner.CleanImage(path + "j.bmp");
	   	dataset = DatasetManager.getAlphaNumericFrom(path, ".bmp");
	    System.out.println("Text Found: " + CharacterRecognition.getCharacter(out, dataset));
	    ImageDisplayer.displayImage(ImageDisplayer.Mat2BufferedImage(out), "test");
	}
}
