package fr.ovrckdlike.ppp.objects;

import java.util.ArrayList;
import java.util.List;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.gui.IngredientVisualizer;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.physics.Dot;

public class Plate extends Item implements IngredientContainer{
	private boolean[] content = new boolean[15];
	private boolean dirty;
	private byte ingCount;
	private List<IngredientVisualizer> ivList = new ArrayList();

	
	public Plate(Dot pos, boolean dirty) {
		this(pos, dirty, 1);
	}
	
	public Plate(Dot pos, boolean dirty, int mode) {
		super(pos);
		this.dirty = dirty;
		this.mode = mode;
		
		for (int i = 0; i < 5; i++) {
			Dot posCopy = new Dot(pos);
			ivList.add(i, new IngredientVisualizer(0, posCopy));
		}
		
		ingCount = 0;
	}
	
	public boolean[] getContent() {
		return content;
	}
	
	public boolean getDirty() {
		return this.dirty;
	}
	
	public void wash() {
		this.dirty = false;
	}
	
	public boolean isEmpty() {
		return (ingCount == 0);
	}
	
	public void prepare() {}
	
	public void cook() {
		content[14] = true;
	}
	
	@Override
	public boolean fill(Ingredient ingredient) {
		if (!ingredient.getPrepared()) return false;
		if (dirty) return false;
		
		if (this.content[ingredient.getType()] == true) return false;
		if (ingCount > 5) return false;
		else {
			this.content[ingredient.getType()] = true;
			ingCount++;
			return true;
		}
	}
	
	public void flush() {
		ingCount = 0;
		for (IngredientVisualizer iv:ivList) {
			iv.setVisible(false);
		}
		for (int i = 0; i < 15; i++) {
			this.content[i] = false;
		}
	}
	
	public boolean merge(boolean[] food) {	//fusion du contenu de 2 assiettes
		if (dirty) return false;
		if (this.content[13]) return false;
		
		boolean[] afterMerge = new boolean[15];
		int nbIngredients = 0;
		for(int i = 0; i< 13; i++) {
			if (food[i] && this.content[i]) return false;
			afterMerge[i] = (food[i]||this.content[i]);
			nbIngredients++;
		}
		if (nbIngredients > 5) return false;
		else {
			ingCount = (byte) nbIngredients;
			this.content = afterMerge;
			return true;
		}
		
	}
	
	public void render() {
		int zoom = mode+1;
		if (mode == 2) return;
		if (!dirty)
			Renderer.drawTexture(space.surroundBySquare(0), Texture.plate);
		else 
			Renderer.drawTexture(space.surroundBySquare(0), Texture.dirtyPlate);
		int[] ingIdx = new int[5];
		byte i = 0;
		for (int k=0; k < 13; k++) {
			if (content[k]) {
				ingIdx[i] = k;
				i++;
			}
		}
		Dot pos = space.getPos();
		
		switch (ingCount) {
		case 1:
			ivList.get(0).setPos(pos.getX(), pos.getY()-35);
			ivList.get(0).setIngredient(ingIdx[0]);
			ivList.get(0).setVisible(true);
			break;
		case 2:
			ivList.get(0).setPos(pos.getX()-35, pos.getY());
			ivList.get(0).setIngredient(ingIdx[0]);
			ivList.get(0).setVisible(true);
			
			ivList.get(1).setPos(pos.getX()+35, pos.getY());
			ivList.get(1).setIngredient(ingIdx[1]);
			ivList.get(1).setVisible(true);
			break;
		case 3:
			ivList.get(0).setPos(pos.getX(), pos.getY()-35);
			ivList.get(0).setIngredient(ingIdx[0]);
			ivList.get(0).setVisible(true);
			
			ivList.get(1).setPos(pos.getX()+30, pos.getY()+18);
			ivList.get(1).setIngredient(ingIdx[1]);
			ivList.get(1).setVisible(true);
			
			ivList.get(2).setPos(pos.getX()-30, pos.getY()+18);
			ivList.get(2).setIngredient(ingIdx[2]);
			ivList.get(2).setVisible(true);
			break;
		case 4:
			ivList.get(0).setPos(pos.getX()-25, pos.getY()-25);
			ivList.get(0).setIngredient(ingIdx[0]);
			ivList.get(0).setVisible(true);
			
			ivList.get(1).setPos(pos.getX()+25, pos.getY()-25);
			ivList.get(1).setIngredient(ingIdx[1]);
			ivList.get(1).setVisible(true);
			
			ivList.get(2).setPos(pos.getX()+25, pos.getY()+25);
			ivList.get(2).setIngredient(ingIdx[2]);
			ivList.get(2).setVisible(true);
			
			ivList.get(3).setPos(pos.getX()-25, pos.getY()+25);
			ivList.get(3).setIngredient(ingIdx[3]);
			ivList.get(3).setVisible(true);
			break;
		case 5 :
			ivList.get(0).setPos(pos.getX(), pos.getY()-35);
			ivList.get(0).setIngredient(ingIdx[0]);
			ivList.get(0).setVisible(true);
			
			ivList.get(1).setPos(pos.getX()+33, pos.getY()-11);
			ivList.get(1).setIngredient(ingIdx[1]);
			ivList.get(1).setVisible(true);
			
			ivList.get(2).setPos(pos.getX()+21, pos.getY()+28);
			ivList.get(2).setIngredient(ingIdx[2]);
			ivList.get(2).setVisible(true);
			
			ivList.get(3).setPos(pos.getX()-21, pos.getY()+28);
			ivList.get(3).setIngredient(ingIdx[3]);
			ivList.get(3).setVisible(true);
			
			ivList.get(4).setPos(pos.getX()-33, pos.getY()-11);
			ivList.get(4).setIngredient(ingIdx[4]);
			ivList.get(4).setVisible(true);
			break;
		default :
		break;
		}
		for (IngredientVisualizer iv:ivList) {
			iv.render();
		}
	}
}
