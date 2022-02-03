package fr.ovrckdlike.ppp.tiles;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.objects.Ingredient;
import fr.ovrckdlike.ppp.objects.Item;

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
		if (oldContent != null) {
			oldContent.setMode(0);
		}
		return oldContent;
	}
	
	public Item getContent() {
		return this.content;
	}
	
	public Ingredient use() {
		if (content == null) {
			return new Ingredient(ingredientType);
		}
		else return null;
	}
	
	public void render() {
		Renderer.drawTexture(this.pos[0], this.pos[1], this.size, this.size, 0, Texture.ingredientRefiller);
		switch (this.ingredientType) {
		case 0:
			break;
		case 1:
			Renderer.drawTexture(this.pos[0]+35, this.pos[1]+45, 45f, 45f, 0.35f, Texture.salade);
		}
	}
}
