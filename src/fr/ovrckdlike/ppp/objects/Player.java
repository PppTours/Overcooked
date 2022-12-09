package fr.ovrckdlike.ppp.objects;

import java.util.List;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_0;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_CONTROL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_TAB;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

import java.util.ArrayList;

import fr.ovrckdlike.ppp.graphics.Color;
import fr.ovrckdlike.ppp.graphics.KeyListener;
import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.physics.Time;
import fr.ovrckdlike.ppp.tiles.Tile;


public class Player {
	private byte id;
	private float pos[] = new float[2];
	private int size;
	private int direction;
	private int moveSpeed;
	private boolean blocked;
	private Item inHand;
	private float lastMove;
	private float dashTime;
	private boolean dashIsReady;
	
	private boolean up;
	private boolean down;
	private boolean left;
	private boolean right;
	private boolean dash;
	private boolean pickDrop;
	private boolean interact;
	private long lastPickDrop = 0;
	private long lastInteract = 0;
	
	
	public Player(float[] pos, byte id) {
		this.id = id;
		this.pos = pos;
		this.blocked = false;
		this.size = 100;
		this.direction = 0;
		this.moveSpeed = 300;
		this.lastMove = 0;
		this.dashIsReady = true;
		this.inHand = null;
	}
	
	public void updateKeyPressed() {
		switch (id) {
		case 1 :
			up = KeyListener.isKeyPressed(GLFW_KEY_W);
			down = KeyListener.isKeyPressed(GLFW_KEY_S);
			left = KeyListener.isKeyPressed(GLFW_KEY_A);
			right = KeyListener.isKeyPressed(GLFW_KEY_D);
			pickDrop = KeyListener.isKeyPressed(GLFW_KEY_TAB);
			interact = KeyListener.isKeyPressed(GLFW_KEY_F);
			dash = KeyListener.isKeyPressed(GLFW_KEY_SPACE);
			break;
		case 2 :
			up = KeyListener.isKeyPressed(GLFW_KEY_UP);
			down = KeyListener.isKeyPressed(GLFW_KEY_DOWN);
			left = KeyListener.isKeyPressed(GLFW_KEY_LEFT);
			right = KeyListener.isKeyPressed(GLFW_KEY_RIGHT);
			pickDrop = KeyListener.isKeyPressed(GLFW_KEY_RIGHT_CONTROL);
			interact = KeyListener.isKeyPressed(GLFW_KEY_KP_0);
			dash = KeyListener.isKeyPressed(GLFW_KEY_ENTER);
			break;
		default :
			return;
		}
		
		if (up || down || left || right) {
			changeAngle(up, down, left, right);
			movePlayer(Time.get().getDt());
			if (dash) dash(Time.get().getDt());
			else releaseDash(Time.get().getDt());
		}
	}
	
	public void resetLastPickDrop() {
		lastPickDrop = Time.get().getCurrentTime();
	}
	
	public void resetLastInteract() {
		lastInteract = Time.get().getCurrentTime();
	}
	
	public long getLastInteract() {
		return lastInteract;
	}
	
	public long getLastPickDrop() {
		return lastPickDrop;
	}
	
	public boolean getPickDrop() {
		return pickDrop;
	}
	
	public boolean getInteract() {
		return interact;
	}
	
	public int getSize() {
		return size;
	}
	public void setPos(float[] newPos) {
		pos[0] = newPos[0];
		pos[1] = newPos[1];
	}
	
	public float getLastMove() {
		return lastMove;
	}
	
	public Item getInHand() {
		return this.inHand;
	}
	
	public void setInHand(Item item) {
		this.inHand = item;
	}
	
	public void releaseDash(long dt) {
		if (dashTime <= 0) dashIsReady = true;
		dashTimer(dt);
	}
	
	public void dash(long dt) {
		if (dashIsReady) {
			dashIsReady = false;
			dashTime = .1f;
		}
		dashTimer(dt);
	}
	
	public void lockMove() {
		blocked = true;
	}
	public void unlockMove() {
		blocked = false;
	}
	
	private void dashTimer(long dt) {
		if (dashTime > 0) {
			float s_dt = dt/1E9f;
			dashTime -= s_dt;
		}
	}
	
	public int nearestItem(List<Item> itemList) {
		if (itemList.size() == 0) return -1;
		else {
			int nearest = -1;
			float mindist = Float.MAX_VALUE;
			int i = 0;
			for(Item item:itemList) {
				if (item.getInPlayerHand()) {
					i++;
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
	
	public double angleTo(float[] pos) {
		float deltaX = this.pos[0] - pos[0];
		float deltaY = this.pos[1] - pos[1];
		
		if (deltaX == 0 && deltaY == 0) {
			return 0;
		}
		double angle;
		if (deltaY < 0) angle = 0;
		else angle = Math.PI;
		angle +=  (Math.atan(deltaX/deltaY) - Math.PI/2);
		if (angle < 0) angle += 2 * Math.PI;
		return angle;
		
	}
	
	public int directionTo(float[] pos) {
		double angle = this.angleTo(pos);
		
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
	
	public boolean takeNearestItem(List<Item> itemList) {
		int itemId = this.nearestItem(itemList);
		if (itemId == -1) return false;
		Item item = itemList.get(itemId);
		if (distanceTo(item.getPos()) > size*1.05) return false;
		else {
			if (this.inHand != null) return false;
			else {
				this.inHand = item;
				item.setInPlayerHand(true);
				return true;
			}
		}
	}
	
	public float[] whereToDrop() {
		double angle = getDirectionAngle();
		int distance = 75;
		float[] dropPos = new float[2];
		dropPos[0] = pos[0] + (float) (distance * Math.cos(angle));
		dropPos[1] = pos[1] - (float) (distance * Math.sin(angle));
		return dropPos;
	}
	public void take(Item item) {
		if (inHand == null && item != null) {
			inHand = item;
			inHand.setInPlayerHand(true);
		}
	}
	
	public void drop() {
		if (inHand != null) {
			inHand.setInPlayerHand(false);
			inHand = null;
		}
	}
	
	public void render() {
		float renderpos[] = {this.pos[0]-this.size/2, this.pos[1]-this.size/2};
		Renderer.drawTexture(renderpos[0]-10, renderpos[1]-10, this.size+20, this.size+20, (float)(this.direction*Math.PI/4), Texture.CatSkin);
		if (this.inHand != null) {
			switch (direction){
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
	
	public void setPos(float x, float y) {
		pos[0] = x;
		pos[1] = y;
	}
	
	public int getDirection() {
		return this.direction;
	}
	
	public double getDirectionAngle() {
		double angle;
		switch (direction){
			case 0:
				angle = Math.PI/2;
				break;
			case 1:
				angle = Math.PI/4;
				break;
			case 2:
				angle = 0;
				break;
			case 3:
				angle = 7*Math.PI/4;
				break;
			case 4:
				angle = 3*Math.PI/2;
				break;
			case 5:
				angle = 5*Math.PI/4;
				break;
			case 6:
				angle = Math.PI;
				break;
			case 7:
				angle = 3*Math.PI/4;
				break;
			default :
				angle = 0;
				break;
		}
		return angle;
	}
	
	public void movePlayer( long dt) {
		if (!blocked) {
			float s_dt = (float) (dt / 1E9);
			float dist = moveSpeed * s_dt;
			if (dashTime > 0) dist *= 7;
			float distX, distY;
			double angle = getDirectionAngle();
			
			distX = (float)(dist * Math.cos(angle));
			distY = (float)(-dist * Math.sin(angle));
			
			pos[0] += distX;
			pos[1] += distY;
			lastMove = (float) (Math.sqrt(distX * distX + distY * distY));
		}
	}
	
	public void collide(List<Tile> tileList, List<Player> playerList) {
		double angle;
		if (dashTime <= 0f) {
			for (Player player:playerList) {
				if (player != this && player.dashTime <= 0) {
					float movement = size - distanceTo(player.pos);
					if (movement > 0) {
						angle = angleTo(player.pos);
						this.pos[0] += (-(movement) * Math.cos(angle))/2;
						this.pos[1] += ((movement) * Math.sin(angle))/2;
						player.pos[0] += ((movement) * Math.cos(angle))/2;
						player.pos[1] += (-(movement) * Math.sin(angle))/2;
					}
				}
			}
		}
		
		for (Tile tile:tileList) {
			float toMoveBack = size/2 - distanceTo(tile.nearestFromPos(pos));
			if (toMoveBack > 0) {
				angle = angleTo(tile.nearestFromPos(pos));
				pos[0] += (-(toMoveBack) * Math.cos(angle))/2;
				pos[1] += ((toMoveBack) * Math.sin(angle))/2;
			}
			if (tile.isInTile(pos)) {

				float[] tileCenter = new float[2];
				tileCenter[0] = tile.getPos()[0] + tile.getSize() / 2;
				tileCenter[1] = tile.getPos()[1] + tile.getSize() / 2;

				angle = angleTo(tileCenter);
				pos[0] += (-(15) * Math.cos(angle));
				pos[1] += ((15) * Math.sin(angle));
			}
		}
	}
	
	public void changeAngle(boolean up, boolean down, boolean left, boolean right) {
		if (!blocked) {
			if (up && right) direction = 1;
			else if (up && left) direction = 7;
			else if (down && left) direction = 5;
			else if (down && right) direction = 3;
			else if (up) direction = 0;
			else if (down) direction = 4;
			else if (left) direction = 6;
			else if (right) direction = 2;
		}
	}
}
