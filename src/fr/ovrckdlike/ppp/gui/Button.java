package fr.ovrckdlike.ppp.gui;

import fr.ovrckdlike.ppp.graphics.Color;
import fr.ovrckdlike.ppp.graphics.Renderer;

public class Button {
	private String text;
	private Color color;
	private int pos[] = new int [2];
	private int size[] = new int [2];
	private boolean selected = false;
	
	public Button(String text, Color color, int[] pos, int size[], boolean selected) {
		this.color = color;
		this.pos = pos;
		this.size = size;
		this.text = text;
		this.selected = false;
	}
	
	public void render() {
		Renderer.drawQuad(this.pos[0], this.pos[1], this.size[0], this.size[1], this.color);
		if (selected) {
			Renderer.drawQuad(this.pos[0], this.pos[1], this.size[0], this.size[1], Color.darkGreenSelec);
		}
	}
	
	public void setSelected(boolean selec) {
		this.selected = selec;
	}
	
}
