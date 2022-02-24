package fr.ovrckdlike.ppp.tiles;

import java.util.List;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.objects.Item;
import fr.ovrckdlike.ppp.objects.Plate;

public class Table extends ContainerTile {
	
	
	public Table(float[] pos, boolean withPlate, List<Item> itemList) {
		this.pos = pos;
		if (withPlate) {
			float[] platePos = new float[2];
			platePos[0] = this.pos[0] + size/2;
			platePos[1] = this.pos[1] + size/2;
			content = new Plate(platePos, false);
			
		}
		else content = null;
		this.type = 1;
	}
	
	public void render() {
		Renderer.drawTexture(pos[0], pos[1], size, size, 0, Texture.table);
	}
	
}
