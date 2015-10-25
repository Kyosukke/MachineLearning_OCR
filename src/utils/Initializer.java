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
	static int divider = 1;
	
	public static void main(String[] args) {
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		System.out.println(Core.NATIVE_LIBRARY_NAME);
		List<Character> dataset;
		List<String> paths = new ArrayList<String>();

		Mat out = ImageCleaner.CleanImage(path_test + "sym_space.bmp");
		
//		paths.add(path_test);
		paths.add(path_member + "quach_o/step1/quach_o-");
		paths.add(path_member + "ngo_c/step1/ngo_c-");
		paths.add(path_member + "victor_j/step1/victor_j-");
		
		List<Mat> m = ImageDisplayer.divideMat(out, divider);
		
		for (Mat ma : m) {
			ImageDisplayer.displayImage(ImageDisplayer.Mat2BufferedImage(ma), "test");
		}
		
	   	dataset = DatasetManager.getAlphaNumericFrom(paths, divider, ".bmp");
	    System.out.println("Text Found: " + CharacterRecognition.getCharacter(out, dataset, divider));
	    ImageDisplayer.displayImage(ImageDisplayer.Mat2BufferedImage(out), "test");
	}
}
