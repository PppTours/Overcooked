package fr.ovrckdlike.ppp.objects;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.tiles.CookerContainer;

public class Pot extends CookerContainer{
	private int[] content;
	
	
	public Pot(float[] pos) {
		this.pos = pos;
		this.direction = 0;
		for (int i = 0; i < 3; i++) {
			this.content[i] = -1;
		}
	}
	public void flush() {
		for (int i = 0; i < 3; i++) {
			this.content[i] = -1;
		}
	}
	
	public int checkForContent(int content) { // mettre -1 pour vide
		int corresponding = 0;
		for (int i = 0; i < 3; i++) {
			if (this.content[i] != content) corresponding++;
		}
		return corresponding;
	}
	
	
	public void render() {
		int zoom = this.mode+1;
		float renderPos[] = {this.pos[0] - (this.size*zoom)/2, this.pos[1] - (this.size*zoom)/2};
		
		Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.pot);
		if (this.checkForContent(-1) == 3) {
			Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.potEmpty);
		}
		else {
			int totalContent = 3 - this.checkForContent(-1);
			float alphaTomato = this.checkForContent(0) / totalContent;
			Renderer.drawTextureTransparent(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), alphaTomato, Texture.potTomato);
			float alphaOnion = this.checkForContent(2) / totalContent;
			Renderer.drawTextureTransparent(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), alphaOnion, Texture.potOnion);
			float alphaMushroom = this.checkForContent(3) / totalContent;
			Renderer.drawTextureTransparent(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), alphaMushroom, Texture.potMushroom);
		}
	}
}
