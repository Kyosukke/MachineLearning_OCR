package charrecognition;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.opencv.core.Mat;

import preprocessing.ImageCleaner;
import utils.Character;
import utils.DatasetManager;
import utils.MatManager;

public class CharacterRecognition {
	
	public static int findK(String pathForTest, List<Character> dataset, String fmt) {
		Mat img = new Mat();
		Random rdm = new Random();
		Map<Integer, String> filenames = DatasetManager.getFileNames();
		int i = 0;
		int k = 0;
		
		do {
			k++;
			i = 48 + Math.abs(rdm.nextInt()) % (58 - 48 + 1);
//			i = 32 + Math.abs(rdm.nextInt()) % (126 - 32 + 1);
			img = ImageCleaner.CleanImage(pathForTest + filenames.get(i) + fmt);
		}
		while ((char)i != CharacterRecognition.getCharacter(img, dataset, k) && k < dataset.size());
		
		return k;
	}
	
	public static char getCharacter(Mat img, List<Character> dataset, int k) {
		int[] cnt = new int[127];
		int match = 0;
		double[] toFind = MatManager.getDataFromMat(MatManager.cropMat(img));
		
		int[] res = getKNN(k, toFind, dataset);
		
		for(int i = 0; i < k; i++) {
			cnt[dataset.get(res[i]).getValue()] += 1 + (k - i);
			System.out.println("SIM CHAR:" + dataset.get(res[i]).getValue());
		}
		
		for (int i = 0; i < 127; i++) {
			if (cnt[i] > cnt[match])
				match = i;
		}
		
		return (char)match;
	}
	
	private static int[] getKNN(int k, double[] toFind, List<Character> dataset) {
		Map<Integer, Double> tmp = new HashMap<Integer, Double>();
		int[] res = new int[k];
		
		for (int i = 0; i < dataset.size(); i++) {
			if (dataset.get(i).getData() != null)
				tmp.put(i, FormulaManager.euclidianDistance(toFind, dataset.get(i).getData()));
		}
		
		Map<Integer, Double> sorted = sortByComparator(tmp);
		Object[] keys = sorted.keySet().toArray();
		
		for (int i = 0; i < k; i++) {
			res[i] = Integer.parseInt(keys[i].toString());
		}
		
		return res;
	}
	
	private static Map<Integer, Double> sortByComparator(Map<Integer, Double> unsortMap) {

		// Convert Map to List
		List<Map.Entry<Integer, Double>> list = 
			new LinkedList<Map.Entry<Integer, Double>>(unsortMap.entrySet());

		// Sort list with comparator, to compare the Map values
		Collections.sort(list, new Comparator<Map.Entry<Integer, Double>>() {
			public int compare(Map.Entry<Integer, Double> o1,
                                           Map.Entry<Integer, Double> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});

		// Convert sorted map back to a Map
		Map<Integer, Double> sortedMap = new LinkedHashMap<Integer, Double>();
		for (Iterator<Map.Entry<Integer, Double>> it = list.iterator(); it.hasNext();) {
			Map.Entry<Integer, Double> entry = it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}
}
