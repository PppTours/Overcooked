package fr.ovrckdlike.ppp.tiles;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.map.Map;
import fr.ovrckdlike.ppp.objects.Item;
import fr.ovrckdlike.ppp.objects.Plate;
import fr.ovrckdlike.ppp.objects.Player;
import fr.ovrckdlike.ppp.physics.Dot;
import fr.ovrckdlike.ppp.physics.Rectangle;

public class PlateReturn extends Tile implements ContainerTile {
  int plateNb;


  public PlateReturn(Dot pos) {
    this.space = new Rectangle(pos, size, size, 0f);
    this.plateNb = 0;
    this.type = 8;
  }


  public void addPlate() {
    plateNb++;
  }


  @Override
  public void use(Player player) {}

  @Override
  public Item takeOrDrop(Item content) {
    if (content != null) {
      return content;
    }
    if (plateNb > 0) {
      plateNb--;
      Dot platePos = new Dot(space.getPos());
      Plate newPlate = new Plate(platePos, true, 0);
      Map.get().getItemList().add(newPlate);
      return newPlate;
    } else {
      return null;
    }
  }


  public void render() {
    Renderer.drawTexture(space, Texture.plateReturn);
    if (plateNb > 0) {
      Dot pos = space.getPos();
      Rectangle plateSpace = new Rectangle(new Dot(pos), size, size, 0f);
      Renderer.drawTexture(plateSpace, Texture.dirtyPlate);
    }
  }


  @Override
  public Item getContent() {
    return null;
  }
}
