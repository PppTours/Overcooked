package fr.ovrckdlike.ppp.tiles;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Sound;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.objects.CookerContainer;
import fr.ovrckdlike.ppp.objects.Item;
import fr.ovrckdlike.ppp.objects.Player;
import fr.ovrckdlike.ppp.physics.Dot;
import fr.ovrckdlike.ppp.physics.Rectangle;
import fr.ovrckdlike.ppp.physics.Time;
import fr.ovrckdlike.ppp.scene.SoundHandler;

/**
 * A class that represents a GasCooker.
 */
public class GasCooker extends Tile implements ContainerTile, Burnable {
  /**
   * The content of the gas cooker.
   */
  private CookerContainer content;

  /**
   * Indicates if the gas cooker is burning.
   */
  private boolean burning;

  /**
   * Indicates if the gasCooker is already in fire
   */
  private boolean inFire;

  /**
   * Get the content of the gas cooker.
   *
   * @return The content of the gas cooker.
   */
  public Item getContent() {
    return content;
  }

  /**
   * A Constructor of GasCooker.
   *
   * @param pos The position of the gas cooker.
   * @param onGas The content of the gas cooker.
   */
  public GasCooker(Dot pos, CookerContainer onGas) {
    this.type = 3;
    this.space = new Rectangle(pos, size, size, 0f);
    this.content = onGas;
    this.content.setPos(pos);
    this.content.setMode(1);
    burning = false;
    inFire = false;
  }

  /**
   * Cook the content of the gas cooker.
   */
  public void cook() {
    if (content == null) {
      SoundHandler.stop(SoundHandler.gasCooking);
      return;
    }
    if (content.isFilled()) {
      SoundHandler.play(SoundHandler.gasCooking);
      long dt = Time.get().getDt();
      content.cook(dt);
      if (content.isBurnt() && !inFire) {
        setInFire();
        inFire = true;
      }
    }
  }

  /**
   * Use the gas cooker.
   *
   * @param player The player that use the gas cooker.
   */
  public void use(Player player) {}

  /**
   * Render the gas cooker.
   */
  public void render() {
    Renderer.drawTexture(space, Texture.gasCooker);
    if (content != null) {
      content.render();
    }

    if (burning) {
      Renderer.drawTexture(space, Texture.fire);
    }
  }

  /**
   * Take or drop an item in the gas cooker.
   *
   * @param newContent The item to take or drop.
   * @return The item that was in the gas cooker.
   */
  @Override
  public Item takeOrDrop(Item newContent) {
    if ((newContent instanceof CookerContainer || newContent == null) && !burning) {
      CookerContainer oldContent = content;
      content = (CookerContainer) newContent;
      if (content != null) {
        content.setMode(1);
        content.setPos(space.getPos());
      }
      if (oldContent != null) {
        oldContent.setMode(0);
      }
      return oldContent;
    }
    return newContent;

  }

  /**
   * Indicates if the gas cooker is burning.
   *
   * @return True if the gas cooker is burning, false otherwise.
   */
  @Override
  public boolean isBurning() {
    return burning;
  }

  /**
   * Set the gas cooker in fire.
   */
  @Override
  public void setInFire() {
    burning = true;
    content.burn();
    SoundHandler.play(SoundHandler.ignite);
    SoundHandler.play(SoundHandler.fire);
  }

  /**
   * Stop the fire in the gas cooker.
   */
  @Override
  public void stopFire() {
    burning = false;
    SoundHandler.stop(SoundHandler.fire);
    if (content != null) {
      content.stopFire();

    }
    inFire = false;
  }
}
