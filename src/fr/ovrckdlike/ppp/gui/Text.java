package fr.ovrckdlike.ppp.gui;

import fr.ovrckdlike.ppp.graphics.Color;
import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.physics.Time;

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
		int xSize = (int) (7 * size * (textLength-1) * Math.cos(angle)+7*size);
		int ySize = (int) (7 * size * (textLength-1) * Math.sin(angle)+7*size);
		
		for (int i = 0; i < textLength; i++) {
			float renderPosX = (float)(pos[0] + 7 * size * i * Math.cos(angle) - xSize/2);
			float renderPosY = (float)(pos[1] + 7 * size * i * Math.sin(angle) - ySize/2);
			char c = text.charAt(i);
			Renderer.drawLetter(c, renderPosX, renderPosY, size, color, Texture.font, angle);
		}
	}

}
