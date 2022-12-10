package fr.ovrckdlike.ppp.tiles;

import fr.ovrckdlike.ppp.gui.TimeBar;
import fr.ovrckdlike.ppp.objects.Item;

public abstract class CookerContainer extends Item {
	protected final int cookingTime = 10;
	protected float currentCookingTime;
	protected boolean cooked;
	protected boolean burnt;
	protected TimeBar timebar;
	
	public void prepare() {}
	public abstract void render();
	
	public abstract void flush();
	public abstract boolean isFilled();
	
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
