package utils;

import java.util.List;

public class Character {

	private char value;
	private List<int[]> length;
	private List<int[]> width;

	public Character(char value, List<int[]> length, List<int[]> width) {
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

	public List<int[]> getLength() {
		return length;
	}

	public void setLength(List<int[]> length) {
		this.length = length;
	}

	public List<int[]> getWidth() {
		return width;
	}

	public void setWidth(List<int[]> width) {
		this.width = width;
	}
}
