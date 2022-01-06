package fr.ovrckdlike.ppp.objects;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;

public class IngredientRefiller extends Tile {
	private int ingredientType;
	private Item content;
	
	public IngredientRefiller(float[] pos, int ingredientType) {
		this.pos = pos;
		this.type = 7;
		this.content = null;
		this.ingredientType = ingredientType;
	}
	
	public Item takeOrDrop(Item newContent) {
		Item oldContent = this.content;
		this.content = newContent;
		if (this.content != null) {
			this.content.setMode(1);
			this.content.setPos(this.pos[0]+20, this.pos[0]+20);
		}
		return oldContent;
	}
	
	
	public Ingredient use() {
		return new Ingredient(ingredientType, pos);
	}
	
	public void render() {
		Renderer.drawTexture(this.pos[0], this.pos[1], this.size, this.size, 0, Texture.ingredientRefiller);
	}
}
