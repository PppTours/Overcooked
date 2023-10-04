package fr.ovrckdlike.ppp.tiles;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.map.Map;
import fr.ovrckdlike.ppp.objects.Extinguisher;
import fr.ovrckdlike.ppp.objects.Item;
import fr.ovrckdlike.ppp.objects.Plate;
import fr.ovrckdlike.ppp.objects.Player;
import fr.ovrckdlike.ppp.physics.Dot;
import fr.ovrckdlike.ppp.physics.Rectangle;

/**
 * A class representing a table in the game.
 */
public class Table extends Tile implements ContainerTile {
  /**
   * The content of the table.
   *
   * @see Item
   */
  private Item content;

  /**
   * The constructor of the table.
   *
   * @param pos  The position of the table.
   * @param onTop The content of the table.
   */
  public Table(Dot pos, String onTop) {
    this.space = new Rectangle(pos, size, size, 0f);
    if (onTop.equals("PLA")) {
      content = new Plate(new Dot(pos), false);
      Map.get().getItemList().add(content);
    } else if (onTop.equals("EXT")) {
      content = new Extinguisher(new Dot(pos));
      Map.get().getItemList().add(content);
    } else {
      content = null;
    }
    this.type = 1;
  }

  /**
   * A Getter for the content of the table.
   *
   * @return The content of the table.
   */
  @Override
  public Item getContent() {
    return content;
  }

  /**
   * Manage the action to take or drop something on the table.
   *
   * @param newContent The new content of the table.
   * @return The old content of the table.
   */
  @Override
  public Item takeOrDrop(Item newContent) {
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
   * Manage the action to use the content of the table.
   *
   * @param player The player who use the content of the table.
   */
  public void use(Player player) {}

  /**
   * Render the table.
   */
  public void render() {
    Renderer.drawTexture(space, Texture.table);
    if (content != null) {
      content.render();
    }
  }

}
