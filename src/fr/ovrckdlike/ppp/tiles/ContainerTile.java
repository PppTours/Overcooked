package fr.ovrckdlike.ppp.tiles;

import fr.ovrckdlike.ppp.objects.Item;

public interface ContainerTile {

  //public abstract CookerContainer takeOrDrop(CookerContainer newContent);
  public Item takeOrDrop(Item newContent);

  public Item getContent();
}
