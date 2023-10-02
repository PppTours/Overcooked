package fr.ovrckdlike.ppp.physics;

import fr.ovrckdlike.ppp.graphics.Color;
import fr.ovrckdlike.ppp.graphics.Renderer;

public class Dot {
  private float posX;
  private float posY;
  
  public Dot(float x, float y) {
    this.posX = x;
    this.posY = y;
  }
  
  public Dot(Dot pos) {
    posX = pos.posX;
    posY = pos.posY;
  }
  
  public float distanceTo(Dot d) {
    return (float)
        (Math.sqrt((posX - d.posX) * (posX - d.posX) + (posY - d.posY) * (posY - d.posY)));
  }
  
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
  
  public boolean isEqual(Dot d) {
    return (d.posX == posX && d.posY == posY);
  }
  
  public void setX(float newX) {
    posX = newX;
  }
  
  public void setY(float newY) {
    posY = newY;
  }
  
  public float getX() {
    return posX;
  }
  
  public float getY() {
    return posY;
  }
  
  public void addToThis(float addToX, float addToY) {
    posX += addToX;
    posY += addToY;
  }
  
  public Dot add(float a, float b) {
    return new Dot(posX + a, posY + b);
  }
  
  public Dot add(Dot d) {
    return new Dot(posX + d.posX, posY + d.posY);
  }
  
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
