package fr.ovrckdlike.ppp.physics;

import fr.ovrckdlike.ppp.graphics.Color;
import fr.ovrckdlike.ppp.graphics.Renderer;
import java.util.ArrayList;
import java.util.List;

public class Rectangle {
  private float centerX;      //center
  private float centerY;
  private float width;    //edges
  private float height;
  private float angle;

  //x, y are the center of the rectangle
  public Rectangle(float x, float y, float width, float height) {
    this(x, y, width, height, 0f);
  }

  public Rectangle(Rectangle r) {
    centerX = r.centerX;
    centerY = r.centerY;
    width = r.width;
    height = r.height;
    angle = r.angle;
  }

  //x, y are the center of the rectangle
  public Rectangle(float midX, float midY, float width, float height, float angle) {
    centerX = midX;
    centerY = midY;
    this.width = width;
    this.height = height;
    this.angle = angle;
  }

  public Rectangle(Dot centre, float width, float height, float angle) {
    centerX = centre.getX();
    centerY = centre.getY();
    this.width = width;
    this.height = height;
    this.angle = angle;
  }

  public static Rectangle fromCorner(
      float cornerX, float cornerY, float width, float height, float angle) {
    float rectX = (float) (cornerX + (width / 2)
        * Math.cos(angle) + (height / 2) * Math.sin(angle));
    float rectY = (float) (cornerY + (height / 2)
        * Math.cos(angle) - (height / 2) * Math.sin(angle));
    return new Rectangle(rectX, rectY, width, height, angle);
  }

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

  public void setPos(Dot d) {
    centerX = d.getX();
    centerY = d.getY();
  }

  public float getAngle() {
    return angle;
  }

  public void setAngle(float newAngle) {
    angle = newAngle;
  }

  public float getX() {
    return centerX;
  }

  public float getY() {
    return centerY;
  }

  public Dot getPos() {
    return new Dot(centerX, centerY);
  }

  public float[] getSize() {
    float[] size = {width, height};
    return size;
  }

  public float getWidth() {
    return width;
  }

  public float getHeight() {
    return height;
  }

  public Rectangle resized(float addToDimensions) {
    return new Rectangle(centerX, centerY, width + addToDimensions, height + addToDimensions);
  }

  public void resize(float newWidth, float newHeight) {
    width = newWidth;
    height = newHeight;
  }

  // return the nearest dot from the pos included in this rectangle
  public Dot nearestFromPos(Dot pos) {
    Dot unRotPos = pos.rotateAround(getPos(), -angle);

    int signX = (unRotPos.getX() > centerX) ? 1 : -1;
    int signY = (unRotPos.getY() > centerY) ? 1 : -1;

    float resX = centerX + Math.min(Math.abs(centerX - unRotPos.getX()), width / 2) * signX;
    float resY = centerY + Math.min(Math.abs(centerY - unRotPos.getY()), height / 2) * signY;
    return new Dot(resX, resY).rotateAround(getPos(), angle);
  }

  public boolean includes(Dot pos) {
    Dot rotPos = pos.rotateAround(getPos(), -angle);
    return centerX - width / 2 <= rotPos.getX() && rotPos.getX() <= centerX + width / 2
        && centerY - height / 2 <= rotPos.getY() && rotPos.getY() <= centerY + height / 2;
  }

  //return the max sized rectangle contained in the current one with angle and w/h ratio specified
  public Rectangle surrounded(float angle, float ratio) {
    float deltaAngle = this.angle - angle;
    double maxSizeWithX = width
        / (Math.abs(ratio * Math.cos(deltaAngle)) + Math.abs(Math.sin(deltaAngle)));
    double maxSizeWithY = height
        / (Math.abs(Math.cos(deltaAngle)) + Math.abs(ratio * Math.sin(deltaAngle)));
    float size = (float) (Math.min(maxSizeWithX, maxSizeWithY));
    return new Rectangle(centerX, centerY, size * ratio, size, angle);
  }

  //return a horizontal rectangle containing the current rectangle
  public Rectangle surround() {
    double flatX = Math.abs(width * Math.cos(angle)) + Math.abs(height * Math.sin(angle));
    double flatY = Math.abs(height * Math.cos(+angle)) + Math.abs(width * Math.sin(angle));
    return new Rectangle(centerX, centerY, (float) (flatX), (float) (flatY), 0);
  }

  public void changeAngle(float angle) {
    this.angle = angle;
  }

  public void render() {
    Renderer.drawQuad(this, Color.transparentWhite);
  }
}
