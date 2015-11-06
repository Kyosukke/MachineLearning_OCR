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
import utils.MatManager;

public class CharacterRecognition {
	
	private static int KNN = 5;

	public static char getCharacter(Mat img, List<Character> dataset) {
		int[] cnt = new int[127];
		int match = 0;
		double[] toFind = MatManager.getDataFromMat(img);
		
		int[] res = getKNN(KNN, toFind, dataset);
		
		for(int i = 0; i < KNN; i++) {
			cnt[dataset.get(res[i]).getValue()] += 1 + (KNN - i);
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
			tmp.put(i, FormulaManager.euclidianDistance(toFind, dataset.get(i).getData(), 100));
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
