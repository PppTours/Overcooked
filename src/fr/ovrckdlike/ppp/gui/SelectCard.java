package fr.ovrckdlike.ppp.gui;

import fr.ovrckdlike.ppp.graphics.Color;
import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;

public class SelectCard {
	private Texture image;
	private Text text;
	private float[] pos;
	private float[] size;
	private Color background;
	
	public SelectCard(float[] pos, float[] size, Texture img, String text, Color background) {
		this.pos = pos;
		this.size = size;
		this.background = background;
		image = img;
		float[] textPos = {pos[0]+size[0]/2, pos[1]+size[1]-20};
		this.text = new Text(text, textPos, Color.black, 3, 0);
	}
	
	public void setPos(float[] pos) {
		this.pos = pos;
	}
	
	public void render() {
		Renderer.drawQuad(pos[0], pos[1], size[0], size[1], background);
		Renderer.drawTexture(pos[0]+10, pos[1]+10, size[0]-20, size[1]-50, 0, image);
		text.render();
	}
	

}
