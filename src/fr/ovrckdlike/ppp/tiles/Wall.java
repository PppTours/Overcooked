package fr.ovrckdlike.ppp.tiles;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.objects.Player;
import fr.ovrckdlike.ppp.physics.Dot;
import fr.ovrckdlike.ppp.physics.Rectangle;

/**
 * A class to represent a wall.
 */
public class Wall extends Tile {
  public Wall(Dot wallPos) {
    space = new Rectangle(wallPos, size, size, 0f);
    type = 10;
  }

  public void use(Player player) {}

  public void render() {
    Renderer.drawTexture(space, Texture.wall);
  }

}
