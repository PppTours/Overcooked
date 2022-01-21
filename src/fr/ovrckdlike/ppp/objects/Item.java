package fr.ovrckdlike.ppp.objects;

public abstract class Item {
	protected float pos[] = new float[2];
	protected int size = 40;
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
}
