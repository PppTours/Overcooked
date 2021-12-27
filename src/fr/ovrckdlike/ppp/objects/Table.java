package fr.ovrckdlike.ppp.objects;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;

public class Table extends Tile {
	private boolean content[] = new boolean[5]; // on ajustera la taille en fonction, voir txt pour + info
	
	
	public Table(float[] pos) {
		this.pos = pos;
		this.content = null;
		this.type = 1;
	}
	
	public void render() {
		Renderer.drawTexture(pos[0], pos[1], size[0], size[1], Texture.table);
	}
	
	public boolean[] use(boolean newContent[]) {
		boolean oldContent[] = this.content;
		this.content = newContent;
		return oldContent;
	}
	
}
