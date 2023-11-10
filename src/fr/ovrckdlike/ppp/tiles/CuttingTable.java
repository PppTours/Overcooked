package fr.ovrckdlike.ppp.tiles;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.gui.TimeBar;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.objects.Ingredient;
import fr.ovrckdlike.ppp.objects.Item;
import fr.ovrckdlike.ppp.objects.Player;
import fr.ovrckdlike.ppp.physics.Dot;
import fr.ovrckdlike.ppp.physics.Rectangle;
import fr.ovrckdlike.ppp.physics.Time;
import fr.ovrckdlike.ppp.scene.SoundHandler;


/**
 * A class that represents a CuttingTable.
 */
public class CuttingTable extends Tile implements ContainerTile {
  // reinitialiser currentCuttingTime au changement de contenu

  /**
   * The time to cut an ingredient.
   */
  private final int cuttingTime;

  /**
   * The content of the cutting table.
   */
  private Item content;

  /**
   * The current cutting time.
   */
  private float currentCuttingTime;

  /**
   * The time bar of the cutting table.
   */
  private final TimeBar timeBar;

  /**
   * The position of the time bar.
   */
  private Dot timeBarPos;

  /**
   * Get the content of the cutting table.
   *
   * @return The content of the cutting table.
   */
  public Item getContent() {
    return content;
  }

  /**
   * Take or drop an item in the cutting table.
   *
   * @param newContent The item to take or drop.
   * @return The item that was in the cutting table.
   */
  public Item takeOrDrop(Item newContent) {
    currentCuttingTime = 0f;
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


  /**
   * Constructor of CuttingTable.
   *
   * @param pos The position of the cutting table.
   */
  public CuttingTable(Dot pos) {

    this.space = new Rectangle(pos, size, size, 0f);
    this.content = null;
    this.type = 2;
    this.currentCuttingTime = 0;
    this.cuttingTime = 2;
    this.timeBarPos = new Dot(pos.getX(), pos.getY() + 35f);
    this.timeBar = new TimeBar(timeBarPos, cuttingTime);
  }

  /**
   * Render the cutting table.
   */
  public void render() {
    Renderer.drawTexture(space, Texture.cuttingTable);
    if (content != null) {
      content.render();
    }
    timeBar.render(currentCuttingTime);
  }

  /**
   * Use the cutting table.
   *
   * @param player The player that use the cutting table.
   */
  public void use(Player player) {
    if (content instanceof Ingredient) {
      if (!((Ingredient) content).getPrepared()) {
        player.lockMove();
        long dt = Time.get().getDt();
        float sdt = dt / 1E9f;
        currentCuttingTime += sdt;
        if (currentCuttingTime >= cuttingTime) {
          currentCuttingTime = 0;
          player.unlockMove();
          content.prepare();
        }
      }
    }
  }
}
