package fr.ovrckdlike.ppp.tiles;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.objects.Item;

public class Table extends ContainerTile {
	
	
	public Table(float[] pos) {
		this.pos = pos;
		this.content = null;
		this.type = 1;
	}
	
	public void render() {
		Renderer.drawTexture(pos[0], pos[1], size, size, 0, Texture.table);
	}
	
}
