package fr.ovrckdlike.ppp.objects;

public abstract class Tile {
	protected int size = 120;
	protected float pos[] = new float[2];	// position en px
	protected int type;
	
	public abstract void render();
	//public abstract void use();
	
}
