package fr.ovrckdlike.ppp.tiles;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.objects.Ingredient;
import fr.ovrckdlike.ppp.objects.Player;
import fr.ovrckdlike.ppp.physics.Dot;
import fr.ovrckdlike.ppp.physics.Rectangle;

/**
 * Class that represent a bin.
 */
public class Bin extends Tile {
  /**
   * A constructor for the bin.
   *
   * @param pos The position of the bin.
   */
  public Bin(Dot pos) {
    this.type = 11;
    this.space = new Rectangle(pos, size, size, 0f);
  }

  /**
   * Method that allow to use the bin.
   *
   * @param player The player that use the bin.
   */
  public void use(Player player) {
    if (player.getInHand() != null) {
      player.getInHand().flush();
    }
    if (player.getInHand() instanceof Ingredient) {
      player.drop();
    }
  }

  /**
   * Method that render the bin.
   */
  public void render() {
    Renderer.drawTexture(space, Texture.bin);
  }
}
