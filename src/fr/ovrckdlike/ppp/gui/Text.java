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
	private float size;
	private float xSize;
	private float ySize;

	public Text(String text, float[] renderPos, Color textColor, float textSize, float angle) {
		this.text = text;
		pos = renderPos;
		color = textColor;
		size = textSize;
		this.angle = angle;
		xSize = (int) (size * (text.length() - 1) * Math.cos(angle) + size);
		ySize = (int) (size * (text.length() - 1) * Math.sin(angle) + size);
	}

	public Text(String text, float[] renderPos, float[] size, Color textColor, float angle) {
		this.text = text;
		pos = renderPos;
		color = textColor;
		this.size = Math.min(Math.abs((int) (size[0] / (1 + (text.length() - 1) * Math.cos(angle)))),
				Math.abs((int) (size[1] / (1 + (text.length() - 1) * Math.sin(angle)))));
		this.angle = angle;
		xSize = (int) (this.size * (text.length() - 1) * Math.cos(angle) + this.size);
		ySize = (int) (this.size * (text.length() - 1) * Math.sin(angle) + this.size);

	}
	
	public void changeSize(float newSize) {
		size = newSize;
		xSize = (int) (this.size * (text.length() - 1) * Math.cos(angle) + this.size);
		ySize = (int) (this.size * (text.length() - 1) * Math.sin(angle) + this.size);
	}
	
	public void render() {
		int textLength = text.length();

		for (int i = 0; i < textLength; i++) {
			float renderPosX = (float) (pos[0] + size * i * Math.cos(angle) - xSize / 2);
			float renderPosY = (float) (pos[1] + size * i * Math.sin(angle) - ySize / 2);
			char c = text.charAt(i);
			Renderer.drawLetter(c, renderPosX, renderPosY, (int)(size / 7), color, Texture.font, angle);
		}
	}

	public float getWidth() {
		return xSize;
	}

	public float getHeight() {
		return ySize;
	}

}
