package charrecognition;

public class FormulaManager {
	
	private static int size = 10; // pixels of image - 10*10

	public static double euclidianDistance(double[] x, double[] y) {
		double sum = 0.0;
		double distance = 0.0;
		
		for (int i = 0; i < size; i++) {
			sum += Math.pow(Math.abs(x[i] - y[i]), 2);
		}
		
		distance = Math.sqrt(sum);
		
		return distance;
	}
	
	public static double chebyshevDistance(double[] x, double[] y) {
		double[] sum = new double[size];
		double max = 0.0;
		
		for (int i = 0; i < size; i++) {
			sum[i] = Math.abs(x[i] - y[i]);
		}
		
		for (double d : sum) {
			if (d > max)
				max = d;
		}
		
		return max;
	}
	
	public static double manhattanDistance(double[] x, double[] y) {
		double sum = 0.0;
		double distance = 0.0;
		
		for (int i = 0; i < size; i++) {
			sum += Math.abs(x[i] - y[i]);
		}
		
		distance = sum;
		
		return distance;
	}
}
