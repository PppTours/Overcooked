package fr.ovrckdlike.ppp.gui;

import fr.ovrckdlike.ppp.graphics.Color;
import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.physics.Dot;
import fr.ovrckdlike.ppp.physics.Rectangle;

/**
 * A Button on the interface.
 */
public class Button {
  /**
   * The text of the button.
   */
  private final Text text;

  /**
   * The color of the button.
   */
  protected Color color;

  /**
   * The position of the button.
   */
  protected Rectangle pos;

  /**
   * If the button is selected.
   */
  private boolean selected;

  /**
   * A constructor for the button.
   *
   * @param pos The position of the button.
   * @param text The text of the button.
   * @param color The color of the button.
   */
  public Button(Rectangle pos, String text, Color color) {
    this.color = color;
    this.pos = pos;
    Dot textPos = pos.getPos();
    this.text = new Text(text, textPos, Color.black, 30f, 0);
    this.selected = false;
  }

  /**
   * A setter to change the selection of the button.
   *
   * @param selec A boolean to set the selection.
   */
  public void setSelected(boolean selec) {
    this.selected = selec;
  }

  /**
   * A method that allow to render the button.
   */
  public void render() {
    if (selected) {
      Renderer.drawQuad(pos, Color.black);
    }
    Renderer.drawQuad(pos, this.color);
    text.render();
    if (selected) {
      Renderer.drawQuad(pos, Color.transparentWhite);
    }
  }
}
