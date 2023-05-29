package fr.ovrckdlike.ppp.objects;

import java.util.List;

import fr.ovrckdlike.ppp.physics.Circle;
import fr.ovrckdlike.ppp.physics.Dot;
import fr.ovrckdlike.ppp.tiles.Tile;

public abstract class Item extends Entity{
	
	protected boolean inPlayerHand;
	protected int mode;
	protected float angle;
	
	public Item(Dot pos) {
		space = new Circle(pos, 25);
		inPlayerHand = false;
	}
	
	public abstract void render();
	public abstract void prepare();
	
	public boolean isOnGround() {
		return (mode == 0 && !inPlayerHand);
	}
	
	public void setMode(int mode) {
		this.mode = mode;
	}
	
	public void changeAngle(float newAngle) {
		angle = newAngle;
	}
	
	public void changeAngle(int direction) {
		angle = (float)(-direction*Math.PI/4f);
	}
	
	public void setInPlayerHand(boolean set) {
		this.inPlayerHand = set;
	}
	
	public boolean isOnTable() {
		if (mode == 0) return false;
		else return true;
	}
	
	public boolean getInPlayerHand() {
		return this.inPlayerHand;
	}
	
	public void setPos(Dot newPos) {
		space.setPos(newPos);
	}
	public Dot getPos() {
		return space.getPos();
	}
	
	public abstract void flush();
	
	public float distanceTo(Dot pos) {
		return space.getPos().distanceTo(pos);
	}
	
	public double angleTo(Dot pos) {
		return space.getPos().angleTo(pos);
	}
	
	public <T extends Entity> void collideEntity(List<T> objList) {
		if (!inPlayerHand && mode == 0) {
			for (Entity obj:objList) {
				if (obj != this) {
					float move = -space.getPos().distanceTo(obj.space.getPos()) + (space.getRay() + obj.space.getRay());
					if (move > 0) space.collide(obj.space, move, true);	
				}
			}
		}
		
	}
	
	public void collideTile(List<Tile> tileList) {
		if (!inPlayerHand && mode == 0) {
			for (Tile tile:tileList) {	
				float move = -getPos().distanceTo(tile.getSpace().nearestFromPos(getPos())) + space.getRay();
				if (move > 0) space.collide(tile.getSpace(), move);
			}
		}
	}
}
