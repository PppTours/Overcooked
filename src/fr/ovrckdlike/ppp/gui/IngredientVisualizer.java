package fr.ovrckdlike.ppp.gui;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.objects.Ingredient;

public class IngredientVisualizer {
	private float[] pos = new float[2];
	private int ingType;
	private boolean visible;
	
	
	public IngredientVisualizer(int ingType, float[] pos) {
		this.ingType = ingType;
		this.pos = pos;
	}
	
	public void setVisible(boolean param) {
		visible = param;
	}
	
	public void setPos(float x, float y) {
		this.pos[0] = x;
		this.pos[1] = y;
	}
	
	public void setIngredient(int ingType) {
		this.ingType = ingType;
	}
	
	public void render() {
		if (visible) {
			Renderer.drawTexture(pos[0]-15, pos[1]-15, 30, 30, 0, Texture.circle);
			Renderer.drawTexture(pos[0]-13, pos[1]-13, 26, 26, 0, Ingredient.getTexture(ingType));
		}
		else return;
	}

}
