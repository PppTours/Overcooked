package fr.ovrckdlike.ppp.gui;

import fr.ovrckdlike.ppp.graphics.Color;
import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.physics.Time;

public class Button {
	private Text text;
	private Color color;
	private float pos[] = new float [2];
	private float size[] = new float [2];
	private boolean selected = false;
	
	public Button(float[] pos, float size[], String text, Color color) {
		this.color = color;
		this.pos = pos;
		this.size = size;
		float[] textPos = {pos[0]+size[0]/2, pos[1]+size[1]/2};
		this.text = new Text(text, textPos, size, Color.black, 0);
		this.selected = false;
	}
	
	public void setSelected(boolean selec) {
		this.selected = selec;
	}
	
	public void render() {
		if (selected) {
			Renderer.drawQuad(this.pos[0]-3, this.pos[1]-3, this.size[0]+6, this.size[1]+6, Color.black);
		}
		Renderer.drawQuad(this.pos[0], this.pos[1], this.size[0], this.size[1], this.color);
		text.render();
		if (selected) {
			Renderer.drawQuad(this.pos[0], this.pos[1], this.size[0], this.size[1], Color.transparentWhite);
		}
	}
}
