package fr.ovrckdlike.ppp.tiles;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.objects.Ingredient;
import fr.ovrckdlike.ppp.objects.Item;
import fr.ovrckdlike.ppp.objects.Player;
import fr.ovrckdlike.ppp.scene.GameScene;

public class IngredientRefiller extends ContainerTile {
	private int ingredientType;
	
	public IngredientRefiller(float[] pos, int ingredientType) {
		this.pos = pos;
		this.type = 6;
		this.content = null;
		this.ingredientType = ingredientType;
	}
	
	public Item getContent() {
		return this.content;
	}
	
	public void use(Player player) {
		if (content == null) {
			if (player.getInHand() == null) {
				Ingredient ing = new Ingredient(ingredientType);
				GameScene.getItemList().add(ing);
				player.setInHand(ing);
			}
		}
	}
	
	public void render() {
		Renderer.drawTexture(this.pos[0], this.pos[1], this.size, this.size, 0, Texture.ingredientRefiller);
		switch (this.ingredientType) {
		case 0:
			Renderer.drawTexture(this.pos[0]+35, this.pos[1]+45, 45f, 45f, 0.35f, Texture.tomato);
			break;
		case 1:
			Renderer.drawTexture(this.pos[0]+35, this.pos[1]+45, 45f, 45f, 0.35f, Texture.salade);
			break;
		case 2:
			Renderer.drawTexture(this.pos[0]+35, this.pos[1]+45, 45f, 45f, 0.35f, Texture.onion);
			break;
		case 3:
			Renderer.drawTexture(this.pos[0]+35, this.pos[1]+45, 45f, 45f, 0.35f, Texture.mushroom);
			break;
		case 4:
			Renderer.drawTexture(this.pos[0]+35, this.pos[1]+45, 45f, 45f, 0.35f, Texture.meat);
			break;
		case 5:
			Renderer.drawTexture(this.pos[0]+35, this.pos[1]+45, 45f, 45f, 0.35f, Texture.cheese);
			break;
		case 6:
			Renderer.drawTexture(this.pos[0]+35, this.pos[1]+45, 45f, 45f, 0.35f, Texture.pasta);
			break;
		case 7:
			Renderer.drawTexture(this.pos[0]+35, this.pos[1]+45, 45f, 45f, 0.35f, Texture.sausage);
			break;
		case 8:
			Renderer.drawTexture(this.pos[0]+35, this.pos[1]+45, 45f, 45f, 0.35f, Texture.pizzaDough);
			break;
		case 9:
			Renderer.drawTexture(this.pos[0]+35, this.pos[1]+45, 45f, 45f, 0.35f, Texture.burgerBread);
			break;
		case 10:
			Renderer.drawTexture(this.pos[0]+35, this.pos[1]+45, 45f, 45f, 0.35f, Texture.chicken);
			break;
		case 11:
			Renderer.drawTexture(this.pos[0]+35, this.pos[1]+45, 45f, 45f, 0.35f, Texture.rice);
			break;
		case 12:
			Renderer.drawTexture(this.pos[0]+35, this.pos[1]+45, 45f, 45f, 0.35f, Texture.potato);
			break;
		}
	}
}
