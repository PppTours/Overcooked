package fr.ovrckdlike.ppp.gui;

import fr.ovrckdlike.ppp.graphics.Color;
import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.physics.Dot;
import fr.ovrckdlike.ppp.physics.Rectangle;

/**
 * A class that allows to display a time bar.
 */
public class TimeBar {
  /**
   * The space where the TimeBar will be rendered.
   */
  private final Rectangle space;

  /**
   * The maximum time of the TimeBar.
   */
  private final float maxTime;

  /**
   * The current time of the TimeBar.
   */
  private float currentTime;

  /**
   * Constructor of TimeBar.
   *
   * @param pos The position of the TimeBar.
   * @param maxTime The maximum time of the TimeBar.
   */
  public TimeBar(Dot pos, float maxTime) {
    space = new Rectangle(pos, 50f, 10f, 0f);
    this.maxTime = maxTime;
    this.currentTime = 0f;
  }

  /**
   * Constructor of TimeBar.
   *
   * @param rect The rectangle where the TimeBar will be rendered.
   * @param maxTime The maximum time of the TimeBar.
   */
  public TimeBar(Rectangle rect, float maxTime) {
    this.space = rect;
    this.currentTime = maxTime;
    this.maxTime = maxTime;
  }

  /**
   * Calculate the time bar gauge filling.
   *
   * @param currentTime The current time of the TimeBar.
   */
  private void calculateTimeBarGauge(float currentTime) {
    if (currentTime < 2 * maxTime && currentTime > 0) {
      this.currentTime = currentTime;
      Renderer.drawQuad(space.resized(4), Color.white);
      if (currentTime < maxTime) {
        float fillingSize = this.currentTime / maxTime * space.getWidth();
        Renderer.drawQuad(Rectangle.fromCorner(space.getX() - space.getWidth() / 2,
            space.getY() - space.getHeight() / 2,
            fillingSize, space.getHeight(), 0f), Color.darkGreen);
      } else if (currentTime < 1.3 * maxTime) {
        Renderer.drawQuad(Rectangle.fromCorner(space.getX() - space.getWidth() / 2,
            space.getY() - space.getHeight() / 2, space.getWidth(), space.getHeight(), 0f),
            Color.darkGreen);
      } else {
        Renderer.drawQuad(Rectangle.fromCorner(space.getX() - space.getWidth() / 2,
            space.getY() - space.getHeight() / 2,
            space.getWidth(), space.getHeight(), 0f), Color.red);
      }
    }
  }

  /**
   * Render the TimeBar.
   *
   * @param currentTime The current time of the TimeBar.
   * @param pos The position of the TimeBar.
   */
  public void render(float currentTime, Dot pos) {
    space.setPos(pos);

    calculateTimeBarGauge(currentTime);
  }

  /**
   * Render the TimeBar.
   *
   * @param currentTime The current time of the TimeBar.
   */
  public void render(float currentTime) {
    calculateTimeBarGauge(currentTime);
  }

}
