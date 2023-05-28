package fr.ovrckdlike.ppp.gui;

import fr.ovrckdlike.ppp.graphics.Color;
import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.physics.Dot;
import fr.ovrckdlike.ppp.physics.Rectangle;

public class SelectCard {
	private Texture image;
	private Text text;
	Rectangle pos;
	private Color background;
	
	public SelectCard(Rectangle pos, Texture img, String text, Color background) {
		this.pos = pos;
		this.background = background;
		image = img;
		Dot textPos = pos.getPos();
		this.text = new Text(text, textPos, Color.black, 3, 0);
	}
	
	public void setPos(Dot newPos) {
		pos.setPos(newPos);
	}
	
	public void render() {
		Renderer.drawQuad(pos, background);
		Renderer.drawTexture(new Rectangle(pos.getX(), pos.getY()-20, pos.getWidth()-20, pos.getHeight()-20), image);	//changer la méthode de render des 
		text.render();
	}
	

}
