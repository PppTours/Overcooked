package fr.ovrckdlike.ppp.physics;

import fr.ovrckdlike.ppp.graphics.Color;
import fr.ovrckdlike.ppp.graphics.Renderer;

public class Dot {
	private float x;
	private float y;
	
	public Dot(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Dot(Dot pos) {
		x = pos.x;
		y = pos.y;
	}
	
	public float distanceTo(Dot d) {
		return (float) (Math.sqrt((x-d.x) * (x-d.x) + (y-d.y) * (y-d.y)));
	}
	
	public float angleTo(Dot d) {
		if (equals(d)) {
			return 0f;
		}
		float r = distanceTo(d);
		float dX = d.x - x;
		float dY = d.y - y;
		return (Math.asin(dY/r) < 0)? (float)(Math.acos(dX/r)) : -(float)(Math.acos(dX/r));
	}
	
	public boolean isEqual(Dot d) {
		return (d.x == x && d.y == y);
	}
	
	public void setX(float newX) {
		x = newX;
	}
	
	public void setY(float newY) {
		y = newY;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public void addToThis(float xAdd, float yAdd) {
		x += xAdd;
		y += yAdd;
	}
	
	public Dot add(float a, float b) {
		return new Dot(x+a, y+b);
	}
	
	public Dot add(Dot d) {
		return new Dot(x+d.x, y+d.y);
	}
	
	public Dot rotateAround(Dot mid, float angle) {
		float deltaX = x - mid.x;
		float deltaY = y - mid.y;
		
		return new Dot((float)(deltaX*Math.cos(angle) + deltaY*Math.sin(angle))+mid.getX(),
						(float)(-deltaX*Math.sin(angle) + deltaY*Math.cos(angle))+mid.getY());
	}
	
	public Dot rotate(float angle) {
		return new Dot((float)(x*Math.cos(angle) + -y*Math.sin(angle)),
						(float)(x*Math.sin(angle) + y*Math.cos(angle)));
	}
	
	public Dot multiply(float k) {
		return new Dot(x*k, y*k);
	}
	
	@Override
	public boolean equals(Object d) {
		if (!(d instanceof Dot)) return false;
		else return (((Dot) d).getX() == x && ((Dot) d).getY() == y);
	}
	
	public void render() {
		Renderer.drawQuad(new Rectangle(this, 3, 3, 0), Color.red);
	}
	
	@Override
	public String toString() {
		return "("+x+", "+y+")";
	}

}
