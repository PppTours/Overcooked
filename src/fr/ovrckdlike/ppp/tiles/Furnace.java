package fr.ovrckdlike.ppp.tiles;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.gui.TimeBar;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.objects.Item;
import fr.ovrckdlike.ppp.objects.Plate;
import fr.ovrckdlike.ppp.objects.Player;
import fr.ovrckdlike.ppp.physics.Dot;
import fr.ovrckdlike.ppp.physics.Rectangle;
import fr.ovrckdlike.ppp.physics.Time;


/**
 * A class that represents a Furnace.
 */
public class Furnace extends Tile implements ContainerTile, Burnable {
  /**
   * Indicates if the furnace is burning.
   */
  private boolean burning;

  /**
   * The plate in the furnace.
   */
  private Plate inFurnace;

  /**
   * The time to cook an ingredient.
   */
  private final int cookingTime = 10;

  /**
   * The time in the furnace.
   */
  private float timeInFurnace;

  /**
   * The time bar of the furnace.
   */
  private final TimeBar timeBar;

  /**
   * The position of the time bar.
   */
  private final Dot timeBarPos;


  /**
   * Constructor of Furnace.
   *
   * @param pos The position of the furnace.
   */
  public Furnace(Dot pos) {
    type = 9;
    this.space = new Rectangle(pos, size, size, 0f);
    timeBarPos = new Dot(space.getPos().getX(), pos.getY() + 48f);
    inFurnace = null;
    timeInFurnace = 0;
    timeBar = new TimeBar(timeBarPos, cookingTime);
  }


  /**
   * Get the content of the furnace.
   *
   * @return The content of the furnace.
   */
  public Item getContent() {
    return inFurnace;
  }

  /**
   * Take or drop an item in the furnace.
   *
   * @param newContent The item to take or drop.
   * @return The item that was in the furnace.
   */
  public Item takeOrDrop(Item newContent) {
    if (newContent instanceof Plate || newContent == null) {
      Plate oldContent = inFurnace;
      if (oldContent != null) {
        oldContent.setMode(0);
      }
      inFurnace = (Plate) newContent;
      if (burning) {
        inFurnace.burn();
      }
      if (inFurnace != null) {
        timeInFurnace = (inFurnace.getCooked()) ? cookingTime : 0f;
        inFurnace.setMode(1);
        inFurnace.setPos(space.getPos());
      }
      return oldContent;
    } else {
      return newContent;
    }
  }

  /**
   * Cook the ingredient in the furnace.
   */
  public void cook() {
    if (inFurnace == null) {
      return;
    }
    if (!inFurnace.isEmpty()) {
      long dt = Time.get().getDt();
      float sdt = dt / 1E9f;
      timeInFurnace += sdt;
      if (inFurnace != null) {
        if (timeInFurnace > cookingTime) {
          inFurnace.cook();
        }
        if (timeInFurnace > 2 * cookingTime) {
          setInFire();
        }

      }
    }
  }

  /**
   * Use the furnace.
   *
   * @param player The player that use the furnace.
   */
  public void use(Player player) {}

  /**
   * Render the furnace.
   */
  public void render() {
    Renderer.drawTexture(space, Texture.furnaceBack);

    if (inFurnace != null) {
      inFurnace.render();
    }

    Renderer.drawTexture(space, Texture.furnaceFront);
    timeBar.render(timeInFurnace, timeBarPos);

    if (burning) {
      Renderer.drawTexture(space, Texture.fire);
    }
  }

  /**
   * Indicates if the furnace is burning.
   *
   * @return True if the furnace is burning, false otherwise.
   */
  @Override
  public boolean isBurning() {
    return burning;
  }

  /**
   * Set the furnace in fire.
   */
  @Override
  public void setInFire() {
    burning = true;
    inFurnace.burn();
  }

  /**
   * Stop the fire in the furnace.
   */
  @Override
  public void stopFire() {
    burning = false;
  }

}
