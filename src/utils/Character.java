package utils;

public class Character {

	private char value;
	private int[] length;
	private int[] width;

	public Character(char value, int[] length, int[] width) {
		this.value = value;
		this.length = length;
		this.width = width;
	}
	
	public char getValue() {
		return value;
	}

	public void setValue(char value) {
		this.value = value;
	}
	
	public int getLength(int i) {
		return length[i];
	}

	public void setLength(int[] length) {
		this.length = length;
	}

	public int getWidth(int i) {
		return width[i];
	}

	public void setWidth(int[] width) {
		this.width = width;
	}
}
