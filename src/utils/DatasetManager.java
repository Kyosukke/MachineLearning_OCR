package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opencv.core.Mat;

import preprocessing.ImageCleaner;

public class DatasetManager {

	public static List<Character> getAlphaNumericFrom(List<String> paths, String fmt) {
		List<Character> dataset = new ArrayList<Character>();
		Map<Integer, String> filename = getFileNames();
		
		for (String s : paths) {
			for (int i = 48; i < 58; i++) {
				
				Mat img = ImageCleaner.CleanImage(s + filename.get(i) + fmt);
				img = MatManager.cropMat(img);
				img = MatManager.resizeMat(img, 20);
				img = ImageCleaner.CleanImage(img, false);
				dataset.add(new Character((char)i, MatManager.getDataFromMat(img)));
			}
		}
		
		/* external dataset *//*
		int value = '0';
		Mat img;
		for (int i = 1; i < 2044/*7008*; i++) {
			img = ImageCleaner.CleanImage("assets/OCR/OCR_sample/" + (char)value + "-" + i + fmt);
			
			if (img == null || img.empty()) {
				if (value < '9')
					value++;
				else if (value <= '9')
					value = 'A';
				else if (value <= 'Z') {
					img = ImageCleaner.CleanImage("assets/OCR/OCR_sample/" + (char)(value + 32) + "-" + i + fmt);
					
					if (img == null || img.empty())
						value++;
					else {
						img = MatManager.cropMat(img);
						img = MatManager.resizeMat(img, 10);
						img = ImageCleaner.CleanImage(img, false);
						dataset.add(new Character((char)(value + 32), MatManager.getDataFromMat(img)));
					}
				}
				i--;
			}
			else {
				img = MatManager.cropMat(img);
				img = MatManager.resizeMat(img, 10);
				img = ImageCleaner.CleanImage(img, false);
				dataset.add(new Character((char)value, MatManager.getDataFromMat(img)));
			}
		}*/
		
		return dataset;
	}
	
	public static Map<Integer, String> getFileNames() {
		Map<Integer, String> filename = new HashMap<Integer, String>();
		
		filename.put(32, "sym_space");
		filename.put(33, "sym_exclmark");
		filename.put(34, "sym_quotmark");
		filename.put(35, "sym_num");
		filename.put(36, "sym_dollar");
		filename.put(37, "sym_pcent");
		filename.put(38, "sym_amper");
		filename.put(39, "sym_apos");
		filename.put(40, "sym_lparen");
		filename.put(41, "sym_rparen");
		filename.put(42, "sym_star");
		filename.put(43, "sym_plus");
		filename.put(44, "sym_comma");
		filename.put(45, "sym_hyphen");
		filename.put(46, "sym_point");
		filename.put(47, "sym_slash");
		filename.put(58, "sym_colon");
		filename.put(59, "sym_scolon");
		filename.put(60, "sym_lthan");
		filename.put(61, "sym_equal");
		filename.put(62, "sym_gthan");
		filename.put(63, "sym_questmark");
		filename.put(64, "sym_arob");
		filename.put(91, "sym_lsqbracket");
		filename.put(92, "sym_bslash");
		filename.put(93, "sym_rsqbracket");
		filename.put(94, "sym_caret");
		filename.put(95, "sym_under");
		filename.put(96, "sym_bquote");
		filename.put(123, "sym_lcbracket");
		filename.put(124, "sym_pipe");
		filename.put(125, "sym_rcbracket");
		filename.put(126, "sym_tilde");
		for (int i = 48; i < 58; i++) {
			filename.put(i, "num_" + (char)i);
		}
		for (int i = 65; i < 91; i++) {
			filename.put(i, (char)i + "");
		}
		for (int i = 97; i < 123; i++) {
			filename.put(i, (char)i + "_small");
		}
		
		return filename;
	}
}
