package fr.ovrckdlike.ppp.tiles;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.gui.TimeBar;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.objects.Plate;
import fr.ovrckdlike.ppp.objects.Player;
import fr.ovrckdlike.ppp.physics.Dot;
import fr.ovrckdlike.ppp.physics.Rectangle;
import fr.ovrckdlike.ppp.physics.Time;
import java.util.ArrayList;

/**
 * Class representing a sink, it can wash plates.
 */
public class Sink extends Tile {
  /**
   * Number of plates in the sink.
   */
  private int nbPlate;

  /**
   * Time to wash a plate.
   */
  private final int washTime;

  /**
   * Direction of the sink.
   */
  private final int direction;

  /**
   * Attached dryer.
   */
  private Dryer attachedDryer;

  /**
   * List of plates in the sink.
   */
  private ArrayList<Plate> plates;

  /**
   * Current wash time.
   */
  private float currentWashTime;

  /**
   * Time bar.
   */
  private final TimeBar tb;

  /**
   * Constructor of the sink.
   *
   * @param pos Position of the sink.
   * @param direction Direction of the sink.
   */
  public Sink(Dot pos, int direction) {
    this.type = 4;
    this.washTime = 3;
    this.space = new Rectangle(pos, size, size, (float) (direction * (- Math.PI / 2)));
    this.nbPlate = 0;
    this.attachedDryer = null;
    this.direction = direction;
    this.currentWashTime = 0;
    Dot timeBarPos = new Dot(pos.getX() - 25f, pos.getY() + 48f);
    this.tb = new TimeBar(timeBarPos, washTime);
  }

  /**
   * Add a plate to the sink.
   */
  public void addPlate() {
    nbPlate++;
  }

  /**
   * Attach a dryer to the sink.
   *
   * @param dryer Dryer to attach.
   */
  public void setAttachedDryer(Dryer dryer) {
    attachedDryer = dryer;
  }

  /**
   * Get the direction of the sink.
   *
   * @return Direction of the sink.
   */
  public int getDirection() {
    return direction;
  }

  /**
   * Drop a plate in the sink.
   *
   * @param plate Plate to drop.
   */
  public void drop(Plate plate) {
    if (plate.getDirty() && plate.isEmpty()) {
      plates.add(plate);
      nbPlate++;
    }
  }

  /**
   * Manage the use of the sink by the player.
   *
   * @param player Player using the sink.
   */
  public void use(Player player) {
    long dt = Time.get().getDt();
    float sdt = dt / 1E9f;
    if (nbPlate > 0) {
      player.lockMove();
      currentWashTime += sdt;

      if (currentWashTime >= washTime) {
        nbPlate--;
        attachedDryer.addPlate();
        currentWashTime = 0;
        if (nbPlate == 0) {
          player.unlockMove();
        }
      }
    }
  }


  /**
   * Render the sink.
   */
  public void render() {
    Renderer.drawTexture(space, Texture.sink);
    int angle = switch (direction) {
      case 1 -> 90;
      case 2 -> 180;
      case 3 -> 270;
      default -> 0;
    };

    int posDep = -15;
    for (int i = 0; i < nbPlate; i++) {
      Rectangle rec = space.resized(-0.8f * space.getWidth()).move(new Dot(posDep, 10));
      rec.changeAngle(angle);
      Renderer.drawTexture(rec, Texture.dirtyPlateSink);
      posDep += 5;
    }

    tb.render(currentWashTime);
  }

}
