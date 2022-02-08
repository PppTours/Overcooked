package fr.ovrckdlike.ppp.tiles;

public abstract class Tile {
	protected int size = 120;
	protected float pos[] = new float[2];	// position en px
	protected int type;
	
	public float[] getPos() {
		return pos;
	}
	
	public int getSize() {
		return size;
	}
	
	public abstract void render();
	//public abstract void use();
	
	public float[] nearestFromPos(float[] pos) {
		if (pos[0] > this.pos[0] && pos[0] < this.pos[0]+this.size) {
			if (pos[1] < this.pos[1]) {
				float[] newPos = {pos[0], this.pos[1]};
				return newPos;
			}
			else { 
				float[] newPos = {pos[0], this.pos[1]+this.size};
				return newPos;
			}
		}
		else if (pos[1] > this.pos[1] && pos[1] < this.pos[1]+this.size) {
			if (pos[0] < this.pos[0]) {
				float[] newPos = {this.pos[0], pos[1]};
				return newPos;
			}
			else { 
				float[] newPos = {this.pos[0]+this.size, pos[1]};
				return newPos;
			}
		}
		else {
			if (pos[0] < this.pos[0]) {
				if (pos[1] < this.pos[1]) {
					float[] newPos = {this.pos[0], this.pos[1]};
					return newPos;
				}
				else {
					float[] newPos = {this.pos[0], this.pos[1]+this.size};
					return newPos;
				}
			}
			else {
				if (pos[1] < this.pos[1]) {
				float[] newPos = {this.pos[0]+this.size, this.pos[1]};
				return newPos;
				}
				else {
					float[] newPos = {this.pos[0]+this.size, this.pos[1]+this.size};
					return newPos;
				}
			}
		}
	}
}
