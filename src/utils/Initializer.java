package utils;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import charrecognition.CharacterRecognition;
import gui.OcrGui;

public class Initializer {
	
	static String path_test1 = "assets/step1/";
	static String path_test2 = "assets/step2/";
	static String path_member = "assets/OCR/";
	static int divider = 1;
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		List<Character> dataset;
		List<String> paths = new ArrayList<String>();

		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		
		paths.add(path_member + "quach_o/step1/quach_o-");
		paths.add(path_member + "ngo_c/step1/ngo_c-");
		paths.add(path_member + "victor_j/step1/victor_j-");

		dataset = DatasetManager.getAlphaNumericFrom(paths, ".bmp");
		
		int k = CharacterRecognition.findK(path_member + "abbar_s/step1/abbar_s-", dataset, ".bmp");

		System.out.println("k: " + k);
		OcrGui gui = new OcrGui(dataset, k);
		
		/*if (true)
			System.out.println("txt: " + TextRecognition.readText(path_member + "victor_j/step2/nature.bmp", dataset, k));
		else {
			Mat img = ImageCleaner.CleanImage(path_member + "abbar_s/step1/abbar_s-" + "Z" + ".bmp");
			System.out.println("Character Found: " + CharacterRecognition.getCharacter(img, dataset, k));
			
		}*/
	}
}
