package fr.ovrckdlike.ppp.objects;

import java.util.ArrayList;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;


public class Player {
	private float pos[] = new float[2];
	private int size;
	private int angle;
	private int moveSpeed;
	private Item inHand;
	
	public Player(float[] pos) {
		this.pos = pos;
		this.size = 70;
		this.angle = 0;
		this.moveSpeed = 300;
		this.inHand = null;
	}
	
	public Item getInHand() {
		return this.inHand;
	}
	
	public int nearestItem(ArrayList<Item> itemList) {
		if (itemList.size() == 0) return -1;
		else {
			int nearest = 0;
			float mindist = Float.MAX_VALUE;
			int i = 0;
			for(Item item:itemList) {
				if (item.getInPlayerHand()) {
					continue;
				}
				else {
					float itemPos[] = item.getPos();
					float dist = this.distanceTo(itemPos);
					if (dist < mindist) {
						nearest = i;
						mindist = dist;
					}
					i++;
				}
				
			}
			return nearest;
		}
	}
	
	public float distanceTo(float[] pos) {
		float deltaX = this.pos[0] - pos[0];
		float deltaY = this.pos[1] - pos[1];
		
		float dist = (float)(Math.sqrt(deltaX * deltaX + deltaY * deltaY));
		return dist;
	}
	
	public int directionTo(float[] pos) {
		float deltaX = this.pos[0] - pos[0];
		float deltaY = this.pos[1] - pos[1];
		
		double angle;
		if (deltaY < 0) angle = 0;
		else angle = Math.PI;
		
		angle +=  (Math.PI - Math.atan(deltaX/deltaY));
		angle = angle % (2 * Math.PI);
		if (angle <= Math.PI/8 || angle > 15 * Math.PI/8) return 0;
		if (angle > Math.PI/8 && angle <= 3 * Math.PI/8) return 1;
		if (angle > 3 * Math.PI/8 && angle <= 5 * Math.PI/8) return 2;
		if (angle > 5 * Math.PI/8 && angle <= 7 * Math.PI/8) return 3;
		if (angle > 7 * Math.PI/8 && angle <= 9 * Math.PI/8) return 4;
		if (angle > 9 * Math.PI/8 && angle <= 11 * Math.PI/8) return 5;
		if (angle > 11 * Math.PI/8 && angle <= 13 * Math.PI/8) return 6;
		if (angle > 13 * Math.PI/8 && angle <= 15 * Math.PI/8) return 7;
		return -1;
	}
	
	public boolean takeNearestItem(ArrayList<Item> itemList) {
		int itemId = this.nearestItem(itemList);
		if (itemId == -1) return false;
		Item item = itemList.get(itemId);
		if (distanceTo(item.getPos()) > 50) return false;
		else {
			if (this.inHand != null) return false;
			else {
				this.inHand = item;
				item.setInPlayerHand(true);
				item.setMode(0);
				return true;
			}
		}
	}
	
	public void drop() {
		if (inHand != null) {
			inHand.setMode(1);
			inHand.setInPlayerHand(false);
			inHand = null;
		}
	}
	
	public void render() {
		float renderpos[] = {this.pos[0]-this.size/2, this.pos[1]-this.size/2};
		Renderer.drawTexture(renderpos[0], renderpos[1], this.size, this.size, 0, Texture.smiley);
		if (this.inHand != null) {
			switch (angle){
			case 0:
				this.inHand.setPos(this.pos[0], this.pos[1] - this.size/2);
				break;
			case 1:
				this.inHand.setPos( (float)(this.pos[0] + (Math.sqrt(2)*this.size/2)/2),
									(float)(this.pos[1] - (Math.sqrt(2)*this.size/2)/2));
				break;
			case 2:
				this.inHand.setPos(this.pos[0] + this.size/2, this.pos[1]);
				break;
			case 3:
				this.inHand.setPos( (float)(this.pos[0] + (Math.sqrt(2)*this.size/2)/2),
									(float)(this.pos[1] + (Math.sqrt(2)*this.size/2)/2));
				break;
			case 4:
				this.inHand.setPos(this.pos[0], this.pos[1] + this.size/2);
				break;
			case 5:
				this.inHand.setPos( (float)(this.pos[0] - (Math.sqrt(2)*this.size/2)/2),
									(float)(this.pos[1] + (Math.sqrt(2)*this.size/2)/2));
				break;
			case 6:
				this.inHand.setPos(this.pos[0] - this.size/2, this.pos[1]);
				break;
			case 7:
				this.inHand.setPos( (float)(this.pos[0] - (Math.sqrt(2)*this.size/2)/2),
									(float)(this.pos[1] - (Math.sqrt(2)*this.size/2)/2));
				break;
			}
			this.inHand.render();
		}
	}
	
	
	public float[] getPos(){
		return pos;
	}
	
	public int getAngle() {
		return this.angle;
	}
	
	
	public void movePlayer( long dt) {
		float s_dt = (float) (dt / 1E9);
		float dist = moveSpeed * s_dt;
		switch (angle){
		case 0:
			pos[1] -= dist;
			break;
		case 1:
			pos[1] -= ((Math.sqrt(2)/2) * dist);
			pos[0] += ((Math.sqrt(2)/2) * dist);
			break;
		case 2:
			pos[0] += dist;
			break;
		case 3:
			pos[1] += ((Math.sqrt(2)/2) * dist);
			pos[0] += ((Math.sqrt(2)/2) * dist);
			break;
		case 4:
			pos[1] += dist;
			break;
		case 5:
			pos[1] += ((Math.sqrt(2)/2) * dist);
			pos[0] -= ((Math.sqrt(2)/2) * dist);
			break;
		case 6:
			pos[0] -= dist;
			break;
		case 7:
			pos[1] -= ((Math.sqrt(2)/2) * dist);
			pos[0] -= ((Math.sqrt(2)/2) * dist);
			break;
		}
	}
	public void changeAngle(boolean up, boolean down, boolean left, boolean right) {

		if ( up && right ) angle = 1;
		else if ( up && left ) angle = 7;
		else if ( down && left ) angle = 5;
		else if ( down && right ) angle = 3;
		else if (up) angle = 0;
		else if (down) angle = 4;
		else if (left) angle = 6;
		else if (right) angle = 2;
	}
}
