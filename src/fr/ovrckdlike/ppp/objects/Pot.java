package fr.ovrckdlike.ppp.objects;

import java.util.ArrayList;
import java.util.List;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.gui.IngredientVisualizer;
import fr.ovrckdlike.ppp.gui.TimeBar;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.tiles.CookerContainer;

public class Pot extends CookerContainer implements IngredientContainer{
	private int[] content = new int[3];
	private int nbIng;
	private TimeBar timeBar;
	private List<IngredientVisualizer> ivList = new ArrayList();
	
	
	public Pot(float[] pos) {
		this.pos = pos;
		this.direction = 0;
		for (int i = 0; i < 3; i++) {
			this.content[i] = -1;
			float[] posIV = new float[2];
			posIV[0] = 0f;
			posIV[1] = 0f;
			ivList.add(new IngredientVisualizer(0, posIV));
		}
		float[] timeBarPos = {pos[0]-25, pos[1]+25*mode};
		timeBar = new TimeBar(timeBarPos, cookingTime);
		nbIng = 0;
	}
	
	public void flush() {
		for (int i = 0; i < 3; i++) {
			this.content[i] = -1;
			ivList.get(i).setVisible(false);
		}
		nbIng = 0;
		
	}
	
	@Override
	public boolean fill(Ingredient ing) {
		if (nbIng > 2) return false;
		if (!ing.getPrepared()) return false;
		if (ing.getType() == 0 || ing.getType() == 2 || ing.getType() == 3) {
			content[nbIng] = ing.getType();
			nbIng++;
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
		float renderPos[] = {this.pos[0] - (this.size*zoom)/2, this.pos[1] - (this.size*zoom)/2};
		
		Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.pot);
		Renderer.drawTexture(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), Texture.potEmpty);
		if (checkForContent(-1) != 3) {
			float totalContent = 3 - this.checkForContent(-1);
			float alphaTomato = this.checkForContent(0) / totalContent;
			Renderer.drawTextureTransparent(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), alphaTomato, Texture.potTomato);
			float alphaOnion = this.checkForContent(2) / totalContent;
			Renderer.drawTextureTransparent(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), alphaOnion, Texture.potOnion);
			float alphaMushroom = this.checkForContent(3) / totalContent;
			Renderer.drawTextureTransparent(renderPos[0], renderPos[1], this.size*zoom, this.size*zoom, (float)(direction*Math.PI/4), alphaMushroom, Texture.potMushroom);
		}
		
		switch(nbIng) {
		case 1:
			ivList.get(0).setPos(pos[0], pos[1]-35);
			ivList.get(0).setIngredient(content[0]);
			ivList.get(0).setVisible(true);
			break;
		case 2:
			ivList.get(0).setPos(pos[0]-35, pos[1]);
			ivList.get(0).setIngredient(content[0]);
			ivList.get(0).setVisible(true);
			
			ivList.get(1).setPos(pos[0]+35, pos[1]);
			ivList.get(1).setIngredient(content[1]);
			ivList.get(1).setVisible(true);
			break;
		case 3:
			ivList.get(0).setPos(pos[0], pos[1]-35);
			ivList.get(0).setIngredient(content[0]);
			ivList.get(0).setVisible(true);
			
			ivList.get(1).setPos(pos[0]+30, pos[1]+18);
			ivList.get(1).setIngredient(content[1]);
			ivList.get(1).setVisible(true);
			
			ivList.get(2).setPos(pos[0]-30, pos[1]+18);
			ivList.get(2).setIngredient(content[2]);
			ivList.get(2).setVisible(true);
			break;
		default:
			break;
		}
		for (IngredientVisualizer iv:ivList) {
			iv.render();
		}
		
		float[] timeBarPos = {pos[0]-25, pos[1]+25*mode};
		timeBar.render(currentCookingTime, timeBarPos);
	}
}
