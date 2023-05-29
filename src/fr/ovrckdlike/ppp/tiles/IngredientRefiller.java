package fr.ovrckdlike.ppp.tiles;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.map.Map;
import fr.ovrckdlike.ppp.objects.Ingredient;
import fr.ovrckdlike.ppp.objects.Item;
import fr.ovrckdlike.ppp.objects.Player;
import fr.ovrckdlike.ppp.physics.Dot;
import fr.ovrckdlike.ppp.physics.Rectangle;

public class IngredientRefiller extends Tile implements ContainerTile {
	private int ingredientType;
	private Item content;
	
	public IngredientRefiller(Dot pos, int ingredientType) {
		this.space = new Rectangle(pos, size, size, 0f);
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
				ing.setInPlayerHand(true);
				Map.get().getItemList().add(ing);
				player.setInHand(ing);
			}
		}
	}
	
	public void render() {
		Renderer.drawTexture(space, Texture.ingredientRefiller);
		Rectangle ingSpace = new Rectangle(space.getPos().getX()-5,
											space.getPos().getY(),
											45f, 45f, -0.35f);
		switch (this.ingredientType) {
		case 0:
			Renderer.drawTexture(ingSpace, Texture.tomato);
			break;
		case 1:
			Renderer.drawTexture(ingSpace, Texture.salade);
			break;
		case 2:
			Renderer.drawTexture(ingSpace, Texture.onion);
			break;
		case 3:
			Renderer.drawTexture(ingSpace, Texture.mushroom);
			break;
		case 4:
			Renderer.drawTexture(ingSpace, Texture.meat);
			break;
		case 5:
			Renderer.drawTexture(ingSpace, Texture.cheese);
			break;
		case 6:
			Renderer.drawTexture(ingSpace, Texture.pasta);
			break;
		case 7:
			Renderer.drawTexture(ingSpace, Texture.sausage);
			break;
		case 8:
			Renderer.drawTexture(ingSpace, Texture.pizzaDough);
			break;
		case 9:
			Renderer.drawTexture(ingSpace, Texture.burgerBread);
			break;
		case 10:
			Renderer.drawTexture(ingSpace, Texture.chicken);
			break;
		case 11:
			Renderer.drawTexture(ingSpace, Texture.rice);
			break;
		case 12:
			Renderer.drawTexture(ingSpace, Texture.potato);
			break;
		}
		if (content != null) content.render();
	}

	@Override
	public Item takeOrDrop(Item newContent) {
		Item oldContent = this.content;
		this.content = newContent;
		if (this.content != null) {
			this.content.setMode(1);
			this.content.setPos(space.getPos());
		}
		if (oldContent != null) {
			oldContent.setMode(0);
		}
		return oldContent;
	}
}
