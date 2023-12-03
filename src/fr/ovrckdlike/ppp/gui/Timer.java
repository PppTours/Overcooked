package fr.ovrckdlike.ppp.gui;

import fr.ovrckdlike.ppp.graphics.Color;
import fr.ovrckdlike.ppp.physics.Dot;
import fr.ovrckdlike.ppp.physics.Rectangle;

public class Timer extends Text {

  private static Timer timer;
  int maxTime;
  private float currentTime;

  private boolean isEnded;
  private boolean isStarted;

  public Timer(String text, Dot renderPos,
               Color textColor, float textSize, float angle) {
    super(text, renderPos, textColor, textSize, angle);
  }

  public Timer(String text, Rectangle space, Color textColor) {
    super(text, space, textColor);
    isEnded = false;
  }

  public static Timer getTimer() {
    if (timer == null) {
      timer = new Timer("0", new Dot(0, 0), Color.white, 1, 0);
    }
    return timer;
  }

  public void setMaxTime(int maxTime) {
    this.maxTime = maxTime;

  }

  public void setCurrentTime(int currentTime) {
    setText(String.valueOf(currentTime));
  }

  public boolean isEnded() {
    return isEnded;
  }

  public void startTimer() {
    if (isStarted) {
      return;
    }
    setCurrentTime(0);
    isStarted = true;
    float sdt = this.maxTime / 1E9f;
    while (currentTime < maxTime) {
      currentTime += sdt;
      setCurrentTime((int) currentTime);
    }
    isEnded = true;
  }

  public void render() {
    super.render();
  }
}
