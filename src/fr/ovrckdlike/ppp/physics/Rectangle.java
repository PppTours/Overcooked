package fr.ovrckdlike.ppp.physics;

import fr.ovrckdlike.ppp.graphics.Color;
import fr.ovrckdlike.ppp.graphics.Renderer;
import java.util.ArrayList;
import java.util.List;

/**
 * A class representing a rectangle, it's used for the physics engine.
 */
public class Rectangle {
  /**
   * the x value of the center of the rectangle.
   */
  private float centerX;      //center
  /**
   * the y value of the center of the rectangle.
   */
  private float centerY;
  /**
   * the width of the rectangle.
   */
  private float width;    //edges

  /**
   * the height of the rectangle.
   */
  private float height;

  /**
   * the angle of the rectangle.
   */
  private float angle;


  /**
   * Create a rectangle with the given parameters.
   *
   * @param x the x value of the center of the rectangle.
   * @param y the y value of the center of the rectangle.
   * @param width the width of the rectangle.
   * @param height the height of the rectangle.
   */
  public Rectangle(float x, float y, float width, float height) {
    this(x, y, width, height, 0f);
  }

  /**
   * A copy constructor.
   *
   * @param r the rectangle to copy.
   */
  public Rectangle(Rectangle r) {
    centerX = r.centerX;
    centerY = r.centerY;
    width = r.width;
    height = r.height;
    angle = r.angle;
  }

  /**
   * Create a rectangle with the given parameters and an angle.
   *
   * @param midX the x value of the center of the rectangle.
   * @param midY the y value of the center of the rectangle.
   * @param width the width of the rectangle.
   * @param height the height of the rectangle.
   * @param angle the angle of the rectangle.
   */
  public Rectangle(float midX, float midY, float width, float height, float angle) {
    centerX = midX;
    centerY = midY;
    this.width = width;
    this.height = height;
    this.angle = angle;
  }

  /**
   * Create a rectangle with the given parameters and an angle.
   *
   * @param centre The position of the center of the rectangle.
   * @param width The width of the rectangle.
   * @param height The height of the rectangle.
   * @param angle The angle of the rectangle.
   */
  public Rectangle(Dot centre, float width, float height, float angle) {
    centerX = centre.getX();
    centerY = centre.getY();
    this.width = width;
    this.height = height;
    this.angle = angle;
  }

  /**
   * Create a new Rectangle from the corner.
   *
   * @param cornerX the x value of the corner.
   * @param cornerY the y value of the corner.
   * @param width the width of the rectangle.
   * @param height the height of the rectangle.
   * @param angle the angle of the rectangle.
   * @return the new rectangle.
   */
  public static Rectangle fromCorner(
      float cornerX, float cornerY, float width, float height, float angle) {
    float rectX = (float) (cornerX + (width / 2)
        * Math.cos(angle) + (height / 2) * Math.sin(angle));
    float rectY = (float) (cornerY + (height / 2)
        * Math.cos(angle) - (height / 2) * Math.sin(angle));
    return new Rectangle(rectX, rectY, width, height, angle);
  }

  /**
   * Get the corners of the rectangle.
   *
   * @return A list of the corners of the rectangle.
   */
  public List<Dot> getCorners() {
    float c1x = (float) (centerX - (width / 2) * Math.cos(angle) + (height / 2) * Math.sin(angle));
    float c1y = (float) (centerY - (height / 2) * Math.cos(angle) - (width / 2) * Math.sin(angle));

    float c2x = (float) (centerX - (width / 2) * Math.cos(angle) - (height / 2) * Math.sin(angle));
    float c2y = (float) (centerY + (height / 2) * Math.cos(angle) - (width / 2) * Math.sin(angle));

    float c3x = (float) (centerX + (width / 2) * Math.cos(angle) + (height / 2) * Math.sin(angle));
    float c3y = (float) (centerY - (height / 2) * Math.cos(angle) + (width / 2) * Math.sin(angle));

    float c4x = (float) (centerX + (width / 2) * Math.cos(angle) - (height / 2) * Math.sin(angle));
    float c4y = (float) (centerY + (height / 2) * Math.cos(angle) + (width / 2) * Math.sin(angle));

    ArrayList<Dot> res = new ArrayList<>();
    res.add(new Dot(c1x, c1y));
    res.add(new Dot(c2x, c2y));
    res.add(new Dot(c3x, c3y));
    res.add(new Dot(c4x, c4y));
    return res;
  }

  /**
   * Set the position of the rectangle.
   *
   * @param d the new position of the center of the rectangle.
   */
  public void setPos(Dot d) {
    centerX = d.getX();
    centerY = d.getY();
  }

  /**
   * Get the angle of the rectangle.
   *
   * @return the angle of the rectangle.
   */
  public float getAngle() {
    return angle;
  }

  /**
   * Set the angle of the rectangle.
   *
   * @param newAngle the new angle of the rectangle.
   */
  public void setAngle(float newAngle) {
    angle = newAngle;
  }

  /**
   * Get the x value of the center of the rectangle.
   *
   * @return the x value of the center of the rectangle.
   */
  public float getX() {
    return centerX;
  }

  /**
   * Get the y value of the center of the rectangle.
   *
   * @return the y value of the center of the rectangle.
   */
  public float getY() {
    return centerY;
  }

  /**
   * Get the position of the center of the rectangle.
   *
   * @return the position of the center of the rectangle.
   */
  public Dot getPos() {
    return new Dot(centerX, centerY);
  }

  /**
   * Get the size (array of width and height) of the rectangle.
   *
   * @return the size of the rectangle.
   */
  public float[] getSize() {
    float[] size = {width, height};
    return size;
  }

  /**
   * Get the width of the rectangle.
   *
   * @return the width of the rectangle.
   */
  public float getWidth() {
    return width;
  }

  /**
   * Get the height of the rectangle.
   *
   * @return the height of the rectangle.
   */
  public float getHeight() {
    return height;
  }

  /**
   * Resize the rectangle with a float that will be added to the width and the height.
   *
   * @param addToDimensions the float to add to the width and the height.
   * @return the new rectangle.
   */
  public Rectangle resized(float addToDimensions) {
    return new Rectangle(centerX, centerY, width + addToDimensions, height + addToDimensions);
  }

  /**
   * Resize the rectangle with new value.
   *
   * @param newWidth the new width
   * @param newHeight the new height
   */
  public void resize(float newWidth, float newHeight) {
    width = newWidth;
    height = newHeight;
  }

  /**
   * Return the nearest dot from the pos included in this rectangle.
   *
   * @param pos the position to check.
   * @return the nearest dot from the pos included in this rectangle.
   */
  public Dot nearestFromPos(Dot pos) {
    Dot unRotPos = pos.rotateAround(getPos(), -angle);

    int signX = (unRotPos.getX() > centerX) ? 1 : -1;
    int signY = (unRotPos.getY() > centerY) ? 1 : -1;

    float resX = centerX + Math.min(Math.abs(centerX - unRotPos.getX()), width / 2) * signX;
    float resY = centerY + Math.min(Math.abs(centerY - unRotPos.getY()), height / 2) * signY;
    return new Dot(resX, resY).rotateAround(getPos(), angle);
  }

  /**
   * Return true if the rectangle includes the dot pos.
   *
   * @param pos the dot to check.
   * @return true if the rectangle includes the dot pos.
   */
  public boolean includes(Dot pos) {
    Dot rotPos = pos.rotateAround(getPos(), -angle);
    return centerX - width / 2 <= rotPos.getX() && rotPos.getX() <= centerX + width / 2
        && centerY - height / 2 <= rotPos.getY() && rotPos.getY() <= centerY + height / 2;
  }

  /**
   * Return the max sized rectangle contained in the current one with angle and w/h ratio specified.
   *
   * @param angle the angle of the new rectangle.
   * @param ratio the w/h ratio of the new rectangle.
   * @return the max sized rectangle contained in the current one with angle and w/h ratio
   */
  public Rectangle surrounded(float angle, float ratio) {
    float deltaAngle = this.angle - angle;
    double maxSizeWithX = width
        / (Math.abs(ratio * Math.cos(deltaAngle)) + Math.abs(Math.sin(deltaAngle)));
    double maxSizeWithY = height
        / (Math.abs(Math.cos(deltaAngle)) + Math.abs(ratio * Math.sin(deltaAngle)));
    float size = (float) (Math.min(maxSizeWithX, maxSizeWithY));
    return new Rectangle(centerX, centerY, size * ratio, size, angle);
  }

  /**
   * Return a horizontal rectangle containing the current rectangle.
   *
   * @return a horizontal rectangle containing the current rectangle.
   */
  public Rectangle surround() {
    double flatX = Math.abs(width * Math.cos(angle)) + Math.abs(height * Math.sin(angle));
    double flatY = Math.abs(height * Math.cos(+angle)) + Math.abs(width * Math.sin(angle));
    return new Rectangle(centerX, centerY, (float) (flatX), (float) (flatY), 0);
  }

  /**
   * Set the angle of the rectangle to the given angle.
   *
   * @param angle the new angle of the rectangle.
   */
  public void changeAngle(float angle) {
    this.angle = angle;
  }

  public Rectangle move(Dot a) {
    return new Rectangle(centerX + a.getX(), centerY + a.getY(), width, height);
  }

  /**
   * Render the rectangle.
   */
  public void render() {
    Renderer.drawQuad(this, Color.transparentWhite);
  }
}
