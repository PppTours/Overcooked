package fr.ovrckdlike.ppp.gui;

import fr.ovrckdlike.ppp.physics.Dot;
import fr.ovrckdlike.ppp.physics.Rectangle;

/**
 * An abstract class to represent the card selection mechanism.
 */
public abstract class SelectCard {
  protected Rectangle space;

  public SelectCard(Rectangle pos) {
    this.space = pos;
  }

  public void setPos(Dot newPos) {
    space.setPos(newPos);
  }

  public abstract Object getChoice();

  public abstract void render();
}
