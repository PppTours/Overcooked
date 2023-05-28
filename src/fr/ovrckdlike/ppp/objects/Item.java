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
	
	public void setMode(int mode) {
		this.mode = mode;
	}
	
	public void changeAngle(float newAngle) {
		angle = newAngle;
	}
	
	public void changeAngle(int direction) {
		angle = (float)(direction*Math.PI/4f);
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
	
	/*
	@Deprecated
	public void collide(List<Player> playerList, List<Item>itemList, List<Tile>tileList) {
		if (isOnTable()) return;
		
		float size = 2 * space.getRay();
		Dot pos = space.getPos();
		
		for(Player player:playerList) {
			if (player.getInHand() != this) {
				float toMove = (player.getSize() + size)/2 - player.distanceTo(pos);
				if (toMove > 0) {
					float angle = angleTo(player.getPos());
					pos.addToThis((float)((-toMove) * Math.cos(angle)*3/4),(float)((toMove) * Math.sin(angle)*3/4));
					float[] playerPos = {(float)(player.getPos()[0] + toMove * Math.cos(angle)/4),
					(float)(player.getPos()[1] - toMove * Math.sin(angle)/4)};
					player.setPos(playerPos);
					
				}
			}
			
		}
		
		for (Item item:itemList) {
			if (this != item && !inPlayerHand && !item.inPlayerHand && !item.isOnTable()) {
				float toMove = size - distanceTo(item.getPos());
				if (toMove > 0) {
					double angle = angleTo(item.getPos());
					pos.addToThis((float)((-(toMove) * Math.cos(angle))/2), (float)(((toMove) * Math.sin(angle))/2));
					item.getPos().addToThis((float)(((toMove) * Math.cos(angle))/2), (float)((-(toMove) * Math.sin(angle))/2));
				}
			}
		}
		for (Tile tile:tileList) {
			float toMove = size/2 - distanceTo(tile.nearestFromPos(pos));
			if (toMove > 0) {
				double angle = angleTo(tile.nearestFromPos(pos));
				pos.addToThis((float)((-(toMove) * Math.cos(angle))/2), (float)(((toMove) * Math.sin(angle))/2));
			}
			if (tile.isInTile(pos)) {

				float[] tileCenter = new float[2];
				tileCenter[0] = tile.getPos()[0] + tile.getSize() / 2;
				tileCenter[1] = tile.getPos()[1] + tile.getSize() / 2;

				double angle = angleTo(tileCenter);
				pos.addToThis((float)((-(15) * Math.cos(angle))/2), (float)(((15) * Math.sin(angle))/2));
			}
		}
	}*/
}
