package fr.ovrckdlike.ppp.physics;

import fr.ovrckdlike.ppp.graphics.Color;
import fr.ovrckdlike.ppp.graphics.Renderer;

/**
 * A class that represents a dot to manage collision and object position.
 */
public class Dot {
  private float posX;
  private float posY;

  /**
   * A constructor for the dot.
   *
   * @param x x coordinates.
   * @param y y coordinates.
   */
  public Dot(float x, float y) {
    this.posX = x;
    this.posY = y;
  }

  /**
   * A copy constructor.
   *
   * @param pos the dots to copy.
   */
  public Dot(Dot pos) {
    posX = pos.posX;
    posY = pos.posY;
  }

  /**
   * Calculate the distance between two dots.
   *
   * @param d the second dots.
   * @return the distance between the two dots.
   */
  public float distanceTo(Dot d) {
    return (float)
        (Math.sqrt((posX - d.posX) * (posX - d.posX) + (posY - d.posY) * (posY - d.posY)));
  }

  /**
   * Calculate the angle between the two dots.
   *
   * @param d the second dots.
   * @return the angle.
   */
  public float angleTo(Dot d) {
    if (equals(d)) {
      return 0f;
    }
    float r = distanceTo(d);
    float distanceX = d.posX - posX;
    float distanceY = d.posY - posY;
    return (Math.asin(distanceY / r) < 0)
        ? (float) (Math.acos(distanceX / r)) : - (float) (Math.acos(distanceX / r));
  }

  /**
   * Check if the dots are at the same place.
   *
   * @param d the second dots.
   * @return true if dots are at the same place, false otherwise.
   */
  public boolean isEqual(Dot d) {
    return (d.posX == posX && d.posY == posY);
  }

  /**
   * A getter for the x value.
   *
   * @return the x value.
   */
  public float getX() {
    return posX;
  }

  /**
   * A setter for the x value.
   *
   * @param newX the new x value.
   */
  public void setX(float newX) {
    posX = newX;
  }

  /**
   * A getter for the y value.
   *
   * @return the y value.
   */
  public float getY() {
    return posY;
  }

  /**
   * A setter for the y value.
   *
   * @param newY the new y value.
   */
  public void setY(float newY) {
    posY = newY;
  }

  /**
   * Move the dot with x and y value that will be added.
   *
   * @param addToX value to add to x.
   * @param addToY value to add to y.
   */
  public void addToThis(float addToX, float addToY) {
    posX += addToX;
    posY += addToY;
  }


  /**
   * Create a new dot based on the first one but with value added.
   *
   * @param a x value to add.
   * @param b y value to add.
   * @return a new dot.
   */
  public Dot add(float a, float b) {
    return new Dot(posX + a, posY + b);
  }

  /**
   * Create a new dot based on the first one but with value added.
   *
   * @param d A dot to get and add new value.
   * @return a new dot.
   */
  public Dot add(Dot d) {
    return new Dot(posX + d.posX, posY + d.posY);
  }

  /**
   * Rotate the point with angle and a point that will be the mid.
   *
   * @param mid The center of the rotation.
   * @param angle The angle of the rotation.
   * @return A new dot after the rotation.
   */
  public Dot rotateAround(Dot mid, float angle) {
    float deltaX = posX - mid.posX;
    float deltaY = posY - mid.posY;
    
    return new Dot((float) (deltaX * Math.cos(angle) + deltaY * Math.sin(angle)) + mid.getX(),
            (float) (- deltaX * Math.sin(angle) + deltaY * Math.cos(angle)) + mid.getY());
  }
  
  public Dot rotate(float angle) {
    return new Dot((float) (posX * Math.cos(angle) + -posY * Math.sin(angle)),
            (float) (posX * Math.sin(angle) + posY * Math.cos(angle)));
  }
  
  public Dot multiply(float k) {
    return new Dot(posX * k, posY * k);
  }
  
  @Override
  public boolean equals(Object d) {
    if (!(d instanceof Dot)) {
      return false;
    } else {
      return (((Dot) d).getX() == posX && ((Dot) d).getY() == posY);
    }
  }
  
  public void render() {
    Renderer.drawQuad(new Rectangle(this, 3, 3, 0), Color.red);
  }
  
  @Override
  public String toString() {
    return "(" + posX + ", " + posY + ")";
  }

}
