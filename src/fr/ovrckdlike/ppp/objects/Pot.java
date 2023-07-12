package fr.ovrckdlike.ppp.objects;

import java.util.ArrayList;
import java.util.List;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.gui.IngredientVisualizer;
import fr.ovrckdlike.ppp.gui.TimeBar;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.physics.Dot;
import fr.ovrckdlike.ppp.physics.Rectangle;

public class Pot extends CookerContainer implements IngredientContainer{
	private int[] content = new int[3];
	private int nbIng;
	private List<IngredientVisualizer> ivList = new ArrayList();
	
	
	public Pot(Dot pos) {
		super(pos);
		angle = 0f;
		for (int i = 0; i < 3; i++) {
			this.content[i] = -1;
			Dot posIV = new Dot(0f, 0f);
			ivList.add(new IngredientVisualizer(0, posIV));
		}
		Dot timeBarPos = new Dot(space.getPos().getX(), space.getPos().getY()+25*mode);
		timebar = new TimeBar(timeBarPos, cookingTime);
		nbIng = 0;
	}
	
	public void flush() {
		for (int i = 0; i < 3; i++) {
			this.content[i] = -1;
			ivList.get(i).setVisible(false);
		}
		nbIng = 0;
		currentCookingTime = 0f;
		
	}
	
	@Override
	public boolean isFilled() {
		if (nbIng > 0) return true;
		else return false;
	}
	
	@Override
	public boolean fill(Ingredient ing) {
		if (nbIng > 2) return false;
		if (!ing.getPrepared()) return false;
		if (ing.getType() == 0 || ing.getType() == 2 || ing.getType() == 3) {
			content[nbIng] = ing.getType();
			nbIng++;
			currentCookingTime *= (float) (nbIng-1)/(float) nbIng;
			return true;
		}
		else return false;
	}
	
	public int checkForContent(int content) { // mettre -1 pour vide
		int corresponding = 0;
		for (int i = 0; i < 3; i++) {
			if (this.content[i] == content) corresponding++;
		}
		return corresponding;
	}
	
	
	public void render() {
		int zoom = this.mode+1;
		Rectangle printSurface = space.resized(zoom*space.getRay()).surroundBySquare(angle);
		
		Renderer.drawTexture(printSurface, Texture.pot);
		Renderer.drawTexture(printSurface, Texture.potEmpty);
		if (checkForContent(-1) != 3) {
			float totalContent = 3 - this.checkForContent(-1);
			float alphaTomato = this.checkForContent(0) / totalContent;
			Renderer.drawTextureTransparent(printSurface, alphaTomato, Texture.potTomato);
			float alphaOnion = this.checkForContent(2) / totalContent;
			Renderer.drawTextureTransparent(printSurface, alphaOnion, Texture.potOnion);
			float alphaMushroom = this.checkForContent(3) / totalContent;
			Renderer.drawTextureTransparent(printSurface, alphaMushroom, Texture.potMushroom);
		}
		
		Dot pos = space.getPos();
		switch(nbIng) {
		case 1:
			ivList.get(0).setPos(pos.getX(), pos.getY()-35);
			ivList.get(0).setIngredient(content[0]);
			ivList.get(0).setVisible(true);
			break;
		case 2:
			ivList.get(0).setPos(pos.getX()-35, pos.getY());
			ivList.get(0).setIngredient(content[0]);
			ivList.get(0).setVisible(true);
			
			ivList.get(1).setPos(pos.getX()+35, pos.getY());
			ivList.get(1).setIngredient(content[1]);
			ivList.get(1).setVisible(true);
			break;
		case 3:
			ivList.get(0).setPos(pos.getX(), pos.getY()-35);
			ivList.get(0).setIngredient(content[0]);
			ivList.get(0).setVisible(true);
			
			ivList.get(1).setPos(pos.getX()+30, pos.getY()+18);
			ivList.get(1).setIngredient(content[1]);
			ivList.get(1).setVisible(true);
			
			ivList.get(2).setPos(pos.getX()-30, pos.getY()+18);
			ivList.get(2).setIngredient(content[2]);
			ivList.get(2).setVisible(true);
			break;
		default:
			break;
		}
		for (IngredientVisualizer iv:ivList) {
			iv.render();
		}
		
		Dot timeBarPos = new Dot(pos.getX(), pos.getY()+30);
		timebar.render(currentCookingTime, timeBarPos);
	}
}
