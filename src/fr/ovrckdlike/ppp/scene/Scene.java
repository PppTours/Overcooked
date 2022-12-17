package fr.ovrckdlike.ppp.scene;

public abstract class Scene {

	public abstract void render();
	public abstract void run();
	
	public void execute() {
		run();
		render();
	}
}
