package fr.ovrckdlike.ppp.objects;

import fr.ovrckdlike.ppp.physics.Circle;
import fr.ovrckdlike.ppp.tiles.Tile;
import java.util.List;

/**
 * An abstract class to represent an entity.
 */
public abstract class Entity {
  protected Circle space;

  public abstract <T extends Entity> void collideEntity(List<T> objList);

  public abstract void collideTile(List<Tile> tileList);

}
