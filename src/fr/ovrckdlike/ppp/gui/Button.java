package fr.ovrckdlike.ppp.gui;

public class Button {
	private boolean selected = false;
	private String text;
	private float color[] = new float[3];
	private int pos[] = new int [2];
	private int size[] = new int [2];
	
	public Button(String text, float[] color, int[] pos, int size[]) {
		this.color = color;
		this.pos = pos;
		this.size = size;
		this.text = text;
	}
	
	public void render() {
		
	}
	
}
