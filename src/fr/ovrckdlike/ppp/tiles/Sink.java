package fr.ovrckdlike.ppp.tiles;

import java.util.ArrayList;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.gui.TimeBar;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.objects.Plate;
import fr.ovrckdlike.ppp.objects.Player;
import fr.ovrckdlike.ppp.physics.Time;

public class Sink extends Tile {
	private int nbPlate;
	private int washTime;
	private int direction;
	private Dryer attachedDryer;
	private ArrayList<Plate> plates;
	private float currentWashTime;
	private TimeBar tb;
	
	
	public Sink(float[] pos, int direction) {
		
		this.type = 4;
		this.washTime = 3;
		this.pos = pos;
		this.nbPlate = 0;
		this.attachedDryer = null;
		this.direction = direction;
		this.currentWashTime = 0;
		float[] timeBarPos = new float[2];
		timeBarPos[0] = this.pos[0]+35; timeBarPos[1] = this.pos[1]+108;
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
		Renderer.drawTexture(this.pos[0], this.pos[1], this.size, this.size, (float)(Math.PI*direction/2), Texture.sink);
		tb.render(currentWashTime);
	}

}
