package fr.ovrckdlike.ppp.objects;

import java.util.ArrayList;
import java.util.List;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.gui.IngredientVisualizer;
import fr.ovrckdlike.ppp.gui.TimeBar;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.scene.GameScene;
import fr.ovrckdlike.ppp.tiles.CookerContainer;

public class Pan extends CookerContainer implements IngredientContainer{
	Ingredient content;
	List<IngredientVisualizer> ivList;
	
	public Pan(float[] pos) {
		content = null;
		ivList = new ArrayList<IngredientVisualizer>();
		float[] timeBarPos = {pos[0]-25, pos[1]+25*mode};
		timebar = new TimeBar(timeBarPos, cookingTime);
	}
	
	
	@Override
	public boolean fill(Ingredient ing) {
		if (ing.isCookable()) {
			content = ing;
			return true;
		}
		else return false;
	}
	
	@Override
	public boolean isFilled() {
		if (content != null) return true;
		else return false;
	}

	@Override
	public void flush() {
		GameScene.deleteItem(content);
		content = null;
		currentCookingTime = 0f;
		
	}
	
	@Override
	public void render() {
		int zoom = 1+mode;
		Renderer.drawTexture(pos[0], pos[1], size*zoom, size*zoom, 0, Texture.pan);
		
	}
}
