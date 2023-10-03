package fr.ovrckdlike.ppp.tiles;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.map.Map;
import fr.ovrckdlike.ppp.objects.Ingredient;
import fr.ovrckdlike.ppp.objects.Item;
import fr.ovrckdlike.ppp.objects.Player;
import fr.ovrckdlike.ppp.physics.Dot;
import fr.ovrckdlike.ppp.physics.Rectangle;

/**
 * A class that represents an IngredientRefiller.
 */
public class IngredientRefiller extends Tile implements ContainerTile {
  /**
   * The type of the ingredient refiller.
   */
  private final int ingredientType;

  /**
   * The content of the ingredient refiller.
   */
  private Item content;

  /**
   * Constructor of IngredientRefiller.
   *
   * @param pos The position of the ingredient refiller.
   * @param ingredientType The type of the ingredient refiller.
   */
  public IngredientRefiller(Dot pos, int ingredientType) {
    this.space = new Rectangle(pos, size, size, 0f);
    this.type = 6;
    this.content = null;
    this.ingredientType = ingredientType;
  }

  /**
   * Get the content of the ingredient refiller.
   *
   * @return The content of the ingredient refiller.
   */
  public Item getContent() {
    return this.content;
  }

  /**
   * Use the ingredient refiller.
   *
   * @param player The player that use the ingredient refiller.
   */
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

  /**
   * Render the ingredient refiller.
   */
  public void render() {
    Renderer.drawTexture(space, Texture.ingredientRefiller);
    Rectangle ingSpace = new Rectangle(space.getPos().getX() - 5,
        space.getPos().getY(),
        45f, 45f, - 0.35f);
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
      default:
        break;
    }
    if (content != null) {
      content.render();
    }
  }

  /**
   * Take or drop an item in the ingredient refiller.
   *
   * @param newContent The item to take or drop.
   * @return The item that was in the ingredient refiller.
   */
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
