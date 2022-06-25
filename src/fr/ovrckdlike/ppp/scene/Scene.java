package fr.ovrckdlike.ppp.scene;

public abstract class Scene {
	protected int id;
	protected boolean running;
	
	
	public abstract void render();
	public abstract void run();
}
