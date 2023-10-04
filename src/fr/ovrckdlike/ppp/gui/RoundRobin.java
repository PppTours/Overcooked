package fr.ovrckdlike.ppp.gui;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.physics.Rectangle;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that allows to select an element in a list of SelectCard.
 */
public class RoundRobin {
  /**
   * The space where the RoundRobin will be rendered.
   */
  private Rectangle space;

  /**
   * The list of SelectCard.
   */
  private List<SelectCard> elements;

  /**
   * The index of the selected SelectCard.
   */
  private int selected;

  /**
   * A boolean that indicates if the selection can be moved.
   */
  private boolean moveReady;

  /**
   * A boolean that indicates if the selection is locked.
   */
  private boolean lock;

  /**
   * Constructor of RoundRobin.
   *
   * @param space The rectangle where the RoundRobin will be rendered.
   */
  public RoundRobin(Rectangle space) {
    this.space = space;
    elements = new ArrayList<SelectCard>();
    selected = -1;
    moveReady = true;
    lock = false;
  }

  /**
   * Add a SelectCard to the RoundRobin.
   *
   * @param sc The SelectCard to add.
   */
  public void addSelectCard(SelectCard sc) {
    sc.setPos(space.getPos());
    if (selected == -1) {
      selected++;
    }
    elements.add(sc);
  }

  /**
   * Remove a SelectCard from the RoundRobin.
   *
   * @return The removed SelectCard.
   */
  public SelectCard getSelectedCard() {
    if (selected == -1) {
      return null;
    } else {
      return elements.get(selected);
    }
  }

  /**
   * Move the selection to the right.
   */
  public void moveSelectionRight() {
    if (!moveReady || lock) {
      return;
    }
    if (selected == -1) {
      return;
    }
    selected++;
    if (selected == elements.size()) {
      selected = 0;
    }
    moveReady = false;
  }

  /**
   * Move the selection to the left.
   */
  public void moveSelectionLeft() {
    if (!moveReady || lock) {
      return;
    }
    if (selected == -1) {
      return;
    }
    if (selected == 0) {
      selected = elements.size();
    }
    selected--;
    moveReady = false;
  }

  /**
   * Lock the selection.
   *
   * @param locked The boolean that indicates if the selection is locked.
   */
  public void setLocked(boolean locked) {
    lock = locked;
  }

  /**
   * Reset the move.
   */
  public void resetMove() {
    moveReady = true;
  }

  /**
   * Render the RoundRobin.
   */
  public void render() {
    if (selected != -1) {
      elements.get(selected).render();
    }
    Rectangle renderSpace1 = new Rectangle(
        space.getX() - space.getWidth() / 8 * 5, space.getY(),
        space.getWidth() / 4, space.getHeight() / 2);
    Rectangle renderSpace2 = new Rectangle(
        space.getX() + space.getWidth() / 8 * 5, space.getY(),
        space.getWidth() / 4, space.getHeight() / 2);
    Renderer.drawTexture(renderSpace1, Texture.arrowLeft);
    Renderer.drawTexture(renderSpace2, Texture.arrowRight);
  }
}
