package fr.ovrckdlike.ppp.objects;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_0;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_CONTROL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_TAB;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

import fr.ovrckdlike.ppp.graphics.KeyListener;
import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.graphics.SoundHandler;
import fr.ovrckdlike.ppp.internal.Sound;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.physics.Circle;
import fr.ovrckdlike.ppp.physics.Dot;
import fr.ovrckdlike.ppp.physics.Time;
import fr.ovrckdlike.ppp.tiles.Tile;
import java.util.List;

/**
 * A class representing a player in the game.
 */
public class Player extends Entity {
  /**
   * The skin of the player.
   */
  private final Texture skin;

  /**
   * The Id of the player.
   */
  private final byte id;

  /**
   * The direction of the player.
   */
  private int direction;

  /**
   * The speed of the player.
   */
  private final int moveSpeed;

  /**
   * If the player is blocked.
   */
  private boolean blocked;

  /**
   * The item in the hand of the player.
   *
   * @see Item
   */
  private Item inHand;

  /**
   * The last move of the player.
   */
  private float lastMove;

  /**
   * The time of the dash.
   */
  private float dashTime;

  /**
   * If the dash is ready.
   */
  private boolean dashIsReady;

  /**
   * If the player want to pick or drop an item.
   */
  private boolean pickDrop;

  /**
   * If the player want to interact with something.
   */
  private boolean interact;

  /**
   * The last time the player picked or dropped an item.
   */
  private long lastPickDrop = 0;

  /**
   * The last time the player interacted with something.
   */
  private long lastInteract = 0;


  /**
   * The constructor of the player.
   *
   * @param pos  The position of the player.
   * @param id   The id of the player.
   * @param skin The skin of the player.
   */
  public Player(Dot pos, byte id, Texture skin) {
    this.skin = skin;
    this.id = id;
    space = new Circle(pos, 50);
    blocked = false;
    direction = 0;
    moveSpeed = 300;
    lastMove = 0;
    dashIsReady = true;
    inHand = null;
  }

  /**
   * Manage the key action for the player.
   * Allow to move, turn, pick or drop an item, interact with something.
   */
  public void updateKeyPressed() {
    // controls
    boolean up;
    boolean left;
    boolean down;
    boolean right;
    boolean dash;
    switch (id) {
      case 1:
        up = KeyListener.isKeyPressed(GLFW_KEY_W);
        down = KeyListener.isKeyPressed(GLFW_KEY_S);
        left = KeyListener.isKeyPressed(GLFW_KEY_A);
        right = KeyListener.isKeyPressed(GLFW_KEY_D);
        pickDrop = KeyListener.isKeyPressed(GLFW_KEY_TAB);
        interact = KeyListener.isKeyPressed(GLFW_KEY_F);
        dash = KeyListener.isKeyPressed(GLFW_KEY_SPACE);
        break;
      case 2:
        up = KeyListener.isKeyPressed(GLFW_KEY_UP);
        down = KeyListener.isKeyPressed(GLFW_KEY_DOWN);
        left = KeyListener.isKeyPressed(GLFW_KEY_LEFT);
        right = KeyListener.isKeyPressed(GLFW_KEY_RIGHT);
        pickDrop = KeyListener.isKeyPressed(GLFW_KEY_RIGHT_CONTROL);
        interact = KeyListener.isKeyPressed(GLFW_KEY_KP_0);
        dash = KeyListener.isKeyPressed(GLFW_KEY_ENTER);
        break;
      default:
        return;
    }

    if (up || down || left || right) {
      changeAngle(up, down, left, right);
      movePlayer(Time.get().getDt());
      if (dash) {
        dash(Time.get().getDt());
      } else {
        releaseDash(Time.get().getDt());
      }
    }
  }

  /**
   * Reset the last time the player picked or dropped an item.
   */
  public void resetLastPickDrop() {
    lastPickDrop = Time.get().getCurrentTime();
  }

  /**
   * Reset the last time the player interacted with something.
   */
  public void resetLastInteract() {
    lastInteract = Time.get().getCurrentTime();
  }

  /**
   * A Getter for the last time the player interacted with something.
   *
   * @return The last time the player interacted with something.
   */
  public long getLastInteract() {
    return lastInteract;
  }

  /**
   * A Getter for the last time the player picked or dropped an item.
   *
   * @return The last time the player picked or dropped an item.
   */
  public long getLastPickDrop() {
    return lastPickDrop;
  }

  /**
   * A Getter for the pickDrop attribute.
   *
   * @return The pickDrop attribute.
   */
  public boolean getPickDrop() {
    return pickDrop;
  }

  /**
   * A Getter for the interact attribute.
   *
   * @return The interact attribute.
   */
  public boolean getInteract() {
    return interact;
  }

  /**
   * A getter for the size of the player (hitbox).
   *
   * @return The size of the player.
   */
  public float getSize() {
    return space.getRay() * 2f;
  }

  /**
   * A setter for the position of the player.
   *
   * @param newPos The new position of the player.
   */
  public void setPos(Dot newPos) {
    space.setPos(newPos);
  }

  /**
   * A setter for the position of the player.
   *
   * @param x The x coordinate of the new position of the player.
   * @param y The y coordinate of the new position of the player.
   */
  public void setPos(float x, float y) {
    space.setPos(new Dot(x, y));
  }

  /**
   * A getter for the last move of the player.
   *
   * @return The last move of the player.
   */
  public float getLastMove() {
    return lastMove;
  }

  /**
   * A getter for the item in the hand of the player.
   *
   * @return The item in the hand of the player.
   */
  public Item getInHand() {
    return this.inHand;
  }

  /**
   * A setter for the item in the hand of the player.
   *
   * @param item The new item in the hand of the player.
   */
  public void setInHand(Item item) {
    this.inHand = item;
  }

  /**
   * A getter for the id of the player.
   *
   * @param dt Time for the next dash.
   */
  public void releaseDash(long dt) {
    if (dashTime <= 0) {
      dashIsReady = true;
    }
    dashTimer(dt);
  }

  /**
   * Allow the player to dash.
   *
   * @param dt Time for the next dash.
   */
  public void dash(long dt) {
    if (dashIsReady) {
      dashIsReady = false;
      dashTime = .1f;
    }
    dashTimer(dt);
  }

  /**
   * To lock the player.
   */
  public void lockMove() {
    blocked = true;
  }

  /**
   * To unlock the player.
   */
  public void unlockMove() {
    blocked = false;
  }

  /**
   * The timer for the dash.
   *
   * @param dt Time for the next dash.
   */
  private void dashTimer(long dt) {
    if (dashTime > 0) {
      float sdt = dt / 1E9f;
      dashTime -= sdt;
    }
  }

  /**
   * Find the nearest item from the player.
   *
   * @param itemList The list of the items.
   * @return The id of the nearest item.
   */
  public int nearestItem(List<Item> itemList) {
    if (itemList.isEmpty()) {
      return -1;
    } else {
      int nearest = -1;
      float mindist = Float.MAX_VALUE;
      int i = 0;
      for (Item item : itemList) {
        if (!item.getInPlayerHand()) {
          Dot itemPos = item.getPos();
          float dist = distanceTo(itemPos);
          if (dist < mindist) {
            nearest = i;
            mindist = dist;
          }
        }
        i++;

      }
      return nearest;
    }
  }

  /**
   * Calculate the distance between the player and a position.
   *
   * @param pos The position to calculate the distance.
   * @return The distance between the player and the position.
   */
  public float distanceTo(Dot pos) {
    return space.getPos().distanceTo(pos);
  }

  /**
   * Calculate the angle between the player and a position.
   *
   * @param pos The position to calculate the angle.
   * @return The angle between the player and the position.
   */
  public double angleTo(Dot pos) {
    return space.getPos().angleTo(pos);

  }

  /**
   * Calculate the direction between the player and a position.
   *
   * @param pos The position to calculate the direction.
   * @return The direction between the player and the position.
   */
  public int directionTo(Dot pos) {
    double angle = this.angleTo(pos);

    if (angle <= Math.PI / 8 || angle > 15 * Math.PI / 8) {
      return 0;
    }
    if (angle > Math.PI / 8 && angle <= 3 * Math.PI / 8) {
      return 1;
    }
    if (angle > 3 * Math.PI / 8 && angle <= 5 * Math.PI / 8) {
      return 2;
    }
    if (angle > 5 * Math.PI / 8 && angle <= 7 * Math.PI / 8) {
      return 3;
    }
    if (angle > 7 * Math.PI / 8 && angle <= 9 * Math.PI / 8) {
      return 4;
    }
    if (angle > 9 * Math.PI / 8 && angle <= 11 * Math.PI / 8) {
      return 5;
    }
    if (angle > 11 * Math.PI / 8 && angle <= 13 * Math.PI / 8) {
      return 6;
    }
    if (angle > 13 * Math.PI / 8) {
      return 7;
    }
    return -1;
  }

  /**
   * Take the nearest item from the player.
   *
   * @param itemList The list of the items near to player.
   * @return If the player took an item.
   */
  public boolean takeNearestItem(List<Item> itemList) {
    int itemId = this.nearestItem(itemList);
    if (itemId == -1) {
      return false;
    }
    Item item = itemList.get(itemId);
    if (distanceTo(item.getPos()) > space.getRay() * 2 * 1.05) {
      return false;
    } else {
      if (this.inHand != null) {
        return false;
      } else {
        this.inHand = item;
        item.setInPlayerHand(true);
        return true;
      }
    }
  }

  /**
   * Find where the player can drop an item.
   *
   * @return The position where the player can drop an item.
   */
  public Dot whereToDrop() {
    double angle = getDirectionAngle();
    int distance = 75;
    return new Dot(space.getPos().getX() + (float) (distance * Math.cos(angle)),
        space.getPos().getY() - (float) (distance * Math.sin(angle)));
  }

  /**
   * Take an item.
   *
   * @param item The item to take.
   */
  public void take(Item item) {
    if (inHand == null && item != null) {
      inHand = item;
      inHand.setInPlayerHand(true);
    }
  }

  /**
   * Drop the item in the hand of the player.
   */
  public void drop() {
    if (inHand != null) {
      inHand.setInPlayerHand(false);
      inHand = null;
    }
  }

  /**
   * Render the player.
   */
  public void render() {
    Renderer.drawTexture(
        space.resized(60).surroundBySquare(
            (float) ((4 - direction) * Math.PI / 4)), skin);
    if (this.inHand != null) {
      Dot pos = space.getPos();
      float ray = space.getRay();
      Dot inHandPos = switch (direction) {
        case 0 -> new Dot(pos.getX(), pos.getY() - ray);
        case 1 -> new Dot((float) (pos.getX() + (Math.sqrt(2) * ray) / 2),
            (float) (pos.getY() - (Math.sqrt(2) * ray) / 2));
        case 2 -> new Dot(pos.getX() + ray, pos.getY());
        case 3 -> new Dot((float) (pos.getX() + (Math.sqrt(2) * ray) / 2),
            (float) (pos.getY() + (Math.sqrt(2) * ray) / 2));
        case 4 -> new Dot(pos.getX(), pos.getY() + ray);
        case 5 -> new Dot((float) (pos.getX() - (Math.sqrt(2) * ray) / 2),
            (float) (pos.getY() + (Math.sqrt(2) * ray) / 2));
        case 6 -> new Dot(pos.getX() - ray, pos.getY());
        case 7 -> new Dot((float) (pos.getX() - (Math.sqrt(2) * ray) / 2),
            (float) (pos.getY() - (Math.sqrt(2) * ray) / 2));
        default -> new Dot(0f, 0f);
      };
      inHand.setPos(inHandPos);
      inHand.changeAngle(direction);
      inHand.render();
    }
  }

  /**
   * Get the position of the player.
   *
   * @return The position of the player.
   */
  public Dot getPos() {
    return space.getPos();
  }

  /**
   * Get the direction of the player.
   *
   * @return The direction of the player.
   */
  public int getDirection() {
    return this.direction;
  }

  /**
   * Get the angle of the player.
   *
   * @return The angle of the player.
   */
  public double getDirectionAngle() {
    return switch (direction) {
      case 0 -> Math.PI / 2;
      case 1 -> Math.PI / 4;
      case 3 -> 7 * Math.PI / 4;
      case 4 -> 3 * Math.PI / 2;
      case 5 -> 5 * Math.PI / 4;
      case 6 -> Math.PI;
      case 7 -> 3 * Math.PI / 4;
      default -> 0;
    };
  }

  /**
   * Move the player.
   *
   * @param dt Time for the next move.
   */
  public void movePlayer(long dt) {
    if (!blocked) {
      float sdt = (float) (dt / 1E9);
      float dist = moveSpeed * sdt;
      if (dashTime > 0) {
        SoundHandler.play(SoundHandler.dashing);
        dist *= 7;
      } else {
        SoundHandler.play(SoundHandler.walking);
      }
      float distX;
      float distY;
      double angle = getDirectionAngle();

      distX = (float) (dist * Math.cos(angle));
      distY = (float) (-dist * Math.sin(angle));

      space.getPos().addToThis(distX, distY);
      lastMove = (float) (Math.sqrt(distX * distX + distY * distY));
    } else {
      SoundHandler.stop(SoundHandler.walking);
    }
  }

  /**
   * Manage the collision between the player and the other players.
   *
   * @param playerList The list of the players.
   */
  public void collidePlayer(List<Player> playerList) {
    if (dashTime <= 0f) {
      for (Player p : playerList) {
        if (p != this && p.dashTime <= 0f) {
          float move = space.getRay() + p.space.getRay() - distanceTo(p.space.getPos());
          if (move > 0) {
            space.collide(p.space, move, true);
          }
        }

      }
    }
  }

  /**
   * Manage the collision between the player and the entities.
   *
   * @param objList The list of the entities.
   * @param <T>    The type of the entities.
   */
  public <T extends Entity> void collideEntity(List<T> objList) {
    if (dashTime <= 0f) {
      for (Entity obj : objList) {
        float move = space.getRay() + obj.space.getRay() - distanceTo(obj.space.getPos());
        if (move > 0) {
          space.collide(obj.space, move, true);
        }
      }
    }
  }

  /**
   * Manage the collision between the player and the tiles.
   *
   * @param tileList The list of the tiles.
   */
  public void collideTile(List<Tile> tileList) {
    for (Tile tile : tileList) {
      float move = -getPos().distanceTo(tile.getSpace().nearestFromPos(getPos())) + space.getRay();
      if (move > 0) {
        space.collide(tile.getSpace(), move);
      }
    }
  }

  /**
   * Change the direction of the player.
   *
   * @param up   If the player want to go up.
   * @param down If the player want to go down.
   * @param left If the player want to go left.
   * @param right If the player want to go right.
   */
  public void changeAngle(boolean up, boolean down, boolean left, boolean right) {
    if (!blocked) {
      if (up && right) {
        direction = 1;
      } else if (up && left) {
        direction = 7;
      } else if (down && left) {
        direction = 5;
      } else if (down && right) {
        direction = 3;
      } else if (up) {
        direction = 0;
      } else if (down) {
        direction = 4;
      } else if (left) {
        direction = 6;
      } else if (right) {
        direction = 2;
      }
    }
  }
}