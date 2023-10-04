package fr.ovrckdlike.ppp.tiles;

import fr.ovrckdlike.ppp.gameplay.RecipeScheduler;
import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.map.Map;
import fr.ovrckdlike.ppp.objects.Item;
import fr.ovrckdlike.ppp.objects.Plate;
import fr.ovrckdlike.ppp.objects.Player;
import fr.ovrckdlike.ppp.physics.Dot;
import fr.ovrckdlike.ppp.physics.Rectangle;
import fr.ovrckdlike.ppp.scene.GameScene;

/**
 * A class to manage service tables (the table where you put finished recipe for the client).
 */
public class ServiceTable extends Tile {

  /**
   * The constructor of the class.
   *
   * @param pos The position of the tile.
   * @param dir The direction of the tile.
   */
  public ServiceTable(Dot pos, int dir) {
    this.type = 7;
    this.space = new Rectangle(pos, size, size, (float) (dir * (Math.PI / 2)));
  }

  /**
   * The method that manage the use of the tile by the player.
   *
   * @param player The player that use the tile.
   */
  public void use(Player player) {

    Item content = player.getInHand();
    if (content instanceof Plate) {
      boolean[] ingList = ((Plate) content).getContent();
      Map.get().getItemList().remove(content);
      RecipeScheduler.get().checkContent(ingList);
      player.drop();
      GameScene.addPlateToReturn();
    }
  }


  /**
   * The method that render the tile.
   */
  public void render() {
    Renderer.drawTexture(space, Texture.serviceTable);
  }

}
