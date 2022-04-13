package fr.ovrckdlike.ppp.tiles;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.objects.Item;
import fr.ovrckdlike.ppp.objects.Player;
import fr.ovrckdlike.ppp.physics.Time;


public class GasCooker extends ContainerTile{
//	private CookerContainer onGas;
	
	
	public GasCooker(float[] pos, CookerContainer onGas) {
		this.type = 3;
		this.pos = pos;
		this.content = onGas;
		this.content.setPos(this.pos[0]+size/2, this.pos[1]+size/2);
		this.content.setMode(1);
	}
	
	
	public void cook() {
		long dt = Time.get().getDt();
		((CookerContainer) content).cook(dt); //TODO changer, ici use ne dois pas faire ca
	}
	public void use(Player player) {}
	
	
	
	public void render() {
		Renderer.drawTexture(this.pos[0], this.pos[1], this.size, this.size, 0, Texture.gasCooker);
	}
}
