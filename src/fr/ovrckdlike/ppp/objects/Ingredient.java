package fr.ovrckdlike.ppp.objects;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;

public class Ingredient extends Item {
	private int type;
	private boolean prepared;
	
	
	
	public int getType() {
		return this.type;
	}
	
	public Ingredient(int type) {
		this.type = type;
		this.prepared = false;
		this.mode = 0;
		
	}
	
	public void prepare() {
		this.prepared = true;
	}
	
	public void render() {
		int zoom = this.mode+1;
		float renderPos[] = {this.pos[0] - (this.size*zoom)/2, this.pos[1] - (this.size*zoom)/2};
		switch (type) {
		case 1 :
			if (prepared) Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, 0, Texture.salade); //changer la texture
			else Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, 0, Texture.salade);

		}
	}
}
