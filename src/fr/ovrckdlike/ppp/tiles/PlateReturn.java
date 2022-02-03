package fr.ovrckdlike.ppp.tiles;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.objects.Plate;

public class PlateReturn extends Tile{
	int plateNb;
	
	public PlateReturn(float[] pos) {
		this.pos = pos;
		this.plateNb = 0;
	}
	
	
	public void addPlate() {
		plateNb++;
	}
	
	
	public Plate takePlate() {
		if (plateNb > 0) {
			plateNb--;
			float[] platePos = {this.pos[0]+20, this.pos[1]+20};
			Plate newPlate = new Plate(platePos, true);
			return newPlate;
		}
		else return null;
	}
	
	
	public void render() {
		Renderer.drawTexture(this.pos[0], this.pos[1], this.size, this.size, 0, Texture.plateReturn);
	}
}
