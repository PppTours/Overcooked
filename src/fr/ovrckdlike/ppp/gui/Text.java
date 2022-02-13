package fr.ovrckdlike.ppp.gui;

import fr.ovrckdlike.ppp.graphics.Color;
import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;

public class Text {
	private String text;
	private float[] pos = new float[2];
	private Color color;
	private float angle;
	private int size;
	
	public Text(String text, float[] renderPos, Color textColor, int textSize, float angle) {
		this.text = text;
		pos = renderPos;
		color = textColor;
		size = textSize;
		this.angle = angle;
	}
	
	public void render() {
		int textLength = text.length();
		
		for (int i = 0; i < textLength; i++) {
			float renderPosX = (float)(pos[0] + 7 * size * i * Math.cos(angle));
			float renderPosY = (float)(pos[1] + 7 * size * i * Math.sin(angle));
			char c = text.charAt(i);
			Renderer.drawLetter(c, renderPosX, renderPosY, size, color, Texture.font, angle);
		}
	}

}
