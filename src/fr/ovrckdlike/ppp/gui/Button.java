package fr.ovrckdlike.ppp.gui;

import fr.ovrckdlike.ppp.graphics.Color;

public class Button {
	private boolean selected = false;
	private boolean clicked = false;
	private String text;
	private Color color;
	private int pos[] = new int [2];
	private int size[] = new int [2];
	
	public Button(String text, Color color, int[] pos, int size[]) {
		this.color = color;
		this.pos = pos;
		this.size = size;
		this.text = text;
		this.selected = false;
		this.clicked = false;
	}
	
	public void render() {
		
	}
	
}
