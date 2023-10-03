package fr.ovrckdlike.ppp.objects;

import fr.ovrckdlike.ppp.physics.Circle;
import fr.ovrckdlike.ppp.physics.Dot;
import fr.ovrckdlike.ppp.tiles.Tile;
import java.util.List;

/**
 * A class that represents an Item.
 */
public abstract class Item extends Entity {
  /**
   * If the item is in the player's hand.
   */
  protected boolean inPlayerHand;

  /**
   * The mode of the item.
   */
  protected int mode;

  /**
   * The angle of the item.
   */
  protected float angle;

  /**
   * Constructor of Item.
   *
   * @param pos The position of the item.
   */
  public Item(Dot pos) {
    space = new Circle(pos, 25);
    inPlayerHand = false;
  }

  /**
   * Render the item.
   */
  public abstract void render();

  /**
   * Prepare the item.
   */
  public abstract void prepare();

  /**
   * If the item is on the ground.
   *
   * @return If the item is on the ground.
   */
  public boolean isOnGround() {
    return (mode == 0 && !inPlayerHand);
  }

  /**
   * Set the mode of the item.
   *
   * @param mode The new mode.
   */
  public void setMode(int mode) {
    this.mode = mode;
  }

  /**
   * Set the angle of the item.
   *
   * @param newAngle The new angle.
   */
  public void changeAngle(float newAngle) {
    angle = newAngle;
  }

  /**
   * change the angle of the item with a direction.
   *
   * @param direction The direction of the angle.
   */
  public void changeAngle(int direction) {
    angle = (float) (- direction * Math.PI / 4f);
  }

  /**
   * Set the item in the player's hand.
   *
   * @param set If the item is in the player's hand.
   */
  public void setInPlayerHand(boolean set) {
    this.inPlayerHand = set;
  }

  /**
   * Indicates if the item is on a table.
   *
   * @return True if the item is on a table, false otherwise.
   */
  public boolean isOnTable() {
    return mode != 0;
  }

  /**
   * Indicates if the item is in the player's hand.
   *
   * @return True if the item is in the player's hand, false otherwise.
   */
  public boolean getInPlayerHand() {
    return this.inPlayerHand;
  }

  /**
   * Set the position of the item.
   *
   * @param newPos The new position.
   */
  public void setPos(Dot newPos) {
    space.setPos(newPos);
  }

  /**
   * Get the position of the item.
   *
   * @return The position of the item.
   */
  public Dot getPos() {
    return space.getPos();
  }

  /**
   * Flush the item.
   */
  public abstract void flush();

  /**
   * Get the distance between the item and a position.
   *
   * @param pos The position.
   * @return The distance between the item and the position.
   */
  public float distanceTo(Dot pos) {
    return space.getPos().distanceTo(pos);
  }

  /**
   * Get the angle between the item and a position.
   *
   * @param pos The position.
   * @return The angle between the item and the position.
   */
  public double angleTo(Dot pos) {
    return space.getPos().angleTo(pos);
  }

  /**
   * Manage the collision between the item and a list of entity.
   *
   * @param objList The list of entity.
   * @param <T> The type of the entity.
   */
  public <T extends Entity> void collideEntity(List<T> objList) {
    if (!inPlayerHand && mode == 0) {
      for (Entity obj : objList) {
        if (obj != this) {
          float move = -space.getPos().distanceTo(obj.space.getPos())
              + (space.getRay() + obj.space.getRay());
          if (move > 0) {
            space.collide(obj.space, move, true);
          }
        }
      }
    }
  }

  /**
   * Manage the collision between the item and a list of tile.
   *
   * @param tileList The list of tile.
   */
  public void collideTile(List<Tile> tileList) {
    if (!inPlayerHand && mode == 0) {
      for (Tile tile : tileList) {
        float move = -getPos().distanceTo(tile.getSpace().nearestFromPos(getPos()))
            + space.getRay();
        if (move > 0) {
          space.collide(tile.getSpace(), move);
        }
      }
    }
  }
}
