package fr.ovrckdlike.ppp.gui;

import fr.ovrckdlike.ppp.graphics.Color;
import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.physics.Dot;
import fr.ovrckdlike.ppp.physics.Rectangle;
import fr.ovrckdlike.ppp.physics.Time;

/**
 * This class manage the Text rendering.
 */
public class Text {
  /**
   * The text to render.
   */
  private String text;

  /**
   * The scape where the text is rendered.
   */
  private final Rectangle space;

  /**
   * The color of the text.
   */
  private final Color color;

  /**
   * The angle of the text.
   */
  private float angle;

  /**
   * Height of character in nb of pixels.
   */
  private float size;

  /**
   * The constructor of the text.
   *
   * @param text the text to render.
   * @param renderPos the position of the text.
   * @param textColor the color of the text.
   * @param textSize the size of the text.
   * @param angle the angle of the text.
   */
  public Text(String text, Dot renderPos, Color textColor, float textSize, float angle) {
    this.text = text;
    space = new Rectangle(renderPos, text.length() * textSize, textSize, angle);
    color = textColor;
    size = textSize;
    this.angle = angle;
  }

  /**
   * A constructor of the text.
   *
   * @param text the text to render.
   * @param space the space where the text is rendered.
   * @param textColor the color of the text.
   */
  public Text(String text, Rectangle space, Color textColor) {
    this.text = text;
    this.space = new Rectangle(space);
    color = textColor;
    this.size = space.getHeight();
  }

  /**
   * Change the size of the text.
   *
   * @param newSize the new size of the text.
   */
  public void changeSize(float newSize) {
    size = newSize;
    space.resize(text.length() * size, size);
  }

  public void setText(String text) {
    this.text = text;
  }

  /**
   * Render the text.
   */
  public void render() {
    Dot pos = space.getPos();
    float width = space.getWidth();
    float height = space.getHeight();
    for (int i = 0; i < text.length(); i++) {
      float renderPosX = (float)
          (pos.getX() - (width / 2 - height * i - size / 2) * Math.cos(angle));
      float renderPosY = (float)
          (pos.getY() - (width / 2 - height * i - size / 2) * Math.sin(angle));
      char c = text.charAt(i);
      Renderer.drawLetter(c, renderPosX, renderPosY,
          (int) (size / 7), color, Texture.font, - angle);
    }
  }
}
