package fr.ovrckdlike.ppp.gui;

import fr.ovrckdlike.ppp.graphics.Color;
import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.physics.Dot;
import fr.ovrckdlike.ppp.physics.Rectangle;
import fr.ovrckdlike.ppp.physics.Time;


public class Text {
	private String text;
	private Rectangle space;
	private Color color;
	private float angle;
	private float size;		//height of character in nb of pixels

	public Text(String text, Dot renderPos, Color textColor, float textSize, float angle) {
		this.text = text;
		space = new Rectangle(renderPos, text.length()*textSize, textSize, angle);
		color = textColor;
		size = textSize;
		this.angle = angle;
	}

	public Text(String text, Rectangle space, Color textColor) {
		this.text = text;
		this.space = new Rectangle(space);
		color = textColor;
		this.size = space.getHeight();
	}
	
	public void changeSize(float newSize) {
		size = newSize;
		space.resize(text.length()*size, size);
	}
	
	public void render() {
		Dot pos = space.getPos();
		float width = space.getWidth();
		float height = space.getHeight();
		for (int i = 0; i < text.length(); i++) {
			float renderPosX = (float) (pos.getX() - (width/2-height*i-size/2) * Math.cos(angle));
			float renderPosY = (float) (pos.getY() - (width/2-height*i-size/2) * Math.sin(angle));
			char c = text.charAt(i);
			Renderer.drawLetter(c, renderPosX, renderPosY, (int)(size / 7), color, Texture.font, -angle);
		}
	}
}
