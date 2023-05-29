package fr.ovrckdlike.ppp.objects;

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

import java.util.List;

import fr.ovrckdlike.ppp.graphics.KeyListener;
import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.physics.Circle;
import fr.ovrckdlike.ppp.physics.Dot;
import fr.ovrckdlike.ppp.physics.Time;
import fr.ovrckdlike.ppp.tiles.Sink;
import fr.ovrckdlike.ppp.tiles.Tile;


public class Player extends Entity{
	private Texture skin;
	private byte id;
	private int direction;
	private int moveSpeed;
	private boolean blocked;
	private Item inHand;
	private float lastMove;
	private float dashTime;
	private boolean dashIsReady;
	
	// controls
	private boolean up;
	private boolean down;
	private boolean left;
	private boolean right;
	private boolean dash;
	private boolean pickDrop;
	private boolean interact;
	private long lastPickDrop = 0;
	private long lastInteract = 0;
	
	
	public Player(Dot pos, byte id, Texture skin) {
		this.skin = skin;
		this.id = id;
		space = new Circle(pos, 50);
		blocked = false;
		direction = 0;
		moveSpeed = 300;
		lastMove = 0;
		dashIsReady = true;
		inHand = null;
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
	
	public float getSize() {
		return space.getRay() * 2f;
	}
	public void setPos(Dot newPos) {
		space.setPos(newPos);
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
					Dot itemPos = item.getPos();
					float dist = distanceTo(itemPos);
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
	
	public float distanceTo(Dot pos) {
		return space.getPos().distanceTo(pos);
	}
	
	public double angleTo(Dot pos) {
		return space.getPos().angleTo(pos);
		
	}
	
	public int directionTo(Dot pos) {
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
		if (distanceTo(item.getPos()) > space.getRay()*2*1.05) return false;
		else {
			if (this.inHand != null) return false;
			else {
				this.inHand = item;
				item.setInPlayerHand(true);
				return true;
			}
		}
	}
	
	public Dot whereToDrop() {
		double angle = getDirectionAngle();
		int distance = 75;
		Dot dropPos = new Dot(space.getPos().getX() + (float) (distance * Math.cos(angle)),
							  space.getPos().getY() - (float) (distance * Math.sin(angle)));
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
		Renderer.drawTexture(space.resized(60).surroundBySquare((float)((4-direction)*Math.PI/4)), skin);
		if (this.inHand != null) {
			Dot pos = space.getPos();
			float ray = space.getRay();
			Dot inHandPos;
			switch (direction){
			case 0:
				inHandPos = new Dot(pos.getX(), pos.getY() - ray);
				break;
			case 1:
				inHandPos = new Dot( (float)(pos.getX() + (Math.sqrt(2)*ray)/2),
									(float)(pos.getY() - (Math.sqrt(2)*ray)/2));
				break;
			case 2:
				inHandPos = new Dot(pos.getX() + ray, pos.getY());
				break;
			case 3:
				inHandPos = new Dot( (float)(pos.getX() + (Math.sqrt(2)*ray)/2),
									(float)(pos.getY() + (Math.sqrt(2)*ray)/2));
				break;
			case 4:
				inHandPos = new Dot(pos.getX(), pos.getY() + ray);
				break;
			case 5:
				inHandPos = new Dot( (float)(pos.getX() - (Math.sqrt(2)*ray)/2),
									(float)(pos.getY() + (Math.sqrt(2)*ray)/2));
				break;
			case 6:
				inHandPos = new Dot(pos.getX() - ray, pos.getY());
				break;
			case 7:
				inHandPos = new Dot( (float)(pos.getX() - (Math.sqrt(2)*ray)/2),
									(float)(pos.getY() - (Math.sqrt(2)*ray)/2));
				break;
			default :
				inHandPos = new Dot(0f,0f);
				break;
			}
			inHand.setPos(inHandPos);
			inHand.changeAngle(direction);
			inHand.render();
		}
	}
	
	public Dot getPos(){
		return space.getPos();
	}
	
	public void setPos(float x, float y) {
		space.setPos(new Dot(x, y));
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
	
	public void movePlayer(long dt) {
		if (!blocked) {
			float s_dt = (float) (dt / 1E9);
			float dist = moveSpeed * s_dt;
			if (dashTime > 0) dist *= 7;
			float distX, distY;
			double angle = getDirectionAngle();
			
			distX = (float)(dist * Math.cos(angle));
			distY = (float)(-dist * Math.sin(angle));
			
			space.getPos().addToThis(distX, distY);
			lastMove = (float) (Math.sqrt(distX * distX + distY * distY));
		}
	}
	
	public void collidePlayer(List<Player> playerList) {
		if (dashTime <= 0f) {
			for (Player p:playerList) {
				if (p != this && p.dashTime <= 0f) {
					float move = space.getRay() + p.space.getRay() - distanceTo(p.space.getPos());
					if (move > 0) 
						space.collide(p.space, move, true);
				}
				
			}
		}
	}
	
	public <T extends Entity> void collideEntity(List<T> objList) {
		if (dashTime <= 0f) {
			for (Entity obj:objList) {
				float move = space.getRay() + obj.space.getRay() - distanceTo(obj.space.getPos());
				if (move > 0) 
					space.collide(obj.space, move, true);
			}
		}
	}
	
	public void collideTile(List<Tile> tileList) {
		for (Tile tile:tileList) {
			float move = -getPos().distanceTo(tile.getSpace().nearestFromPos(getPos())) + space.getRay();
			if (move > 0)
				space.collide(tile.getSpace(), move);
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