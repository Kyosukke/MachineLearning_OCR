package utils;

public class Character {

	private char value;
	private double[] data;

	public Character(char value, double[] data) {
		this.value = value;
		this.data = data;
	}
	
	public char getValue() {
		return value;
	}

	public void setValue(char value) {
		this.value = value;
	}

	public double[] getData() {
		return data;
	}

	public void setData(double[] data) {
		this.data = data;
	}
}
