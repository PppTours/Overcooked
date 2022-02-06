package fr.ovrckdlike.ppp.objects;

import java.util.List;

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
	
	public float distanceTo(float[] pos) {
		float deltaX = this.pos[0] - pos[0];
		float deltaY = this.pos[1] - pos[1];
		
		float dist = (float)(Math.sqrt(deltaX * deltaX + deltaY * deltaY));
		return dist;
	}
	
	public double angleTo(float[] pos) {
		float deltaX = this.pos[0] - pos[0];
		float deltaY = this.pos[1] - pos[1];
		
		double angle;
		if (deltaY < 0) angle = 0;
		else angle = Math.PI;
		
		angle +=  (Math.atan(deltaX/deltaY) - Math.PI/2);
		if (angle < 0) angle += 2 * Math.PI;
		return angle;
	}
	
	public void collide(List<Player> playerList, List<Item>itemList) {
		float[] itemPos = new float[2];
		itemPos[0] = pos[0];
		itemPos[1] = pos[1];
		
		for(Player player:playerList) {
			if (player.distanceTo(this.pos) < (player.getSize()+this.size)/2) {
				double angle = player.angleTo(this.pos);
				this.pos[0] += (player.getLastMove()) * Math.cos(angle);
				this.pos[1] += (-player.getLastMove()) * Math.sin(angle);
			}
		}
		
		for (Item item:itemList) {
			if (this != item && !inPlayerHand && !item.inPlayerHand) {
				if (distanceTo(item.pos) < size) {
					float toMove = size - distanceTo(item.pos);
					double angle = angleTo(item.pos);
					this.pos[0] += (-(toMove) * Math.cos(angle))/2;
					this.pos[1] += ((toMove) * Math.sin(angle))/2;
					item.pos[0] += ((toMove) * Math.cos(angle))/2;
					item.pos[1] += (-(toMove) * Math.sin(angle))/2;
				}
			}
		}
	}
}
