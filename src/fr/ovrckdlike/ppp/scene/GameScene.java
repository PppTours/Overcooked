package fr.ovrckdlike.ppp.scene;

import fr.ovrckdlike.ppp.graphics.Color;
import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;

public class GameScene extends Scene {
	

	public void render() {
		Renderer.drawQuad(10, 10, 100, 100, Color.red);
		Renderer.drawTexture(120, 120, 500, 500, Texture.smiley);
	}
	
	public void run() {
		//TODO faire le jeu !
	}




}
