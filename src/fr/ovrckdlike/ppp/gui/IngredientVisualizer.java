package fr.ovrckdlike.ppp.gui;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.objects.Ingredient;
import fr.ovrckdlike.ppp.physics.Circle;
import fr.ovrckdlike.ppp.physics.Dot;

public class IngredientVisualizer {
	private Circle space;
	private int ingType;
	private boolean visible;
	
	
	public IngredientVisualizer(int ingType, Dot pos) {
		this.ingType = ingType;
		this.space = new Circle(pos, 15);
	}
	
	public void setVisible(boolean param) {
		visible = param;
	}
	
	public void setPos(float newX, float newY) {
		space.setPos(new Dot(newX, newY));
	}
	
	public void setPos(Dot newPos) {
		space.setPos(newPos);
	}
	
	public void setIngredient(int ingType) {
		this.ingType = ingType;
	}
	
	public void render() {
		if (visible) {
			Renderer.drawTexture(space.surroundBySquare(0), Texture.circle);
			Renderer.drawTexture(space.resized(13).surroundBySquare(0), Ingredient.getTexture(ingType));
		}
		else return;
	}

}
