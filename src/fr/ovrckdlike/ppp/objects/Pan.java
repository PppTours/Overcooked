package fr.ovrckdlike.ppp.objects;

import java.util.ArrayList;
import java.util.List;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.gui.IngredientVisualizer;
import fr.ovrckdlike.ppp.gui.TimeBar;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.physics.Dot;
import fr.ovrckdlike.ppp.scene.GameScene;

public class Pan extends CookerContainer implements IngredientContainer{
	Ingredient content;
	List<IngredientVisualizer> ivList;
	
	public Pan(Dot pos) {
		super(pos);
		content = null;
		ivList = new ArrayList<IngredientVisualizer>();
		Dot timeBarPos = new Dot(pos.getX()-25, pos.getY()+25*mode);
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
		float zoom = space.getRay()*(mode+1);
		Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.pan);
		
	}
}
