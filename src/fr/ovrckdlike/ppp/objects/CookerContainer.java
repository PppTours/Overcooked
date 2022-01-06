package fr.ovrckdlike.ppp.objects;

public abstract class CookerContainer extends Item {
	protected int cookingTime;
	protected float currentCookingTime;
	protected boolean cooked;
	protected boolean burnt;
	
	public void prepare() {};
	public abstract void render();
	
	public void cook(long dt) {
		float s_dt = dt/1E9f;
		currentCookingTime += s_dt;
		if (currentCookingTime > cookingTime){
			cooked = true;
		}
		if (currentCookingTime > 2*cookingTime) {
			burnt = true;
		}
	}
}
