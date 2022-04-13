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
		if (this.type == 6 || this.type == 11) prepared = true;
		else prepared = false;
		this.direction = 0;
		this.mode = 0;
		
	}
	
	public void prepare() {
		this.prepared = true;
	}
	
	public void render() {
		int zoom = this.mode+1;
		float renderPos[] = {this.pos[0] - (this.size*zoom)/2, this.pos[1] - (this.size*zoom)/2};
		switch (type) {
		case 0 :
			if (prepared) Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.tomato); //changer la texture
			else Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.tomato);
			break;
		case 1 :
			if (prepared) Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.salade); //changer la texture
			else Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.salade);
			break;
		case 2 :
			if (prepared) Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.onion); //changer la texture
			else Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.onion);
			break;
		case 3 :
			if (prepared) Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.mushroom); //changer la texture
			else Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.mushroom);
			break;
		case 4 :
			if (prepared) Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.meat); //changer la texture
			else Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.meat);
			break;
		case 5 :
			if (prepared) Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.cheese); //changer la texture
			else Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.cheese);
			break;
		case 6 :
			if (prepared) Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.pasta); //changer la texture
			else Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.pasta);
			break;
		case 7 :
			if (prepared) Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.sausage); //changer la texture
			else Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.sausage);
			break;
		case 8 :
			if (prepared) Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.pizzaDough); //changer la texture
			else Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.pizzaDough);
			break;
		case 9 :
			if (prepared) Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.burgerBread); //changer la texture
			else Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.burgerBread);
			break;
		case 10 :
			if (prepared) Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.chicken); //changer la texture
			else Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.chicken);
			break;
		case 11 :
			if (prepared) Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.rice); //changer la texture
			else Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.rice);
			break;
		case 12 :
			if (prepared) Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.potato); //changer la texture
			else Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.potato);
			break;

		}
	}

	public void flush() {}
}
