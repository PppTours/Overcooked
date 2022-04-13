package fr.ovrckdlike.ppp.objects;

import java.util.List;
import fr.ovrckdlike.ppp.tiles.Tile;

public abstract class Item {
	protected float pos[] = new float[2];
	protected int size = 50;
	protected boolean inPlayerHand = false;
	protected int mode;
	protected int direction;
	
	public abstract void render();
	public abstract void prepare();
	
	public void setMode(int mode) {
		this.mode = mode;
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
	
	public void setPos(float x, float y) {
		this.pos[0] = x;
		this.pos[1] = y;
	}
	public float[] getPos() {
		return this.pos;
	}
	
	public abstract void flush();
	
	public float distanceTo(float[] pos) {
		float deltaX = this.pos[0] - pos[0];
		float deltaY = this.pos[1] - pos[1];
		
		float dist = (float)(Math.sqrt(deltaX * deltaX + deltaY * deltaY));
		return dist;
	}
	
	public double angleTo(float[] pos) {
		float deltaX = this.pos[0] - pos[0];
		float deltaY = this.pos[1] - pos[1];
		
		if (deltaX == 0 && deltaY == 0) return 0;
		
		double angle;
		if (deltaY < 0) angle = 0;
		else angle = Math.PI;
		
		
		angle +=  (Math.atan(deltaX/deltaY) - Math.PI/2);
		if (angle < 0) angle += 2 * Math.PI;
		return angle;
	}
	
	public void collide(List<Player> playerList, List<Item>itemList, List<Tile>tileList) {
		if (isOnTable()) return;
		
		float[] itemPos = new float[2];
		itemPos[0] = pos[0];
		itemPos[1] = pos[1];
		
		for(Player player:playerList) {
			if (player.getInHand() != this) {
				float toMove = (player.getSize() + size)/2 - player.distanceTo(pos);
				if (toMove > 0) {
					double angle = angleTo(player.getPos());
					this.pos[0] += (-toMove) * Math.cos(angle)*3/4;
					this.pos[1] += (toMove) * Math.sin(angle)*3/4;
					float[] playerPos = {(float)(player.getPos()[0] + toMove * Math.cos(angle)/4),
					(float)(player.getPos()[1] - toMove * Math.sin(angle)/4)};
					player.setPos(playerPos);
					
				}
			}
			
		}
		
		for (Item item:itemList) {
			if (this != item && !inPlayerHand && !item.inPlayerHand && !item.isOnTable()) {
				float toMove = size - distanceTo(item.pos);
				if (toMove > 0) {
					double angle = angleTo(item.pos);
					this.pos[0] += (-(toMove) * Math.cos(angle))/2;
					this.pos[1] += ((toMove) * Math.sin(angle))/2;
					item.pos[0] += ((toMove) * Math.cos(angle))/2;
					item.pos[1] += (-(toMove) * Math.sin(angle))/2;
				}
			}
		}
		for (Tile tile:tileList) {
			float toMove = size/2 - distanceTo(tile.nearestFromPos(pos));
			if (toMove > 0) {
				double angle = angleTo(tile.nearestFromPos(pos));
				pos[0] += (-(toMove) * Math.cos(angle))/2;
				pos[1] += ((toMove) * Math.sin(angle))/2;
			}
			if (tile.isInTile(pos)) {

				float[] tileCenter = new float[2];
				tileCenter[0] = tile.getPos()[0] + tile.getSize() / 2;
				tileCenter[1] = tile.getPos()[1] + tile.getSize() / 2;

				double angle = angleTo(tileCenter);
				pos[0] += (-(15) * Math.cos(angle));
				pos[1] += ((15) * Math.sin(angle));
			}
		}
	}
}
