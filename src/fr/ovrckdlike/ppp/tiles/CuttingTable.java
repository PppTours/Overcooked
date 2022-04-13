package fr.ovrckdlike.ppp.tiles;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.objects.Item;
import fr.ovrckdlike.ppp.objects.Player;
import fr.ovrckdlike.ppp.physics.Time;



public class CuttingTable extends ContainerTile{
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
	
	
	public void use(Player player) {
		long dt = Time.get().getDt();
		float s_dt = dt/1E9f;
		this.currentCuttingTime += s_dt;
		if (this.currentCuttingTime >= this.cuttingTime) {
			this.content.prepare();
		}
	}
}
