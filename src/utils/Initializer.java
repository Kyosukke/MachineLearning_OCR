package utils;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;

import charrecognition.CharacterRecognition;
import preprocessing.ImageCleaner;

public class Initializer {
	
	static String path_test = "assets/step1/";
	static String path_member = "assets/OCR/";
	
	public static void main(String[] args) {
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		System.out.println(Core.NATIVE_LIBRARY_NAME);
		List<Character> dataset;
		List<String> paths = new ArrayList<String>();

		Mat out = ImageCleaner.CleanImage(path_member + "ngo_c/step1/ngo_c-" + "sym_arob.bmp");
		
		paths.add(path_test);
		paths.add(path_member + "quach_o/step1/quach_o-");
		paths.add(path_member + "ngo_c/step1/ngo_c-");
		
	   	dataset = DatasetManager.getAlphaNumericFrom(paths, ".bmp");
	    System.out.println("Text Found: " + CharacterRecognition.getCharacter(out, dataset));
	    ImageDisplayer.displayImage(ImageDisplayer.Mat2BufferedImage(out), "test");
	}
}
