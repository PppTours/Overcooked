package fr.ovrckdlike.ppp.objects;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;

public class Bin extends Tile{
	
	public Bin(float[] pos) {
		this.type = 6;
		this.pos = pos;
	}
	
	public void use(Plate plate) {
		plate.flush();
		
	}
	
	//TODO rajouter un use pour les cooker container
	
	public void render() {
		Renderer.drawTexture(pos[0], pos[1], size, size, 0, Texture.bin);
	}
}
