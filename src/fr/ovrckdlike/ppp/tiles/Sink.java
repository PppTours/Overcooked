package fr.ovrckdlike.ppp.tiles;

import java.util.ArrayList;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.objects.Plate;

public class Sink extends Tile {
	private int nbPlate;
	private int washTime;
	private int direction;
	private ArrayList<Plate> plates;
	private float currentWashTime;
	
	
	public Sink(float[] pos, int direction) {
		this.type = 4;
		this.pos = pos;
		this.nbPlate = 0;
		this.direction = direction;
		this.currentWashTime = 0;
		this.plates = new ArrayList<Plate>();
	}
	
	public void drop(Plate plate) {
		if (plate.getDirty() && plate.isEmpty()) {
			plates.add(plate);
			nbPlate++;
		}
	}
	
	public void use(long dt, Dryer dryer) {
		float s_dt = dt/1E9f;
		if (nbPlate > 0) {
			currentWashTime += s_dt;
			
			if (currentWashTime >= washTime) {
				nbPlate--;
				plates.get(nbPlate).wash(); 	//transférer l'assiette au séchoir
				dryer.add(plates.remove(nbPlate));
				currentWashTime = 0;
			}
		}
		
	}
	
	
	public void render() {
		Renderer.drawTexture(this.pos[0], this.pos[1], this.size, this.size, (float)(Math.PI*direction/2), Texture.sink);
	}

}
