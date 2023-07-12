package fr.ovrckdlike.ppp.gui;

import fr.ovrckdlike.ppp.graphics.Color;
import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.physics.Dot;
import fr.ovrckdlike.ppp.physics.Rectangle;
import fr.ovrckdlike.ppp.physics.Time;

public class Button {
	private Text text;
	protected Color color;
	protected Rectangle pos;
	private boolean selected = false;
	
	public Button(Rectangle pos, String text, Color color) {
		this.color = color;
		this.pos = pos;
		Dot textPos = pos.getPos();
		this.text = new Text(text, textPos, Color.black, 30f, 0);
		this.selected = false;
	}
	
	public void setSelected(boolean selec) {
		this.selected = selec;
	}
	
	public void render() {
		if (selected) {
			Renderer.drawQuad(pos, Color.black);
		}
		Renderer.drawQuad(pos, this.color);
		text.render();
		if (selected) {
			Renderer.drawQuad(pos, Color.transparentWhite);
		}
	}
}
