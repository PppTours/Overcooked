package fr.ovrckdlike.ppp.objects;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;

public class Table extends Tile {
	private Item content;
	
	
	public Table(float[] pos) {
		this.pos = pos;
		this.content = null;
		this.type = 1;
	}
	
	public void render() {
		Renderer.drawTexture(pos[0], pos[1], size, size, 0, Texture.table);
	}
	
	public Item use(Item newContent) {	// a changer 
		Item oldContent = this.content;
		this.content = newContent;
		if (this.content != null) {
			this.content.setMode(1);
			this.content.setPos(this.pos[0]+20, this.pos[1]+20);
		}
		return oldContent;
	}
	
}
