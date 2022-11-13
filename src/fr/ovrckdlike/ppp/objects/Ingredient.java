package fr.ovrckdlike.ppp.objects;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.map.Map;

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
	
	public static Texture getTexture(int ingType) {
		switch (ingType) {
		case 0:
			return Texture.tomato;
		case 1:
			return Texture.salade;
		case 2:
			return Texture.onion;
		case 3:
			return Texture.mushroom;
		case 4:
			return Texture.meat;
		case 5:
			return Texture.cheese;
		case 6:
			return Texture.pasta;
		case 7:
			return Texture.sausage;
		case 8:
			return Texture.pizzaDough;
		case 9:
			return Texture.burgerBread;
		case 10:
			return Texture.chicken;
		case 11:
			return Texture.rice;
		case 12:
			return Texture.potato;
		default:
			return null;
		}
	}
	
	public boolean getPrepared() {
		return prepared;
	}
	
	@Override
	public void flush() {
		Map.get().getItemList().remove(this);
	}
	
	public void prepare() {
		this.prepared = true;
	}
	
	public void render() {
		int zoom = this.mode+1;
		float renderPos[] = {this.pos[0] - (this.size*zoom)/2, this.pos[1] - (this.size*zoom)/2};
		switch (type) {
		case 0 :
			if (prepared) Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.slicedTomato);
			else Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.tomato);
			break;
		case 1 :
			if (prepared) Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.slicedSalade);
			else Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.salade);
			break;
		case 2 :
			if (prepared) Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.slicedOnion);
			else Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.onion);
			break;
		case 3 :
			if (prepared) Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.slicedMushroom);
			else Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.mushroom);
			break;
		case 4 :
			if (prepared) Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.slicedMeat);
			else Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.meat);
			break;
		case 5 :
			if (prepared) Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.slicedCheese);
			else Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.cheese);
			break;
		case 6 :
			if (prepared) Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.pasta);
			else Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.pasta);
			break;
		case 7 :
			if (prepared) Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.slicedSausage);
			else Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.sausage);
			break;
		case 8 :
			if (prepared) Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.pizzaDough);
			else Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.pizzaDough);
			break;
		case 9 :
			if (prepared) Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.slicedBread);
			else Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.burgerBread);
			break;
		case 10 :
			if (prepared) Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.slicedChicken);
			else Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.chicken);
			break;
		case 11 :
			if (prepared) Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.rice);
			else Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.rice);
			break;
		case 12 :
			if (prepared) Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.slicedPotato);
			else Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.potato);
			break;

		}
	}
}
