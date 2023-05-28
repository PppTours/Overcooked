package fr.ovrckdlike.ppp.physics;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;

import fr.ovrckdlike.ppp.graphics.Color;
import fr.ovrckdlike.ppp.graphics.KeyListener;
import fr.ovrckdlike.ppp.graphics.Renderer;

public class Rectangle {
	private float x;			//center
	private float y;
	private float width;		//edges
	private float height;
	private float angle;
	
	//x, y are the center of the rectangle
	public Rectangle(float x, float y, float width, float height) {
		this(x, y, width, height, 0f);
	}
	
	public Rectangle(Rectangle r) {
		x = r.x;
		y = r.y;
		width = r.width;
		height = r.height;
		angle = r.angle;
	}
	
	//x, y are the center of the rectangle
	public Rectangle(float midX, float midY, float width, float height, float angle) {
		x = midX;
		y = midY;
		this.width = width;
		this.height = height;
		this.angle = angle;
	}
	
	public Rectangle(Dot centre, float width, float height, float angle) {
		x = centre.getX();
		y = centre.getY();
		this.width = width;
		this.height = height;
		this.angle = angle;
	}
	
	public static Rectangle fromCorner(float xCorner, float yCorner, float width, float height, float angle) {
		float xRect = (float) (xCorner + (width/2)* Math.cos(angle)+ (height/2)* Math.sin(angle));
		float yRect = (float) (yCorner + (height/2)* Math.cos(angle)- (height/2)* Math.sin(angle));
		return new Rectangle(xRect, yRect, width, height, angle);
	}
	
	public List<Dot> getCorners(){
		float c1x = (float) (x - (width/2)* Math.cos(angle) + (height/2)* Math.sin(angle));		// rotations fonctionnelles !
		float c1y = (float) (y - (height/2)* Math.cos(angle) - (width/2)* Math.sin(angle));
		
		float c2x = (float) (x - (width/2)* Math.cos(angle) - (height/2)* Math.sin(angle));
		float c2y = (float) (y + (height/2)* Math.cos(angle) - (width/2)* Math.sin(angle));
		
		float c3x = (float) (x + (width/2)* Math.cos(angle) + (height/2)* Math.sin(angle));
		float c3y = (float) (y - (height/2)* Math.cos(angle) + (width/2)* Math.sin(angle));
		
		float c4x = (float) (x + (width/2)* Math.cos(angle) - (height/2)* Math.sin(angle));
		float c4y = (float) (y + (height/2)* Math.cos(angle) + (width/2)* Math.sin(angle));
		
		ArrayList<Dot> res = new ArrayList();
		res.add(new Dot(c1x, c1y));
		res.add(new Dot(c2x, c2y));
		res.add(new Dot(c3x, c3y));
		res.add(new Dot(c4x, c4y));
		return res;
	}
	public void setPos(Dot d) {
		x = d.getX();
		y = d.getY();
	}
	
	public float getAngle() {
		return angle;
	}
	
	public void setAngle(float newAngle) {
		angle = newAngle;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public Dot getPos() {
		return new Dot(x, y);
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
		return new Rectangle(x, y, width+addToDimensions, height+addToDimensions);
	}
	
	public void resize(float newWidth, float newHeight) {
		width = newWidth;
		height = newHeight;
	}
	
	// return the nearest dot from the pos included in this rectangle
	public Dot nearestFromPos(Dot pos) {
		Dot unRotPos = pos.rotateAround(getPos(), -angle);
		
		int xSign = (unRotPos.getX() > x)? 1 : -1;
		int ySign = (unRotPos.getY() > y)? 1 : -1;
		
		float Xres = x + Math.min(Math.abs(x - unRotPos.getX()), width/2)*xSign;
		float Yres = y + Math.min(Math.abs(y - unRotPos.getY()), height/2)*ySign;
		return new Dot(Xres, Yres).rotateAround(getPos(), angle);
	}
	
	public boolean includes(Dot pos) {
		Dot rotPos = pos.rotateAround(getPos(), -angle);
		if (x - width/2 <= rotPos.getX() && rotPos.getX() <= x + width/2
		&& y - height/2 <= rotPos.getY() && rotPos.getY() <= y + height/2) {
			return true;
		}
		else return false;
	}
	
	//return the max sized rectangle contained in the current one with angle and w/h ratio specified
	public Rectangle surrounded(float angle, float ratio) {
		float deltaAngle = this.angle - angle;
		double maxSizeWithX = width / (Math.abs(ratio*Math.cos(deltaAngle)) + Math.abs(Math.sin(deltaAngle)));
		double maxSizeWithY = height / (Math.abs(Math.cos(deltaAngle)) + Math.abs(ratio*Math.sin(deltaAngle)));	//compute rect sizes
		float size = (float) (Math.min(maxSizeWithX, maxSizeWithY));
		return new Rectangle(x, y, size*ratio, size, angle);
	}
	
	//return a horizontal rectangle containing the current rectangle
	public Rectangle surround() {
		double pi = Math.PI;
		double flatX = Math.abs(width*Math.cos(angle)) + Math.abs(height*Math.sin(angle));
		double flatY = Math.abs(height*Math.cos(+angle)) + Math.abs(width*Math.sin(angle));
		return new Rectangle(x, y, (float)(flatX), (float)(flatY), 0);
	}
	
	public void changeAngle(float angle) {
		this.angle = angle;
	}
	
	public void render() {
		Renderer.drawQuad(this, Color.transparentWhite);
	}
}
