package fr.ovrckdlike.ppp.objects;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.physics.Dot;

/**
 * A class to represent an extinguisher.
 */
public class Extinguisher extends Item {

  public Extinguisher(Dot pos) {
    super(pos);
    setMode(1);
  }

  @Override
  public void render() {
    float zoom = space.getRay() * (mode + 1);
    Renderer.drawTexture(space.resized(zoom).surroundBySquare(0), Texture.extinguisher);
  }

  @Override
  public void prepare() {}

  @Override
  public void flush() {}

}
