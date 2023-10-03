package fr.ovrckdlike.ppp.tiles;


import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.map.Map;
import fr.ovrckdlike.ppp.objects.Plate;
import fr.ovrckdlike.ppp.objects.Player;
import fr.ovrckdlike.ppp.physics.Dot;
import fr.ovrckdlike.ppp.physics.Rectangle;

/**
 * A class that represents a Dryer.
 */
public class Dryer extends Tile {

  /**
   * The number of plate in the dryer.
   */
  private int nbPlate;

  /**
   * The direction of the dryer.
   */
  private final int direction;

  /**
   * Constructor of Dryer.
   *
   * @param pos The position of the dryer.
   * @param direction The direction of the dryer.
   */
  public Dryer(Dot pos, int direction) {
    this.space = new Rectangle(pos, size, size, (float) (- Math.PI * direction / 2));
    this.type = 5;
    this.nbPlate = 0;
    this.direction = direction;
  }

  /**
   * Add a plate in the dryer.
   */
  public void addPlate() {
    nbPlate++;
  }

  /**
   * Use the dryer.
   *
   * @param player The player that use the dryer.
   */
  public void use(Player player) {}


  /**
   * Take a plate from the dryer.
   *
   * @param player The player that take the plate.
   */
  public void takePlate(Player player) {
    if (nbPlate > 0) {
      if (player.getInHand() == null) {
        Dot tempPos = new Dot(-50f, -50f);
        Plate plate = new Plate(tempPos, false, 0);
        plate.setInPlayerHand(true);
        Map.get().getItemList().add(plate);
        player.setInHand(plate);
        nbPlate--;
      }
    }
  }

  /**
   * Render the dryer.
   */
  public void render() {
    Renderer.drawTexture(space, Texture.dryer);

  }
}
