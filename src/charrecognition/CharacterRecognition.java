package charrecognition;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.opencv.core.Mat;

import utils.Character;
import utils.DatasetManager;

public class CharacterRecognition {
	
	private static int KNN = 5;

	public static char getCharacter(Mat img, List<Character> dataset, int divider) {
		int[] cnt = new int[127];
		int match = 0;
		Character c = DatasetManager.createCharacter((char)0, img, divider);
		
		int[] res = getKNN(KNN, findLengthDiff(c, dataset, divider), findWidthDiff(c, dataset, divider), dataset.size());
		
		for(int i = 0; i < KNN; i++) {
			cnt[dataset.get(res[i]).getValue()] += 1 + (KNN - i);
			System.out.println("SIM CHAR:" + dataset.get(res[i]).getValue());
		}
		
		for (int i = 0; i < 127; i++) {
			if (cnt[i] > cnt[match])
				match = i;
		}
		
		return (char)match;
		/*
		try {
			Tesseract instance = Tesseract.getInstance();
			String str = instance.doOCR(ImageDisplayer.Mat2BufferedImage(img));
			System.out.println("Result: " + str);
		}
		catch (TesseractException e){
			System.err.println(e.getMessage());
		}*/
	}
	
	private static int[] getKNN(int k, int[] length, int[] width, int size) {
		Map<Integer, Integer> tmp = new HashMap<Integer, Integer>();
		int[] res = new int[k];
		
		for (int i = 0; i < size; i++) {
			tmp.put(i, length[i] + width[i]);
		}
		
		Map<Integer, Integer> sorted = sortByComparator(tmp);
		Object[] keys = sorted.keySet().toArray();
		
		for (int i = 0; i < k; i++) {
			res[i] = Integer.parseInt(keys[i].toString());
		}
		
		return res;
	}
	
	private static int[] findLengthDiff(Character c, List<Character> dataset, int divider) {
		int[] res = new int[dataset.size()];
		
		for (int i = 0; i < dataset.size() - 1; i++) {
			for (int j = 0; j < divider * divider; j++) {
				res[i] = findDiff(c.getLength().get(j), dataset.get(i).getLength().get(j), divider);
			}
		}
		
		return res;
	}
	
	private static int[] findWidthDiff(Character c, List<Character> dataset, int divider) {
		int[] res = new int[dataset.size()];
		
		for (int i = 0; i < dataset.size(); i++) {
			for (int j = 0; j < divider * divider; j++) {
				res[i] = findDiff(c.getWidth().get(j), dataset.get(i).getWidth().get(j), divider);
			}
		}

		return res;
	}
	
	private static int findDiff(int[] c, int[] value, int divider) {
		int diff = 0;
		
		for (int i = 0; i < 50 / divider; i++) {
			diff += (c[i] > value[i]) ? (c[i] - value[i]) : (value[i] - c[i]);
		}
		
		return diff;
	}
	
	private static Map<Integer, Integer> sortByComparator(Map<Integer, Integer> unsortMap) {

		// Convert Map to List
		List<Map.Entry<Integer, Integer>> list = 
			new LinkedList<Map.Entry<Integer, Integer>>(unsortMap.entrySet());

		// Sort list with comparator, to compare the Map values
		Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
			public int compare(Map.Entry<Integer, Integer> o1,
                                           Map.Entry<Integer, Integer> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});

		// Convert sorted map back to a Map
		Map<Integer, Integer> sortedMap = new LinkedHashMap<Integer, Integer>();
		for (Iterator<Map.Entry<Integer, Integer>> it = list.iterator(); it.hasNext();) {
			Map.Entry<Integer, Integer> entry = it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}
}
