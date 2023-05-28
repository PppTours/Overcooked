package fr.ovrckdlike.ppp.tiles;

import java.util.ArrayList;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.gui.TimeBar;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.objects.Plate;
import fr.ovrckdlike.ppp.objects.Player;
import fr.ovrckdlike.ppp.physics.Dot;
import fr.ovrckdlike.ppp.physics.Rectangle;
import fr.ovrckdlike.ppp.physics.Time;

public class Sink extends Tile {
	private int nbPlate;
	private int washTime;
	private int direction;
	private Dryer attachedDryer;
	private ArrayList<Plate> plates;
	private float currentWashTime;
	private TimeBar tb;
	
	
	public Sink(Dot pos, int direction) {
		
		this.type = 4;
		this.washTime = 3;
		this.space = new Rectangle(pos, size, size, (float)(direction*(-Math.PI/2)));
		this.nbPlate = 0;
		this.attachedDryer = null;
		this.direction = direction;
		this.currentWashTime = 0;
		Dot timeBarPos = new Dot(pos.getX()-25f, pos.getY()+48f);
		this.tb = new TimeBar(timeBarPos, washTime);
	}
	
	public void addPlate() {
		nbPlate++;
	}
	
	public void setAttachedDryer(Dryer dryer) {
		attachedDryer = dryer;
	}
	
	public int getDirection() {
		return direction;
	}
	
	public void drop(Plate plate) {
		if (plate.getDirty() && plate.isEmpty()) {
			plates.add(plate);
			nbPlate++;
		}
	}
	
	public void use(Player player) {
		long dt = Time.get().getDt();
		float s_dt = dt/1E9f;
		if (nbPlate > 0) {
			player.lockMove();
			currentWashTime += s_dt;
			
			if (currentWashTime >= washTime) {
				nbPlate--;
				attachedDryer.addPlate();
				currentWashTime = 0;
				if (nbPlate == 0) player.unlockMove();
			}
		}
	}
	
	
	public void render() {
		Renderer.drawTexture(space, Texture.sink);
		tb.render(currentWashTime);
	}

}
