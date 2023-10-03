package fr.ovrckdlike.ppp.tiles;

import fr.ovrckdlike.ppp.objects.Item;

/**
 * An interface to represent a container tile.
 */
public interface ContainerTile {

  //public abstract CookerContainer takeOrDrop(CookerContainer newContent);
  public Item takeOrDrop(Item newContent);

  public Item getContent();
}
