package fr.ovrckdlike.ppp.objects;

import fr.ovrckdlike.ppp.graphics.KeyListener;

import static org.lwjgl.glfw.GLFW.*;

public class Player {
	private float pos[];
	private int angle;
	private int moveSpeed;
	
public Player(float[] pos) {
		this.pos = pos;
		this.angle = 0;
		this.moveSpeed = 200;
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
