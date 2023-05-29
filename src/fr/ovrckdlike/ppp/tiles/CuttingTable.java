package fr.ovrckdlike.ppp.tiles;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.gui.TimeBar;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.objects.Ingredient;
import fr.ovrckdlike.ppp.objects.Item;
import fr.ovrckdlike.ppp.objects.Player;
import fr.ovrckdlike.ppp.physics.Dot;
import fr.ovrckdlike.ppp.physics.Rectangle;
import fr.ovrckdlike.ppp.physics.Time;



public class CuttingTable extends Tile implements ContainerTile{	// reinitialiser currentCuttingTime au changement de contenu
	private int cuttingTime;
	private Item content;
	private float currentCuttingTime;
	private TimeBar timeBar;
	private Dot timeBarPos;
	
	public Item getContent() {
		return content;
	}
	
	public Item takeOrDrop(Item newContent) {
		currentCuttingTime = 0f;
		Item oldContent = this.content;
		this.content = newContent;
		if (this.content != null) {
			this.content.setMode(1);
			this.content.setPos(space.getPos());
		}
		if (oldContent != null) {
			oldContent.setMode(0);
		}
		return oldContent;
	}
	
	
	public CuttingTable(Dot pos) {
		
		this.space = new Rectangle(pos, size, size, 0f) ;
		this.content = null;
		this.type = 2;
		this.currentCuttingTime = 0;
		this.cuttingTime = 2;
		this.timeBarPos = new Dot(pos.getX(), pos.getY() + 35f); 
		this.timeBar = new TimeBar(timeBarPos, cuttingTime);
	}
	
	public void render() {
		Renderer.drawTexture(space, Texture.cuttingTable);
		if (content != null) content.render();
		timeBar.render(currentCuttingTime);
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
