package fr.ovrckdlike.ppp.objects;

import java.util.List;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.graphics.Renderer;


public class ServiceTable extends Tile {
	private int direction;
	
	
	public ServiceTable(float[] pos, int dir) {
		this.type = 7;
		this.pos = pos;
		this.direction = dir;
	}
	
	public int use(Plate plate, List<Item> itemList, PlateReturn pr) {
		pr.addPlate();
		int score = 0;	//TODO compter les points et déréférencer l'assiette.
		itemList.remove(plate);
		return score;
	}
	
	
	public void render() {
		Renderer.drawTexture(this.pos[0], this.pos[1], this.size, this.size, (float)(this.direction*(Math.PI/2)), Texture.serviceTable);
	}

}
