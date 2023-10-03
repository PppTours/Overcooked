package fr.ovrckdlike.ppp.gui;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.physics.Dot;
import fr.ovrckdlike.ppp.physics.Rectangle;

/**
 * A class to show the different map in the choice menu.
 */
public class SkinCard extends SelectCard {
  private Texture skin;

  public SkinCard(Dot pos, Texture skin) {
    super(new Rectangle(pos, 300f, 400f, 0f));
    this.skin = skin;
  }

  public SkinCard(SkinCard sc) {
    super(new Rectangle(sc.space));
    skin = sc.skin;
  }

  @Override
  public Object getChoice() {
    return skin;
  }

  @Override
  public void render() {
    Renderer.drawTexture(new Rectangle(space.getPos(), 250f, 250f, 0f), skin);
  }

}
