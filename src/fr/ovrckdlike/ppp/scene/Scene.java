package fr.ovrckdlike.ppp.scene;

import fr.ovrckdlike.ppp.physics.Time;

public abstract class Scene {
	private float changeCooldown;
	

	public abstract void render();
	public abstract void run();
	
	
	public void clockCooldown() {
		if (changeCooldown > 0) changeCooldown -= Time.get().getDtS();
	}
	
	public boolean noCooldown() {
		return (changeCooldown <= 0);
	}
	
	public void setCooldown(float time) {
		changeCooldown = time;
	}
	
	public void execute() {
		run();
		render();
	}
}
