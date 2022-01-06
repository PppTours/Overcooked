package fr.ovrckdlike.ppp.objects;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;

public class Ingredient extends Item {
	private int type;
	private boolean prepared;
	
	
	
	public int getType() {
		return this.type;
	}
	
	public Ingredient(int type, float[] pos) {
		this.type = type;
		this.prepared = false;
		this.pos = pos;
		this.mode = 1;
		
	}
	
	public void prepare() {
		this.prepared = true;
	}
	
	public void render() {
		int zoom = this.mode+1;
		float renderPos[] = {this.pos[0] - (this.size*zoom)/2, this.pos[1] - (this.size*zoom)/2};
		switch (type) {
		case 1 :
			Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, 0, Texture.salade);

		}
	}
}
