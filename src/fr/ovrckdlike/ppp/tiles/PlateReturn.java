package fr.ovrckdlike.ppp.tiles;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.map.Map;
import fr.ovrckdlike.ppp.objects.Item;
import fr.ovrckdlike.ppp.objects.Plate;
import fr.ovrckdlike.ppp.objects.Player;
import fr.ovrckdlike.ppp.physics.Dot;
import fr.ovrckdlike.ppp.physics.Rectangle;

/**
 * This class manage the plate return.
 */
public class PlateReturn extends Tile implements ContainerTile {
  /**
   * The number of plates that have been returned.
   */
  int plateNb;

  /**
   * The constructor of the plate return (singleton).
   *
   * @param pos the position of the plate return.
   */
  public PlateReturn(Dot pos) {
    this.space = new Rectangle(pos, size, size, 0f);
    this.plateNb = 0;
    this.type = 8;
  }

  /**
   * The method that add a plate to the plate return.
   */
  public void addPlate() {
    plateNb++;
  }


  /**
   * The method that allow the player to use the plate return. And manage the plate return.
   *
   * @param player the player that use the plate return.
   */
  @Override
  public void use(Player player) {}

  /**
   * The method that allow the player to take or drop an item.
   *
   * @param content the item to take or drop.
   * @return the item that have been taken or dropped.
   */
  @Override
  public Item takeOrDrop(Item content) {
    if (content != null) {
      return content;
    }
    if (plateNb > 0) {
      plateNb--;
      Dot platePos = new Dot(space.getPos());
      Plate newPlate = new Plate(platePos, true, 0);
      Map.get().getItemList().add(newPlate);
      return newPlate;
    } else {
      return null;
    }
  }


  /**
   * The method that render the plate return.
   */
  public void render() {
    Renderer.drawTexture(space, Texture.plateReturn);
    if (plateNb > 0) {
      Dot pos = space.getPos();
      Rectangle plateSpace = new Rectangle(new Dot(pos), size, size, 0f);
      Renderer.drawTexture(plateSpace, Texture.dirtyPlate);
    }
  }

  /**
   * The method that return the content of the plate return.
   *
   * @return the content of the plate return.
   */
  @Override
  public Item getContent() {
    return null;
  }
}
