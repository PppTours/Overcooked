package fr.ovrckdlike.ppp.tiles;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.objects.Item;



public class CuttingTable extends Tile{
	private Item content;
	private int cuttingTime;
	private float currentCuttingTime;
	
	
	public CuttingTable(float[] pos) {
		this.pos = pos;
		this.content = null;
		this.type = 2;
		this.cuttingTime = 2;
	}
	
	public void render() {
		Renderer.drawTexture(pos[0], pos[1], size, size, 0, Texture.cuttingTable);
	}
	
	public Item dropOrTake(Item newContent) {	// a changer 
		Item oldContent = this.content;
		this.content = newContent;
		this.currentCuttingTime = 0f;
		if (this.content != null) {
			this.content.setMode(1);
			this.content.setPos(this.pos[0]+20, this.pos[1]+20);
		}
		if (oldContent != null) {
			oldContent.setMode(0);
		}
		return oldContent;
	}
	
	public void use(long dt) {
		
		float s_dt = dt/1E9f;
		this.currentCuttingTime += s_dt;
		if (this.currentCuttingTime >= this.cuttingTime) {
			this.content.prepare();
		}
	}
}
