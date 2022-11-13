package fr.ovrckdlike.ppp.tiles;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.gui.TimeBar;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.objects.Ingredient;
import fr.ovrckdlike.ppp.objects.Item;
import fr.ovrckdlike.ppp.objects.Player;
import fr.ovrckdlike.ppp.physics.Time;



public class CuttingTable extends Tile implements ContainerTile{	// reinitialiser currentCuttingTime au changement de contenu
	private int cuttingTime;
	private Item content;
	private float currentCuttingTime;
	private TimeBar timeBar;
	private float[] timeBarPos = new float[2];
	
	public Item getContent() {
		return content;
	}
	
	public Item takeOrDrop(Item newContent) {
		currentCuttingTime = 0f;
		Item oldContent = this.content;
		this.content = newContent;
		if (this.content != null) {
			this.content.setMode(1);
			this.content.setPos(this.pos[0]+size/2, this.pos[1]+size/2);
		}
		if (oldContent != null) {
			oldContent.setMode(0);
		}
		return oldContent;
	}
	
	
	public CuttingTable(float[] pos) {
		
		this.pos = pos;
		this.content = null;
		this.type = 2;
		this.currentCuttingTime = 0;
		this.cuttingTime = 2;
		this.timeBarPos[0] = pos[0]+35f; 
		this.timeBarPos[1] = pos[1]+108f;
		this.timeBar = new TimeBar(timeBarPos, cuttingTime);
	}
	
	public void render() {
		Renderer.drawTexture(pos[0], pos[1], size, size, 0, Texture.cuttingTable);
		timeBar.render(currentCuttingTime, timeBarPos);
	}
	
	public void use(Player player) {
		if (content instanceof Ingredient) {
			if (!((Ingredient) content).getPrepared()) {
				player.lockMove();
				long dt = Time.get().getDt();
				float s_dt = dt/1E9f;
				currentCuttingTime += s_dt;
				if (currentCuttingTime >= cuttingTime) {
					currentCuttingTime = 0;
					player.unlockMove();
					content.prepare();
				}
			}
		}
	}
}
