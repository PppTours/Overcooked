package fr.ovrckdlike.ppp.objects;

import java.util.ArrayList;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;

public class Dryer extends Tile{
	private int nbPlate;
	private int direction;
	private ArrayList<Plate> plates;
	
	public Dryer(float[] pos, int direction) {
		this.pos = pos;
		this.type = 5;
		this.nbPlate = 0;
		this.direction = direction;
		this.plates = new ArrayList<Plate>();
	}
	
	
	public void add(Plate plate) {
		nbPlate++;
		plates.add(plate);
	}
	
	public Plate take() {
		if (nbPlate > 0) {
			return plates.remove(nbPlate-1);
		}
		else return null;
	}
	
	
	public void render() {
		Renderer.drawTexture(this.pos[0], this.pos[1], this.size, this.size, (float)(Math.PI*this.direction/2), Texture.dryer);
		
	}
}
