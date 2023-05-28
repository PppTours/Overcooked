package fr.ovrckdlike.ppp.objects;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.map.Map;
import fr.ovrckdlike.ppp.physics.Dot;

public class Ingredient extends Item {
	private int type;
	private boolean prepared;
	private boolean cooked;
	private boolean cookable;
	
	public boolean isCooked() {
		return cooked;
	}
	
	public boolean isCookable() {
		return cookable;
	}
	
	public void cook() {
		cooked = true;
	}
	
	public int getType() {
		return this.type;
	}
	
	public Ingredient(int type) {
		super(new Dot(0f, 0f));
		this.type = type;
		if (this.type == 6 || this.type == 11) prepared = true;
		else prepared = false;
		angle = 0;
		mode = 0;
		cooked = false;
		
		if (type == 0 || type == 4 || type == 6 || type == 10 || type == 11 || type == 12) {
			cookable = true;
		}
		else cookable = false;
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
		float zoom = space.getRay()*(mode+1);
		switch (type) {
		case 0 :
			if (prepared) Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.slicedTomato);
			else Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.tomato);
			break;
		case 1 :
			if (prepared) Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.slicedSalade);
			else Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.salade);
			break;
		case 2 :
			if (prepared) Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.slicedOnion);
			else Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.onion);
			break;
		case 3 :
			if (prepared) Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.slicedMushroom);
			else Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.mushroom);
			break;
		case 4 :
			if (prepared) Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.slicedMeat);
			else Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.meat);
			break;
		case 5 :
			if (prepared) Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.slicedCheese);
			else Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.cheese);
			break;
		case 6 :
			if (prepared) Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.pasta);
			else Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.pasta);
			break;
		case 7 :
			if (prepared) Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.slicedSausage);
			else Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.sausage);
			break;
		case 8 :
			if (prepared) Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.pizzaDough);
			else Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.pizzaDough);
			break;
		case 9 :
			if (prepared) Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.slicedBread);
			else Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.burgerBread);
			break;
		case 10 :
			if (prepared) Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.slicedChicken);
			else Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.chicken);
			break;
		case 11 :
			if (prepared) Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.rice);
			else Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.rice);
			break;
		case 12 :
			if (prepared) Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.slicedPotato);
			else Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.potato);
			break;

		}
	}
}
